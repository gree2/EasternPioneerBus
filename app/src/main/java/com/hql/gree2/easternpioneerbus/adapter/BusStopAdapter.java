package com.hql.gree2.easternpioneerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.hql.gree2.easternpioneerbus.R;
import com.hql.gree2.easternpioneerbus.model.BusStop;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;


public class BusStopAdapter extends RealmBaseAdapter<BusStop> implements ListAdapter {

    private static class ViewHolder {
        TextView stopName;
    }

    public BusStopAdapter(Context context, RealmResults<BusStop> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_bus_stop_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.stopName = (TextView) convertView.findViewById(R.id.stop_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BusStop busStop = realmResults.get(position);
        viewHolder.stopName.setText(busStop.getStopIndex() + " " + busStop.getStopName());
        return convertView;
    }

    public RealmResults<BusStop> getRealmResults() {
        return realmResults;
    }

}
