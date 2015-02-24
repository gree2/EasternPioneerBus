package com.hql.gree2.easternpioneerbus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.hql.gree2.easternpioneerbus.adapter.SettingsSyncAdapter;
import com.hql.gree2.easternpioneerbus.dao.BusLine;
import com.hql.gree2.easternpioneerbus.manager.DatabaseManager;
import com.hql.gree2.easternpioneerbus.manager.IDatabaseManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


public class SettingsSyncActivity extends Activity {

    private ListView list;
    private SettingsSyncAdapter adapter;
    private List<BusLine> busLines;
    private IDatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // animation
        overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_hold);
        setContentView(R.layout.activity_settings_sync);

        // init database manager
        databaseManager = new DatabaseManager(this);

        busLines = new ArrayList<>();
        list = (ListView) findViewById(R.id.listView);
        //
        refreshBusLines();
    }

    private void refreshBusLines() {
        busLines = databaseManager.listBusLines();
        if (adapter == null) {
            adapter = new SettingsSyncAdapter(this, busLines);
            list.setAdapter(adapter);
        } else {
            list.setAdapter(null);
            adapter.clear();
            adapter.addAll(busLines);
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
        }
    }

    @Override
    protected void onRestart() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager(this);
        }
        super.onRestart();
    }

    @Override
    protected void onResume() {
        // init database manager
        databaseManager = DatabaseManager.getInstance(this);
        super.onResume();
        // umeng
        MobclickAgent.onPageStart("SettingsSyncActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStop() {
        if (databaseManager != null) {
            databaseManager.closeDbConnections();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        // animation
        overridePendingTransition(R.anim.anim_slide_hold, R.anim.anim_slide_out);
        super.onPause();
        // umeng
        MobclickAgent.onPageEnd("SettingsSyncActivity");
        MobclickAgent.onPause(this);
    }
}
