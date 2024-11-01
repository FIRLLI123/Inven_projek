package com.example.inven;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.inven.Data.Data_BayarEX;
import com.example.inven.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Isi_user extends AppCompatActivity {

    Handler mHandler;
    TextView id_user_owner, id_user;
    ListView listdataoutlet1;
    Button tambah_item, ubah_owner;
    EditText namabarang;

    TextView username, nama_user, posisi, perusahaan, kategori;
    LinearLayout caridatabarang;

    public static String id_list;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    private ProgressDialog pDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isi_user);


        this.mHandler = new Handler();
        m_Runnable.run();

        id_list = "";


        context = Isi_user.this;
        pDialog = new ProgressDialog(context);

        id_user_owner = (TextView) findViewById(R.id.id_user_owner);//1
        id_user = (TextView) findViewById(R.id.id_user);//1
        username = (TextView) findViewById(R.id.username);//1
        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);
        kategori = (TextView)findViewById(R.id.kategori);

        tambah_item = (Button) findViewById(R.id.tambah_item);
        ubah_owner = (Button) findViewById(R.id.ubah_owner);
        namabarang = (EditText) findViewById(R.id.namabarang);



        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        caridatabarang = (LinearLayout) findViewById(R.id.caridatabarang);



        namabarang.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)

                    list();

            }
        });

        tambah_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else if (posisi.getText().toString().equals("Owner")) {

                    String a = username.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();
                    String d = id_user_owner.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Input_item_user.class);
                    i.putExtra("username", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("id", "" + d + "");

                    startActivity(i);

                }else{

                    Toast.makeText(getApplicationContext(), "AKSES DIBATASI", Toast.LENGTH_LONG).show();

                }

            }
        });


        caridatabarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list();

            }
        });


        ubah_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else if (posisi.getText().toString().equals("Owner")) {

                    String a = username.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();
                    String d = id_user_owner.getText().toString();

                    Intent i = new Intent(getApplicationContext(), User.class);
                    i.putExtra("username", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("id", "" + d + "");
                    startActivity(i);

                }else{

                    Toast.makeText(getApplicationContext(), "AKSES DIBATASI", Toast.LENGTH_LONG).show();

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
        String kiriman4 = i.getStringExtra("username");
        username.setText(kiriman4);


        //prosesdasboard1();


    }

    private boolean isProsesDashboard1Executed = false;
    private final Runnable m_Runnable = new Runnable() {
        public void run() {

            if (!isProsesDashboard1Executed) {
                prosesdasboard1();
                isProsesDashboard1Executed = true;
            }

            //prosesdasboard1();


            Isi_user.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };



    public void prosesdasboard1(){

        new CountDownTimer(700, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                list();
                user();

            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "tampil_karyawan.php" )
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
/*
                        text_masuk.setText(
                                rupiahFormat.format(response.optDouble("yes")));
                        text_keluar.setText(
                                rupiahFormat.format( response.optDouble("oke") ));
                        text_total.setText(
                                rupiahFormat.format( response.optDouble("saldo") ));
**/
                        try {
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("username",         responses.optString("username"));
                                map.put("password",         responses.optString("password"));
                                map.put("nama",       responses.optString("nama"));
                                map.put("posisi",       responses.optString("posisi"));




                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        ttl.setText("Total : Rp "+formatter.format(total));
//                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_karyawan,
                new String[] {"id","username","password","nama","posisi"},
                new int[] {R.id.idlist,R.id.usernamelist,R.id.passwordlist,R.id.namalist, R.id.posisilist});

        listdataoutlet1.setAdapter(simpleAdapter);

        listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_list = ((TextView) view.findViewById(R.id.idlist)).getText().toString();
                id_user.setText(id_list);



                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else if (posisi.getText().toString().equals("Owner")) {

                    String a = id_user.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Input_isi_user.class);
                    i.putExtra("id", "" + a + "");

                    startActivity(i);

                }else{

                    Toast.makeText(getApplicationContext(), "AKSES DIBATASI", Toast.LENGTH_LONG).show();

                }












            }
        });
        //swipe_refresh.setRefreshing(false);
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
                        //nama_user.setText((response.optString("nama")));
                        //posisi.setText((response.optString("posisi")));
                        id_user_owner.setText((response.optString("id")));
//                        nomor.setText((response.optString("nomor")));
//                        alamat.setText((response.optString("alamat")));
//                        tanggalpembelian.setText((response.optString("tanggalpembelian")));
//                        sales.setText((response.optString("sales")));
//                        bidang.setText((response.optString("bidang")));
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