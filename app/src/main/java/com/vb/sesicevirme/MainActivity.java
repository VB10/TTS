package com.vb.sesicevirme;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton voice_button;
    private EditText edt_multi;
    private Intent ıntent;

    public static final int request_code_voice=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load();
    }

    private void load() {
        voice_button= (ImageButton) findViewById(R.id.btn_Voice);
        edt_multi= (EditText) findViewById(R.id.et_Multi);

    }

    public void finish(View view) {
        ıntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//ses tanıma
        ıntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        try {
            startActivityForResult(ıntent, request_code_voice);
        }catch (ActivityNotFoundException e) {
            e.printStackTrace();//aktivitiy yoksa hata
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("telefon bu sistemi desteklmiyor...")
                    .setTitle("VB")
                    .setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog =builder.create();
            alertDialog.show();
        }

    }

    public void Share(View view) {
        String txt = edt_multi.getText().toString();
        if (txt.isEmpty()) Toast.makeText(this, "Boş yapılmaz..", Toast.LENGTH_SHORT).show();
        else  {
            Intent share_intent = new Intent(Intent.ACTION_SEND);
            share_intent.setType("text/plain");
            share_intent.putExtra(Intent.EXTRA_SUBJECT,"Vb deneme");
            share_intent.putExtra(Intent.EXTRA_TEXT,txt);
            startActivity(Intent.createChooser(share_intent,"Paylasmak icin birini secin"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case request_code_voice:{
                if (resultCode==RESULT_OK && data!=null) {
                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edt_multi.setText(speech.get(0));
                }
                break;
            }

        }

    }
}
