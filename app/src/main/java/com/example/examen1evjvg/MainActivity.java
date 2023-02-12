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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button botonConvertir, botonInfo, botonHistorial;
    EditText cantidad;
    RadioGroup monedasOrigen, monedasDestino;
    RadioButton radioEuroOrigen, radioDolarOrigen, radioBitcoinOrigen, radioEuroDestino, radioDolarDestino, radioBitcoinDestino;
    String seleccionOrigen = "";
    String seleccionDestino = "";
    TextView textConversion, textMoneda;
    int selectedOrigen;
    int selectedDestino;
    float conversion, cantidadFloat;
    boolean haConvertido = false;
    String url = "https://coinmarketcap.com/es/currendies/bitcoin/";
    ArrayList<Float> historialConversiones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonConvertir = findViewById(R.id.botonConvertir);
        botonConvertir.setOnClickListener(this);
        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(this);
        botonHistorial = findViewById(R.id.botonHistorial);
        botonHistorial.setOnClickListener(this);

        textConversion = findViewById(R.id.textViewConversion);
        textMoneda = findViewById(R.id.textView7);
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
                    hacerConversion();
                    textConversion.setText((int) conversion);
                    historialConversiones.add(conversion);
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
            case R.id.botonHistorial:
                Toast historial = Toast.makeText(getApplicationContext(), "Tus ultimas conversiones: ", Toast.LENGTH_LONG); //me falta tiempo
                historial.show();
                break;
        }
    }

    public String seleccionesDestino(){
        selectedDestino = monedasDestino.getCheckedRadioButtonId();
        if (selectedDestino == R.id.radioEuroDestino){
            seleccionDestino = "euros";
        }
        if (selectedDestino == R.id.radioDolarDestino){
            seleccionDestino = "dolares";
        }
        if (selectedDestino == R.id.radioBitcoinDestino){
            seleccionDestino = "bitcoins";
        }
        if (selectedDestino == 0){
            seleccionDestino = "";
        }
        return seleccionDestino;
    }
    public String seleccionesOrigen(){
        String hint;
        selectedOrigen = monedasOrigen.getCheckedRadioButtonId();
        if (selectedOrigen == R.id.radioEuroOrigen){
            seleccionOrigen = "euros";
            textMoneda.setText("€");
            cantidad.setHint("Euros");
        }
        if (selectedOrigen == R.id.radioDolarOrigen){
            seleccionOrigen = "dolares";
            textMoneda.setText("$");
            cantidad.setHint("Dólares");
        }
        if (selectedOrigen == R.id.radioBitcoinOrigen){
            seleccionOrigen = "bitcoins";
            textMoneda.setText("\u20BF");
            cantidad.setHint("Bitcoins");
        }
        if (selectedOrigen == 0){
            seleccionOrigen = "";
            cantidad.setHint("Importe");
        }
        return seleccionOrigen;
    }

    public float hacerConversion(){
        //febrero de 2023
        Toast adv = Toast.makeText(getApplicationContext(), "No puedes convertir la misma moneda", Toast.LENGTH_SHORT);
        cantidadFloat = Float.parseFloat(cantidad.getText().toString());
        if (seleccionDestino == "euros"){
            if (seleccionOrigen == "euros"){
                adv.show();
            }
            if (seleccionOrigen == "dolares"){
                conversion = (float) (cantidadFloat * 1.07);
            }
            if (seleccionOrigen == "bitcoins"){
                conversion = (float) (cantidadFloat * 0.000048);
            }
        }

        if (seleccionDestino == "dolares"){
            if (seleccionOrigen == "dolares"){
                adv.show();
            }
            if (seleccionOrigen == "euros"){
                conversion = (float) (cantidadFloat / 1.07);
            }
            if (seleccionOrigen == "bitcoins"){
                conversion = (float) (cantidadFloat * 0.000048);
            }
        }

        if (seleccionDestino == "bitcoins"){
            if (seleccionOrigen == "bitcoins"){
                adv.show();
            }
            if (seleccionOrigen == "euros"){
                conversion = (float) (cantidadFloat / 1.07);
            }
            if (seleccionOrigen == "dolares"){
                conversion = (float) (cantidadFloat * 0.000045);
            }
        }
        return conversion;
    }
}