package com.example.inven;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.inven.helper.Config;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Pembayaran_akhir extends AppCompatActivity {
TextView id_pembayaran, nama_pelanggan, nomor, tanggal_pembelian, tanggal_dibayar, nama_input, nama_user, jumlah_item, modal, margin, posisi, perusahaan, username;
EditText rp_uang, rp_kembalian, rp_subtotal;

EditText uang, kembalian, subtotal;

    TextView uang_list, kembalian_list, total_list;
    Button input_pembayaran, btncetak;
ImageView hapus_list;
    private ProgressDialog pDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran_akhir);


        id_pembayaran = (TextView) findViewById(R.id.id_pembayaran);
        nama_pelanggan = (TextView) findViewById(R.id.nama_pelanggan);
        nama_input = (TextView) findViewById(R.id.nama_input);
        nomor = (TextView) findViewById(R.id.nomor);
        tanggal_pembelian = (TextView) findViewById(R.id.tanggal_pembelian);
        tanggal_dibayar = (TextView) findViewById(R.id.tanggal_dibayar);
        rp_subtotal = (EditText) findViewById(R.id.rp_subtotal);
        nama_user = (TextView) findViewById(R.id.nama_user);
        jumlah_item = (TextView) findViewById(R.id.jumlah_item);
        modal = (TextView) findViewById(R.id.modal);
        margin = (TextView) findViewById(R.id.margin);


        subtotal = (EditText) findViewById(R.id.subtotal);
        rp_uang = (EditText) findViewById(R.id.rp_uang);
        rp_kembalian = (EditText) findViewById(R.id.rp_kembalian);

        uang = (EditText) findViewById(R.id.uang);
        kembalian = (EditText) findViewById(R.id.kembalian);


        uang_list = (TextView) findViewById(R.id.uang_list);
        kembalian_list = (TextView) findViewById(R.id.kembalian_list);
        total_list = (TextView) findViewById(R.id.total_list);

        hapus_list = (ImageView) findViewById(R.id.hapus_list);
        input_pembayaran = (Button) findViewById(R.id.input_pembayaran);
        btncetak = (Button) findViewById(R.id.btncetak);


        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);
        username = (TextView)findViewById(R.id.username);

        context = Pembayaran_akhir.this;
        pDialog = new ProgressDialog(context);

        //int nilai1= Integer.valueOf(kembalian.getText().toString());

tanggal_dibayar.setText(getCurrentDate());




        hapus_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_pembayaran.getText().toString().equals("null")) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Data belum ada", Toast.LENGTH_LONG).show();
                }  else {

                    delete_pembayaran();



                }
            }
        });


        input_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int nilai1=Integer.valueOf(kembalian.getText().toString());



                    if (uang.getText().toString().length() == 0) {                    //1
                        //jika form Email belum di isi / masih kosong
                        //iddelete1.setError("");
                        Toast.makeText(getApplicationContext(), "Silahkan isi", Toast.LENGTH_LONG).show();
                    }else if(nama_input.getText().toString().equals("fisik")) {
                        update_fisik();//1
                        update_fisik();
                        update_fisik();
                        save();

                    }else if(nama_input.getText().toString().equals("elektrik")) {
                        update_elektrik();//1
                        update_elektrik();
                        update_elektrik();
                        save();

                    }else{


                    }

            }
        });




