package com.hql.gree2.easternpioneerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hql.gree2.easternpioneerbus.R;
import com.hql.gree2.easternpioneerbus.model.BusStopItem;

import java.util.ArrayList;
import java.util.List;

public class BusStopAdapter extends BaseAdapter {

    private Context context;

    private List<BusStopItem> items = new ArrayList<>();

    public BusStopAdapter(Context context, ArrayList<BusStopItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_bus_stop_item, parent, false);
        }

        TextView stopName = (TextView) convertView.findViewById(R.id.stop_name);
        stopName.setText(items.get(position).getStopIndex() + "-" + items.get(position).getStopName());

        return convertView;
    }
}
