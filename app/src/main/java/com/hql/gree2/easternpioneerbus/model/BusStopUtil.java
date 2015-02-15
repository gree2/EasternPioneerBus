package com.hql.gree2.easternpioneerbus.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BusStopUtil {
    InputStream inputStream;
    ArrayList<BusStopItem> busList;

    public BusStopUtil(InputStream inputStream) {
        this.inputStream = inputStream;
        initShuttleBusList();
    }

    private void initShuttleBusList() {
        busList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
//                if (11 != row.length){
//                    continue;
//                }
                busList.add(new BusStopItem(row));
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
    }

    public ArrayList<BusStopItem> getBusLineInfoByLineIndex(int lineIndex) {
        ArrayList<BusStopItem> lineList = new ArrayList<>();
        for (BusStopItem item : busList) {
            if (item.getLineIndex() == lineIndex) {
                lineList.add(item);
            }
        }
        return lineList;
    }

}
