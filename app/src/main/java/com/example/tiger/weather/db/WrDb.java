package com.example.tiger.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tiger.weather.model.City;
import com.example.tiger.weather.model.County;
import com.example.tiger.weather.model.Province;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiger on 16/7/15.
 */
public final class WrDb {

    public static final String DB_NAME = "tiger";

    public static final int VERSION = 1;

    private static WrDb wrDb;

    private SQLiteDatabase db;

    private SQLiteOpenHelper dbHelper;

    private final static String TAG = WrDb.class.getName();

    private final static String creatProvince = "create table province (id text primary key, name text)";

    private final static String creatCity = "create table city (id text primary key, proid text ,name text)";

    private final static String creatCounty = "create table county (id text primary key, cityid text, name text)";

    private WrDb(Context context){
        SQLiteOpenHelper datebasebHelper = new SQLiteOpenHelper(context, "tiger.db", null, VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                try {
                    db.execSQL(creatProvince);
                    db.execSQL(creatCity);
                    db.execSQL(creatCounty);
                } catch (Exception e) {
                    Logger.e(TAG, e);
                }
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        dbHelper = datebasebHelper;
    }

    public synchronized static WrDb getInstence(Context context){
        if (wrDb == null){
            wrDb = new WrDb(context);
        }
        return wrDb;
    }


//    private static DatabaseHelper db;

//    private synchronized static DatabaseHelper getDb(){//应该是等效于instence
//        return db;
//    }

//    public synchronized static void init (Context context){
//        if (context != null && db == null){
//            db = new DatabaseHelper(context);
//        }
//    }

//    private static class DatabaseHelper extends SQLiteOpenHelper{
//
//        public DatabaseHelper(Context context){
//            super(context, "tiger.db", null, -1);
//        }
//
//        public void onCreate(SQLiteDatabase database){
//            try {
//                database.execSQL(creatProvince);
//                database.execSQL(creatCity);
//                database.execSQL(creatCounty);
//            }catch (Exception e){
//                Logger.e(TAG, e);
//            }
//        }
//
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//        }
//    }

    public void saveProvince(Province province){
        if (province != null){
            ContentValues values = new ContentValues();
            values.put("id", province.getProId());
            values.put("name", province.getProName());
            wrDb.dbHelper.getWritableDatabase().insert("province", null, values);
        }
    }

    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = wrDb.dbHelper.getReadableDatabase().query("province", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setProId(cursor.getString(0));
                province.setProName(cursor.getString(1));
                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCity(City city){
        if (city != null){
            ContentValues values = new ContentValues();
            values.put("id", city.getCityId());
            values.put("name", city.getCityName());
            values.put("proid", city.getProId());
            wrDb.dbHelper.getWritableDatabase().insert("city", null, values);
        }
    }

    public List<City> loadCity(String proId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = wrDb.dbHelper.getReadableDatabase().query("city", null, "proid = ?", new String[]{proId}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setCityId(cursor.getString(0));
                city.setProId(cursor.getString(1));
                city.setCityName(cursor.getString(2));
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCounty(County county){
        if (county != null){
            ContentValues values = new ContentValues();
            values.put("id", county.getCountyId());
            values.put("name", county.getCountyName());
            values.put("cityid", county.getCityId());
            wrDb.dbHelper.getWritableDatabase().insert("county", null, values);
        }
    }

    public List<County> loadCounties(String cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = wrDb.dbHelper.getReadableDatabase().query("county", null, "cityid = ?", new String[]{cityId}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.setCountyId(cursor.getString(0));
                county.setCityId(cursor.getString(1));
                county.setCountyName(cursor.getString(2));
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }


//    public static boolean insertToTable(String sql, Object[] objects){
//        //String sql = "insert or replace into" + tableName + "(id, name) values (?,?)";
//        try {
//            getDb().getWritableDatabase().execSQL(sql, objects);
//            return true;
//        }catch (Exception e){
//            Logger.e(TAG, e);
//        }
//        return false;
//    }
//
//    public static  List<List<Object>> select(String[] s, String sql){
//        List<List<Object>> res = new ArrayList<List<Object>>();
//        if (s != null && s.length != 0){
//            Cursor cursor = getDb().getReadableDatabase().rawQuery(sql, s);
//            if (cursor.moveToFirst()){
//                while (!cursor.isAfterLast()){
//                    List<Object> tmp  = new ArrayList<Object>();
//                    for (int i = 0; i < cursor.getColumnCount(); i++) {
//                        tmp.add(cursor.getString(i));
//                    }
//                    res.add(tmp);//存进结果
//                    cursor.moveToNext();
//                }
//            }
//            cursor.close();
//            getDb().close();
//        }
//        return res;
//    }
}
