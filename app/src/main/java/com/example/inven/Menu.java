package com.example.inven;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Menu extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Context context;

    TextView jumlah_pending;

    LinearLayout ada_pembeli, stok_ku, saldo_ku, item_elektrik_ku, laporan_ku, mutasi_stok, mutasi_cash, transaksi_pending, edit_user;
    TextView username, nama_user, posisi, perusahaan;


    TextView multi, saldo1;

    LinearLayout klik_dari, klik_sampai, cari_tanggal;
    TextView tanggal_dari, tanggal_sampai;

    TextView item_fisik, modal_fisik, penjualan_fisik, margin_fisik;

    Handler mHandler;

    TextView kondisi;

    TextView nomor, selengkapnya;

    LinearLayout blank_gambar, data_dashboard;

    TextView tanggal_awal, tanggal, tanggalpembelian, hasiltanggal;
    TextView bidang;

    LinearLayout hub_kami;


    //ImageView blank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        ImageView blank = findViewById(R.id.blank);
        ImageView gerak2 = findViewById(R.id.gerak2);


        context = Menu.this;
        pDialog = new ProgressDialog(context);

        item_fisik = (TextView) findViewById(R.id.item_fisik);
        modal_fisik = (TextView) findViewById(R.id.modal_fisik);
        penjualan_fisik = (TextView) findViewById(R.id.penjualan_fisik);
        margin_fisik = (TextView) findViewById(R.id.margin_fisik);

        kondisi = (TextView) findViewById(R.id.kondisi);

        jumlah_pending = (TextView) findViewById(R.id.jumlah_pending);

        klik_dari = (LinearLayout) findViewById(R.id.klik_dari);
        klik_sampai = (LinearLayout) findViewById(R.id.klik_sampai);
        cari_tanggal = (LinearLayout) findViewById(R.id.cari_tanggal);

        tanggal_dari = (TextView) findViewById(R.id.tanggal_dari);
        tanggal_sampai = (TextView) findViewById(R.id.tanggal_sampai);

        selengkapnya = (TextView) findViewById(R.id.selengkapnya);

        bidang = (TextView) findViewById(R.id.bidang);
        hub_kami = (LinearLayout) findViewById(R.id.hub_kami);

        edit_user = (LinearLayout) findViewById(R.id.edit_user);//1

        ada_pembeli = (LinearLayout) findViewById(R.id.ada_pembeli);//1
        stok_ku = (LinearLayout) findViewById(R.id.stok_ku);//1
        saldo_ku = (LinearLayout) findViewById(R.id.saldo_ku);//1
        item_elektrik_ku = (LinearLayout) findViewById(R.id.item_elektrik_ku);//1
        laporan_ku = (LinearLayout) findViewById(R.id.laporan_ku);//1
        mutasi_stok = (LinearLayout) findViewById(R.id.mutasi_stok);//1
        mutasi_cash = (LinearLayout) findViewById(R.id.mutasi_cash);//1
        transaksi_pending = (LinearLayout) findViewById(R.id.transaksi_pending);//1

        username = (TextView) findViewById(R.id.username);//1
        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView) findViewById(R.id.posisi);//1
        perusahaan = (TextView) findViewById(R.id.perusahaan);//1


        tanggal_awal = (TextView) findViewById(R.id.tanggal_awal);//1
        tanggal = (TextView) findViewById(R.id.tanggal);//1
        tanggalpembelian = (TextView) findViewById(R.id.tanggalpembelian);//1
        hasiltanggal = (TextView) findViewById(R.id.hasiltanggal);//1


        multi = (TextView) findViewById(R.id.multi);//1
        saldo1 = (TextView) findViewById(R.id.saldo1);//1

        data_dashboard = (LinearLayout) findViewById(R.id.data_dashboard);
        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);

        nomor = (TextView) findViewById(R.id.nomor);//1

        tanggal.setText(getCurrentServerDate());
        tanggal_dari.setText(getCurrentServerDate());
        tanggal_sampai.setText(getCurrentServerDate());

        Intent i = getIntent();
        String kiriman = i.getStringExtra("username");
        username.setText(kiriman);


        edit_user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();
                    String d = username.getText().toString();
                    String e = bidang.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Isi_user.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("username", "" + d + "");
                    i.putExtra("bidang", "" + e + "");


                    startActivity(i);


                }

            }

        });


        ada_pembeli.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();
                    String d = username.getText().toString();
                    String e = bidang.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Ada_gabung.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("username", "" + d + "");
                    i.putExtra("bidang", "" + e + "");


                    startActivity(i);


                }

            }

        });


        stok_ku.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();
                    String d = bidang.getText().toString();

                    Intent i = new Intent(getApplicationContext(), isi_stok.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("bidang", "" + d + "");


                    startActivity(i);


                }

            }

        });



        saldo_ku.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Isi_saldo.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");


                    startActivity(i);


                }

            }

        });






        item_elektrik_ku.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Isi_elektrik.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");


                    startActivity(i);


                }

            }

        });


        laporan_ku.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();
                    String d = username.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Rekapan.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("username", "" + d + "");


                    startActivity(i);


                }

            }

        });


        selengkapnya.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();
                    String d = username.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Rekapan.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("username", "" + d + "");



                    startActivity(i);


                }

            }

        });


        mutasi_stok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();
                    String d = bidang.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Mutasi.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("bidang", "" + d + "");


                    startActivity(i);


                }

            }

        });



        mutasi_cash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Mutasi_Cash.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");


                    startActivity(i);


                }

            }

        });


        transaksi_pending.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();
                    String d = username.getText().toString();
                    String e = bidang.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Transaksi_pending.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("username", "" + d + "");
                    i.putExtra("bidang", "" + e + "");


                    startActivity(i);


                }

            }

        });




        cari_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lap_fisik();

            }
        });


        hub_kami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = nomor.getText().toString();
                    String d = username.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Contact.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("username", "" + d + "");


                    startActivity(i);


                }

            }
        });

        this.mHandler = new Handler();
        m_Runnable.run();

