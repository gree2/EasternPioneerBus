package com.hql.gree2.easternpioneerbus.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hqlgree2 on 3/29/15.
 */
public class BusStop extends RealmObject {

    @PrimaryKey
    private long id = 0;
    private long busLineId = 0;
    private int stopIndex = 0;
    private String stopName = "";
    private String stopDesc = "";
    private String class07 = "";
    private String class09 = "";
    private String class13 = "";
    private String class17 = "";
    private String distance = "";
    private String distanceEta = "";
    private String stopImage = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBusLineId() {
        return busLineId;
    }

    public void setBusLineId(long busLineId) {
        this.busLineId = busLineId;
    }

    public int getStopIndex() {
        return stopIndex;
    }

    public void setStopIndex(int stopIndex) {
        this.stopIndex = stopIndex;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }

    public String getClass07() {
        return class07;
    }

    public void setClass07(String class07) {
        this.class07 = class07;
    }

    public String getClass09() {
        return class09;
    }

    public void setClass09(String class09) {
        this.class09 = class09;
    }

    public String getClass13() {
        return class13;
    }

    public void setClass13(String class13) {
        this.class13 = class13;
    }

    public String getClass17() {
        return class17;
    }

    public void setClass17(String class17) {
        this.class17 = class17;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistanceEta() {
        return distanceEta;
    }

    public void setDistanceEta(String distanceEta) {
        this.distanceEta = distanceEta;
    }

    public String getStopImage() {
        return stopImage;
    }

    public void setStopImage(String stopImage) {
        this.stopImage = stopImage;
    }
}
