package com.example.inven;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.inven.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Ada_Saldo extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog pDialog;
    private Context context;


    EditText nama_pelanggan, nama_beli, nama_saldo, saldo_saat_ini, hargamodal, harga, pembayaran, isi;
    TextView id_data, nomor, nama_input, nama_user, posisi, perusahaan;
    TextView tanggal;
    TextView saldo_sebelumnya;
    TextView jam;
    TextView jumlah, keterangan;
    TextView total_non_rp, subtotal_sementara, total_modal, margin;
    LinearLayout pilih_saldo;

    private static final int REQUEST_SELECT_BARANG = 1; // Konstanta untuk permintaan pemilihan barang

    //tambahan
    TextView totalformatrp;

    ListView listinputperdana1;

    Button input, hapus, refresh, btnpembayaran, pilihitem, cariid;

    RadioButton cash, tempo;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    public static String idlist;
    public static String namabelilist;
    public static String hargalist;
    public static ImageView hapuss;
    public static String qtysebelumnyalist;
    public static String qtylist;
    public static String qtysaatinilist;
    public static String hargamodallist;
    public static String totallist;

    NumberFormat formatter = new DecimalFormat("#,###,###,###");
    int subtotal = 0;
    int total_int = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ada_saldo);



        idlist = "";
        namabelilist = "";
        //hapuss = "";

        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);

        listinputperdana1 = (ListView) findViewById(R.id.listinputperdana);

        id_data = (TextView) findViewById(R.id.id_data);//1
        nama_pelanggan = (EditText) findViewById(R.id.nama_pelanggan);//2
        nomor = (TextView) findViewById(R.id.nomor);//3
        nama_beli = (EditText) findViewById(R.id.nama_beli);//4
        nama_saldo = (EditText) findViewById(R.id.nama_saldo);//5
        saldo_sebelumnya = (TextView) findViewById(R.id.saldo_sebelumnya);//6
        harga = (EditText) findViewById(R.id.harga);//7
        saldo_saat_ini = (EditText) findViewById(R.id.saldo_saat_ini);//8
        hargamodal = (EditText) findViewById(R.id.hargamodal);//9
        isi = (EditText) findViewById(R.id.isi);//9
        nama_user = (TextView) findViewById(R.id.nama_user);//9
        nama_input = (TextView) findViewById(R.id.nama_input);//9

        total_modal = (TextView) findViewById(R.id.total_modal);//9
        margin = (TextView) findViewById(R.id.margin);//9
        keterangan = (TextView) findViewById(R.id.keterangan);//9
        //pembayaran = (EditText) findViewById(R.id.pembayaran);//10

        tanggal = (TextView) findViewById(R.id.tanggal);//11
        jam = (TextView) findViewById(R.id.jam);//12

        jumlah = (TextView) findViewById(R.id.jumlah);
        //jumlah = (TextView) findViewById(R.id.jumlah);
        total_non_rp = (TextView) findViewById(R.id.total_non_rp);
        //totalformatrp = (TextView) findViewById(R.id.totalformatrp);
        subtotal_sementara = (TextView) findViewById(R.id.subtotal_sementara);

        input = (Button) findViewById(R.id.input);
        hapus = (Button) findViewById(R.id.hapus);
        refresh = (Button) findViewById(R.id.refresh);
        btnpembayaran = (Button) findViewById(R.id.btnpembayaran);
        pilihitem = (Button) findViewById(R.id.pilihitem);
        //cariid = (Button) findViewById(R.id.cariid);

        pilih_saldo = (LinearLayout) findViewById(R.id.pilih_saldo);


        context = Ada_Saldo.this;
        pDialog = new ProgressDialog(context);

        int jumlah1 = 0;