//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(blank);
//        Glide.with(this)
//                .load(R.drawable.bubble_gerak)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(imageViewTarget);

        gambar_gerak1();
        //gambar_gerak2();

        cash();
        //multi();
        //dana();
        user();
        count_pending();

        verifikasi_gambar();

        String tanggalAwalString = tanggal.getText().toString();
        String tanggalAkhirString = tanggalpembelian.getText().toString();

        // Panggil metode untuk menghitung selisih tanggal
        calculateDateDifference(tanggalAwalString, tanggalAkhirString);

        //hasil_tanggal();



    }




    public void gambar_gerak1(){
        ImageView blank = findViewById(R.id.blank);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(blank);
        Glide.with(this)
                .load(R.drawable.notfound)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageViewTarget);

    }


    public void gambar_gerak2() {
        ImageView gerak2 = findViewById(R.id.gerak2);

        // Pengecekan apakah aktivitas masih aktif sebelum memuat gambar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed()) {
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gerak2);
                Glide.with(this)
                        .load(R.drawable.draft2)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageViewTarget);
            }
        }
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {

            cash();
            user();
            lap_fisik();
            count_pending();

            if (modal_fisik.getText().toString().equals("null")) {

                data_dashboard.setVisibility(View.GONE);
                blank_gambar.setVisibility(View.VISIBLE);
                //hideDialog();

            }else{
                data_dashboard.setVisibility(View.VISIBLE);
                blank_gambar.setVisibility(View.GONE);

            }

            String tanggalAwalString = tanggal.getText().toString();
            String tanggalAkhirString = tanggalpembelian.getText().toString();

            // Panggil metode untuk menghitung selisih tanggal
            calculateDateDifference(tanggalAwalString, tanggalAkhirString);




            String jumlahPendingStr = jumlah_pending.getText().toString();
            int jumlahPending = Integer.parseInt(jumlahPendingStr);

            if (jumlahPending > 0) {
                gambar_gerak2();
            } else {
                // Tidak melakukan apa-apa jika jumlah_pending <= 0
            }



            Menu.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };


    private boolean expiredAlertShown = false;
    private void calculateDateDifference(String startDateString, String endDateString) {
        // Format tanggal yang diinginkan
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            // Konversi string tanggal menjadi objek Date
            Date startDate = format.parse(startDateString);
            Date endDate = format.parse(endDateString);

            // Hitung selisih antara dua tanggal
            long diffInMillies = endDate.getTime() - startDate.getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            // Tampilkan hasil pada TextView
            hasiltanggal.setText(""+ diffInDays + "");


            if (diffInDays <= 3 && diffInDays > 0 && !expiredAlertShown) {
                // Handle kondisi ketika diffInDays lebih dari 0 tetapi kurang dari atau sama dengan 3
                showExpiredAlert(diffInDays);
                expiredAlertShown = true;
            } else if (diffInDays < 1 && !expiredAlertShown) {
                // Handle kondisi ketika diffInDays kurang dari 1
                showImmediateExpiredAlert();
                expiredAlertShown = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showExpiredAlert(long daysRemaining) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pemberitahuan");
        builder.setMessage("Expired mu tinggal " + daysRemaining + " hari. Yuk isi ulang sekarang");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handling OK button click
                // Pindah ke aktivitas baru menggunakan Intent
//                Intent intent = new Intent(YourCurrentActivity.this, YourNewActivity.class);
//                startActivity(intent);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showImmediateExpiredAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pemberitahuan");
        builder.setMessage("Expired mu sudah habis. Aplikasi akan ditutup.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handling OK button click
                finishAffinity();  // Menutup semua aktivitas yang terkait dengan aplikasi
            }
        });

        // Hilangkan tombol cancel (setNegativeButton)
        builder.setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();
    }





    @Override
    public void onBackPressed() {

        this.moveTaskToBack(true);
        //Toast.makeText(this,"Silahkan Klik Logout untuk keluar aplikasi", Toast.LENGTH_LONG).show();
        //finish();
    }


    public void verifikasi_gambar(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        //jumlahkanaplikasi();

        //lap_fisik();
        //hitungstok();
        //int nilai1=Integer.valueOf(sisahstok1.getText().toString());


        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {

//                if (kondisi.getText().toString().equals("null")) {
//
//                    data_dashboard.setVisibility(View.GONE);
//                    blank_gambar.setVisibility(View.VISIBLE);
                    hideDialog();
//
//                }else{
//                    data_dashboard.setVisibility(View.VISIBLE);
//                    blank_gambar.setVisibility(View.GONE);
//                    hideDialog();
//                }


                //mTextField.setText("done!");
            }
        }.start();


    }




    public String getCurrentDate() {

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        return hariotomatis;



    }


    public static String getCurrentServerDate() {
        // Set time zone to Asia/Jakarta
        TimeZone serverTimeZone = TimeZone.getTimeZone("Asia/Jakarta");


        // Get Calendar instance with the server time zone
        Calendar calendar = Calendar.getInstance(serverTimeZone);

        // Get the current date and time in the server's time zone
        Date serverDate = calendar.getTime();

        // Format the date as needed
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setTimeZone(serverTimeZone);

        return dateFormat.format(serverDate);
    }




    private void multi() {

        AndroidNetworking.post(Config.host + "saldo_depan_multi.php")
                .addBodyParameter("nama_beli", multi.getText().toString())
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
                        saldo1.setText((response.optString("sisa_saldo")));
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


//    private void dana() {
//
//        AndroidNetworking.post(Config.host + "saldo_depan_dana.php")
//                .addBodyParameter("nama_beli", dana.getText().toString())
//                .addBodyParameter("perusahaan", perusahaan.getText().toString())
//                //.addBodyParameter("dana", dana.getText().toString())
//
//                //.addBodyParameter("bulan", bulan1.getText().toString())
//                //.addBodyParameter("bulan1", bulan1.getText().toString())
//                //.addBodyParameter("bulan2", bulan2.getText().toString())
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//
//                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
//                        //idoutlet1.setText((response.optString("idoutlet")));
//                        //namaoutlet1.setText((response.optString("namaoutlet")));
//                        saldo2.setText((response.optString("sisa_saldo")));
//                        //saldo2.setText((response.optString("saldo2")));
//                        //actualhero1.setText((response.optString("actual")));
//                        //achhero1.setText((response.optString("ach")));
//                        //gaphero1.setText((response.optString("gap")));
//                        //bulan1.setText((response.optString("bulan")));
//
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//
//                    }
//                });
//
//
//    }

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
                        nama_user.setText((response.optString("nama")));
                        posisi.setText((response.optString("posisi")));
                        perusahaan.setText((response.optString("perusahaan")));
                        nomor.setText((response.optString("perusahaan")));
                        tanggalpembelian.setText((response.optString("tanggalpembelian")));
                        tanggalpembelian.setText((response.optString("tanggalpembelian")));
                        bidang.setText((response.optString("bidang")));
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




    private void lap_fisik() {
//        pDialog.setMessage("Process...");
//        showDialog();
//        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "lap_summary.php")
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
                        //kondisi.setText((response.optString("penjualan")));
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


    private void count_pending() {
//        pDialog.setMessage("Process...");
//        showDialog();
//        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "count_pending.php")
//                .addBodyParameter("tanggaldari", tanggal_dari.getText().toString())
//                .addBodyParameter("tanggalsampai", tanggal_sampai.getText().toString())
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
                        jumlah_pending.setText((response.optString("id")));

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


}