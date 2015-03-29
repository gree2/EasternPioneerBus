package com.hql.gree2.easternpioneerbus.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hqlgree2 on 3/29/15.
 */
public class BusLine extends RealmObject {

    @PrimaryKey
    private long id;
    private int lineIndex = 0;
    private String lineName = "";
    private String lineCode = "";

    private RealmList<BusStop> busStops;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public RealmList<BusStop> getBusStops() {
        return busStops;
    }

    public void setBusStops(RealmList<BusStop> busStops) {
        this.busStops = busStops;
    }
}
