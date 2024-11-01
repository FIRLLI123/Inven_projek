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
import android.text.Editable;
import android.text.TextWatcher;
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

public class Take_out_Cash_Isi extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog pDialog;
    private Context context;


    EditText nama_pelanggan, nama_beli, code, namaitem, qty, qtysaatini, hargamodal_non_rp,hargamodal_rp, totalhargamodal, harga, isi, kategori;
    TextView id_data, nomor, nama_user, posisi, perusahaan;
    TextView tanggal, parameter;
    TextView qtysebelumnya;
    TextView jam;
    TextView jumlah, total_non_rp, nama_input;
    TextView total, subtotal_sementara, total_modal, margin, keterangan, qtysebelumnya_rp;


    TextView multi, saldo1, saldo2;

    TextView tanggal_awal;

    LinearLayout i_code, i_code_t, i_nama_beli, i_nama_beli_t;
    TextView i_namaitem, i_hargamodal_t, i_qty_t, i_isi_t, panah, i_nama_stok_t;

    private static final int REQUEST_SELECT_BARANG = 1; // Konstanta untuk permintaan pemilihan barang

    //tambahan
    TextView totalformatrp;

    ListView listinputperdana1;

    Button input, hapus, refresh, btnpembayaran, pilihitem_fisik, cariid, pilihitem_elektrik;
    LinearLayout pilih_saldo;
    RadioButton cash, tempo;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    public static String idlist, nomorlist, codelist, namaitemlist, qtysebelumnyalist, qtylist, qtysaatinilist, hargamodallist, hargalist, totallist;

    NumberFormat formatter = new DecimalFormat("#,###,###,###");
    int subtotal = 0;

    RadioButton fisik, elektrik;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_out_cash_isi);

        saldo1 = (TextView) findViewById(R.id.saldo1);//1
        saldo2 = (TextView) findViewById(R.id.saldo2);//1

        i_code = (LinearLayout) findViewById(R.id.i_code);
        i_code_t = (LinearLayout) findViewById(R.id.i_code_t);

        i_nama_beli = (LinearLayout) findViewById(R.id.i_nama_beli);
        i_nama_beli_t = (LinearLayout) findViewById(R.id.i_nama_beli_t);

        i_namaitem = (TextView) findViewById(R.id.i_namaitem);
        i_hargamodal_t = (TextView) findViewById(R.id.i_hargamodal_t);
        i_qty_t = (TextView) findViewById(R.id.i_qty_t);
        i_isi_t = (TextView) findViewById(R.id.i_isi_t);

        i_nama_stok_t = (TextView) findViewById(R.id.i_nama_stok_t);
        panah = (TextView) findViewById(R.id.panah);


        fisik = (RadioButton) findViewById(R.id.fisik);
        elektrik = (RadioButton) findViewById(R.id.elektrik);

        fisik.setOnClickListener(this);
        elektrik.setOnClickListener(this);



        idlist = "";

        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);

        listinputperdana1 = (ListView) findViewById(R.id.listinputperdana);

        id_data = (TextView) findViewById(R.id.id_data);
        nomor = (TextView) findViewById(R.id.nomor);
        nama_user = (TextView) findViewById(R.id.nama_user);
        code = (EditText) findViewById(R.id.code);
        nama_pelanggan = (EditText) findViewById(R.id.nama_pelanggan);
        namaitem = (EditText) findViewById(R.id.namaitem);
        qtysebelumnya = (TextView) findViewById(R.id.qtysebelumnya);
        qty = (EditText) findViewById(R.id.qty);
        qtysaatini = (EditText) findViewById(R.id.qtysaatini);
        hargamodal_rp = (EditText) findViewById(R.id.hargamodal_rp);
        hargamodal_non_rp = (EditText) findViewById(R.id.hargamodal_non_rp);
        totalhargamodal = (EditText) findViewById(R.id.totalhargamodal);
        harga = (EditText) findViewById(R.id.harga);
        nama_beli = (EditText) findViewById(R.id.nama_beli);
        isi = (EditText) findViewById(R.id.isi);
        parameter = (TextView) findViewById(R.id.parameter);
        kategori = (EditText) findViewById(R.id.kategori);
        keterangan = (TextView) findViewById(R.id.keterangan);

        qtysebelumnya_rp = (TextView) findViewById(R.id.qtysebelumnya_rp);

        tanggal_awal = (TextView) findViewById(R.id.tanggal_awal);

        tanggal = (TextView) findViewById(R.id.tanggal);
        jam = (TextView) findViewById(R.id.jam);
        nomor = (TextView) findViewById(R.id.nomor);
        total_non_rp = (TextView) findViewById(R.id.total_non_rp);
        nama_input = (TextView) findViewById(R.id.nama_input);

        jumlah = (TextView) findViewById(R.id.jumlah);
        //jumlah = (TextView) findViewById(R.id.jumlah);
        total = (TextView) findViewById(R.id.total);
        totalformatrp = (TextView) findViewById(R.id.totalformatrp);
        subtotal_sementara = (TextView) findViewById(R.id.subtotal_sementara);
        total_modal = (TextView) findViewById(R.id.total_modal);
        margin = (TextView) findViewById(R.id.margin);

        input = (Button) findViewById(R.id.input);
        hapus = (Button) findViewById(R.id.hapus);
        refresh = (Button) findViewById(R.id.refresh);
        btnpembayaran = (Button) findViewById(R.id.btnpembayaran);
        pilihitem_fisik = (Button) findViewById(R.id.pilihitem_fisik);
        pilihitem_elektrik = (Button) findViewById(R.id.pilihitem_elektrik);

        cariid = (Button) findViewById(R.id.cariid);


        context = Take_out_Cash_Isi.this;
        pDialog = new ProgressDialog(context);

        int jumlah1 = 0;








        tanggal.setText(getCurrentDate());
        jam.setText(jamotomatis());

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if (code.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    code.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (namaitem.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    namaitem.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (qty.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    qty.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (harga.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    harga.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (hargamodal_non_rp.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    hargamodal_non_rp.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {


                    inputdanjumlahkanaplikasi();


                }
            }
        });



        btnpembayaran.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if (hargamodal_non_rp.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    hargamodal_non_rp.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (isi.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    isi.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {


                    inputdanjumlahkanaplikasi();
                }

            }

        });


        pilihitem_fisik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

//                Intent i = new Intent(getApplicationContext(), Performance_outlet.class);
//                startActivity(i);

                // Ketika tombol untuk memilih barang di klik
                Intent intent = new Intent(getApplicationContext(), Data_barang.class);
                String a = perusahaan.getText().toString();
                intent.putExtra("perusahaan", "" + a + "");
                startActivityForResult(intent, REQUEST_SELECT_BARANG);
            }

        });

        cariid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cek_stok();


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
                alert_hapus();
            }

        });





        pilihitem_elektrik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), Data_item_elektrik.class);

                String a = perusahaan.getText().toString();
                intent.putExtra("perusahaan", "" + a + "");
                startActivityForResult(intent, 11);




            }

        });


        Intent i = getIntent();
        String kiriman = i.getStringExtra("nama_user");
        nama_user.setText(kiriman);
        String kiriman2 = i.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman3);


        //KasAdapter2();
        cek_koneksi_dari_nomor();



        parameter.setText("elektrik");
        i_code_t.setVisibility(View.GONE);
        i_code.setVisibility(View.GONE);
        code.setText("-");
        i_nama_beli_t.setVisibility(View.GONE);
        i_nama_beli.setVisibility(View.GONE);
        nama_beli.setText("");
        i_namaitem.setText("Nama Saldo");
        i_hargamodal_t.setVisibility(View.VISIBLE);
        hargamodal_non_rp.setVisibility(View.GONE);
        i_qty_t.setVisibility(View.GONE);
        qty.setVisibility(View.GONE);
        qty.setText("1");
