package com.example.android.calcolotasi;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class preferenze extends ListActivity implements  View.OnClickListener {

    private static final String LOGTAG = "Preferenze";
    private static final String USERNAME = "pref_username";
    private static final String VIEWIMAGE = "pref_image";

    private SharedPreferences settings;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    private List<Provincia> province;

/*   SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;*/




    ProvinceDataSource datasource;

    private File file;
    private static final String FILENAME = "jsondata";

    Button leggi;
    Button all;
    Button fancy;
    Button cheap;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferenze);

        all = (Button) findViewById(R.id.all);
        fancy = (Button) findViewById(R.id.fancy);
        cheap = (Button) findViewById(R.id.cheap);

        all.setOnClickListener(this);
        fancy.setOnClickListener(this);
        cheap.setOnClickListener(this);

       /* leggi = (Button) findViewById(R.id.leggi);
        leggi.setOnClickListener(this);*/

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                try {
                    preferenze.this.refreshPreference(null);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        settings.registerOnSharedPreferenceChangeListener(listener);

       /* provincePullParser parser = new provincePullParser();
        List<Provincia> province = parser.parseXML(this);*/



         /*dbhelper = new ProvinceDBOpenHelper(this);
        database = dbhelper.getWritableDatabase();*/

        datasource = new ProvinceDataSource(this);
        datasource.open();

        List<Provincia> province = datasource.findAll();

        if(province.size() == 0) {
            createData();
            province = datasource.findAll();
        }

        refreshDisplay();



      //  File f = getFilesDir();
        File extDir = getExternalFilesDir(null);
        String path = extDir.getAbsolutePath();
        //UIHelper.displayText(this, R.id.textView1, path);
        file = new File(extDir, FILENAME);

        //settings = getPreferences(MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_preferenze, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_cheap:
                    province = datasource.findFiltered("price < 300", "price ASC");
                    refreshDisplay();
                break;
            case R.id.menu_fancy:
                    province = datasource.findFiltered("price < 300", "price ASC");
                    refreshDisplay();
                break;

            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPreference(View v) throws IOException, JSONException {
        Log.i(LOGTAG, "Clicked set");


        /*  Intent intent = new Intent(this, settings.class);
        startActivity(intent);

        SharedPreferences.Editor editor = settings.edit();
        String prefValue = UIHelper.getText(this, R.id.editText1);
        editor.putString(USERNAME, prefValue);
        editor.commit();*/

        try {
            createFile(v);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //UIHelper.displayText(this, R.id.textView1, prefValue);
    }

    public void refreshPreference(View v) throws IOException, JSONException {
        Log.i(LOGTAG, "Clicked show");
        try {
            readFile(v);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void refreshDisplay() {
        ArrayAdapter<Provincia> adapter = new ArrayAdapter<Provincia>(
                this,
                android.R.layout.simple_list_item_1,
                province);
        setListAdapter(adapter);
    }

    public void createFile(View v) throws IOException, JSONException {

       /* if(!checkExternalStorage()) {
            return;
        }*/

        JSONArray data = new JSONArray();
        getNewJSONData(data);

        String text = data.toString();
        FileOutputStream fos = new FileOutputStream(file);
        //FileOutputStream fos = openFileOutput("provincia", MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();

       // UIHelper.displayText(this, R.id.textView1, "il file è scritto sul disco:\n" + data.toString());

    }

    private void getNewJSONData(JSONArray data) throws JSONException {
        JSONObject provincia;

        provincia = new JSONObject();
        provincia.put("provincia", "Trapani");
        provincia.put("prezzo", 900);
        data.put(provincia);

        provincia = new JSONObject();
        provincia.put("provincia", "Pisa");
        provincia.put("prezzo", 800);
        data.put(provincia);

        provincia = new JSONObject();
        provincia.put("provincia", "Bologna");
        provincia.put("prezzo", 700);
        data.put(provincia);

        provincia = new JSONObject();
        provincia.put("provincia", "Firenze");
        provincia.put("prezzo", 600);
        data.put(provincia);
    }

    public void readFile(View v) throws IOException, JSONException {
        FileInputStream fis = new FileInputStream(file);
        //FileInputStream fis = openFileInput("provincia");
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer b = new StringBuffer();
        while (bis.available() != 0) {
            char c = (char) bis.read();
            b.append(c);
        }

        bis.close();
        fis.close();

        JSONArray data = new JSONArray(b.toString());

        StringBuffer provinciaBuffer = new StringBuffer();
        for (int i = 0; i < data.length(); i++) {
            String provincia = data.getJSONObject(i).getString("provincia");
            provinciaBuffer.append(provincia + "\n");
        }

      //  UIHelper.displayText(this, R.id.textView1, provinciaBuffer.toString());

    }


    /*public boolean checkExternalStorage() {
        String state = Environment.getExternalStorageState();

        if(state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else if(state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            UIHelper.displayText(this, R.id.textView1, "External storage è solo read-only");
        } else {
            UIHelper.displayText(this, R.id.textView1, "External storage non è disponibile");
        }

        return false;
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.leggi:
                try {
                    readFile(v);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;*/


            case R.id.all:

                break;
            case R.id.cheap:

                break;
            case R.id.fancy:

                break;
            default:break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }





    private void createData() {

        provincePullParser parser = new provincePullParser();
        List<Provincia> province = parser.parseXML(this);

        for(Provincia provincia : province) {
            datasource.create(provincia);
        }


        /*Provincia provincia = new Provincia();
        provincia.setNome("Marausa");
        provincia.setDescrizione("Un capodanno da favola!");
        provincia.setPrice(22);
        provincia.setImage("map_Marausa");
        provincia = datasource.create(provincia);
        Log.i(LOGTAG, "provincia creata con id " + provincia.getId());

        provincia = new Provincia();
        provincia.setNome("Calatafimi");
        provincia.setDescrizione("Mah, giusto per passarsi una serata diversa...");
        provincia.setPrice(12);
        provincia.setImage("map_Calatafimi");
        provincia = datasource.create(provincia);
        Log.i(LOGTAG, "provincia creata con id " + provincia.getId());

        provincia = new Provincia();
        provincia.setNome("Buseto Palizzolo");
        provincia.setDescrizione("Meh...");
        provincia.setPrice(12);
        provincia.setImage("map_Buseto");

        provincia = datasource.create(provincia);
        Log.i(LOGTAG, "provincia creata con id " + provincia.getId());*/
    }


}