//        input_pembayaran.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int nilai1=Integer.valueOf(kembalian.getText().toString());
//
//                if(nilai1 <=0) {
//                    Toast.makeText(getApplicationContext(), "Uang pelanggan kurang", Toast.LENGTH_LONG).show();
//
//
//
//                }else{
//
//                    if (uang.getText().toString().length() == 0) {                    //1
//                        //jika form Email belum di isi / masih kosong
//                        //iddelete1.setError("");
//                        Toast.makeText(getApplicationContext(), "Silahkan isi", Toast.LENGTH_LONG).show();
//                    }else if(nama_input.getText().toString().equals("fisik")) {
//                        update_fisik();//1
//                        update_fisik();
//                        update_fisik();
//                        save();
//
//                    }else if(nama_input.getText().toString().equals("elektrik")) {
//                        update_elektrik();//1
//                        update_elektrik();
//                        update_elektrik();
//                        save();
//
//                    }else{
//
//
//                    }
//
//
//
//                }
//
//            }
//        });


        btncetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (nama_input.getText().toString().equals("fisik")) {
                    update_status_transaksi_fisik();//1

                }

                else if(nama_input.getText().toString().equals("elektrik")) {
                    update_status_transaksi_elektrik();//1


                }



            }
        });

        Intent i = getIntent();
        String kiriman = i.getStringExtra("tanggal");
        tanggal_pembelian.setText(kiriman);
        String kiriman2 = i.getStringExtra("nomor");
        nomor.setText(kiriman2);
        String kiriman3 = i.getStringExtra("nama_input");
        nama_input.setText(kiriman3);
        String kiriman4 = i.getStringExtra("rp_total");
        rp_subtotal.setText(kiriman4);
        String kiriman5 = i.getStringExtra("total_non_rp");
        subtotal.setText(kiriman5);
        String kiriman6 = i.getStringExtra("nama_pelanggan");
        nama_pelanggan.setText(kiriman6);
        String kiriman8 = i.getStringExtra("nama_user");
        nama_user.setText(kiriman8);
        String kiriman9 = i.getStringExtra("jumlah_item");
        jumlah_item.setText(kiriman9);
        String kiriman10 = i.getStringExtra("modal");
        modal.setText(kiriman10);
        String kiriman11 = i.getStringExtra("margin");
        margin.setText(kiriman11);
        String kiriman12 = i.getStringExtra("posisi");
        posisi.setText(kiriman12);
        String kiriman13 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman13);
        String kiriman14 = i.getStringExtra("username");
        username.setText(kiriman14);