//        i_isi_t.setVisibility(View.GONE);
//        isi.setVisibility(View.GONE);
//        isi.setText("ISI SALDO");
        i_nama_stok_t.setText("Saldo Tersedia : ");
        panah.setVisibility(View.GONE);
        kategori.setText("SALDO");
        harga.setText("0");
        nama_beli.setText("ISI CASH");


        hargamodal_rp.addTextChangedListener(new TextWatcher() {
            private String setEdittext = hargamodal_rp.getText().toString().trim();
            private String setTextView;


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.toString().equals(setEdittext)) {
                    hargamodal_rp.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp.]","");
                    if(!replace.isEmpty()){
                        setEdittext = formatrupiah(Double.parseDouble(replace));
                        setTextView =  setEdittext;

                    }else{
                        setEdittext = "";
                        //setTextView = "Hasil Input";



                    }

                    hargamodal_rp.setText(setEdittext);

//                    String num;
//                    num = etNumber.getText().toString().replace(".","");
//                    tvNumber.setText(num);




                    //tvNumber.setText(setTextView);
                    hargamodal_rp.setSelection(setEdittext.length());
                    hargamodal_rp.addTextChangedListener(this);

                    String uang_non_rp;
                    String subtotal_non_rp;
                    uang_non_rp = hargamodal_rp.getText().toString().replace("Rp.", "").replace(",", "").replace(".", "");
                    //subtotal_non_rp = rp_subtotal.getText().toString();
                    hargamodal_non_rp.setText(uang_non_rp);
                }




            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });


        cash();



    }


    private String formatrupiah(Double number) {
        Locale localeID =new Locale("IND","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String formatrupiah = numberFormat.format(number);
        String[] split = formatrupiah.split(",");
        int length =split[0].length();
        return split[0].substring(0,2)+"."+split[0].substring(2,length);





    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == REQUEST_SELECT_BARANG) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String a1 = data.getStringExtra("code");
                    code.setText(a1);

                    String a2 = data.getStringExtra("namaitem");
                    namaitem.setText(a2);

                    String a3 = data.getStringExtra("hargamodal");
                    hargamodal_non_rp.setText(a3);

                    String a4 = data.getStringExtra("harga");
                    harga.setText(a4);

                    String a5 = data.getStringExtra("sisastok");
                    qtysebelumnya.setText(a5);
                    // Lakukan sesuatu dengan selectedBarang
                }
            }
        } else if (requestCode == 22) {
            if (resultCode == RESULT_OK) {
                if (data != null) {

                    String a2 = data.getStringExtra("nama_saldo");
                    namaitem.setText(a2);

                    String a3 = data.getStringExtra("sisa_saldo");
                    qtysebelumnya.setText(a3);

                    // Lakukan sesuatu dengan selectedBarang
                }
            }
        } else if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                if (data != null) {

                    String b2 = data.getStringExtra("nama_beli");
                    nama_beli.setText(b2);

                    String b3 = data.getStringExtra("hargamodal");
                    hargamodal_non_rp.setText(b3);

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
                        code.setText("");
                        namaitem.setText("");
                        hargamodal_non_rp.setText("");
                        harga.setText("");
                        qtysebelumnya.setText("");
                        qty.setText("");
                        qtysaatini.setText("0");
                        //keterangan1.setText("");
                        total.setText("0");
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


    public void alert_hapus() {

        String namaitem_alert = namaitem.getText().toString();
        String qty_alert = qty.getText().toString();

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
                .setMessage("Yakin ingin hapus?? \n"+"Item : "+namaitem_alert+" "+qty_alert+"pcs")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapus_input();
                        modal();
                        margin();
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

    public void cek_stok(){


        cari();
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

                if (qtysebelumnya.getText().toString().equals("0")) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //code.setError("harus diisi");
                    alert();
                    hideDialog();
                }else {

                    hideDialog();

                }






                //mTextField.setText("done!");
            }
        }.start();

        //






    }


    private void hapus_input() {
        pDialog.setMessage("Delete Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "hapus_input.php")
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
                            code.setText("");
                            namaitem.setText("");
                            hargamodal_non_rp.setText("");
                            harga.setText("");
                            qtysebelumnya.setText("0");
                            qty.setText("");
                            qtysaatini.setText("0");
                            //keterangan1.setText("");
                            total.setText("0");
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


    private void cari() {

        AndroidNetworking.post(Config.host + "cekitem.php")
                .addBodyParameter("code", code.getText().toString())
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
                        namaitem.setText((response.optString("namaitem")));
                        qtysebelumnya.setText((response.optString("sisastok")));
                        hargamodal_non_rp.setText((response.optString("modal_ubah")));
                        harga.setText((response.optString("harga_ubah")));
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



    public void inputdanjumlahkanaplikasi(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        //jumlahkanaplikasi();

        hitungHasil();
//        int nilai1=Integer.valueOf(qtysebelumnya.getText().toString());
//        int nilai2=Integer.valueOf(qty.getText().toString());
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

                hideDialog();
                inputgak();

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
        String codealert = code.getText().toString();
        String namaitemalert = namaitem.getText().toString();
        String hargamodalalert = hargamodal_rp.getText().toString();
        String totalhargamodalalert = totalhargamodal.getText().toString();
        String hargaalert = harga.getText().toString();
        String qtysebelumnyaalert = qtysebelumnya.getText().toString();
        String qtyalert = qty.getText().toString();
        String qtysaatinialert = qtysaatini.getText().toString();
        String totalalert = totalformatrp.getText().toString();


        //String a = validasib1.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Hallo");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)

                .setIcon(R.drawable.tanya)
                .setTitle(R.string.app_name)
                .setMessage("Silahkan Di cek Kembali ya \n" +
                        "Kamu akan menambah : "+hargamodalalert+"\nStok Cash Menjadi : "+totalalert)
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
                        code.setText("");
                        namaitem.setText("");
                        qtysebelumnya.setText("0");
                        qty.setText("");
                        qtysaatini.setText("");
                        harga.setText("");
                        //hargamodal_non_rp.setText("");
                        //keterangan1.setText("");
                        totalhargamodal.setText("0");
                        total.setText("0");
                        total.setText("0");
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
        AndroidNetworking.post(Config.host + "input_penjualan_gabung_isi.php")
                //.addBodyParameter("id", id_data.getText().toString())
                .addBodyParameter("nama_pelanggan", nama_pelanggan.getText().toString())
                .addBodyParameter("nomor", nomor.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
                .addBodyParameter("nama_beli", nama_beli.getText().toString())
                .addBodyParameter("code", code.getText().toString())
                .addBodyParameter("namaitem", namaitem.getText().toString())

                //.addBodyParameter("qtysebelumnya", qtysebelumnya.getText().toString())
                .addBodyParameter("qty", qty.getText().toString())
                //.addBodyParameter("qtysaatini", qtysaatini.getText().toString())
                .addBodyParameter("hargamodal", hargamodal_non_rp.getText().toString())
                .addBodyParameter("totalmodal", hargamodal_non_rp.getText().toString())
                .addBodyParameter("harga", harga.getText().toString())
                .addBodyParameter("total", total.getText().toString())
                .addBodyParameter("jam", jam.getText().toString())
                .addBodyParameter("keterangan", keterangan.getText().toString())
                .addBodyParameter("isi", isi.getText().toString())
                .addBodyParameter("nama_user", nama_user.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                .addBodyParameter("parameter", parameter.getText().toString())
                .addBodyParameter("kategori", kategori.getText().toString())



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
                            code.setText("");
                            namaitem.setText("");
                            qtysebelumnya.setText("0");
                            qty.setText("");
                            qtysaatini.setText("");
                            harga.setText("");
                            hargamodal_non_rp.setText("");
                            //keterangan1.setText("");
                            totalhargamodal.setText("0");
                            total.setText("0");


                            KasAdapter2();

                            String a = nama_user.getText().toString();
                            String b = posisi.getText().toString();
                            String c = perusahaan.getText().toString();
                            Intent intent = new Intent(Take_out_Cash_Isi.this, Mutasi_Cash.class);
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



    private void hitungHasil() {

        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        int qty_h = Integer.parseInt(saldo2.getText().toString());
        //int harga_h = Integer.parseInt(harga.getText().toString());
        //int hasilharga_h = qty_h * harga_h;

        //int qty_h = Integer.parseInt(qty.getText().toString());
        int hargamodal_h = Integer.parseInt(hargamodal_non_rp.getText().toString());
        int hasilhargamodal_h = hargamodal_h * qty_h;

        int qtysebelumnya_h = Integer.parseInt(qtysebelumnya.getText().toString());
        //int qty_h = Integer.parseInt(harga.getText().toString());
        int hasilsaldo = qty_h + hargamodal_h;



        //total.setText(String.valueOf(hasilsaldo));
        totalformatrp.setText(rupiahFormat.format(hasilsaldo));

        //totalhargamodal.setText(String.valueOf(hasilhargamodal_h));

        //qtysaatini.setText(String.valueOf(hasilstoksaatini_h));



    }


    private void KasAdapter2(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "tampil_gabung.php" )
                .addBodyParameter("nomor", nomor.getText().toString())
                //.addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
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
                                map.put("namaitem",       responses.optString("namaitem"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));
//                                map.put("keterangan",       responses.optString("keterangan"));
//                                map.put("pembayaran",       responses.optString("pembayaran"));

                                //map.put("tanggal",      responses.optString("tanggal"));

                                //jumlah1 += itemCount;

                                subtotal += Integer.parseInt(responses.getString("total"));
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
                        margin();
                        modal();
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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_penjualan,
                new String[] {"id","namaitem","qty","harga", "total"},
                new int[] {R.id.id_list, R.id.nama_list, R.id.qty_list, R.id.harga_list, R.id.total_list});

        listinputperdana1.setAdapter(simpleAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                TextView idDataTextView = (TextView) view.findViewById(R.id.id_list);
                ImageView hapuss = (ImageView) view.findViewById(R.id.hapus_data_list);


                hapuss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                        showDeleteConfirmationDialog(idDataTextView.getText().toString());
                    }





                });


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
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

        AndroidNetworking.post(Config.host + "cek_nomor.php")


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



    private void count_item() {

        AndroidNetworking.post(Config.host + "count_item.php")
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

        AndroidNetworking.post(Config.host + "modal_fisik.php")
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
                        total_modal.setText((response.optString("totalmodal")));
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

        AndroidNetworking.post(Config.host + "margin_fisik.php")
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


    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.fisik) {


            parameter.setText("fisik");

            i_code_t.setVisibility(View.VISIBLE);
            i_code.setVisibility(View.VISIBLE);
            code.setText("");

            i_nama_beli_t.setVisibility(View.GONE);
            i_nama_beli.setVisibility(View.GONE);
            nama_beli.setText("-");

            i_namaitem.setText("Nama Item");

            i_hargamodal_t.setVisibility(View.GONE);
            hargamodal_non_rp.setVisibility(View.GONE);

            i_qty_t.setVisibility(View.VISIBLE);
            qty.setVisibility(View.VISIBLE);
            qty.setText("");

            i_isi_t.setVisibility(View.GONE);
            isi.setVisibility(View.GONE);
            isi.setText("-");

            i_nama_stok_t.setText("Stok Tersedia : ");

            panah.setVisibility(View.GONE);

            kategori.setText("");


//            pembayaran1.setText("CASH");
//            hargabarang1.setEnabled(true);
//            String a = edittext991.getText().toString();
//            idbarang1.setText(a);
//            cariidharga();
//            cariid();
        }
        else if (id == R.id.elektrik){

            parameter.setText("elektrik");

            i_code_t.setVisibility(View.GONE);
            i_code.setVisibility(View.GONE);
            code.setText("-");

            i_nama_beli_t.setVisibility(View.VISIBLE);
            i_nama_beli.setVisibility(View.VISIBLE);
            nama_beli.setText("");

            i_namaitem.setText("Nama Saldo");

            i_hargamodal_t.setVisibility(View.VISIBLE);
            hargamodal_non_rp.setVisibility(View.VISIBLE);

            i_qty_t.setVisibility(View.GONE);
            qty.setVisibility(View.GONE);
            qty.setText("1");

            i_isi_t.setVisibility(View.VISIBLE);
            isi.setVisibility(View.VISIBLE);
            isi.setText("");

            i_nama_stok_t.setText("Saldo Tersedia : ");


            panah.setVisibility(View.VISIBLE);

            kategori.setText("SALDO");

//            pembayaran1.setText("TEMPO");
//            hargabarang1.setText("0");
//            hargabarang1.setEnabled(false);

        }

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
                        saldo2.setText((response.optString("sisa_cash_non_rp")));
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