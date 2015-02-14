package com.hql.gree2.easternpioneerbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SettingsActivity extends Activity {

    static class SettingViewHolder {
        public RelativeLayout layoutSync;
        public RelativeLayout layoutCache;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingViewHolder holder = new SettingViewHolder();
        holder.layoutSync = (RelativeLayout)findViewById(R.id.layout_sync);
        holder.layoutCache = (RelativeLayout)findViewById(R.id.layout_clear);

        holder.layoutSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SettingsSyncActivity.class);
                startActivity(intent);
            }
        });

        holder.layoutCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
