package com.coolweaather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweaather.app.model.CityBean;
import com.coolweaather.app.model.CountryBean;
import com.coolweaather.app.model.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作
 * Created by zhuanxin on 2016/5/30.
 */
public class CoolWeatherDB {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "cool_weather";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static final String TABLE_NAME_PROVINCE = "Province";
    private static final String TABLE_NAME_CITY = "City";
    private static final String TABLE_NAME_COUNTRY = "Country";

    private static CoolWeatherDB sCoolWeatherDB;
    private SQLiteDatabase mDatabase;

    /**
     * 私有化构造方法
     *
     * @param context
     */
    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper helper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        mDatabase = helper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例。
     */
    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (sCoolWeatherDB == null) {
            sCoolWeatherDB = new CoolWeatherDB(context);
        }
        return sCoolWeatherDB;
    }

    /**
     * 添加省份
     *
     * @param bean
     * @return
     */
    public long saveProvince(ProvinceBean bean) {
        long insert = 0;
        if (bean != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", bean.getProvinceName());
            values.put("province_code", bean.getProvinceCode());
            insert = mDatabase.insert(TABLE_NAME_PROVINCE, null, values);
        }
        return insert;
    }

    /**
     * 获取所有省
     *
     * @return
     */
    public List<ProvinceBean> getAllProvinces() {
        List<ProvinceBean> list = new ArrayList<>();
        ProvinceBean provinceBean;
        Cursor query = mDatabase.query(TABLE_NAME_PROVINCE, null, null, null, null, null, null);
        if (query != null && query.moveToFirst()) {
            do {
                provinceBean = new ProvinceBean();
                provinceBean.setId(query.getInt(query.getColumnIndex("id")));
                provinceBean.setProvinceCode(query.getString(query.getColumnIndex("province_code")));
                provinceBean.setProvinceName(query.getString(query.getColumnIndex("province_name")));
                list.add(provinceBean);
            } while (query.moveToNext());
        }
        return list;
    }

    /**
     * 插入城市
     *
     * @param city
     * @return
     */
    public long saveCity(CityBean city) {
        long insert = 0;
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_code", city.getCityCode());
            values.put("city_name", city.getCityName());
            values.put("province_id", city.getProvinceId());
            insert = mDatabase.insert(TABLE_NAME_CITY, null, values);
        }
        return insert;
    }

    /**
     * 获取某省所有城市
     *
     * @param provinceId
     * @return
     */
    public List<CityBean> getAllCityOfProvince(int provinceId) {
        List<CityBean> list = new ArrayList<CityBean>();
        CityBean city;
        if (provinceId > 0) {
            Cursor query = mDatabase.query(TABLE_NAME_CITY, null, "province_id=?", new String[]{provinceId + ""}, null, null, null);
            if (query != null && query.moveToFirst()) {
                do {
                    city = new CityBean();
                    city.setId(query.getInt(query.getColumnIndex("id")));
                    city.setCityCode(query.getString(query.getColumnIndex("city_code")));
                    city.setCityName(query.getString(query.getColumnIndex("city_name")));
                    city.setProvinceId(query.getInt(query.getColumnIndex("province_id")));
                    list.add(city);
                } while (query.moveToNext());
            }
        }
        return list;
    }

    public long saveCountry(CountryBean country) {
        long insert = 0;
        if (country != null) {
            ContentValues values = new ContentValues();
            values.put("country_code", country.getCountryCode());
            values.put("country_name", country.getCountryName());
            values.put("city_id", country.getCityId());
            insert = mDatabase.insert(TABLE_NAME_COUNTRY, null, values);
        }
        return insert;
    }

    public List<CountryBean> getAllCountryOfCity(int cityId) {
        List<CountryBean> list = new ArrayList<>();
        CountryBean country;
        Cursor query = mDatabase.query(TABLE_NAME_COUNTRY, null, "city_id=?", new String[]{cityId + ""}, null, null, null);
        if (query != null && query.moveToFirst()) {
            do {
                country = new CountryBean();
                country.setId(query.getInt(query.getColumnIndex("id")));
                country.setCityId(query.getInt(query.getColumnIndex("city_id")));
                country.setCountryCode(query.getString(query.getColumnIndex("country_code")));
                country.setCountryName(query.getString(query.getColumnIndex("country_name")));
                list.add(country);
            } while (query.moveToNext());
        }
        return list;
    }
}
