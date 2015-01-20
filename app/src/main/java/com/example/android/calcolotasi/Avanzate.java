package com.example.android.calcolotasi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class Avanzate extends Activity implements View.OnClickListener {

    CheckBox checkIna;
    CheckBox valoreStorico;
    private Boolean attivataIna=false;
    private Boolean attivatoValoreStorico=false;
    Button ritorna;
    Boolean ina= false;
    Boolean valStor= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avanzate);

        checkIna = (CheckBox) findViewById(R.id.ina);




        valoreStorico = (CheckBox) findViewById(R.id.valoreStorico);

        checkIna.setChecked(attivataIna);
        valoreStorico.setChecked(attivatoValoreStorico);




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

   /* @Override
    protected void onResume() {
        super.onResume();
        checkIna.setChecked(attivataIna);
        valoreStorico.setChecked(attivatoValoreStorico);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("checkIna", checkIna.isChecked());
        savedInstanceState.putBoolean("valoreStorico", valoreStorico.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        attivataIna = savedInstanceState.getBoolean("checkIna");
        attivatoValoreStorico = savedInstanceState.getBoolean("valoreStorico");
    }*/



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ritorna:

                Intent intent= new Intent();


                if (checkIna.isChecked()) {
                    ina = true;
                    Log.d("ina_avanzate", ina.toString());


                }

                if(valoreStorico.isChecked()) {
                    valStor = true;
                }
                    intent.putExtra("inag", ina);
                    intent.putExtra("valStor", valStor);



                    //intent.putExtra("valoreStorico", valoreStorico.isChecked());
                    setResult(RESULT_OK, intent);
                    finish();
            break;
            }
        }
    }

