package com.hql.gree2.easternpioneerbus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hql.gree2.easternpioneerbus.adapter.NavDrawerListAdapter;
import com.hql.gree2.easternpioneerbus.dao.BusLine;
import com.hql.gree2.easternpioneerbus.manager.DatabaseManager;
import com.hql.gree2.easternpioneerbus.manager.IDatabaseManager;
import com.hql.gree2.easternpioneerbus.model.NavDrawerItem;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    //private BusStopUtil util;

    private IDatabaseManager databaseManager;

    private List<BusLine> busLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // umeng
        MobclickAgent.updateOnlineConfig(this);
        // regist test device
        Log.d("regist test device", getDeviceInfo(this));

        // findview
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        // init database & busline
        databaseManager = new DatabaseManager(this);
        PopulateBusLines();

        mTitle = mDrawerTitle = getTitle();
        navDrawerItems = new ArrayList<>();

        for (BusLine busLine : busLines) {
            navDrawerItems.add(new NavDrawerItem(busLine.getLineName()));
        }

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.menu, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    private void PopulateBusLines() {
        busLines = databaseManager.listBusLines();
        if (0 < busLines.size()) {
            return;
        }
        String[] codeNames = getResources().getStringArray(R.array.bus_line_code_name);
        for (int i = 0; i < codeNames.length; i++) {
            String[] split = codeNames[i].split(",");
            BusLine busLine = new BusLine();
            busLine.setLineCode(split[0]);
            busLine.setLineName(split[1]);
            busLine.setLineIndex(i + 1);
            busLine.setLineSync(false);
            databaseManager.insertBusLine(busLine);
        }
        busLines = databaseManager.listBusLines();
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(),
                        SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Displaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        if (0 <= position && position < busLines.size()) {
            Bundle bundle = new Bundle();
            bundle.putLong("BusLineId", busLines.get(position).getId());
            fragment = new BusStopFragment();
            fragment.setArguments(bundle);
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(busLines.get(position).getLineName());
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // umeng
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        if (databaseManager != null) {
            databaseManager.closeDbConnections();
        }
        super.onStop();
    }

    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
