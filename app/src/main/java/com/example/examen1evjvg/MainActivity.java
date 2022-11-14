package com.example.examen1evjvg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button botonConvertir, botonInfo;
    EditText cantidad;
    RadioGroup monedasOrigen, monedasDestino;
    RadioButton radioEuroOrigen, radioDolarOrigen, radioBitcoinOrigen, radioEuroDestino, radioDolarDestino, radioBitcoinDestino;
    String seleccionOrigen = "";
    String seleccionDestino = "";
    TextView textConversion;
    int selectedOrigen;
    int selectedDestino;
    boolean haConvertido = false;
    String url = "https://coinmarketcap.com/es/currendies/bitcoin/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonConvertir = findViewById(R.id.botonConvertir);
        botonConvertir.setOnClickListener(this);
        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(this);

        textConversion = findViewById(R.id.textViewConversion);
        cantidad = findViewById(R.id.editText);
        monedasOrigen = findViewById(R.id.radioOrigen);
        monedasDestino = findViewById(R.id.radioDestino);

        botonInfo.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("conversion", textConversion.getText().toString());
        outState.putString("cantidad", cantidad.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        cantidad.setText("cantidad");
        textConversion.setText("conversion");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.botonConvertir:
                seleccionesOrigen();
                seleccionesDestino();

                if (seleccionDestino == "" || seleccionOrigen == ""){
                    textConversion.setText("Por favor, escoge una moneda");
                } else{
                    textConversion.setText(cantidad.getText().toString() + seleccionOrigen + cantidad.getText().toString() + seleccionDestino);
                    haConvertido = true;
                }
                if (haConvertido){
                    botonInfo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.botonInfo:

                    botonInfo.setVisibility(View.VISIBLE);
                    Uri uri = Uri.parse(url);
                    Intent link = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(link);

                break;
        }
    }

    public String seleccionesDestino(){
        selectedDestino = monedasDestino.getCheckedRadioButtonId();
        if (selectedDestino == R.id.radioEuroDestino){
            seleccionDestino = " euros";
        }
        if (selectedDestino == R.id.radioDolarDestino){
            seleccionDestino = " dólares";
        }
        if (selectedDestino == R.id.radioBitcoinDestino){
            seleccionDestino = " bitcoins";
        }
        if (selectedDestino == 0){
            seleccionDestino = "";
        }
        return seleccionDestino;
    }
    public String seleccionesOrigen(){
        selectedOrigen = monedasOrigen.getCheckedRadioButtonId();
        if (selectedOrigen == R.id.radioEuroOrigen){
            seleccionOrigen = " euros son ";
        }
        if (selectedOrigen == R.id.radioDolarOrigen){
            seleccionOrigen = " dólares son ";
        }
        if (selectedOrigen == R.id.radioBitcoinOrigen){
            seleccionOrigen = " bitcoins son ";
        }
        if (selectedOrigen == 0){
            seleccionOrigen = "";
        }
        return seleccionOrigen;
    }
}