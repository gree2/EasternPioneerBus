package com.hql.gree2.easternpioneerbus.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.hql.gree2.easternpioneerbus.R;
import com.hql.gree2.easternpioneerbus.app.AppController;
import com.hql.gree2.easternpioneerbus.dao.BusLine;
import com.hql.gree2.easternpioneerbus.dao.BusStop;
import com.hql.gree2.easternpioneerbus.dao.BusStopImage;
import com.hql.gree2.easternpioneerbus.manager.DatabaseManager;
import com.hql.gree2.easternpioneerbus.volley.util.Const;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsSyncAdapter extends ArrayAdapter<BusLine> {

    private String TAG = SettingsSyncAdapter.class.getSimpleName();
    private final Activity context;
    private final List<BusLine> busLines;

    public SettingsSyncAdapter(Activity context, List<BusLine> busLines) {
        super(context, R.layout.activity_settings_sysc_item, busLines);
        this.busLines = busLines;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.activity_settings_sysc_item, null);
            // configure view holder
            SyncViewHolder viewHolder = new SyncViewHolder();

            if (rowView != null) {
                viewHolder.busLine = (TextView) rowView.findViewById(R.id.bus_line_text);
                viewHolder.sync = (ImageView) rowView.findViewById(R.id.bus_line_sync);
                rowView.setTag(viewHolder);
            }
        }

        // fill data
        if (rowView != null) {
            final SyncViewHolder holder = (SyncViewHolder) rowView.getTag();
            final BusLine busLine = busLines.get(position);
            if (busLine != null) {
                holder.busLine.setText(String.valueOf(busLine.getLineIndex()) + "-" + busLine.getLineName());

                // set sync image
                if (busLine.getLineSync()) {
                    holder.sync.setImageResource(R.drawable.done);
                } else {
                    holder.sync.setImageResource(R.drawable.sync);
                }
                // set sync click listener
                holder.sync.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // busline data has sync
                        if (busLine.getLineSync()) {
                            return;
                        }
                        // rotate relative to self
                        RotateAnimation anim = new RotateAnimation(360.0f, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        // Setup anim with desired properties
                        anim.setInterpolator(new LinearInterpolator());
                        // Repeat animation indefinitely
                        anim.setRepeatCount(Animation.INFINITE);
                        anim.setDuration(700); //Put desired duration per anim cycle here, in milliseconds
                        // Start animation
                        final ImageView download = holder.sync;
                        download.startAnimation(anim);

                        // request data
                        final StringRequest request = new StringRequest(Request.Method.POST, Const.URL_LINE_PAGE_BASE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        new Thread(new JsoupParseBusLineHtmlThread(busLine, response, download)).start();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                                        download.setAnimation(null);
                                        download.setImageResource(R.drawable.sync);
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> mParams = new HashMap<>();
                                mParams.put("state", busLine.getLineName());
                                mParams.put("l", "1");
                                mParams.put("w", busLine.getLineCode());
                                return mParams;
                            }
                        };
                        Log.d(TAG, request.getUrl());
                        // Adding request to request queue
                        AppController.getInstance().addToRequestQueue(request);
                    }
                });
            }
        }

        return rowView;
    }

    @Override
    public long getItemId(int position) {
        BusLine item = getItem(position);
        return item.getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    static class SyncViewHolder {
        public TextView busLine;
        public ImageView sync;
    }

    private class JsoupParseBusLineHtmlThread implements Runnable{

        private String html;

        private BusLine busLine;

        private ImageView sync;

        public JsoupParseBusLineHtmlThread(BusLine busLine, String html, ImageView sync){
            this.busLine = busLine;
            this.html = html;
            this.sync = sync;
        }

        @Override
        public void run() {
            Document doc = Jsoup.parse(html);
            int stopIndex = 1;
            Element table = doc.select("table[style=border-collapse:collapse]").first();
            Element tbody = table.select("tbody").first();
            Elements trs = tbody.select("tr");
            // connect to database
            DatabaseManager databaseManager = new DatabaseManager(getContext());
            // parse data
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                // busstop
                BusStop stop = new BusStop();
                stop.setBusLine(busLine);
                stop.setStopIndex(stopIndex);
                stop.setStopName(tds.get(0).text().trim());
                stop.setStopDesc(tds.get(1).text().trim());
                stop.setClass07(tds.get(2).text().trim());
                stop.setClass09(tds.get(3).text().trim());
                stop.setClass13(tds.get(4).text().trim());
                stop.setClass17(tds.get(5).text().trim());
                // save data
                databaseManager.insertBusStop(stop);
                Element div = tr.select("div").first();
                Elements links = div.select("a");
                int imageIndex = 1;
                for (Element link : links) {
                    // busstop image
                    BusStopImage image = new BusStopImage();
                    image.setBusStop(stop);
                    image.setImageIndex(imageIndex);
                    image.setImageName(link.attr("href"));
                    // save data
                    databaseManager.insertBusStopImage(image);
                    imageIndex += 1;
                }
                stopIndex += 1;
            }
            // update sync status
            busLine.setLineSync(true);
            databaseManager.updateBusLine(busLine);
            // close database connection
            databaseManager.closeDbConnections();
            // stop animation & change image
            sync.post(new Runnable() {
                @Override
                public void run() {
                    sync.setAnimation(null);
                    sync.setImageResource(R.drawable.done);
                }
            });
        }
    }
}
