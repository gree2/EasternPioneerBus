package com.hql.gree2.easternpioneerbus.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table BUS_STOP.
 */
public class BusStopDao extends AbstractDao<BusStop, Long> {

    public static final String TABLENAME = "BUS_STOP";

    /**
     * Properties of entity BusStop.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BusLineId = new Property(1, long.class, "busLineId", false, "BUS_LINE_ID");
        public final static Property StopIndex = new Property(2, Integer.class, "stopIndex", false, "STOP_INDEX");
        public final static Property StopName = new Property(3, String.class, "stopName", false, "STOP_NAME");
        public final static Property StopDesc = new Property(4, String.class, "stopDesc", false, "STOP_DESC");
        public final static Property Class07 = new Property(5, String.class, "class07", false, "CLASS07");
        public final static Property Class09 = new Property(6, String.class, "class09", false, "CLASS09");
        public final static Property Class13 = new Property(7, String.class, "class13", false, "CLASS13");
        public final static Property Class17 = new Property(8, String.class, "class17", false, "CLASS17");
    }

    ;

    private DaoSession daoSession;

    private Query<BusStop> busLine_BusStopsQuery;

    public BusStopDao(DaoConfig config) {
        super(config);
    }

    public BusStopDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'BUS_STOP' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'BUS_LINE_ID' INTEGER NOT NULL ," + // 1: busLineId
                "'STOP_INDEX' INTEGER," + // 2: stopIndex
                "'STOP_NAME' TEXT," + // 3: stopName
                "'STOP_DESC' TEXT," + // 4: stopDesc
                "'CLASS07' TEXT," + // 5: class07
                "'CLASS09' TEXT," + // 6: class09
                "'CLASS13' TEXT," + // 7: class13
                "'CLASS17' TEXT);"); // 8: class17
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BUS_STOP'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, BusStop entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getBusLineId());

        Integer stopIndex = entity.getStopIndex();
        if (stopIndex != null) {
            stmt.bindLong(3, stopIndex);
        }

        String stopName = entity.getStopName();
        if (stopName != null) {
            stmt.bindString(4, stopName);
        }

        String stopDesc = entity.getStopDesc();
        if (stopDesc != null) {
            stmt.bindString(5, stopDesc);
        }

        String class07 = entity.getClass07();
        if (class07 != null) {
            stmt.bindString(6, class07);
        }

        String class09 = entity.getClass09();
        if (class09 != null) {
            stmt.bindString(7, class09);
        }

        String class13 = entity.getClass13();
        if (class13 != null) {
            stmt.bindString(8, class13);
        }

        String class17 = entity.getClass17();
        if (class17 != null) {
            stmt.bindString(9, class17);
        }
    }

    @Override
    protected void attachEntity(BusStop entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public BusStop readEntity(Cursor cursor, int offset) {
        BusStop entity = new BusStop( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getLong(offset + 1), // busLineId
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // stopIndex
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // stopName
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // stopDesc
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // class07
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // class09
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // class13
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // class17
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, BusStop entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBusLineId(cursor.getLong(offset + 1));
        entity.setStopIndex(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setStopName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStopDesc(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setClass07(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setClass09(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setClass13(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setClass17(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(BusStop entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(BusStop entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    /**
     * Internal query to resolve the "busStops" to-many relationship of BusLine.
     */
    public List<BusStop> _queryBusLine_BusStops(long busLineId) {
        synchronized (this) {
            if (busLine_BusStopsQuery == null) {
                QueryBuilder<BusStop> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.BusLineId.eq(null));
                busLine_BusStopsQuery = queryBuilder.build();
            }
        }
        Query<BusStop> query = busLine_BusStopsQuery.forCurrentThread();
        query.setParameter(0, busLineId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getBusLineDao().getAllColumns());
            builder.append(" FROM BUS_STOP T");
            builder.append(" LEFT JOIN BUS_LINE T0 ON T.'BUS_LINE_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected BusStop loadCurrentDeep(Cursor cursor, boolean lock) {
        BusStop entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        BusLine busLine = loadCurrentOther(daoSession.getBusLineDao(), cursor, offset);
        if (busLine != null) {
            entity.setBusLine(busLine);
        }

        return entity;
    }

    public BusStop loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();

        String[] keyArray = new String[]{key.toString()};
        Cursor cursor = db.rawQuery(sql, keyArray);

        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }

    /**
     * Reads all available rows from the given cursor and returns a list of new ImageTO objects.
     */
    public List<BusStop> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<BusStop> list = new ArrayList<BusStop>(count);

        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }

    protected List<BusStop> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     */
    public List<BusStop> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }

}