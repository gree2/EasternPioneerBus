package com.hql.gree2.easternpioneerbus.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.hql.gree2.easternpioneerbus.dao.BusLine;
import com.hql.gree2.easternpioneerbus.dao.BusLineDao;
import com.hql.gree2.easternpioneerbus.dao.BusStop;
import com.hql.gree2.easternpioneerbus.dao.BusStopDao;
import com.hql.gree2.easternpioneerbus.dao.BusStopImage;
import com.hql.gree2.easternpioneerbus.dao.BusStopImageDao;
import com.hql.gree2.easternpioneerbus.dao.DaoMaster;
import com.hql.gree2.easternpioneerbus.dao.DaoSession;

import java.util.List;


public class DatabaseManager implements IDatabaseManager {


    private static final String TAG = DatabaseManager.class.getCanonicalName();

    private static DatabaseManager instance;

    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     * @param context The Android {@link android.content.Context}.
     */
    public DatabaseManager(final Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, "dfss_bus.db", null);
    }

    /**
     * @param context The Android {@link android.content.Context}.
     * @return this.instance
     */
    public static DatabaseManager getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public synchronized void insertBusLine(BusLine busLine) {
        try {
            if (busLine != null) {
                openWritableDb();
                BusLineDao dao = daoSession.getBusLineDao();
                dao.insert(busLine);
                Log.d(TAG, "Inserted BusLine: " + busLine.getLineName() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BusLine> listBusLines() {
        List<BusLine> list = null;
        try {
            openReadableDb();
            BusLineDao dao = daoSession.getBusLineDao();
            list = dao.loadAll();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateBusLine(BusLine busLine) {
        try {
            if (busLine != null) {
                openWritableDb();
                BusLineDao dao = daoSession.getBusLineDao();
                dao.update(busLine);
                Log.d(TAG, "Updated BusLine: " + busLine.getLineName() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertBusStop(BusStop busStop) {
        try {
            if (busStop != null) {
                openWritableDb();
                BusStopDao dao = daoSession.getBusStopDao();
                dao.insert(busStop);
                Log.d(TAG, "Inserted BusLine: " + busStop.getStopName() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BusStop> listBusStop() {
        List<BusStop> list = null;
        try {
            openReadableDb();
            BusStopDao dao = daoSession.getBusStopDao();
            list = dao.loadAll();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateBusStop(BusStop busStop) {
        try {
            if (busStop != null) {
                openWritableDb();
                BusStopDao dao = daoSession.getBusStopDao();
                dao.update(busStop);
                Log.d(TAG, "Updated BusStop: " + busStop.getStopName() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertBusStopImage(BusStopImage busStopImage) {
        try {
            if (busStopImage != null) {
                openWritableDb();
                BusStopImageDao dao = daoSession.getBusStopImageDao();
                dao.insert(busStopImage);
                Log.d(TAG, "Inserted BusStopImage: " + busStopImage.getImageName() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BusStopImage> listBusStopImages() {
        List<BusStopImage> list = null;
        try {
            openReadableDb();
            BusStopImageDao dao = daoSession.getBusStopImageDao();
            list = dao.loadAll();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateBusStopImage(BusStopImage busStopImage) {
        try {
            if (busStopImage != null) {
                openWritableDb();
                BusStopImageDao dao = daoSession.getBusStopImageDao();
                dao.update(busStopImage);
                Log.d(TAG, "Updated BusStopImage: " + busStopImage.getImageName() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
