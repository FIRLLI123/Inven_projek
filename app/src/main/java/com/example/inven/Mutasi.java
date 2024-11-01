package com.example.inven;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.inven.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Mutasi extends AppCompatActivity {
    LinearLayout klik_dari, klik_sampai, cari_tanggal;
    TextView tanggal_dari, tanggal_sampai;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    ListView listinputperdana1;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    private ProgressDialog pDialog;
    private Context context;

    TextView username, nama_user, posisi, perusahaan;

    TextView total_penjualan, total_modal;

    LinearLayout back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mutasi);

        context = Mutasi.this;
        pDialog = new ProgressDialog(context);

        listinputperdana1 = (ListView) findViewById(R.id.listinputperdana);

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);



        klik_dari = (LinearLayout) findViewById(R.id.klik_dari);
        klik_sampai = (LinearLayout) findViewById(R.id.klik_sampai);
        cari_tanggal = (LinearLayout) findViewById(R.id.cari_tanggal);


        tanggal_dari = (TextView) findViewById(R.id.tanggal_dari);
        tanggal_sampai = (TextView) findViewById(R.id.tanggal_sampai);


        total_penjualan = (TextView) findViewById(R.id.total_penjualan);
        total_modal = (TextView) findViewById(R.id.total_modal);

        username = (TextView) findViewById(R.id.username);//1
        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView) findViewById(R.id.posisi);//1
        perusahaan = (TextView) findViewById(R.id.perusahaan);//1


        back = (LinearLayout) findViewById(R.id.back);//1

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }

        });

        klik_dari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        klik_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog2();
            }
        });


        cari_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesdasboard1();
            }
        });


        tanggal_dari.setText(getCurrentDate());
        tanggal_sampai.setText(getCurrentDate());

        Intent i = getIntent();
        String kiriman = i.getStringExtra("username");
        username.setText(kiriman);
        String kiriman1 = i.getStringExtra("nama_user");
        nama_user.setText(kiriman1);
        String kiriman2 = i.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman3);

        summary_mutasi();
        KasAdapter3();

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


    private void showDateDialog(){

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
                tanggal_dari.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }


    private void showDateDialog2(){

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
                tanggal_sampai.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
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


        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);


                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();

                summary_mutasi();
                KasAdapter3();


            }
        }.start();

    }



    private void KasAdapter3(){


        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "lap_mutasi.php" )
                //.addBodyParameter("nomor", nomor.getText().toString())
                //.addBodyParameter("parameter", kategori.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_dari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_sampai.getText().toString())
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

                            int itemCount = jsonArray.length();
                            for (int i = 0; i < itemCount; i++) {

                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("keterangan",         responses.optString("keterangan"));
                                map.put("tanggal",         responses.optString("tanggal"));
                                //map.put("id",         responses.optString("id"));
                                map.put("namaitem",       responses.optString("namaitem"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("hargamodal",       responses.optString("hargamodal"));
                                map.put("totalmodal",       rupiahFormat.format(responses.optDouble("totalmodal")));
//                                map.put("keterangan",       responses.optString("keterangan"));
//                                map.put("pembayaran",       responses.optString("pembayaran"));

                                //map.put("tanggal",      responses.optString("tanggal"));

                                //jumlah1 += itemCount;


                                //jumlah.setText(String.valueOf(itemCount));
                                aruskas.add(map);
                            }


                            Adapter2();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }




    private void Adapter2(){

        CustomAdapter customAdapter = new CustomAdapter(this, aruskas, R.layout.list_data_mutasi,
                new String[] {"keterangan","tanggal","namaitem","qty","hargamodal", "totalmodal"},
                new int[] { R.id.keterangan_list,R.id.tanggal_list,R.id.nama_list, R.id.qty_list, R.id.harga_list, R.id.total_list});

        listinputperdana1.setAdapter(customAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }





        });

        //swipe_refresh.setRefreshing(false);
    }

    private class CustomAdapter extends SimpleAdapter {

        public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            // Get the values from the data

            ImageView upList = view.findViewById(R.id.up_list);
            ImageView downList = view.findViewById(R.id.down_list);

            LinearLayout blankImage = view.findViewById(R.id.blank_g);

            String keterangan = aruskas.get(position).get("keterangan");
            if ("PENJUALAN".equals(keterangan)) {
                upList.setVisibility(View.VISIBLE);
                downList.setVisibility(View.GONE);
            }else if ("ISI STOK TARIK".equals(keterangan)) {
                upList.setVisibility(View.VISIBLE);
                downList.setVisibility(View.GONE);
            } else if ("ISI STOK".equals(keterangan)) {
                upList.setVisibility(View.GONE);
                downList.setVisibility(View.VISIBLE);
            } else {
                upList.setVisibility(View.GONE);
                downList.setVisibility(View.GONE);
            }



            // Check if data is empty
            if (TextUtils.isEmpty(keterangan)) {
                // Set default image if data is empty
                upList.setVisibility(View.GONE);
                downList.setVisibility(View.GONE);
                blankImage.setVisibility(View.VISIBLE);
            } else {
                // Set the appropriate images based on the "keterangan"
                upList.setVisibility("PENJUALAN".equals(keterangan) ? View.VISIBLE : View.GONE);
                downList.setVisibility("ISI STOK".equals(keterangan) ? View.VISIBLE : View.GONE);
                blankImage.setVisibility(View.GONE);
            }

            return view;
        }
    }



    private void summary_mutasi() {

        AndroidNetworking.post(Config.host + "lap_summary_mutasi.php")
                //.addBodyParameter("nama_beli", multi.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_dari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_sampai.getText().toString())
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
                        total_penjualan.setText((response.optString("total")));
                        total_modal.setText((response.optString("totalmodal")));
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



}