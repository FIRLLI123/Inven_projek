package com.example.inven;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.inven.helper.Config;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Input_item_elektrik extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Context context;

    TextView id_data, nomor, nama_user, posisi, perusahaan;

    EditText namaitem, hargamodal, hargajual, kategori;
    Spinner sp;

    Button input, btnhapus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_item_elektrik);

        sp = (Spinner)findViewById(R.id.spinner);

        nama_user = (TextView) findViewById(R.id.nama_user);
        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);

        namaitem = (EditText) findViewById(R.id.namaitem);
        hargamodal = (EditText) findViewById(R.id.hargamodal);
        hargajual = (EditText) findViewById(R.id.hargajual);
        kategori = (EditText) findViewById(R.id.kategori);

        context = Input_item_elektrik.this;
        pDialog = new ProgressDialog(context);

        input = (Button) findViewById(R.id.input);

        //btnhapus = (Button) findViewById(R.id.btnhapus);


        nama_user = (TextView)findViewById(R.id.nama_user);
        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);


        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if (namaitem.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    namaitem.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }  else if (hargamodal.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    hargamodal.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (hargajual.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    hargajual.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (kategori.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    kategori.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {


                    save();


                }
            }
        });


        hargamodal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed before text changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed during text changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String initialString = editable.toString();

                if (!initialString.isEmpty()) {
                    // Remove all non-digit characters
                    String cleanString = initialString.replaceAll("[^\\d]", "");

                    // Parse the cleaned string to long
                    long parsed;
                    try {
                        parsed = Long.parseLong(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0;
                    }

                    // Format the long value to currency without symbols and decimal places
                    String formatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(parsed);
                    hargamodal.removeTextChangedListener(this);
                    hargamodal.setText(formatted);
                    hargamodal.setSelection(formatted.length());
                    hargamodal.addTextChangedListener(this);
                }
            }
        });



        hargajual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed before text changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed during text changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String initialString = editable.toString();

                if (!initialString.isEmpty()) {
                    // Remove all non-digit characters
                    String cleanString = initialString.replaceAll("[^\\d]", "");

                    // Parse the cleaned string to long
                    long parsed;
                    try {
                        parsed = Long.parseLong(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0;
                    }

                    // Format the long value to currency without symbols and decimal places
                    String formatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(parsed);
                    hargajual.removeTextChangedListener(this);
                    hargajual.setText(formatted);
                    hargajual.setSelection(formatted.length());
                    hargajual.addTextChangedListener(this);
                }
            }
        });





        List<String> item = new ArrayList<>();

        //item.add("--SELECT--");
        item.add("PULSA");
        item.add("PAKET DATA");
        item.add("TAGIHAN");
        item.add("TRANSFER");
        item.add("TOP UP");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Input_item_elektrik.this,android.R.layout.simple_spinner_dropdown_item, item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategori.setText(sp.getSelectedItem().toString());
//                        startActivity(new Intent(date.this,august
//                                .class));



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


    @Override
    public void onBackPressed() {

        String a = nama_user.getText().toString();
        String b = posisi.getText().toString();
        String c = perusahaan.getText().toString();
        Intent intent = new Intent(Input_item_elektrik.this, Isi_elektrik.class);
        intent.putExtra("nama_user", "" + a + "");
        intent.putExtra("posisi", "" + b + "");
        intent.putExtra("perusahaan", "" + c + "");
        startActivity(intent);
        finish();

    }





    private void save() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        String hargamodalFormatted = removeComma(hargamodal.getText().toString());
        String hargaFormatted = removeComma(hargajual.getText().toString());
        AndroidNetworking.post(Config.host + "input_item_elektrik.php")
                //.addBodyParameter("id", id_data.getText().toString())
                .addBodyParameter("nama_beli", namaitem.getText().toString())
                .addBodyParameter("hargamodal", hargamodalFormatted)
                .addBodyParameter("harga", hargaFormatted)
                .addBodyParameter("kategori", kategori.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())





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
                            Toast.makeText(getApplicationContext(), "Berhasil disimpan",
                                    Toast.LENGTH_LONG).show();
                            //nama_pelanggan.setText("Tanpa Nama");


                            String a = nama_user.getText().toString();
                            String b = posisi.getText().toString();
                            String c = perusahaan.getText().toString();
                            Intent intent = new Intent(Input_item_elektrik.this, Isi_elektrik.class);
                            intent.putExtra("nama_user", "" + a + "");
                            intent.putExtra("posisi", "" + b + "");
                            intent.putExtra("perusahaan", "" + c + "");
                            startActivity(intent);
                            finish();


                            //updatestok();
                            //statusiccid.setText("BELUM TERINPUT");
                            //ceklis.setVisibility(View.GONE);
                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "GAGAL MEMASUKAN DATA",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
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


    private String removeComma(String input) {
        // Menghapus semua koma atau titik dari string
        return input.replaceAll("[,.]", "");
    }

}