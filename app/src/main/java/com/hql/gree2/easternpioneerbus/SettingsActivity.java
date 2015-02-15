package com.hql.gree2.easternpioneerbus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hql.gree2.easternpioneerbus.app.AppController;


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
        holder.layoutSync = (RelativeLayout) findViewById(R.id.layout_sync);
        holder.layoutCache = (RelativeLayout) findViewById(R.id.layout_clear);

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
                DoClearCache();
            }
        });

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

}
