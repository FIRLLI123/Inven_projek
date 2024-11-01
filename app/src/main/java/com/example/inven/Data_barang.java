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

public class Data_barang extends AppCompatActivity {
    TextView code, namaitem, hargamodal, totalhargamodal, harga, sisastok;
    ListView listdataoutlet1;
    LinearLayout caridatabarang;
    EditText namabarang;
    LinearLayout blank_gambar;

    TextView nama_user, posisi, perusahaan, kategori;

    TextView keseluruhan,perdana,voucher,aksesoris,lainnya;
    TextView keseluruhan_k,makanan,minuman,cemilan,lainnya_k;

    public static String LINK, codelist, namaitemlist, hargamodallist, hargalist, sisastoklist, kategorilist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    private ProgressDialog pDialog;
    private Context context;


    TextView bidang;
    LinearLayout outlet, kuliner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_barang);

        LINK = Config.host + "history.php";
        codelist = "";
        namaitemlist = "";
        hargamodallist = "";
        hargalist = "";
        sisastoklist = "";
        kategorilist = "";

        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);

        context = Data_barang.this;
        pDialog = new ProgressDialog(context);


        keseluruhan = (TextView) findViewById(R.id.keseluruhan);
        perdana = (TextView) findViewById(R.id.perdana);
        voucher = (TextView) findViewById(R.id.voucher);
        aksesoris = (TextView) findViewById(R.id.aksesoris);
        lainnya = (TextView) findViewById(R.id.lainnya);

        kategori = (TextView) findViewById(R.id.kategori);
        bidang = (TextView) findViewById(R.id.bidang);


        keseluruhan_k = (TextView) findViewById(R.id.keseluruhan_k);
        makanan = (TextView) findViewById(R.id.makanan);
        minuman = (TextView) findViewById(R.id.minuman);
        cemilan = (TextView) findViewById(R.id.cemilan);
        lainnya_k = (TextView) findViewById(R.id.lainnya_k);

        bidang = (TextView) findViewById(R.id.bidang);
        kuliner = (LinearLayout) findViewById(R.id.kuliner);
        outlet = (LinearLayout) findViewById(R.id.outlet);


        code = (TextView) findViewById(R.id.code);
        namaitem = (TextView) findViewById(R.id.namaitem);
        hargamodal = (TextView) findViewById(R.id.hargamodal);

        harga = (TextView) findViewById(R.id.harga);
        sisastok = (TextView) findViewById(R.id.sisastok);



        namabarang = (EditText) findViewById(R.id.namabarang);
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

        //list();


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

        keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom11));
        keseluruhan_k.setTextColor(getResources().getColor(R.color.birutua));

        makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
        makanan.setTextColor(getResources().getColor(R.color.abutua));

        minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
        minuman.setTextColor(getResources().getColor(R.color.abutua));

        cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
        cemilan.setTextColor(getResources().getColor(R.color.abutua));

        lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
        lainnya_k.setTextColor(getResources().getColor(R.color.abutua));



        //------------------------------------------------------------------------

        keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
        keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

        perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
        perdana.setTextColor(getResources().getColor(R.color.abutua));

        voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
        voucher.setTextColor(getResources().getColor(R.color.abutua));

        aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
        aksesoris.setTextColor(getResources().getColor(R.color.abutua));

        lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
        lainnya.setTextColor(getResources().getColor(R.color.abutua));


        keseluruhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("");

                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
                keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

                perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
                perdana.setTextColor(getResources().getColor(R.color.abutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
                voucher.setTextColor(getResources().getColor(R.color.abutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
                aksesoris.setTextColor(getResources().getColor(R.color.abutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("perdana");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                perdana.setBackground(getResources().getDrawable(R.drawable.custom11));
                perdana.setTextColor(getResources().getColor(R.color.birutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
                voucher.setTextColor(getResources().getColor(R.color.abutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
                aksesoris.setTextColor(getResources().getColor(R.color.abutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("voucher");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
                perdana.setTextColor(getResources().getColor(R.color.abutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom11));
                voucher.setTextColor(getResources().getColor(R.color.birutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
                aksesoris.setTextColor(getResources().getColor(R.color.abutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });

        aksesoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("aksesoris");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
                perdana.setTextColor(getResources().getColor(R.color.abutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
                voucher.setTextColor(getResources().getColor(R.color.abutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom11));
                aksesoris.setTextColor(getResources().getColor(R.color.birutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });



        lainnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("lainnya");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
                perdana.setTextColor(getResources().getColor(R.color.abutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
                voucher.setTextColor(getResources().getColor(R.color.abutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
                aksesoris.setTextColor(getResources().getColor(R.color.abutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom11));
                lainnya.setTextColor(getResources().getColor(R.color.birutua));

                prosesdasboard1();

            }
        });


        //-----------------------------------------------------------------------------



        keseluruhan_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("");

                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom11));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.birutua));

                makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                makanan.setTextColor(getResources().getColor(R.color.abutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
                minuman.setTextColor(getResources().getColor(R.color.abutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
                cemilan.setTextColor(getResources().getColor(R.color.abutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya_k.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("makanan");
                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.abutua));



                makanan.setBackground(getResources().getDrawable(R.drawable.custom11));
                makanan.setTextColor(getResources().getColor(R.color.birutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
                minuman.setTextColor(getResources().getColor(R.color.abutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
                cemilan.setTextColor(getResources().getColor(R.color.abutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya_k.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("minuman");
                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.abutua));



                makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                makanan.setTextColor(getResources().getColor(R.color.abutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom11));
                minuman.setTextColor(getResources().getColor(R.color.birutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
                cemilan.setTextColor(getResources().getColor(R.color.abutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya_k.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });

        cemilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("cemilan");
                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.abutua));



                makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                makanan.setTextColor(getResources().getColor(R.color.abutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
                minuman.setTextColor(getResources().getColor(R.color.abutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom11));
                cemilan.setTextColor(getResources().getColor(R.color.birutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya_k.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });



        lainnya_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("lainnya");
                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.abutua));



                makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                makanan.setTextColor(getResources().getColor(R.color.abutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
                minuman.setTextColor(getResources().getColor(R.color.abutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
                cemilan.setTextColor(getResources().getColor(R.color.abutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom11));
                lainnya_k.setTextColor(getResources().getColor(R.color.birutua));

                prosesdasboard1();

            }
        });



        Intent i = getIntent();
        String kiriman = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman);
        String kiriman2 = i.getStringExtra("bidang");
        bidang.setText(kiriman2);

        prosesdasboard1();

        //list();

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

                if(bidang.getText().toString().equals("Kuliner")){

                    kuliner.setVisibility(View.VISIBLE);
                    outlet.setVisibility(View.GONE);

                }else{

                    kuliner.setVisibility(View.GONE);
                    outlet.setVisibility(View.VISIBLE);

                }

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
        AndroidNetworking.post( Config.host + "data_barang_ambil.php" )
                .addBodyParameter("kategori", kategori.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                .addBodyParameter("namaitem", namabarang.getText().toString())
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
                                map.put("code",         responses.optString("code"));
                                map.put("namaitem",         responses.optString("namaitem"));
                                map.put("modal_ubah",       responses.optString("modal_ubah"));
                                map.put("harga_ubah",       responses.optString("harga_ubah"));
                                map.put("sisastok",       responses.optString("sisastok"));
                                map.put("kategori",       responses.optString("kategori"));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_barang_ambil,
                new String[] {"code","namaitem","modal_ubah","harga_ubah","sisastok","kategori"},
                new int[] {R.id.code_list,R.id.namaitem_list,R.id.hargamodal_list,R.id.harga_list, R.id.sisa_stok_list, R.id.kategori_list});

        listdataoutlet1.setAdapter(simpleAdapter);

        listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                codelist = ((TextView) view.findViewById(R.id.code_list)).getText().toString();
                namaitemlist = ((TextView) view.findViewById(R.id.namaitem_list)).getText().toString();
                hargamodallist = ((TextView) view.findViewById(R.id.hargamodal_list)).getText().toString();
                hargalist = ((TextView) view.findViewById(R.id.harga_list)).getText().toString();
                sisastoklist = ((TextView) view.findViewById(R.id.sisa_stok_list)).getText().toString();
                kategorilist = ((TextView) view.findViewById(R.id.kategori_list)).getText().toString();

                // Remove commas from the string values
                hargamodallist = hargamodallist.replace(",", "");
                hargalist = hargalist.replace(",", "");
                sisastoklist = sisastoklist.replace(",", "");

                // Parse string values to numbers
//                double hargamodalValue = Double.parseDouble(hargamodallist);
//                double hargaValue = Double.parseDouble(hargalist);
//                int sisastokValue = Integer.parseInt(sisastoklist);

                // Set the parsed values to the appropriate TextViews
                code.setText(codelist);
                namaitem.setText(namaitemlist);
                hargamodal.setText(String.valueOf(hargamodallist)); // Set as plain numeric value
                harga.setText(String.valueOf(hargalist)); // Set as plain numeric value
                sisastok.setText(String.valueOf(sisastoklist)); // Set as plain numeric value
                kategori.setText(kategorilist);



                if (Integer.parseInt(sisastok.getText().toString()) < 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //code.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Stok Kosong", Toast.LENGTH_LONG).show();
                }else{

                    String a = code.getText().toString();
                    String b = namaitem.getText().toString();
                    String c = hargamodal.getText().toString();
                    String d = harga.getText().toString();
                    String e = sisastok.getText().toString();
                    String f = kategori.getText().toString();
////                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
////                i.putExtra("namaoutlet",""+a+"");
////                i.putExtra("idoutlet",""+b+"");
////                i.putExtra("namasales",""+c+"");
////                i.putExtra("nama",""+d+"");
////                startActivity(i);
//

//
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("code",""+a+"");
                    resultIntent.putExtra("namaitem",""+b+"");
                    resultIntent.putExtra("hargamodal",""+c+"");
                    resultIntent.putExtra("harga",""+d+"");
                    resultIntent.putExtra("sisastok",""+e+"");
                    resultIntent.putExtra("kategori",""+f+"");
                    //resultIntent.putExtra("selectedBarang", selectedBarang);
                    setResult(RESULT_OK, resultIntent);
                    finish();

            }

        }
        });
    }
}
}