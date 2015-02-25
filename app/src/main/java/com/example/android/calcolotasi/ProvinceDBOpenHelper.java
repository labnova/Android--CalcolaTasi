package com.example.android.calcolotasi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ProvinceDBOpenHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "databaseTasi";
    private static final String DATABASE_NAME = "province.db";

    private static final int DATABASE_VERSION = 1;

    /*
     *
      *  <provincia>
        <provID>2</provID>
        <provNome>Pisa</provNome>
        <descrizione>E' una delle città toscane</descrizione>
        <prezzoBiglietto>27 euro</prezzoBiglietto>
        <immagine>map_Pisa</immagine>
        <link>http://www.labnova.it</link>
    </provincia>
      *
      *
      * */

    public static final String TABLE_PROVINCE = "province";
    public static final String COLUMN_ID = "provID";
    public static final String COLUMN_NOME = "provNome";
    public static final String COLUMN_DESCRIZIONE = "descrizione";
    public static final String COLUMN_IMMAGINE = "immagine";
    public static final String COLUMN_PREZZOBIGLIETTO = "prezzoBiglietto";



    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PROVINCE + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT, " +
                    COLUMN_DESCRIZIONE + " TEXT, " +
                    COLUMN_IMMAGINE + " TEXT, " +
                    COLUMN_PREZZOBIGLIETTO + " NUMERIC " +
                    " ) ";


    public ProvinceDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG, "la tabella è stata creata");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVINCE);
        onCreate(db);
    }
}
