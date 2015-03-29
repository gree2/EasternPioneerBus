package com.hql.gree2.easternpioneerbus.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hqlgree2 on 3/29/15.
 */
public class Config extends RealmObject {

    @PrimaryKey
    private String key = "";
    private String value = "";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
