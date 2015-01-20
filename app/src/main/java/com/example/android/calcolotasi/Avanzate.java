package com.example.android.calcolotasi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class Avanzate extends Activity implements View.OnClickListener {

    CheckBox inagibilità;
    CheckBox valoreStorico;
    Button ritorna;
    boolean ina= false;
    boolean valStor= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avanzate);

        inagibilità = (CheckBox) findViewById(R.id.inagibilità);


        if (inagibilità.isChecked()==true) {
            ina= true;
        }
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        inagibilità.setChecked(sharedPref.getBoolean("checked", false));


        valoreStorico = (CheckBox) findViewById(R.id.valoreStorico);


        if(valoreStorico.isChecked() == true) {
            valStor = true;
        }
       /* sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        valoreStorico.setChecked(sharedPref.getBoolean("checked", false));*/

        ritorna = (Button) findViewById(R.id.ritorna);

        ritorna.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_avanzate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ritorna:

                    Intent intent= new Intent();
                    intent.putExtra("inagibilità", ina);
                    intent.putExtra("valoreStorico", valStor);
                    setResult(RESULT_OK, intent);
                    finish();
            break;
            }
        }
    }

