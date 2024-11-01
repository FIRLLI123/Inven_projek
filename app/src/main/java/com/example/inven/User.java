package com.example.inven;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class User extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Context context;


EditText username,password,nama,posisi,perusahaan,nomor,alamat,tanggalpembelian,sales,bidang;
TextView id_user;

Button ubah, simpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        id_user = (TextView) findViewById(R.id.id_user);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        nama = (EditText) findViewById(R.id.nama);
        posisi = (EditText) findViewById(R.id.posisi);
        perusahaan = (EditText) findViewById(R.id.perusahaan);
        nomor = (EditText) findViewById(R.id.nomor);
        alamat = (EditText) findViewById(R.id.alamat);
        tanggalpembelian = (EditText) findViewById(R.id.tanggalpembelian);
        sales = (EditText) findViewById(R.id.sales);
        bidang = (EditText) findViewById(R.id.bidang);


        simpan = (Button) findViewById(R.id.simpan);

        context = User.this;
        pDialog = new ProgressDialog(context);



        simpan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(sales.getText().toString().equals("null")){

                    Toast.makeText(getApplicationContext(), "Koneksi mu lemah, kembali ke halaman sebelumnya", Toast.LENGTH_LONG).show();

                }else if(password.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Kolom masih kosong", Toast.LENGTH_LONG).show();

                }else if(nomor.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Kolom masih kosong", Toast.LENGTH_LONG).show();

                }else if(alamat.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Kolom masih kosong", Toast.LENGTH_LONG).show();

                }else{

                    update_data();

                }


            }


        });


        Intent i = getIntent();
        String kiriman = i.getStringExtra("id");
        id_user.setText(kiriman);

        //user();
        list_id();





    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void update_data() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "ubah_owner.php")
                .addBodyParameter("id", id_user.getText().toString())

//                .addBodyParameter("harga", hargabarang1.getText().toString())
//                .addBodyParameter("total", total1.getText().toString())
//                .addBodyParameter("keterangan", keterangan1.getText().toString())
//                .addBodyParameter("jam", jaminput1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.d("response", response.toString());

                        if (response.optString("response").toString().equals("success")) {
                            hideDialog();
                            //gotoCourseActivity();
                            Toast.makeText(getApplicationContext(), "BERHASIL DISIMPAN",
                                    Toast.LENGTH_LONG).show();


                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }



    private void list_id() {

        AndroidNetworking.post(Config.host + "tampil_karyawan_id.php")
                .addBodyParameter("id", id_user.getText().toString())
                //.addBodyParameter("perusahaan", perusahaan.getText().toString())

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
                        username.setText((response.optString("username")));
                        password.setText((response.optString("password")));
                        nama.setText((response.optString("nama")));
                        posisi.setText((response.optString("posisi")));
                        perusahaan.setText((response.optString("perusahaan")));
                        nomor.setText((response.optString("nomor")));
                        alamat.setText((response.optString("alamat")));
                        tanggalpembelian.setText((response.optString("tanggalpembelian")));
                        sales.setText((response.optString("sales")));
                        bidang.setText((response.optString("bidang")));
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



}