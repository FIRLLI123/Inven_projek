package com.example.inven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class M_stok extends AppCompatActivity {
    LinearLayout stok_fisik, stok_saldo, riwayat_stok_fisik, item_elektrik;
    TextView nama_user, posisi, perusahaan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_stok);

        stok_fisik = (LinearLayout) findViewById(R.id.stok_fisik);//1
        stok_saldo = (LinearLayout) findViewById(R.id.stok_saldo);//1

        riwayat_stok_fisik = (LinearLayout) findViewById(R.id.riwayat_stok_fisik);//1
        item_elektrik = (LinearLayout) findViewById(R.id.item_elektrik);//1


        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);


        stok_fisik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {




                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), isi_stok.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");

                    startActivity(i);

                }


            }

        });


        stok_saldo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Isi_saldo.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");

                    startActivity(i);

                }


            }

        });
            item_elektrik.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {


                    if (nama_user.getText().toString().equals("null")) {

                        Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                    } else {

                        String a = nama_user.getText().toString();
                        String b = posisi.getText().toString();
                        String c = perusahaan.getText().toString();

                        Intent i = new Intent(getApplicationContext(), Isi_elektrik.class);
                        i.putExtra("nama_user", "" + a + "");
                        i.putExtra("posisi", "" + b + "");
                        i.putExtra("perusahaan", "" + c + "");

                        startActivity(i);

                }


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