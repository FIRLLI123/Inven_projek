package com.example.inven;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class Isi_saldo extends AppCompatActivity {
    TextView nama_saldo, sisa_saldo;
    ListView listdataoutlet1;
    Button caridatabarang, tambah_item;
    EditText namabarang;

    TextView nama_user, posisi, perusahaan;

    TextView multi, saldo1;

    LinearLayout klik_tanggal, cari_tanggal;
    TextView tanggal_awal, tanggal;

    LinearLayout blank_gambar;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    ImageView migrasi;

    public static String id_saldolist, nama_saldolist, sisa_saldolist, hargamodallist, hargalist, sisastoklist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isi_saldo);


        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        nama_saldolist = "";
        sisa_saldolist = "";

        //multi = (TextView) findViewById(R.id.multi);//1
        saldo1 = (TextView) findViewById(R.id.saldo1);//1


        nama_saldo = (TextView) findViewById(R.id.nama_saldo);
        sisa_saldo = (TextView) findViewById(R.id.sisa_saldo);

        namabarang = (EditText) findViewById(R.id.namabarang);

        tambah_item = (Button) findViewById(R.id.tambah_item);


        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView) findViewById(R.id.posisi);
        perusahaan = (TextView) findViewById(R.id.perusahaan);


        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        caridatabarang = (Button) findViewById(R.id.caridatabarang);
        //cekdetail1 = (Button) findViewById(R.id.cekdetail)

        klik_tanggal = (LinearLayout) findViewById(R.id.klik_tanggal);
        cari_tanggal = (LinearLayout) findViewById(R.id.cari_tanggal);
        tanggal_awal = (TextView) findViewById(R.id.tanggal_awal);
        tanggal = (TextView) findViewById(R.id.tanggal);

        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);

        migrasi = (ImageView) findViewById(R.id.migrasi);

        tanggal.setText(getCurrentDate());

        caridatabarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });


        klik_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        cari_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
                cash();
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

                    Intent i = new Intent(getApplicationContext(), Input_item_saldo.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");

                    startActivity(i);

                } else {

                    Toast.makeText(getApplicationContext(), "AKSES DIBATASI", Toast.LENGTH_LONG).show();

                }

            }
        });


        migrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else if (posisi.getText().toString().equals("Owner")) {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Input_migrasi_saldo.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");

                    startActivity(i);

                } else {

                    Toast.makeText(getApplicationContext(), "AKSES DIBATASI", Toast.LENGTH_LONG).show();

                }

            }
        });

        list();


        namabarang.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)

                    list();

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
        cash();

    }


    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
//        int year, month, day;
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DATE);
        //SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        //tanggalinputperdana1.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;


    }


    private void showDateDialog() {

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tanggal.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    private void cash() {

        AndroidNetworking.post(Config.host + "cash.php")
                //.addBodyParameter("nama_beli", multi.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                .addBodyParameter("tanggalawal", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalakhir", tanggal.getText().toString())
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
                        saldo1.setText((response.optString("sisa_cash")));
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


    private void list() {

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "data_saldo_ambil_tanggal.php")
//                .addBodyParameter("tanggal", namaoutlet1.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                .addBodyParameter("tanggalawal", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalakhir", tanggal.getText().toString())
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
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));

                                map.put("namaitem", responses.optString("namaitem"));
                                map.put("sisa_saldo", responses.optString("sisa_saldo"));


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

    private void Adapter() {
            if (aruskas.isEmpty()) {
                // Jika aruskas kosong, tampilkan blank_gambar
                // Misalnya:
                blank_gambar.setVisibility(View.VISIBLE);
                listdataoutlet1.setVisibility(View.GONE);
            } else {
                blank_gambar.setVisibility(View.GONE);
                listdataoutlet1.setVisibility(View.VISIBLE);


                SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_saldo_ambil,
                        new String[]{"namaitem", "sisa_saldo"},
                        new int[]{R.id.nama_saldolist, R.id.sisa_saldolist});

                listdataoutlet1.setAdapter(simpleAdapter);

                listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                        //id_saldolist    = ((TextView) view.findViewById(R.id.nama_saldolist)).getText().toString();
                        nama_saldolist = ((TextView) view.findViewById(R.id.nama_saldolist)).getText().toString();
                        sisa_saldolist = ((TextView) view.findViewById(R.id.sisa_saldolist)).getText().toString();

//                idoutletlist  = ((TextView) view.findViewById(R.id.idoutletlistdataoutlet)).getText().toString();
//                namasaleslist  = ((TextView) view.findViewById(R.id.namasaleslistdataoutlet)).getText().toString();
//
//

                        sisa_saldolist = sisa_saldolist.replace(",", "");

                        nama_saldo.setText(nama_saldolist);
                        sisa_saldo.setText(sisa_saldolist);


                        if (posisi.getText().toString().equals("Owner")) {


                            String a = nama_saldo.getText().toString();
                            String b = sisa_saldo.getText().toString();
                            String c = nama_user.getText().toString();
                            String d = posisi.getText().toString();
                            String e = perusahaan.getText().toString();

                            Intent i = new Intent(getApplicationContext(), Input_isi_saldo.class);
                            i.putExtra("namaitem", "" + a + "");
                            i.putExtra("qtysebelumnya", "" + b + "");
                            i.putExtra("nama_user", "" + c + "");
                            i.putExtra("posisi", "" + d + "");
                            i.putExtra("perusahaan", "" + e + "");
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "AKSES DI BATASI", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        }
    }