//        cash = (RadioButton) findViewById(R.id.cash);
//        tempo = (RadioButton) findViewById(R.id.tempo);
//
//        cash.setOnClickListener(this);
//        tempo.setOnClickListener(this);




        tanggal.setText(getCurrentDate());
        jam.setText(jamotomatis());

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (nama_beli.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    nama_beli.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (nama_saldo.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    nama_saldo.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (harga.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    harga.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (hargamodal.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    hargamodal.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (saldo_sebelumnya.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    saldo_sebelumnya.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (saldo_saat_ini.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    saldo_saat_ini.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {


                    inputdanjumlahkanaplikasi();
                    //hitungHasil();
//                    inputgak();

                    //inputdanjumlahkanaplikasi();

                }
            }
        });



//        pilihitem.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//
////                Intent i = new Intent(getApplicationContext(), Performance_outlet.class);
////                startActivity(i);
//
//                // Ketika tombol untuk memilih barang di klik
//                Intent intent = new Intent(getApplicationContext(), Data_barang.class);
//
//                String a = perusahaan.getText().toString();
//                intent.putExtra("perusahaan", "" + a + "");
//
//                startActivityForResult(intent, REQUEST_SELECT_BARANG);
//
//            }
//
//        });


        btnpembayaran.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String a = tanggal.getText().toString();
                String b = nomor.getText().toString();
                String c = nama_input.getText().toString();
                String d = subtotal_sementara.getText().toString();
                String e = total_non_rp.getText().toString();
                String f = nama_pelanggan.getText().toString();
                String g = nama_user.getText().toString();
                String h = jumlah.getText().toString();
                String j = total_modal.getText().toString();
                String k = margin.getText().toString();
                String l = posisi.getText().toString();
                String m = perusahaan.getText().toString();

                Intent i = new Intent(getApplicationContext(), Pembayaran_akhir.class);
                i.putExtra("tanggal", "" + a + "");
                i.putExtra("nama_pelanggan", "" + f + "");
                i.putExtra("nomor", "" + b + "");
                i.putExtra("nama_input", "" + c + "");
                i.putExtra("rp_total", "" + d + "");
                i.putExtra("total_non_rp", "" + e + "");
                i.putExtra("nama_user", "" + g + "");
                i.putExtra("jumlah_item", "" + h + "");
                i.putExtra("modal", "" + j + "");
                i.putExtra("margin", "" + k + "");
                i.putExtra("posisi", "" + l + "");
                i.putExtra("perusahaan", "" + m + "");

                startActivity(i);


            }

        });




        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //count_item();
                KasAdapter2();
                //jumlah.setText(String.valueOf(itemCount));
            }

        });

        hapus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //alert_hapus();
            }

        });


        pilih_saldo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), Data_saldo.class);
                String a = perusahaan.getText().toString();
                intent.putExtra("perusahaan", "" + a + "");
                startActivityForResult(intent, REQUEST_SELECT_BARANG);            }

        });


        pilihitem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), Data_item_elektrik.class);

                String a = perusahaan.getText().toString();
                intent.putExtra("perusahaan", "" + a + "");
                startActivityForResult(intent, 11);


            }

        });

        //KasAdapter2();


        KasAdapter2();

        Intent i = getIntent();
        String kiriman = i.getStringExtra("nama_saldo");
        nama_saldo.setText(kiriman);
        String kiriman2 = i.getStringExtra("sisa_saldo");
        saldo_sebelumnya.setText(kiriman2);
        String kiriman3 = i.getStringExtra("nama_user");
        nama_user.setText(kiriman3);
        String kiriman4 = i.getStringExtra("posisi");
        posisi.setText(kiriman4);
        String kiriman5 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman5);

        cek_koneksi_dari_nomor();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_BARANG) {
            if (resultCode == RESULT_OK) {
                if (data != null) {

                    String a2 = data.getStringExtra("nama_saldo");
                    nama_saldo.setText(a2);

                    String a3 = data.getStringExtra("sisa_saldo");
                    saldo_sebelumnya.setText(a3);

                    // Lakukan sesuatu dengan selectedBarang
                }
            }
        } else if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                if (data != null) {

                    String b2 = data.getStringExtra("nama_beli");
                    nama_beli.setText(b2);

                    String b3 = data.getStringExtra("hargamodal");
                    hargamodal.setText(b3);

                    String b4 = data.getStringExtra("harga");
                    harga.setText(b4);

                    // Lakukan sesuatu dengan selectedBarang
                }
            }
        }
    }

    public void alert() {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Hallo");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)



//        String idoutletalert = namaoutmenu1.getText().toString();
//        String namasalesalert = namasf3.getText().toString();
//        String a = validasib1.getText().toString();
                //new AlertDialog.Builder(this)

                //.setIcon(R.drawable.lihatdatamenu)
                .setTitle(R.string.app_name)
                .setMessage("STOK HABIS")
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nama_pelanggan.setText("Tanpa Nama");
                        nama_beli.setText("");
                        //nama_saldo.setText("");
                        hargamodal.setText("");
                        //saldo_sebelumnya.setText("");
                        harga.setText("");
                        saldo_saat_ini.setText("0");
                        isi.setText("");
                        dialog.cancel();

                    }
                })
