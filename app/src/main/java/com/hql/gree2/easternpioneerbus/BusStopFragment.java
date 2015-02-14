package com.hql.gree2.easternpioneerbus;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hql.gree2.easternpioneerbus.adapter.BusStopAdapter;
import com.hql.gree2.easternpioneerbus.model.BusStopItem;

import java.util.ArrayList;

public class BusStopFragment extends Fragment implements AdapterView.OnItemClickListener {

    private BusStopAdapter adapter;

    private ArrayList<BusStopItem> items;

    private ListView stopsList;

    public BusStopFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bus_stop, container, false);

        items = getArguments().getParcelableArrayList("BusStops");
        adapter = new BusStopAdapter(getActivity(), items);

        stopsList = (ListView) rootView.findViewById(R.id.stops_list);
        stopsList.setAdapter(adapter);
        stopsList.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity().getApplicationContext(),
                BusStopDetailActivity.class);
        intent.putExtra("BusStop", (Parcelable) adapter.getItem(position));
        startActivity(intent);
    }
}
