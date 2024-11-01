package com.example.inven;


import android.content.Intent;
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

public class Data_saldo extends AppCompatActivity {
    TextView nama_saldo, sisa_saldo;
    ListView listdataoutlet1;
    Button caridatabarang;
    EditText namabarang;
    LinearLayout blank_gambar;

    TextView nama_user, posisi, perusahaan;

    public static String LINK, nama_saldolist, sisa_saldolist, hargamodallist, hargalist, sisastoklist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_saldo);

        LINK = Config.host + "history.php";
        nama_saldolist = "";
        sisa_saldolist = "";


        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);

        perusahaan = (TextView)findViewById(R.id.perusahaan);

        nama_saldo = (TextView) findViewById(R.id.nama_saldo);
        sisa_saldo = (TextView) findViewById(R.id.sisa_saldo);

        namabarang = (EditText) findViewById(R.id.namabarang);








        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        caridatabarang = (Button) findViewById(R.id.caridatabarang);
        //cekdetail1 = (Button) findViewById(R.id.cekdetail)


        caridatabarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
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

        Intent i = getIntent();
        String kiriman = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman);

        list();

    }




    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "data_saldo_ambil.php" )
//                .addBodyParameter("tanggal", namaoutlet1.getText().toString())
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

                                map.put("namaitem",         responses.optString("namaitem"));
                                map.put("sisa_saldo",       responses.optString("sisa_saldo"));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_saldo_ambil,
                new String[] {"namaitem","sisa_saldo"},
                new int[] {R.id.nama_saldolist,R.id.sisa_saldolist});

        listdataoutlet1.setAdapter(simpleAdapter);

        listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                nama_saldolist    = ((TextView) view.findViewById(R.id.nama_saldolist)).getText().toString();
                sisa_saldolist    = ((TextView) view.findViewById(R.id.sisa_saldolist)).getText().toString();

//                idoutletlist  = ((TextView) view.findViewById(R.id.idoutletlistdataoutlet)).getText().toString();
//                namasaleslist  = ((TextView) view.findViewById(R.id.namasaleslistdataoutlet)).getText().toString();
//
//
                sisa_saldolist = sisa_saldolist.replace(",", "");

                nama_saldo.setText(nama_saldolist);
                sisa_saldo.setText(sisa_saldolist);





                if (sisa_saldo.getText().toString().equals("0")) {
                    if (nama_saldo.getText().toString().equals("QRIS")) {
                        String a = nama_saldo.getText().toString();
                        String b = sisa_saldo.getText().toString();

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("namaitem", a);
                        resultIntent.putExtra("sisa_saldo", b);

                        setResult(RESULT_OK, resultIntent);
                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), "Stok Kosong", Toast.LENGTH_LONG).show();
                        // Tindakan jika sisa_saldo = 0 namun nama_saldo bukan "qrish"
                        // Tidak melakukan apa pun atau menampilkan pesan sesuai kebutuhan
                    }
                } else {
                    // Tindakan jika sisa_saldo tidak sama dengan 0
                    String a = nama_saldo.getText().toString();
                    String b = sisa_saldo.getText().toString();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("namaitem", a);
                    resultIntent.putExtra("sisa_saldo", b);

                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

            }
        });
    }
}
}