updateaplikasi();


        rp_uang.addTextChangedListener(new TextWatcher() {
            private String setEdittext = rp_uang.getText().toString().trim();
            private String setTextView;


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.toString().equals(setEdittext)) {
                    rp_uang.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp.]","");
                    if(!replace.isEmpty()){
                        setEdittext = formatrupiah(Double.parseDouble(replace));
                        setTextView =  setEdittext;

                    }else{
                        setEdittext = "";
                        //setTextView = "Hasil Input";



                    }

                    rp_uang.setText(setEdittext);

//                    String num;
//                    num = etNumber.getText().toString().replace(".","");
//                    tvNumber.setText(num);




                    //tvNumber.setText(setTextView);
                    rp_uang.setSelection(setEdittext.length());
                    rp_uang.addTextChangedListener(this);

                    hitungTambah();
                }




            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

    }



    private void hitungTambah() {

        String uang_non_rp;
        String subtotal_non_rp;
        uang_non_rp = rp_uang.getText().toString().replace("Rp.", "").replace(",", "").replace(".", "");
        //subtotal_non_rp = rp_subtotal.getText().toString();
        uang.setText(uang_non_rp);
        //subtotal.setText(subtotal_non_rp);

        double angka1 = getAngka(rp_uang);
        double angka2 = getAngka(rp_subtotal);

        int nilai1 = uang_non_rp.isEmpty() ? 0 : Integer.valueOf(uang_non_rp);
        int nilai2 = Integer.valueOf(subtotal.getText().toString());

//        int nilai1 = Integer.valueOf(uang.getText().toString());
//        int nilai2 = Integer.valueOf(subtotal.getText().toString());

        double hasil = angka1 - angka2;
        int hasil_non_rp = nilai1 - nilai2;

        // Tampilkan hasil dengan format Rupiah


        rp_kembalian.setText(formatrupiah(hasil));

        kembalian.setText(String.valueOf(hasil_non_rp));
        //kembalian.setText(kembalian_non_rp);

        if (hasil_non_rp < 0) {
            rp_kembalian.setTextColor(Color.RED);
        } else {
            rp_kembalian.setTextColor(Color.BLACK);
        }

    }


    private String addNumbers() {
        int number1;
        int number2;
        if (rp_uang.getText().toString() != "" && rp_uang.getText().length() > 0) {
            number1 = Integer.parseInt(rp_uang.getText().toString());
        } else {
            number1 = 0;
        }
        if (subtotal.getText().toString() != "" && subtotal.getText().length() > 0) {
            number2 = Integer.parseInt(subtotal.getText().toString());
        } else {
            number2 = 0;
        }

        return Integer.toString(number2 - number1);
    }



    private double getAngka(EditText editText) {
        String angkaStr = editText.getText().toString().replaceAll("[Rp,.]", "");
        return angkaStr.isEmpty() ? 0 : Double.parseDouble(angkaStr);
    }

    private String formatrupiah(Double number) {
        Locale localeID =new Locale("IND","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String formatrupiah = numberFormat.format(number);
        String[] split = formatrupiah.split(",");
        int length =split[0].length();
        return split[0].substring(0,2)+"."+split[0].substring(2,length);





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
        AndroidNetworking.post(Config.host + "input_pembayaran.php")
                .addBodyParameter("nama_pelanggan", nama_pelanggan.getText().toString())
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal_pembelian", tanggal_pembelian.getText().toString())
                .addBodyParameter("tanggal_bayar", tanggal_dibayar.getText().toString())
                .addBodyParameter("nominal", subtotal.getText().toString())

                .addBodyParameter("uang", uang.getText().toString())
                .addBodyParameter("kembalian", kembalian.getText().toString())
                .addBodyParameter("nama_input", nama_input.getText().toString())
                .addBodyParameter("nama_user", nama_user.getText().toString())
                .addBodyParameter("jumlah_item", jumlah_item.getText().toString())
                .addBodyParameter("modal", modal.getText().toString())
                .addBodyParameter("margin", margin.getText().toString())
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



                            updateaplikasi();

                            //updatestok();
                            //statusiccid.setText("BELUM TERINPUT");
                            //ceklis.setVisibility(View.GONE);
                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "GAGAL MEMASUKAN DATA",
                                    Toast.LENGTH_LONG).show();

                            //updateaplikasi();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }


    private void delete_pembayaran() {
        pDialog.setMessage("Delete Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "hapus_pembayaran.php")
                .addBodyParameter("id", id_pembayaran.getText().toString())

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
                            Toast.makeText(getApplicationContext(), "Delete Berhasil",
                                    Toast.LENGTH_LONG).show();

                            updateaplikasi();

                            update_status_transaksi_fisik_gakjadi();
                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();
                            updateaplikasi();
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


    public void updateaplikasi(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        tampil_pembayaran();
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();
                jika_pembayaran();

            }
        }.start();

    }

    public void jika_pembayaran() {

        if ( uang_list.getText().toString().equals("NaN")){
            //1
            btncetak.setEnabled(false);
            btncetak.setBackgroundColor(getResources().getColor(R.color.abu));

            Toast.makeText(getApplicationContext(), "Silahkan Verifikasi Pembayarn", Toast.LENGTH_LONG).show();
        }else{
            btncetak.setEnabled(true);
            btncetak.setBackgroundColor(getResources().getColor(R.color.merah));

        }

    }



    private void tampil_pembayaran() {

        AndroidNetworking.post(Config.host + "tampil_pembayaran.php")
                .addBodyParameter("tanggal_pembelian", tanggal_pembelian.getText().toString())
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("nama_input", nama_input.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);



                        //transfer12.setText((response.optString("transfer")));

                        id_pembayaran.setText((response.optString("id")));

                        uang_list.setText(
                                rupiahFormat.format(response.optDouble("uang")));
                        //transfernoformat1.setText((response.optString("transfer")));

                        kembalian_list.setText(
                                rupiahFormat.format(response.optDouble("kembalian")));
                        //cashnoformat1.setText((response.optString("cash")));
                        total_list.setText((response.optString("nominal")));
//                        tanggal1.setText((response.optString("tanggal")));
//                        jam1.setText((response.optString("jam")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }


                });

    }


    private void update_status_transaksi_fisik() {
        pDialog.setMessage("Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "update_status_transaksi_fisik.php")
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal_pembelian.getText().toString())
                .addBodyParameter("nama_input", nama_input.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "Verifikasi Pembayaran Berhasil",
                                    Toast.LENGTH_LONG).show();


                            String a = tanggal_pembelian.getText().toString();
                            String b = nomor.getText().toString();
                            String c = nama_pelanggan.getText().toString();
                            String d = rp_uang.getText().toString();
                            String e = rp_kembalian.getText().toString();
                            String f = nama_user.getText().toString();
                            String g = posisi.getText().toString();
                            String h = perusahaan.getText().toString();
                            String j = username.getText().toString();

                            Intent i = new Intent(getApplicationContext(), Cetak_input_fisik.class);
                            i.putExtra("tanggal", "" + a + "");
                            i.putExtra("nomor", "" + b + "");
                            i.putExtra("nama_pelanggan", "" + c + "");
                            i.putExtra("rp_uang", "" + d + "");
                            i.putExtra("rp_kembalian", "" + e + "");
                            i.putExtra("nama_user", "" + f + "");
                            i.putExtra("posisi", "" + g + "");
                            i.putExtra("perusahaan", "" + h + "");
                            i.putExtra("username", "" + j + "");

                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(i);


                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }



    private void update_status_transaksi_fisik_gakjadi() {
        pDialog.setMessage("Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "update_status_transaksi_fisik_gakjadi.php")
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal_pembelian.getText().toString())
                .addBodyParameter("nama_input", nama_input.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "Mengubah ke draft",
                                    Toast.LENGTH_LONG).show();



                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }



    private void update_status_transaksi_elektrik() {
        pDialog.setMessage("Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "update_status_transaksi_elektrik.php")
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal_pembelian.getText().toString())
                .addBodyParameter("nama_input", nama_input.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "Verifikasi Pembayaran Berhasil",
                                    Toast.LENGTH_LONG).show();

                            String a = tanggal_pembelian.getText().toString();
                            String b = nomor.getText().toString();
                            String c = nama_pelanggan.getText().toString();
                            String d = rp_uang.getText().toString();
                            String e = rp_kembalian.getText().toString();
                            String f = nama_user.getText().toString();
                            String g = posisi.getText().toString();
                            String h = perusahaan.getText().toString();

                            Intent i = new Intent(getApplicationContext(), Cetak_input.class);
                            i.putExtra("tanggal", "" + a + "");
                            i.putExtra("nomor", "" + b + "");
                            i.putExtra("nama_pelanggan", "" + c + "");
                            i.putExtra("rp_uang", "" + d + "");
                            i.putExtra("rp_kembalian", "" + e + "");
                            i.putExtra("nama_user", "" + f + "");
                            i.putExtra("posisi", "" + g + "");
                            i.putExtra("perusahaan", "" + h + "");

                            startActivity(i);
                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }




    private void update_fisik() {
        pDialog.setMessage("Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "update_status_transaksi_fisik.php")
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal_pembelian.getText().toString())
                .addBodyParameter("nama_input", nama_input.getText().toString())
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
//                            Toast.makeText(getApplicationContext(), "Verifikasi Pembayaran Berhasil",
//                                    Toast.LENGTH_LONG).show();





                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }



    private void update_elektrik() {
        pDialog.setMessage("Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "update_status_transaksi_elektrik.php")
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal_pembelian.getText().toString())
                .addBodyParameter("nama_input", nama_input.getText().toString())
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
//                            Toast.makeText(getApplicationContext(), "Verifikasi Pembayaran Berhasil",
//                                    Toast.LENGTH_LONG).show();

                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }



}