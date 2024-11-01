package com.example.inven;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
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

/*
import id.coretech.printerproject.API.Server;
import id.coretech.printerproject.Adapter.Adapter_Bayar;
import id.coretech.printerproject.App.AppController;
import id.coretech.printerproject.Data.Data_Bayar;
import id.coretech.printerproject.Until.BluetoothHandler;
import id.coretech.printerproject.Until.PrinterCommands;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
**/

public class Isi_elektrik extends AppCompatActivity {
    TextView nama_beli, hargamodal, harga;
    ListView listdataoutlet1;
    LinearLayout caridatabarang;
    EditText namabarang;

    TextView iditem, nama_user, posisi, perusahaan;

    TextView semua,pulsa,paket,tagihan,transfer,topup, kategori;

    Button tambah_item;

    LinearLayout blank_gambar;

    public static String LINK, nama_belilist, hargamodallist, hargalist, iditemlist, kategorilist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    private ProgressDialog pDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isi_elektrik);

        LINK = Config.host + "history.php";
        iditemlist = "";
        nama_belilist = "";
        hargamodallist = "";
        hargalist = "";
        kategorilist = "";

        context = Isi_elektrik.this;
        pDialog = new ProgressDialog(context);

        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);

        semua = (TextView) findViewById(R.id.semua);
        pulsa = (TextView) findViewById(R.id.pulsa);
        paket = (TextView) findViewById(R.id.paket);
        tagihan = (TextView) findViewById(R.id.tagihan);
        transfer = (TextView) findViewById(R.id.transfer);
        topup = (TextView) findViewById(R.id.topup);
        kategori = (TextView) findViewById(R.id.kategori);

        iditem = (TextView) findViewById(R.id.iditem);
        nama_beli = (TextView) findViewById(R.id.nama_beli);
        hargamodal = (TextView) findViewById(R.id.hargamodal);
        harga = (TextView) findViewById(R.id.harga);

        namabarang = (EditText) findViewById(R.id.namabarang);

        tambah_item = (Button) findViewById(R.id.tambah_item);


        nama_user = (TextView)findViewById(R.id.nama_user);
        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);





        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        caridatabarang = (LinearLayout) findViewById(R.id.caridatabarang);
        //cekdetail1 = (Button) findViewById(R.id.cekdetail)





        caridatabarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        tambah_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else if (posisi.getText().toString().equals("Owner")) {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Input_item_elektrik.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");

                    startActivity(i);

                }else{

                    Toast.makeText(getApplicationContext(), "AKSES DIBATASI", Toast.LENGTH_LONG).show();

                }

            }

        });

        list();


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








        kategori.setText("");

        semua.setBackground(getResources().getDrawable(R.drawable.custom11));
        semua.setTextColor(getResources().getColor(R.color.birutua));

        pulsa.setBackground(getResources().getDrawable(R.drawable.custom10));
        pulsa.setTextColor(getResources().getColor(R.color.abutua));

        paket.setBackground(getResources().getDrawable(R.drawable.custom10));
        paket.setTextColor(getResources().getColor(R.color.abutua));

        tagihan.setBackground(getResources().getDrawable(R.drawable.custom10));
        tagihan.setTextColor(getResources().getColor(R.color.abutua));

        topup.setBackground(getResources().getDrawable(R.drawable.custom10));
        topup.setTextColor(getResources().getColor(R.color.abutua));

        transfer.setBackground(getResources().getDrawable(R.drawable.custom10));
        transfer.setTextColor(getResources().getColor(R.color.abutua));



        semua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("");

                semua.setBackground(getResources().getDrawable(R.drawable.custom11));
                semua.setTextColor(getResources().getColor(R.color.birutua));

                pulsa.setBackground(getResources().getDrawable(R.drawable.custom10));
                pulsa.setTextColor(getResources().getColor(R.color.abutua));

                paket.setBackground(getResources().getDrawable(R.drawable.custom10));
                paket.setTextColor(getResources().getColor(R.color.abutua));

                tagihan.setBackground(getResources().getDrawable(R.drawable.custom10));
                tagihan.setTextColor(getResources().getColor(R.color.abutua));

                topup.setBackground(getResources().getDrawable(R.drawable.custom10));
                topup.setTextColor(getResources().getColor(R.color.abutua));

                transfer.setBackground(getResources().getDrawable(R.drawable.custom10));
                transfer.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        pulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("pulsa");
                semua.setBackground(getResources().getDrawable(R.drawable.custom10));
                semua.setTextColor(getResources().getColor(R.color.abutua));



                pulsa.setBackground(getResources().getDrawable(R.drawable.custom11));
                pulsa.setTextColor(getResources().getColor(R.color.birutua));

                paket.setBackground(getResources().getDrawable(R.drawable.custom10));
                paket.setTextColor(getResources().getColor(R.color.abutua));

                tagihan.setBackground(getResources().getDrawable(R.drawable.custom10));
                tagihan.setTextColor(getResources().getColor(R.color.abutua));

                topup.setBackground(getResources().getDrawable(R.drawable.custom10));
                topup.setTextColor(getResources().getColor(R.color.abutua));

                transfer.setBackground(getResources().getDrawable(R.drawable.custom10));
                transfer.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        paket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("paket");
                semua.setBackground(getResources().getDrawable(R.drawable.custom10));
                semua.setTextColor(getResources().getColor(R.color.abutua));



                pulsa.setBackground(getResources().getDrawable(R.drawable.custom10));
                pulsa.setTextColor(getResources().getColor(R.color.abutua));

                paket.setBackground(getResources().getDrawable(R.drawable.custom11));
                paket.setTextColor(getResources().getColor(R.color.birutua));

                tagihan.setBackground(getResources().getDrawable(R.drawable.custom10));
                tagihan.setTextColor(getResources().getColor(R.color.abutua));

                topup.setBackground(getResources().getDrawable(R.drawable.custom10));
                topup.setTextColor(getResources().getColor(R.color.abutua));

                transfer.setBackground(getResources().getDrawable(R.drawable.custom10));
                transfer.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });

        tagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("tagihan");
                semua.setBackground(getResources().getDrawable(R.drawable.custom10));
                semua.setTextColor(getResources().getColor(R.color.abutua));



                pulsa.setBackground(getResources().getDrawable(R.drawable.custom10));
                pulsa.setTextColor(getResources().getColor(R.color.abutua));

                paket.setBackground(getResources().getDrawable(R.drawable.custom10));
                paket.setTextColor(getResources().getColor(R.color.abutua));

                tagihan.setBackground(getResources().getDrawable(R.drawable.custom11));
                tagihan.setTextColor(getResources().getColor(R.color.birutua));

                topup.setBackground(getResources().getDrawable(R.drawable.custom10));
                topup.setTextColor(getResources().getColor(R.color.abutua));

                transfer.setBackground(getResources().getDrawable(R.drawable.custom10));
                transfer.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });



        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("top up");
                semua.setBackground(getResources().getDrawable(R.drawable.custom10));
                semua.setTextColor(getResources().getColor(R.color.abutua));



                pulsa.setBackground(getResources().getDrawable(R.drawable.custom10));
                pulsa.setTextColor(getResources().getColor(R.color.abutua));

                paket.setBackground(getResources().getDrawable(R.drawable.custom10));
                paket.setTextColor(getResources().getColor(R.color.abutua));

                tagihan.setBackground(getResources().getDrawable(R.drawable.custom10));
                tagihan.setTextColor(getResources().getColor(R.color.abutua));

                topup.setBackground(getResources().getDrawable(R.drawable.custom11));
                topup.setTextColor(getResources().getColor(R.color.birutua));

                transfer.setBackground(getResources().getDrawable(R.drawable.custom10));
                transfer.setTextColor(getResources().getColor(R.color.abutua));


                prosesdasboard1();

            }
        });


        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("transfer");
                semua.setBackground(getResources().getDrawable(R.drawable.custom10));
                semua.setTextColor(getResources().getColor(R.color.abutua));



                pulsa.setBackground(getResources().getDrawable(R.drawable.custom10));
                pulsa.setTextColor(getResources().getColor(R.color.abutua));

                paket.setBackground(getResources().getDrawable(R.drawable.custom10));
                paket.setTextColor(getResources().getColor(R.color.abutua));

                tagihan.setBackground(getResources().getDrawable(R.drawable.custom10));
                tagihan.setTextColor(getResources().getColor(R.color.abutua));

                topup.setBackground(getResources().getDrawable(R.drawable.custom10));
                topup.setTextColor(getResources().getColor(R.color.abutua));

                transfer.setBackground(getResources().getDrawable(R.drawable.custom11));
                transfer.setTextColor(getResources().getColor(R.color.birutua));


                prosesdasboard1();

            }
        });

        Intent i = getIntent();
        String kiriman = i.getStringExtra("nama_user");
        nama_user.setText(kiriman);
        String kiriman2 = i.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman3);

        list();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void prosesdasboard1(){

        new CountDownTimer(400, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                list();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
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
        AndroidNetworking.post( Config.host + "data_item_elektrik_ambil.php" )
                .addBodyParameter("kategori", kategori.getText().toString())
               .addBodyParameter("perusahaan", perusahaan.getText().toString())
               .addBodyParameter("nama_beli", namabarang.getText().toString())
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
                                map.put("nama_beli",         responses.optString("nama_beli"));
                                map.put("hargamodal",       responses.optString("hargamodal"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("kategori",         responses.optString("kategori"));



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

        if (aruskas.isEmpty()) {
            // Jika aruskas kosong, tampilkan blank_gambar
            // Misalnya:
            blank_gambar.setVisibility(View.VISIBLE);
            listdataoutlet1.setVisibility(View.GONE);
        } else {
            blank_gambar.setVisibility(View.GONE);
            listdataoutlet1.setVisibility(View.VISIBLE);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_item_elektrik_ambil,
                new String[] {"id","nama_beli","hargamodal","harga","kategori"},
                new int[] {R.id.iditemlist,R.id.nama_belilist,R.id.hargamodallist,R.id.hargalist,R.id.kategori_list});

        listdataoutlet1.setAdapter(simpleAdapter);

        listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                nama_belilist    = ((TextView) view.findViewById(R.id.nama_belilist)).getText().toString();
                hargamodallist    = ((TextView) view.findViewById(R.id.hargamodallist)).getText().toString();
                hargalist    = ((TextView) view.findViewById(R.id.hargalist)).getText().toString();
                iditemlist    = ((TextView) view.findViewById(R.id.iditemlist)).getText().toString();
                kategorilist    = ((TextView) view.findViewById(R.id.kategori_list)).getText().toString();

                hargamodallist = hargamodallist.replace(",", "");
                hargalist = hargalist.replace(",", "");



                // Parse string values to numbers
//                double hargamodalValue = Double.parseDouble(hargamodallist);
//                double hargaValue = Double.parseDouble(hargalist);
//                int sisastokValue = Integer.parseInt(sisastoklist);
                // Set the parsed values to the appropriate TextViews

                iditem.setText(iditemlist);

                hargamodal.setText(String.valueOf(hargamodallist)); // Set as plain numeric value
                harga.setText(String.valueOf(hargalist)); // Set as plain numeric value
                nama_beli.setText(String.valueOf(nama_belilist)); // Set as plain numeric value
                kategori.setText(kategorilist);



                if (posisi.getText().toString().equals("Owner")) {

                    String a = iditem.getText().toString();
                    String b = nama_beli.getText().toString();
                    String c = hargamodal.getText().toString();
                    String d = harga.getText().toString();
                    String e = kategori.getText().toString();
                    String f = nama_user.getText().toString();
                    String g = posisi.getText().toString();
                    String h = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Input_isi_elektrik.class);
                    i.putExtra("iditem", "" + a + "");
                    i.putExtra("nama_beli", "" + b + "");
                    i.putExtra("hargamodal", "" + c + "");
                    i.putExtra("harga", "" + d + "");
                    i.putExtra("kategori", "" + e + "");
                    i.putExtra("nama_user", "" + f + "");
                    i.putExtra("posisi", "" + g + "");
                    i.putExtra("perusahaan", "" + h + "");
                    startActivity(i);


                }else{

                    Toast.makeText(getApplicationContext(), "AKSES DI BATASI", Toast.LENGTH_SHORT).show();

                }

            }
        });
        }
    }


    private String removeComma(String input) {
        // Menghapus semua koma atau titik dari string
        return input.replaceAll("[,.]", "");
    }


}