//                .setNegativeButton("Keluar Aplikasi", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }


    public void alert_hapus(String idData, int position) {

        String namaitem_alert = nama_beli.getText().toString();
        String qty_alert = harga.getText().toString();

        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("HAPUS DATA");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)




                //String namasalesalert = namasf3.getText().toString();
//        String a = validasib1.getText().toString();
                //new AlertDialog.Builder(this)

                //.setIcon(R.drawable.lihatdatamenu)
                .setTitle(R.string.app_name)
                .setMessage("Yakin ingin hapus?? \n"+"Item : "+namabelilist+" "+hargalist+"")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapus_input();
                        margin();
                        modal();
                        count_item();
                        dialog.cancel();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }



    private void hapus_input() {
        pDialog.setMessage("Delete Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "hapus_input_elektrik.php")
                .addBodyParameter("id", id_data.getText().toString())

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
                            Toast.makeText(getApplicationContext(), "Delete Data Penjualan Berhasil",
                                    Toast.LENGTH_LONG).show();
                            KasAdapter2();
                            nama_pelanggan.setText("Tanpa Nama");
                            nama_beli.setText("");
                            //nama_saldo.setText("");
                            hargamodal.setText("");
                            //saldo_sebelumnya.setText("");
                            harga.setText("");
                            saldo_saat_ini.setText("0");
                            isi.setText("");
                            //


                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "Gagal",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }






    public void inputdanjumlahkanaplikasi(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        //jumlahkanaplikasi();

        hitungHasil();
        int nilai1=Integer.valueOf(saldo_sebelumnya.getText().toString());
        int nilai2=Integer.valueOf(hargamodal.getText().toString());
        //hitungstok();
        //int nilai1=Integer.valueOf(sisahstok1.getText().toString());
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                if (nilai1<nilai2) {        //2
                    //jika form Username belum di isi / masih kosong
                    //hargamodal.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Saldo Kurang", Toast.LENGTH_LONG).show();
                }else {

                    hideDialog();
                    inputgak();
                }


                //mTextField.setText("done!");
            }
        }.start();


    }

    public void cek_koneksi_dari_nomor(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        //jumlahkanaplikasi();

        nomor_terakhir();
        //hitungstok();
        //String nilai1=Integer.valueOf(nomor.getText().toString());
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("SEDANG CEK KONEKSI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {


                if(nomor.getText().toString().equals("nomor")){
                    cek_koneksi_dari_nomor();

                }else{
                    hideDialog();
                    KasAdapter2();

                }



                //mTextField.setText("done!");
            }
        }.start();

        //






    }


    public void inputgak() {
        String nama_pelangganalert = nama_pelanggan.getText().toString();
        String nama_belialert = nama_beli.getText().toString();
        String nama_saldoalert = nama_saldo.getText().toString();
        String saldo_sebelumnyaalert = saldo_sebelumnya.getText().toString();
        String hargamodalalert = hargamodal.getText().toString();
        String saldo_saat_inialert = saldo_saat_ini.getText().toString();
        String hargaalert = harga.getText().toString();
        //String totalalert = totalformatrp.getText().toString();
        String isialert = isi.getText().toString();

        //String a = validasib1.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Hallo");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)

                .setIcon(R.drawable.lihatdatamenu)
                .setTitle(R.string.app_name)
                .setMessage("Silahkan Di cek Kembali ya \n" +
                        "Saldo : "+nama_saldoalert+"\nDengan Saldo"+saldo_sebelumnyaalert+"\nPembelian : "+nama_belialert+"\nNominal : "+hargaalert+"\nDan Sisah Saldo Menjadi : "+saldo_saat_inialert)
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        save();
                        count_item();
                        modal();
                        margin();



                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nama_pelanggan.setText("Tanpa Nama");
                        nama_beli.setText("");
                        //nama_saldo.setText("");
                        hargamodal.setText("");
                        //saldo_sebelumnya.setText("");
                        harga.setText("");
                        saldo_saat_ini.setText("0");
                        isi.setText("");
                        //KasAdapter2();
                        dialog.cancel();
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }



    public String jamotomatis(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;



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


    private void save() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "input_penjualan_elektrik.php")
                .addBodyParameter("nama_pelanggan", nama_pelanggan.getText().toString())
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
                .addBodyParameter("nama_beli", nama_beli.getText().toString())
                .addBodyParameter("nama_saldo", nama_saldo.getText().toString())

                .addBodyParameter("saldosebelumnya", saldo_sebelumnya.getText().toString())
                .addBodyParameter("hargamodal", hargamodal.getText().toString())
                .addBodyParameter("saldosaatini", saldo_saat_ini.getText().toString())
                .addBodyParameter("harga", harga.getText().toString())
                .addBodyParameter("jam", jam.getText().toString())
                .addBodyParameter("keterangan", keterangan.getText().toString())
                .addBodyParameter("isi", isi.getText().toString())
                .addBodyParameter("nama_user", nama_user.getText().toString())
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
                            nama_pelanggan.setText("Tanpa Nama");
                            nama_beli.setText("");
                            //nama_saldo.setText("");
                            hargamodal.setText("");
                            //saldo_sebelumnya.setText("");
                            harga.setText("");
                            saldo_saat_ini.setText("0");
                            isi.setText("");


                            KasAdapter2();

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



    private void hitungHasil() {

        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        int qty_h = Integer.parseInt(hargamodal.getText().toString());
        int saldo_h = Integer.parseInt(saldo_sebelumnya.getText().toString());
        int hasilharga_h = saldo_h - qty_h;





        saldo_saat_ini.setText(String.valueOf(hasilharga_h));




    }


    private void KasAdapter2(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "tampil_input_elektrik.php" )
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
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
                                map.put("id",         responses.optString("id"));
                                map.put("nama_beli",       responses.optString("nama_beli"));
                                map.put("nama_saldo",       responses.optString("nama_saldo"));
                                map.put("harga",       rupiahFormat.format(responses.optDouble("harga")));
