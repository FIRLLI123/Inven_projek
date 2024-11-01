package com.example.inven;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.inven.helper.Config;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class MenuDashboard extends AppCompatActivity {

    private ProgressDialog pDialog;
    private Context context;

    LinearLayout ada_pembeli, stok, rekapan;
    TextView saldo1, saldo2,dana,multi,selengkapnya_saldo;

    TextView username, nama_user, posisi, perusahaan;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_dashboard);


        context = MenuDashboard.this;
        pDialog = new ProgressDialog(context);

        ada_pembeli = (LinearLayout) findViewById(R.id.ada_pembeli);//1
        stok = (LinearLayout) findViewById(R.id.stok);//1
        rekapan = (LinearLayout) findViewById(R.id.rekapan);//1

        selengkapnya_saldo = (TextView) findViewById(R.id.selengkapnya_saldo);//1

        saldo1 = (TextView) findViewById(R.id.saldo1);//1
        saldo2 = (TextView) findViewById(R.id.saldo2);//1

        multi = (TextView) findViewById(R.id.multi);//1
        dana = (TextView) findViewById(R.id.dana);//1

        username = (TextView) findViewById(R.id.username);//1
        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView) findViewById(R.id.posisi);//1
        perusahaan = (TextView) findViewById(R.id.perusahaan);//1



        this.mHandler = new Handler();
        m_Runnable.run();


        Intent i = getIntent();
        String kiriman = i.getStringExtra("username");
        username.setText(kiriman);


        ada_pembeli.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), M_input.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");


                    startActivity(i);


                }

            }

        });


        stok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), M_stok.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");

                    startActivity(i);

                }


            }

        });



        rekapan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Rekapan.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");

                    startActivity(i);

                }


            }

        });



        selengkapnya_saldo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {




                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_saldo.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");

                    startActivity(i);

                }


            }

        });




        multi();
        dana();
        user();


    }


    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();


            //prosesdasboard();
            multi();
            dana();
            user();

            MenuDashboard.this.mHandler.postDelayed(m_Runnable, 1000);
        }

    };


    private void multi() {

        AndroidNetworking.post(Config.host + "saldo_depan_multi.php")
                .addBodyParameter("nama_beli", multi.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                //.addBodyParameter("dana", dana.getText().toString())

                //.addBodyParameter("bulan", bulan1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutlet1.setText((response.optString("idoutlet")));
                        //namaoutlet1.setText((response.optString("namaoutlet")));
                        saldo1.setText((response.optString("sisa_saldo")));
                        //saldo2.setText((response.optString("saldo2")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void dana() {

        AndroidNetworking.post(Config.host + "saldo_depan_dana.php")
                .addBodyParameter("nama_beli", dana.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                //.addBodyParameter("dana", dana.getText().toString())

                //.addBodyParameter("bulan", bulan1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutlet1.setText((response.optString("idoutlet")));
                        //namaoutlet1.setText((response.optString("namaoutlet")));
                        saldo2.setText((response.optString("sisa_saldo")));
                        //saldo2.setText((response.optString("saldo2")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void user() {
//        pDialog.setMessage("Verifikasi...");
//        showDialog();
//        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "user_depan.php")
                .addBodyParameter("username", username.getText().toString())
                //.addBodyParameter("dana", dana.getText().toString())

                //.addBodyParameter("bulan", bulan1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutlet1.setText((response.optString("idoutlet")));
                        //username.setText((response.optString("username")));
                        nama_user.setText((response.optString("nama")));
                        posisi.setText((response.optString("posisi")));
                        perusahaan.setText((response.optString("perusahaan")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                        //hideDialog();

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

}