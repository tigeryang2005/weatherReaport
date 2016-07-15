package com.example.tiger.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tiger.weather.model.City;
import com.example.tiger.weather.model.Province;
import com.orhanobut.logger.Logger;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiger on 16/7/15.
 */
public final class WrDb {

    private final static String TAG = WrDb.class.getName();

    private final static String creatProvince = "create table province (id text primary key, name text)";

    private final static String creatCity = "create table city (id text primary key, proid text ,name text)";

    private final static String creatCounty = "create table county (id text primary key, cityid text, name text)";

    private static DatabaseHelper db;

    private synchronized static DatabaseHelper getDb(){
        return db;
    }

    public synchronized static void init (Context context){
        if (context != null && db == null){
            db = new DatabaseHelper(context);
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context){
            super(context, "tiger.db", null, -1);
        }

        public void onCreate(SQLiteDatabase database){
            try {
                database.execSQL(creatProvince);
                database.execSQL(creatCity);
                database.execSQL(creatCounty);
            }catch (Exception e){
                Logger.e(TAG, e);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void saveProvince(Province province){
        if (province != null){
            ContentValues values = new ContentValues();
            values.put("id", province.getProId());
            values.put("name", province.getProName());
            getDb().getWritableDatabase().insert("province", null, values);
        }
    }

    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = getDb().getReadableDatabase().query("province", null, null, null, null, null, null);
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
            getDb().getWritableDatabase().insert("city", null, values);
        }
    }

//    public List<City> loadCitise(String proid){
//
//    }

    public static boolean insertToTable(String sql, Object[] objects){
        //String sql = "insert or replace into" + tableName + "(id, name) values (?,?)";
        try {
            getDb().getWritableDatabase().execSQL(sql, objects);
            return true;
        }catch (Exception e){
            Logger.e(TAG, e);
        }
        return false;
    }

    public static  List<List<Object>> select(String[] s, String sql){
        List<List<Object>> res = new ArrayList<List<Object>>();
        if (s != null && s.length != 0){
            Cursor cursor = getDb().getReadableDatabase().rawQuery(sql, s);
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    List<Object> tmp  = new ArrayList<Object>();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        tmp.add(cursor.getString(i));
                    }
                    res.add(tmp);//存进结果
                    cursor.moveToNext();
                }
            }
            cursor.close();
            getDb().close();
        }
        return res;
    }
}
