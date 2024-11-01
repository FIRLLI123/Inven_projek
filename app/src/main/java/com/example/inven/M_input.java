package com.example.inven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class M_input extends AppCompatActivity {
LinearLayout input_fisik, input_elektrik;
TextView nama_user, posisi, perusahaan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_input);

        input_fisik = (LinearLayout) findViewById(R.id.input_fisik);//1
        input_elektrik = (LinearLayout) findViewById(R.id.input_elektrik);//1

        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);

        input_fisik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {



                String a = nama_user.getText().toString();
                String b = posisi.getText().toString();
                String c = perusahaan.getText().toString();

                Intent i = new Intent(getApplicationContext(), Ada_gabung.class);
                i.putExtra("nama_user", "" + a + "");
                i.putExtra("posisi", "" + b + "");
                i.putExtra("perusahaan", "" + c + "");



                startActivity(i);


            }

        });


        input_elektrik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {



                String a = nama_user.getText().toString();
                String b = posisi.getText().toString();
                String c = perusahaan.getText().toString();

                Intent i = new Intent(getApplicationContext(), Mau_saldo_apa.class);
                i.putExtra("nama_user", "" + a + "");
                i.putExtra("posisi", "" + b + "");
                i.putExtra("perusahaan", "" + c + "");



                startActivity(i);


            }

        });


        Intent i = getIntent();
        String kiriman = i.getStringExtra("nama_user");
        nama_user.setText(kiriman);
        String kiriman2 = i.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman3);


    }
}