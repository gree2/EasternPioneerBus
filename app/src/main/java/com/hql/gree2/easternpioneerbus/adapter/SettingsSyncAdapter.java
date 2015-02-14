package com.hql.gree2.easternpioneerbus.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hql.gree2.easternpioneerbus.R;
import com.hql.gree2.easternpioneerbus.dao.BusLine;

import java.util.List;

public class SettingsSyncAdapter extends ArrayAdapter<BusLine> {
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
                viewHolder.download = (ImageView) rowView.findViewById(R.id.bus_line_download);

                rowView.setTag(viewHolder);
            }
        }

        // fill data
        if (rowView != null) {
            final SyncViewHolder holder = (SyncViewHolder) rowView.getTag();
            final BusLine busLine = busLines.get(position);
            if (busLine != null) {
                holder.busLine.setText(String.valueOf(busLine.getLineIndex()) + "" + busLine.getLineName());
                //holder.download.setText(busLine.getEmail());
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
        public ImageView download;
    }
}
