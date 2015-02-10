package com.hql.gree2.easternpioneerbus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BusStopItem implements Parcelable {
    //序号,线路序号,线路名,车站序号,车站名,位置说明,07:00上课,09:00上课,13:00上课,17:00上课
    private int index;
    private int lineIndex;
    private String lineName;
    private int stopIndex;
    private String stopName;
    private String stopDesc;
    private String class07;
    private String class09;
    private String class13;
    private String class17;
    private ArrayList<String> images;


    public BusStopItem(String[] row) {
        this.index = Integer.parseInt(row[0]);
        this.lineIndex = Integer.parseInt(row[1]);
        this.lineName = row[2];
        this.stopIndex = Integer.parseInt(row[3]);
        this.stopName = row[4];
        this.stopDesc = row[5];
        this.class07 = row[6];
        this.class09 = row[7];
        this.class13 = row[8];
        this.class17 = row[9];
        this.images = new ArrayList<>();
        if (11 == row.length) {
            for (String image : row[10].split(";")) {
                this.images.add(image);
            }
        }
    }

    public static final Parcelable.Creator<BusStopItem> CREATOR
            = new Parcelable.Creator<BusStopItem>() {
        public BusStopItem createFromParcel(Parcel in) {
            return new BusStopItem(in);
        }

        public BusStopItem[] newArray(int size) {
            return new BusStopItem[size];
        }
    };

    public BusStopItem(Parcel in) {
        setIndex(in.readInt());
        setLineIndex(in.readInt());
        setLineName(in.readString());
        setStopIndex(in.readInt());
        setStopName(in.readString());
        setStopDesc(in.readString());
        setClass07(in.readString());
        setClass09(in.readString());
        setClass13(in.readString());
        setClass17(in.readString());
        ArrayList<String> list = new ArrayList<>();
        in.readList(list, ArrayList.class.getClassLoader());
        setImages(list);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getIndex());
        dest.writeInt(getLineIndex());
        dest.writeString(getLineName());
        dest.writeInt(getStopIndex());
        dest.writeString(getStopName());
        dest.writeString(getStopDesc());
        dest.writeString(getClass07());
        dest.writeString(getClass09());
        dest.writeString(getClass13());
        dest.writeString(getClass17());
        dest.writeList(getImages());
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
