package com.example.android.calcolotasi;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener,
        CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener
{

    private static final int REQUEST_CODE = 100;
    private static final int AVANZATE_CODE = 101;

    LinearLayout main;

    Button calcola;
    Button fattispecie;
    Button altriDati;


    TextView aliquotaTasi;
    EditText aliquota;
    EditText renditaCatastale;

    SeekBar possesso;
    TextView possessoText;
    NumberPicker mesi;
    Switch primaCasa;


    TextView titolariDetrazione;
    Spinner numeroTitolariDetrazione;


    Double renditaC = 1.00;
    Double al = 1.00;
    Double baseImponibile = 1.00;
    Double impostaLorda = 1.00;
    Double rataAcconto = 1.00;
    Double rataDiSaldo = 1.00;
    Double quotaPossesso=1.00;
    String possessoPar;
    String inagibilità = "no";



   String[] comuni = {"Aosta", "Torino", "Milano", "Trento", "Venezia", "Trieste", "Genova",
    "Bologna", "Firenze", "Perugia", "Ancona", "L\'Aquila", "Roma", "Campobasso", "Napoli",
    "Potenza", "Bari", "Catanzaro", "Palermo", "Cagliari", "Trapani"};

    String [] numeroTitDetr = {"1","2","3","4","5","6","7","8","9","10"};




    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (LinearLayout) findViewById(R.id.main);

        aliquotaTasi= (TextView) findViewById(R.id.aliquotaText);
        aliquota= (EditText) findViewById(R.id.aliquota);
        renditaCatastale= (EditText) findViewById(R.id.renditaCatastale);

        possesso = (SeekBar) findViewById(R.id.possesso);
        possessoText = (TextView) findViewById(R.id.possessoText);
        possessoText.setText("100");




        mesi = (NumberPicker) findViewById(R.id.mesi);
        mesi.setMaxValue(12);
        mesi.setMinValue(1);
        mesi.setValue(12);

        primaCasa = (Switch) findViewById(R.id.primaCasa);
        primaCasa.setChecked(true);
        primaCasa.setOnCheckedChangeListener(this);



        AutoCompleteTextView completamento = (AutoCompleteTextView) findViewById(R.id.comuni);
        completamento.setAdapter(new ArrayAdapter<String>(this, R.layout.lista_comuni, comuni ));


        possesso.setOnSeekBarChangeListener(this);

        completamento.setOnItemClickListener(this);

        titolariDetrazione = new TextView(this);
        titolariDetrazione.setText(R.string.titolari_detrazione);
        main.addView(titolariDetrazione);
        titolariDetrazione.setVisibility(View.GONE);

        numeroTitolariDetrazione = (Spinner) findViewById(R.id.numeroTitolariDetrazione);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numeroTitDetr);
        numeroTitolariDetrazione.setAdapter(adapter);
        numeroTitolariDetrazione.setOnItemSelectedListener(this);
        numeroTitolariDetrazione.setVisibility(View.GONE);




        calcola= (Button) findViewById(R.id.calcola);
        fattispecie= (Button) findViewById(R.id.fattispecie);
        altriDati = (Button) findViewById(R.id.altriDati);

        calcola.setOnClickListener(this);
        fattispecie.setOnClickListener(this);
        altriDati.setOnClickListener(this);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item1:
                renditaCatastale.setText("item1");
            break;
            case R.id.item2:
                renditaCatastale.setText("item2");
            break;
            case R.id.item3:
                renditaCatastale.setText("item3");
            break;
            default: break;

        }
        return super.onContextItemSelected(item);
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

        if (id == R.id.info) {
            Intent intent= new Intent(this, info.class);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
     public void onClick(View v) {

        switch (v.getId()) {
            case R.id.calcola:
                calcolaTasi(aliquota);
                break;
            case R.id.fattispecie:
                mostraFattispecie();
                break;
            case R.id.altriDati:
                altriDati();
            default: break;

        }
    }

    private void altriDati() {
        Intent intent = new Intent(this, Avanzate.class);
        Boolean checked = false;
        startActivityForResult(intent, AVANZATE_CODE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void calcolaTasi(EditText aliquota) {

        possessoPar =  possessoText.getText().toString();

        try {
            renditaC = Double.parseDouble(String.valueOf(renditaCatastale.getText().toString()));
            al = Double.parseDouble(String.valueOf(aliquota.getText().toString()));


            double poss = (double) Integer.parseInt(possessoText.getText().toString());
            double m = (double) mesi.getValue();
            Log.d("mesi", String.valueOf(m));


            baseImponibile = rivalutazioneCatastale(renditaC) * 160;
            renditaC = baseImponibile;




            if (poss != 100) {

                quotaPossesso = baseImponibile * poss / 100;
                baseImponibile = quotaPossesso;
            }

            impostaLorda = baseImponibile * al / 1000;

            if (m != 12) {
                double impLordSingoloMese = 1.00;
                impLordSingoloMese = impostaLorda /12;
                impostaLorda = impLordSingoloMese * m;

            }
            rataAcconto = impostaLorda / 2;
            rataDiSaldo = rataAcconto;




            Intent intent = new Intent(this, risultati.class);

            intent.putExtra("renditaC", renditaC);
            intent.putExtra("quotaPossesso", quotaPossesso);
            intent.putExtra("Base imponibile", baseImponibile);
            intent.putExtra("impostaLorda", impostaLorda);
            intent.putExtra("rataAcconto", rataAcconto);
            intent.putExtra("rataDiSaldo", rataDiSaldo);

            intent.putExtra("possessoPar", possessoPar);

            intent.putExtra("mesi", m);

            startActivityForResult(intent, REQUEST_CODE);
            overridePendingTransition(R.anim.animazione1, R.anim.animazione2);

        } catch (NumberFormatException e) {
            renditaCatastale.setText("immetti valore!");
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

        }

        if(requestCode == AVANZATE_CODE && resultCode == RESULT_OK) {

            renditaC = Double.parseDouble(String.valueOf(renditaCatastale.getText().toString()));

            Boolean inag = data.getBooleanExtra("inag", false);
            Boolean valStor = data.getBooleanExtra("valStor", false);

           Log.d("inaG", inag.toString());
            //Boolean valStor = intent.getBooleanExtra("valoreStorico", false);

            if (inag.toString() == "true") {

                renditaC = renditaC / 2;
                renditaCatastale.setText(String.valueOf(renditaC));
            }

            if(valStor.toString() == "true") {

                renditaC = renditaC / 2;
                renditaCatastale.setText(String.valueOf(renditaC));
            }

           /* String ina= intent.getStringExtra("ina");
            Log.d("ina", ina);*/
            //String valStor = intent.getStringExtra("valoreStorico");

           /* if(ina == "true") {
                inagibilità = "si";
            }*/
        }
    }

    private Double rivalutazioneCatastale(Double renditaC) {
        Double riv= (renditaC*5)/100;
        renditaC= renditaC+riv;
        return renditaC;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void mostraFattispecie() {
        PopupMenu popupMenu = new PopupMenu(this, fattispecie);
        getMenuInflater().inflate(R.menu.fattispecie, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                fattispecie.setText(item.getTitle());
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String messaggio;
        String item= parent.getItemAtPosition(position).toString();

        switch (item) {
            case "Aosta":
                messaggio="1.00";
                aliquota.setText(messaggio);
                break;
            case "Torino":
                aliquota.setText("3.30");
                break;
            case "Milano":
                aliquota.setText("2.50");
                break;
            case "Trento":
                aliquota.setText("1.00");
                break;
            case "Venezia":
                aliquota.setText("2.90");
                break;
            case "Trieste":
                aliquota.setText("2.50");
                break;
            case "Genova":
                aliquota.setText("3.30");
                break;
            case "Bologna":
                aliquota.setText("3.30");
                break;
            case "Firenze":
                aliquota.setText("3.30");
                break;
            case "Perugia":
                aliquota.setText("3.30");
                break;
            case "Ancona":
                aliquota.setText("3.30");
                break;
            case "L\'Aquila":
                aliquota.setText("2.00");
                break;
            case "Roma":
                aliquota.setText("2.50");
                break;
            case "Campobasso":
                aliquota.setText("2.50");
                break;
            case "Napoli":
                aliquota.setText("3.30");
                break;
            case "Potenza":
                aliquota.setText("2.50");
                break;
            case "Bari":
                aliquota.setText("0");
                break;
            case "Catanzaro":
                aliquota.setText("1.20");
                break;
            case "Palermo":
                aliquota.setText("2.89");
                break;
            case "Cagliari":
                aliquota.setText("3.30");
                break;
            case "Trapani":
                aliquota.setText("1.70");
                break;

            default:
                messaggio="aliquota per Comune";
                aliquota.setText(messaggio);

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String progr= Integer.toString(progress);
            possessoText.setText(progr);

        if(seekBar.getProgress() == 100 && (titolariDetrazione.getVisibility() == View.VISIBLE)) {

            titolariDetrazione.setVisibility(View.GONE);
            numeroTitolariDetrazione.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {



        if (seekBar.getProgress() < 100) {

            titolariDetrazione.setVisibility(View.VISIBLE);
            numeroTitolariDetrazione.setVisibility(View.VISIBLE);


        } else {
            titolariDetrazione.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.primaCasa:
                if(!isChecked){
                    registerForContextMenu(primaCasa);

                }
        }

    }

    //metodi predefiniti dello spinner

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
