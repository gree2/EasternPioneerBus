package com.hql.gree2.easternpioneerbus.manager;

import com.hql.gree2.easternpioneerbus.dao.BusLine;
import com.hql.gree2.easternpioneerbus.dao.BusStop;
import com.hql.gree2.easternpioneerbus.dao.BusStopImage;

import java.util.List;

public interface IDatabaseManager {

    public void closeDbConnections();

    public void insertBusLine(BusLine busLine);

    public List<BusLine> listBusLines();

    public void updateBusLine(BusLine busLine);

    public void insertBusStop(BusStop busStop);

    public List<BusStop> listBusStop();

    public void updateBusStop(BusStop busStop);

    public void insertBusStopImage(BusStopImage busStopImage);

    public List<BusStopImage> listBusStopImages();

    public void updateBusStopImage(BusStopImage busStopImage);

}
