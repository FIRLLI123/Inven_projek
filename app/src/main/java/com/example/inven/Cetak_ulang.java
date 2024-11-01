package com.example.inven;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
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
import java.util.Locale;

public class Cetak_ulang extends AppCompatActivity {

    LinearLayout klik_dari, klik_sampai, cari_tanggal;
    TextView tanggal_dari, tanggal_sampai, tanggal;


    ListView listinputperdana1;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    TextView username, nama_user, posisi, perusahaan;
    TextView kategori,semua,fisik,elektrik;

    TextView nomor;

    private ProgressDialog pDialog;
    private Context context;

    LinearLayout blank_gambar;

    public static String idlist, tanggallist, nomorlist;

    LinearLayout back;
    TextView nama_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cetak_ulang);


        idlist = "";
        tanggallist ="";
        nomorlist ="";

        context = Cetak_ulang.this;
        pDialog = new ProgressDialog(context);

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        listinputperdana1 = (ListView) findViewById(R.id.listinputperdana);

        klik_dari = (LinearLayout) findViewById(R.id.klik_dari);
        klik_sampai = (LinearLayout) findViewById(R.id.klik_sampai);
        cari_tanggal = (LinearLayout) findViewById(R.id.cari_tanggal);


        tanggal_dari = (TextView) findViewById(R.id.tanggal_dari);
        tanggal_sampai = (TextView) findViewById(R.id.tanggal_sampai);
        tanggal = (TextView) findViewById(R.id.tanggal);

        nomor = (TextView) findViewById(R.id.nomor);

        kategori = (TextView) findViewById(R.id.kategori);
        semua = (TextView) findViewById(R.id.semua);
        fisik = (TextView) findViewById(R.id.fisik);
        elektrik = (TextView) findViewById(R.id.elektrik);

        username = (TextView) findViewById(R.id.username);//1
        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView) findViewById(R.id.posisi);//1
        perusahaan = (TextView) findViewById(R.id.perusahaan);//1

        nama_input = (TextView) findViewById(R.id.nama_input);//1

        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);


        back = (LinearLayout) findViewById(R.id.back);//1


        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }

        });


        tanggal_dari.setText(getCurrentDate());
        tanggal_sampai.setText(getCurrentDate());

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


        Intent i = getIntent();
        String kiriman = i.getStringExtra("username");
        username.setText(kiriman);
        String kiriman1 = i.getStringExtra("nama_user");
        nama_user.setText(kiriman1);
        String kiriman2 = i.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman3);


        kategori.setText("");

        semua.setBackground(getResources().getDrawable(R.drawable.custom11));
        semua.setTextColor(getResources().getColor(R.color.birutua));

        fisik.setBackground(getResources().getDrawable(R.drawable.custom10));
        fisik.setTextColor(getResources().getColor(R.color.abutua));

        elektrik.setBackground(getResources().getDrawable(R.drawable.custom10));
        elektrik.setTextColor(getResources().getColor(R.color.abutua));


        semua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("");

                semua.setBackground(getResources().getDrawable(R.drawable.custom11));
                semua.setTextColor(getResources().getColor(R.color.birutua));

                fisik.setBackground(getResources().getDrawable(R.drawable.custom10));
                fisik.setTextColor(getResources().getColor(R.color.abutua));

                elektrik.setBackground(getResources().getDrawable(R.drawable.custom10));
                elektrik.setTextColor(getResources().getColor(R.color.abutua));



                prosesdasboard1();

            }
        });


        fisik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("fisik");
                semua.setBackground(getResources().getDrawable(R.drawable.custom10));
                semua.setTextColor(getResources().getColor(R.color.abutua));



                fisik.setBackground(getResources().getDrawable(R.drawable.custom11));
                fisik.setTextColor(getResources().getColor(R.color.birutua));

                elektrik.setBackground(getResources().getDrawable(R.drawable.custom10));
                elektrik.setTextColor(getResources().getColor(R.color.abutua));



                prosesdasboard1();

            }
        });


        elektrik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("elektrik");
                semua.setBackground(getResources().getDrawable(R.drawable.custom10));
                semua.setTextColor(getResources().getColor(R.color.abutua));



                fisik.setBackground(getResources().getDrawable(R.drawable.custom10));
                fisik.setTextColor(getResources().getColor(R.color.abutua));

                elektrik.setBackground(getResources().getDrawable(R.drawable.custom11));
                elektrik.setTextColor(getResources().getColor(R.color.birutua));



                prosesdasboard1();

            }
        });

        prosesdasboard1();



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

        KasAdapter3();
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);


                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();

                //lap_fisik();
                KasAdapter3();


            }
        }.start();

    }




    private void KasAdapter3(){


        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "tampil_sukses.php" )
                //.addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("parameter", kategori.getText().toString())
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
                                map.put("tanggal",         responses.optString("tanggal"));
                                map.put("tanggal_asli",         responses.optString("tanggal_asli"));
                                map.put("nomor",         responses.optString("nomor"));
                                map.put("nama_pelanggan",         responses.optString("nama_pelanggan"));
                                map.put("qty",         responses.optString("qty"));
                                //map.put("parameter",       responses.optString("parameter"));
                                //map.put("qty",       responses.optString("qty"));
                                //map.put("harga",       responses.optString("harga"));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));
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

        if (aruskas.isEmpty()) {
            // Jika aruskas kosong, tampilkan blank_gambar
            // Misalnya:
            blank_gambar.setVisibility(View.VISIBLE);
            listinputperdana1.setVisibility(View.GONE);
        } else {
            blank_gambar.setVisibility(View.GONE);
            listinputperdana1.setVisibility(View.VISIBLE);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_sukses,
                new String[] {"tanggal","tanggal_asli","nomor","nama_pelanggan","qty","total"},
                new int[] {R.id.tanggal_list,R.id.tanggal_asli_list,R.id.nomor_list, R.id.nama_pelanggan_list, R.id.qty_list, R.id.total_list});

        listinputperdana1.setAdapter(simpleAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idDataTextView = (TextView) view.findViewById(R.id.nomor_list);
                TextView tglDataTextView = (TextView) view.findViewById(R.id.tanggal_asli_list);
                ImageView hapuss = (ImageView) view.findViewById(R.id.trash_list);

                hapuss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                nomorlist    = ((TextView) view.findViewById(R.id.nomor_list)).getText().toString();
                tanggallist  = ((TextView) view.findViewById(R.id.tanggal_asli_list)).getText().toString();



                nomor.setText(nomorlist);
                tanggal.setText(tanggallist);



                String a = nomor.getText().toString();
                String b = tanggal.getText().toString();
                String c = nama_user.getText().toString();
                String d = posisi.getText().toString();
                String e = perusahaan.getText().toString();
                String f = username.getText().toString();
                String g = nama_input.getText().toString();

                Intent i = new Intent(getApplicationContext(), Pembayaran_akhir_ulang.class);
                i.putExtra("nomor",""+a+"");
                i.putExtra("tanggal",""+b+"");
                i.putExtra("nama_user",""+c+"");
                i.putExtra("posisi",""+d+"");
                i.putExtra("perusahaan",""+e+"");
                i.putExtra("username",""+f+"");
                i.putExtra("nama_input",""+g+"");
                startActivity(i);





        }
        });
    }
}





    }



