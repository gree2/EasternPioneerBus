package com.hql.gree2.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class daogen {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.hql.gree2.easternpioneerbus.dao");
        addBus(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java/");
    }

    private static void addBus(Schema schema){
        // entity busline
        Entity busLine = schema.addEntity("BusLine");
        busLine.addIdProperty();
        busLine.addIntProperty("lineIndex");
        busLine.addStringProperty("lineName");
        busLine.addStringProperty("lineCode");
        busLine.addBooleanProperty("lineSync");

        // entity busstop
        Entity busStop = schema.addEntity("BusStop");
        busStop.addIdProperty();
        Property busLineId = busStop.addLongProperty("busLineId").notNull().getProperty();
        busStop.addToOne(busLine, busLineId);
        busStop.addIntProperty("stopIndex");
        busStop.addStringProperty("stopName");
        busStop.addStringProperty("stopDesc");
        busStop.addStringProperty("class07");
        busStop.addStringProperty("class09");
        busStop.addStringProperty("class13");
        busStop.addStringProperty("class17");
        // relation busline's busstop
        ToMany busLineToBusStop = busLine.addToMany(busStop, busLineId);
        busLineToBusStop.setName("busStops");

        // entity busstopimage
        Entity busStopImage = schema.addEntity("BusStopImage");
        busStopImage.addIdProperty();
        Property busStopId = busStopImage.addLongProperty("busStopId").notNull().getProperty();
        busStopImage.addToOne(busStop, busStopId);
        busStopImage.addIntProperty("imageIndex");
        busStopImage.addStringProperty("imageName");
        // relation busstop's busstopimage
        ToMany busStopToBusStopImage = busStop.addToMany(busStopImage, busStopId);
        busStopToBusStopImage.setName("busStopImages");
    }

}
