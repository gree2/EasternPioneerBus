package com.hql.gree2.easternpioneerbus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.hql.gree2.easternpioneerbus.app.AppController;
import com.hql.gree2.easternpioneerbus.model.BusLine;
import com.hql.gree2.easternpioneerbus.model.BusStop;
import com.hql.gree2.easternpioneerbus.model.Config;
import com.hql.gree2.easternpioneerbus.volley.util.Const;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;


public class SettingsActivity extends Activity {

    private String TAG = SettingsActivity.class.getSimpleName();
    private SettingViewHolder holder = new SettingViewHolder();

    static class SettingViewHolder {
        public RelativeLayout layoutSync;
        public ImageView sync_image;
        public ImageView sync_status;
        public RelativeLayout layoutCache;
        public RelativeLayout layoutFeedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // umeng
        PushAgent.getInstance(this).onAppStart();

        holder.layoutSync = (RelativeLayout) findViewById(R.id.layout_sync);
        holder.sync_image = (ImageView) findViewById(R.id.sync_image);
        holder.sync_status = (ImageView) findViewById(R.id.sync_status);
        holder.layoutCache = (RelativeLayout) findViewById(R.id.layout_clear);
        holder.layoutFeedback = (RelativeLayout) findViewById(R.id.layout_feedback);

        // set sync image
        final Realm realm = Realm.getInstance(this);
        final Config config = realm.where(Config.class).equalTo("key", "sync").findFirst();
        if (null != config && Boolean.parseBoolean(config.getValue())){
            holder.sync_status.setImageResource(R.drawable.done);
        }
        // sync click
        final ImageView sync_status = holder.sync_status;
        holder.layoutSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read sync status
                if (null == config || !Boolean.parseBoolean(config.getValue())){
                    DoSyncData();
                }
                else{
                    sync_status.setImageResource(R.drawable.done);
                }
            }
        });

        holder.layoutCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoClearCache();
            }
        });

        holder.layoutFeedback.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DoFeedback();
            }
        });

    }

    private void DoSyncData(){

        // rotate relative to self
        RotateAnimation anim = new RotateAnimation(360.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // Setup anim with desired properties
        anim.setInterpolator(new LinearInterpolator());
        // Repeat animation indefinitely
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700); //Put desired duration per anim cycle here, in milliseconds
        // Start animation
        final ImageView sync_status = holder.sync_status;
        sync_status.startAnimation(anim);
        final Context context = this;

        // request data
        final StringRequest request = new StringRequest(Request.Method.POST, Const.URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Thread(new JsoupParseBusLineHtmlThread(response, sync_status, context)).start();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        sync_status.setAnimation(null);
                        sync_status.setImageResource(R.drawable.sync);
                    }
                }) {
        };
        Log.d(TAG, request.getUrl());
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request);
    }

    private void DoFeedback() {
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.startFeedbackActivity();
    }

    private void DoClearCache() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.clear_cache_question))
                .setPositiveButton(getString(R.string.clear_cache_yes), onClickClearCacheListener)
                .setNegativeButton(getString(R.string.clear_cache_no), onClickClearCacheListener)
                .show();
    }

    DialogInterface.OnClickListener onClickClearCacheListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    // clear cache
                    AppController.getInstance().getRequestQueue().getCache().clear();
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.clear_cache_toast), Toast.LENGTH_SHORT).show();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    // do nth
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // umeng
        MobclickAgent.onPageStart("SettingsActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // umeng
        MobclickAgent.onPageEnd("SettingsActivity");
        MobclickAgent.onPause(this);
    }

    private class JsoupParseBusLineHtmlThread implements Runnable{

        private String html;
        private ImageView sync_status;
        private Context context;

        public JsoupParseBusLineHtmlThread(String html, ImageView sync_status, Context context){
            this.html = html;
            this.sync_status = sync_status;
            this.context = context;
        }

        @Override
        public void run() {
            Realm realm = Realm.getInstance(context);
            realm.beginTransaction();
            Document doc = Jsoup.parse(html);

            // get bus stops
            Element divRoot = doc.select("div[class=sjz_ejmsgbox]").first();
            Elements tables = divRoot.select("table[class=hel]");
            long busStopCount = 1;
            ArrayList<BusStop> busStops = new ArrayList<>();
            for (Element table: tables){
                if (!table.hasAttr("id")){
                    continue;
                }
                String id = table.attr("id")
                        .replace("line", "");
                Elements trs = table.select("tr");
                int busStopIndex = 0;
                for (Element tr: trs){
                    // jump over table header
                    if (0 == busStopIndex){
                        busStopIndex += 1;
                        continue;
                    }
                    // get stop info
                    Elements tds = tr.select("td");
                    BusStop busStop = realm.createObject(BusStop.class);
                    busStop.setId(busStopCount);
                    busStopCount += 1;
                    busStop.setBusLineId(Long.parseLong(id));
                    busStop.setStopName(tds.get(0).text().trim());
                    busStop.setStopDesc(tds.get(1).text().trim());
                    busStop.setStopIndex(busStopIndex);
                    busStopIndex += 1;
                    busStop.setClass07(tds.get(2).text().trim());
                    busStop.setClass09(tds.get(3).text().trim());
                    busStop.setClass13(tds.get(4).text().trim());
                    busStop.setClass17(tds.get(5).text().trim());
                    // get stop image
                    Element link = tds.get(8).select("a").first();
                    if (null != link && link.hasAttr("href")){
                        busStop.setStopImage(link.attr("href").trim());
                    } else {
                        busStop.setStopImage("");
                    }
                    // add to list
                    busStops.add(busStop);
                }
            }

            // get bus lines
            Element div = divRoot.select("div[class=guide_ser]").first();
            Elements links = div.select("a");
            for (Element link: links){
                if (!link.hasAttr("onclick")){
                    continue;
                }
                String id = link.attr("onclick")
                        .replace("ShowLine(", "")
                        .replace(")", "");
                BusLine busLine = realm.createObject(BusLine.class);
                busLine.setId(Long.parseLong(id));
                busLine.setLineCode(id);
                busLine.setLineIndex(Integer.parseInt(id));
                busLine.setLineName(link.text());
                // get bus line's bus stops
                RealmList<BusStop> lineBusStops = new RealmList<>();
                for (BusStop busStop: busStops){
                    if (busStop.getBusLineId() == busLine.getId()){
                        lineBusStops.add(busStop);
                    }
                }
                busLine.setBusStops(lineBusStops);
            }
            Config config = realm.createObject(Config.class);
            config.setKey("sync");
            config.setValue("true");
            realm.commitTransaction();
            // stop animation
            sync_status.post(new Runnable() {
                @Override
                public void run() {
                    sync_status.setAnimation(null);
                    sync_status.setImageResource(R.drawable.done);
                }
            });
        }
    }

}