//                                map.put("keterangan",       responses.optString("keterangan"));
//                                map.put("pembayaran",       responses.optString("pembayaran"));

                                //map.put("tanggal",      responses.optString("tanggal"));

                                //jumlah1 += itemCount;
                                //total_int += Integer.parseInt(responses.getString("harga"));
                                subtotal += Integer.parseInt(responses.getString("harga"));
                                //jumlah.setText(String.valueOf(itemCount));
                                aruskas.add(map);
                            }


                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //jumlah += itemCount;
                        subtotal_sementara.setText("Rp "+formatter.format(subtotal));
                        total_non_rp.setText(Integer.toString(subtotal));
                        count_item();
                        modal();
                        margin();
                        //jumlah.setText(jumlah);
                        subtotal = 0;

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }


    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_saldo,
                new String[] {"id","nama_beli","nama_saldo","harga"},
                new int[] {R.id.id_list, R.id.nama_belilist, R.id.nama_saldolist, R.id.harga_list});




        listinputperdana1.setAdapter(simpleAdapter);




        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //idlist    = ((TextView) view.findViewById(R.id.id_list)).getText().toString();
                TextView idDataTextView = (TextView) view.findViewById(R.id.id_list);
                ImageView hapuss = (ImageView) view.findViewById(R.id.hapus_data_list);


                hapuss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                        showDeleteConfirmationDialog(idDataTextView.getText().toString());
                        }





                });

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
        AndroidNetworking.post(Config.host + "hapus_input_elektrik.php")
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
                            KasAdapter2();
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


    private void nomor_terakhir() {

        AndroidNetworking.post(Config.host + "cek_nomor_elektrik.php")


                .addBodyParameter("tanggal", tanggal.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
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
                        nomor.setText((response.optString("nomor")));

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

    @Override
    public void onClick(View view) {




    }


    private void count_item() {

        AndroidNetworking.post(Config.host + "count_item_elektrik.php")
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
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
                        jumlah.setText((response.optString("id")));
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




    private void modal() {

        AndroidNetworking.post(Config.host + "modal_elektrik.php")
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
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
                        total_modal.setText((response.optString("hargamodal")));
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



    private void margin() {

        AndroidNetworking.post(Config.host + "margin_elektrik.php")
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
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




}