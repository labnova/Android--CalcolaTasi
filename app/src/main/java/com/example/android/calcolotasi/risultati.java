package com.example.android.calcolotasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class risultati extends ActionBarActivity implements View.OnClickListener {

    LinearLayout risultatiLayout;

    Double renditaC;
    Double impostaLorda;
    Double rataAcconto;
    Double rataDiSaldo;

    TextView possessoLabel;
    TextView possessoText;
    Double possesso;
    Double mesi;

    String possessoPar;
    int PossessoInt;
    Button f24;

    ImageView copertina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati);
        risultatiLayout = (LinearLayout) findViewById(R.id.risultatiLayout);
        copertina = (ImageView) findViewById(R.id.copertina);
        copertina.setImageResource(R.drawable.copertina);

        Intent intent= getIntent();

        renditaC= intent.getDoubleExtra("renditaC", 1.00);
        impostaLorda= intent.getDoubleExtra("impostaLorda", 1.00);
        rataAcconto= intent.getDoubleExtra("rataAcconto", 1.00);
        rataDiSaldo= intent.getDoubleExtra("rataDiSaldo", 1.00);
        possesso = intent.getDoubleExtra("quotaPossesso", 1.00);
        Log.d("possesso", String.valueOf(possesso));

        possessoPar = String.valueOf(intent.getStringExtra("possessoPar"));


        Log.d("possessoPar", possessoPar);
        mesi = intent.getDoubleExtra("mesi", 1.00);

        f24 = (Button) findViewById(R.id.f24);

        f24.setOnClickListener(this);



        TextView tv = (TextView) findViewById(R.id.renditaC);
        tv.setText(renditaC.toString());

        TextView impostaL = (TextView) findViewById(R.id.impostaLorda);
        impostaL.setText(impostaLorda.toString());

        TextView rataAccontoText = (TextView) findViewById(R.id.rataAcconto);
        rataAccontoText.setText(rataAcconto.toString());

        TextView rataDiSaldoText = (TextView) findViewById(R.id.rataSaldo);
        rataDiSaldoText.setText(rataDiSaldo.toString());


        Log.d("PossessoInt", String.valueOf(PossessoInt));



        if (possessoPar != "100") {
            possessoLabel = (TextView) findViewById(R.id.possessoLabel);
            possessoText = (TextView) findViewById(R.id.possesso);
            possessoText.setText(possesso.toString());

        }

        TextView mesiText = (TextView) findViewById(R.id.mesi);
        //mesiText.setText(mesi.getText().toString());

       // rataDiSaldoText.setText(rataDiSaldo.toString());
       // public abstract SharedPreferences getSharedPreferences

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_risultati, menu);
        return true;
    }

    public void indietro(MenuItem item) {
        Intent intent= new Intent();
        intent.putExtra("renditaC", renditaC);
        intent.putExtra("impostaLorda", impostaLorda);
        intent.putExtra("rataAcconto", rataAcconto);
        intent.putExtra("rataDiSaldo", rataDiSaldo);
        setResult(RESULT_OK, intent);
        finish();
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
        switch(v.getId()) {
            case R.id.f24:
                Toast.makeText(this, "3958 Tasi prima casa e pertinenze "+ impostaLorda, Toast.LENGTH_LONG).show();
                 break;
            default: break;
        }
    }
}
