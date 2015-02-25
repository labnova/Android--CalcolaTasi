package com.example.android.calcolotasi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class ProvinceDataSource {

    private static final String LOGTAG = "Preferenze";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColums = {
            ProvinceDBOpenHelper.COLUMN_ID,
            ProvinceDBOpenHelper.COLUMN_NOME,
            ProvinceDBOpenHelper.COLUMN_DESCRIZIONE,
            ProvinceDBOpenHelper.COLUMN_PREZZOBIGLIETTO,
            ProvinceDBOpenHelper.COLUMN_IMMAGINE
    };


    public ProvinceDataSource(Context context) {
        dbhelper = new ProvinceDBOpenHelper(context);
        database = dbhelper.getWritableDatabase();

    }

    public void open() {
        Log.i(LOGTAG, "Database aperto");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database chiuso");
        dbhelper.close();
    }

    public Provincia create(Provincia provincia) {
        ContentValues values =  new ContentValues();
        values.put(ProvinceDBOpenHelper.COLUMN_NOME, provincia.getNome());
        values.put(ProvinceDBOpenHelper.COLUMN_DESCRIZIONE, provincia.getDescrizione());
        values.put(ProvinceDBOpenHelper.COLUMN_PREZZOBIGLIETTO, provincia.getPrice());
        values.put(ProvinceDBOpenHelper.COLUMN_IMMAGINE, provincia.getImage());

        long insertid = database.insert(ProvinceDBOpenHelper.TABLE_PROVINCE, null, values);
        provincia.setId(insertid);
        return provincia;

    }

    public List<Provincia> findAll() {

        Cursor cursor =
                database.query(ProvinceDBOpenHelper.TABLE_PROVINCE, allColums, null, null, null, null, null);
        Log.i(LOGTAG, "Ritornano " + cursor.getCount() +  " rows");
        List<Provincia> province = cursorToList(cursor);
        return province;
    }

    public List<Provincia> findFiltered(String selection, String orderBy) {

        Cursor cursor =
                database.query(ProvinceDBOpenHelper.TABLE_PROVINCE,
                        allColums, selection, null, null, null, orderBy);
        Log.i(LOGTAG, "Ritornano " + cursor.getCount() +  " rows");

        List<Provincia> province = cursorToList(cursor);
        return province;
    }



    private List<Provincia> cursorToList(Cursor cursor) {
        List<Provincia> province = new ArrayList<Provincia>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                Provincia provincia = new Provincia();
                provincia.setId(cursor.getLong(cursor.getColumnIndex(ProvinceDBOpenHelper.COLUMN_ID)));
                provincia.setNome(cursor.getString(cursor.getColumnIndex(ProvinceDBOpenHelper.COLUMN_NOME)));
                provincia.setDescrizione(cursor.getString(cursor.getColumnIndex(ProvinceDBOpenHelper.COLUMN_DESCRIZIONE)));
                provincia.setImage(cursor.getString(cursor.getColumnIndex(ProvinceDBOpenHelper.COLUMN_IMMAGINE)));
                provincia.setPrice(cursor.getInt(cursor.getColumnIndex(ProvinceDBOpenHelper.COLUMN_PREZZOBIGLIETTO)));
                province.add(provincia);
            }
        }
        return province;
    }
}
