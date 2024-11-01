package com.example.inven;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.DatePicker;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
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

import android.view.animation.AnimationUtils;

public class Rekapan extends AppCompatActivity {




    Handler mHandler;
    private LinearLayout dropdownView;
    private Animation slideDownAnimation;
    private Animation slideUpAnimation;

    ImageView slide_down, slide_up;

    private ProgressDialog pDialog;
    private Context context;

    TextView kategori;
    TextView semua,fisik,elektrik;

    LinearLayout klik_dari, klik_sampai, cari_tanggal;

    TextView tanggal_dari, tanggal_sampai;

    TextView username, nama_user, posisi, perusahaan, rincian;

    //fisik--------------------
    TextView item_fisik, modal_fisik, penjualan_fisik, margin_fisik;
//----------------------------------------------------------------------------


    //elektrik--------------------
    TextView item_elektrik, modal_elektrik, penjualan_elektrik, margin_elektrik;
//----------------------------------------------------------------------------


    //total
    TextView modal, penjualan, margin;
    TextView cetak_ulang;


    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    LinearLayout blank_gambar, data_dashboard;

    ListView listinputperdana1;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rekapan);


        this.mHandler = new Handler();
        m_Runnable.run();

        context = Rekapan.this;
        pDialog = new ProgressDialog(context);

        dropdownView = findViewById(R.id.dropdown_view);

        // Mendapatkan referensi ke animasi
        slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        slide_down = (ImageView) findViewById(R.id.slide_down);
        slide_up = (ImageView) findViewById(R.id.slide_up);

        listinputperdana1 = (ListView) findViewById(R.id.listinputperdana);

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        kategori = (TextView) findViewById(R.id.kategori);
        semua = (TextView) findViewById(R.id.semua);
        fisik = (TextView) findViewById(R.id.fisik);
        elektrik = (TextView) findViewById(R.id.elektrik);

        rincian = (TextView) findViewById(R.id.rincian);


        klik_dari = (LinearLayout) findViewById(R.id.klik_dari);
        klik_sampai = (LinearLayout) findViewById(R.id.klik_sampai);
        cari_tanggal = (LinearLayout) findViewById(R.id.cari_tanggal);


        tanggal_dari = (TextView) findViewById(R.id.tanggal_dari);
        tanggal_sampai = (TextView) findViewById(R.id.tanggal_sampai);


        item_fisik = (TextView) findViewById(R.id.item_fisik);
        modal_fisik = (TextView) findViewById(R.id.modal_fisik);
        penjualan_fisik = (TextView) findViewById(R.id.penjualan_fisik);
        margin_fisik = (TextView) findViewById(R.id.margin_fisik);




        item_elektrik = (TextView) findViewById(R.id.item_elektrik);
        modal_elektrik = (TextView) findViewById(R.id.modal_elektrik);
        penjualan_elektrik = (TextView) findViewById(R.id.penjualan_elektrik);
        margin_elektrik = (TextView) findViewById(R.id.margin_elektrik);



        modal = (TextView) findViewById(R.id.modal);
        penjualan = (TextView) findViewById(R.id.penjualan);
        margin = (TextView) findViewById(R.id.margin);

        cetak_ulang = (TextView) findViewById(R.id.cetak_ulang);


        username = (TextView) findViewById(R.id.username);//1
        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView) findViewById(R.id.posisi);//1
        perusahaan = (TextView) findViewById(R.id.perusahaan);//1

        data_dashboard = (LinearLayout) findViewById(R.id.data_dashboard);
        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);



        tanggal_dari.setText(getCurrentDate());
        tanggal_sampai.setText(getCurrentDate());

        slide_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slide_up.setVisibility(View.VISIBLE);
                slide_down.setVisibility(View.GONE);
                dropdownView.setVisibility(View.VISIBLE);
                dropdownView.startAnimation(slideDownAnimation);
            }
        });


        slide_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slide_up.setVisibility(View.GONE);
                slide_down.setVisibility(View.VISIBLE);
                dropdownView.startAnimation(slideUpAnimation);
                dropdownView.setVisibility(View.GONE);
            }
        });


        slide_up.setVisibility(View.GONE);
        slide_down.setVisibility(View.VISIBLE);
        dropdownView.startAnimation(slideUpAnimation);
        dropdownView.setVisibility(View.GONE);


        //----------------------------------------------------------------

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


        cetak_ulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = nama_user.getText().toString();
                String b = posisi.getText().toString();
                String c = perusahaan.getText().toString();
                String d = username.getText().toString();

                Intent i = new Intent(getApplicationContext(), Cetak_ulang.class);
                i.putExtra("nama_user", "" + a + "");
                i.putExtra("posisi", "" + b + "");
                i.putExtra("perusahaan", "" + c + "");
                i.putExtra("username", "" + d + "");


                startActivity(i);
            }
        });


        rincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = nama_user.getText().toString();
                String b = posisi.getText().toString();
                String c = perusahaan.getText().toString();
                String d = username.getText().toString();

                Intent i = new Intent(getApplicationContext(), Lap_item_terbanyak.class);
                i.putExtra("nama_user", "" + a + "");
                i.putExtra("posisi", "" + b + "");
                i.putExtra("perusahaan", "" + c + "");
                i.putExtra("username", "" + d + "");


                startActivity(i);
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
        gambar_gerak1();

        if (margin_fisik.getText().toString().equals("null")) {

            data_dashboard.setVisibility(View.GONE);
            blank_gambar.setVisibility(View.VISIBLE);
            //hideDialog();

        }else{
            data_dashboard.setVisibility(View.VISIBLE);
            blank_gambar.setVisibility(View.GONE);
            //hideDialog();
        }

    }


    public void gambar_gerak1(){
        ImageView blank = findViewById(R.id.blank);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(blank);
        Glide.with(this)
                .load(R.drawable.notfound)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageViewTarget);

    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();


            //prosesdasboard();

            //multi();

            //lap_fisik();

            item_fisik = (TextView) findViewById(R.id.item_fisik);
            modal_fisik = (TextView) findViewById(R.id.modal_fisik);
            penjualan_fisik = (TextView) findViewById(R.id.penjualan_fisik);
            margin_fisik = (TextView) findViewById(R.id.margin_fisik);




            item_elektrik = (TextView) findViewById(R.id.item_elektrik);
            modal_elektrik = (TextView) findViewById(R.id.modal_elektrik);
            penjualan_elektrik = (TextView) findViewById(R.id.penjualan_elektrik);
            margin_elektrik = (TextView) findViewById(R.id.margin_elektrik);



            modal = (TextView) findViewById(R.id.modal);
            penjualan = (TextView) findViewById(R.id.penjualan);
            margin = (TextView) findViewById(R.id.margin);


            username = (TextView) findViewById(R.id.username);//1
            nama_user = (TextView) findViewById(R.id.nama_user);//1
            posisi = (TextView) findViewById(R.id.posisi);//1
            perusahaan = (TextView) findViewById(R.id.perusahaan);//1

            data_dashboard = (LinearLayout) findViewById(R.id.data_dashboard);
            blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);


            if (margin_fisik.getText().toString().equals("null")) {

                data_dashboard.setVisibility(View.GONE);
                blank_gambar.setVisibility(View.VISIBLE);
                //hideDialog();

            }else{
                data_dashboard.setVisibility(View.VISIBLE);
                blank_gambar.setVisibility(View.GONE);
                //hideDialog();
            }






            Rekapan.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };


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

                lap_fisik();
                KasAdapter3();

                if (margin_fisik.getText().toString().equals("null")) {

                    data_dashboard.setVisibility(View.GONE);
                    blank_gambar.setVisibility(View.VISIBLE);
                    //hideDialog();

                }else{
                    data_dashboard.setVisibility(View.VISIBLE);
                    blank_gambar.setVisibility(View.GONE);
                    //hideDialog();
                }


            }
        }.start();

    }


    private void lap_fisik() {
//        pDialog.setMessage("Process...");
//        showDialog();
//        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "lap_summary.php")
                .addBodyParameter("parameter", kategori.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_dari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_sampai.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())

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
                        item_fisik.setText((response.optString("id")));
                        modal_fisik.setText((response.optString("totalmodal")));
                        penjualan_fisik.setText((response.optString("total")));
                        margin_fisik.setText((response.optString("margin")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                        hideDialog();

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }






    private void lap_elektrik() {

        AndroidNetworking.post(Config.host + "lap_elektrik.php")
                .addBodyParameter("tanggaldari", tanggal_dari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_sampai.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())

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
                        item_elektrik.setText((response.optString("jumlah_item")));
                        modal_elektrik.setText((response.optString("modal")));
                        penjualan_elektrik.setText((response.optString("penjualan")));
                        margin_elektrik.setText((response.optString("margin")));
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


    private void lap_semua() {

        AndroidNetworking.post(Config.host + "lap_semua.php")
                .addBodyParameter("kategori", kategori.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_dari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_sampai.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())

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
                        modal.setText((response.optString("modal")));
                        penjualan.setText((response.optString("penjualan")));
                        margin.setText((response.optString("margin")));
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




    private void KasAdapter3(){


        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "lap_gabung_revisi.php" )
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
                                map.put("id",         responses.optString("id"));
                                map.put("nama_print",       responses.optString("nama_print"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("namaitem",       responses.optString("namaitem"));
                                //map.put("total",       rupiahFormat.format(responses.optDouble("total")));
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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_laporan,
                new String[] {"tanggal","id","nama_print","qty","harga", "namaitem"},
                new int[] {R.id.tanggal_list,R.id.id_list, R.id.nama_list, R.id.qty_list, R.id.harga_list, R.id.dompet_list});

        listinputperdana1.setAdapter(simpleAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView idDataTextView = (TextView) view.findViewById(R.id.id_list);
                ImageView hapuss = (ImageView) view.findViewById(R.id.hapus_data_list);


                if(posisi.getText().toString().equals("Owner")){

                    hapuss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            showDeleteConfirmationDialog(idDataTextView.getText().toString());
                        }


                    });

                }else{


                    Toast.makeText(getApplicationContext(), "Anda tidak di izinkan untuk menghapus data ini", Toast.LENGTH_LONG).show();
                }



                    }





        });

        //swipe_refresh.setRefreshing(false);
    }




    private void showDeleteConfirmationDialog(final String idData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda yakin ingin menghapus data?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User memilih Ya, maka hapus data
                        deleteData(idData);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User memilih Tidak, tutup dialog
                        dialog.dismiss();
                    }
                });

        // Membuat dan menampilkan AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }








    private void deleteData(String idData) {
        // Menghapus data menggunakan AndroidNetworking atau metode lainnya
        AndroidNetworking.post(Config.host + "hapus_input.php")
                .addBodyParameter("id", idData)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle respons setelah penghapusan data
                        if (response.optString("response").equals("success")) {
                            // Data berhasil dihapus
                            Toast.makeText(getApplicationContext(), "Delete Data Penjualan Berhasil", Toast.LENGTH_LONG).show();

                            KasAdapter3();

                            // ... (Lakukan apa pun yang perlu dilakukan setelah penghapusan)
                        } else {
                            // Gagal menghapus data
                            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // Handle error jika terjadi kesalahan saat menghapus data
                    }
                });
    }



}