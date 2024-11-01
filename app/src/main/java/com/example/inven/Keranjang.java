package com.example.inven;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.inven.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Keranjang extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Context context;

    EditText nama_pelanggan, nama_beli, code, namaitem, qty, qtysaatini, hargamodal, totalhargamodal, harga, isi, kategori;
    TextView id_data, nomor, nama_user, posisi, perusahaan;
    TextView tanggal, parameter;
    TextView qtysebelumnya;
    TextView jam;
    TextView jumlah, total_non_rp, nama_input;
    TextView total, subtotal_sementara, total_modal, margin, keterangan;
    TextView totalformatrp;

    LinearLayout hapus_keranjang, selesai_keranjang;

    NumberFormat formatter = new DecimalFormat("#,###,###,###");

    int subtotal = 0;

    Button input, refresh, kepembayaran;

    ListView listinputperdana1;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    LinearLayout back2;
    ImageView back;

    TextView username;


    public static String idlist, nomorlist, codelist, namaitemlist, qtysebelumnyalist, qtylist, qtysaatinilist, hargamodallist, hargalist, totallist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keranjang);

        idlist = "";

        listinputperdana1 = (ListView) findViewById(R.id.listinputperdana);

        username = (TextView)findViewById(R.id.username);
        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);
        id_data = (TextView) findViewById(R.id.id_data);
        nomor = (TextView) findViewById(R.id.nomor);
        nama_user = (TextView) findViewById(R.id.nama_user);
        tanggal = (TextView) findViewById(R.id.tanggal);
        jam = (TextView) findViewById(R.id.jam);
        nomor = (TextView) findViewById(R.id.nomor);
        total_non_rp = (TextView) findViewById(R.id.total_non_rp);
        nama_input = (TextView) findViewById(R.id.nama_input);
        nama_pelanggan = (EditText) findViewById(R.id.nama_pelanggan);

        jumlah = (TextView) findViewById(R.id.jumlah);
        //jumlah = (TextView) findViewById(R.id.jumlah);
        total = (TextView) findViewById(R.id.total);
        totalformatrp = (TextView) findViewById(R.id.totalformatrp);
        subtotal_sementara = (TextView) findViewById(R.id.subtotal_sementara);
        total_modal = (TextView) findViewById(R.id.total_modal);
        margin = (TextView) findViewById(R.id.margin);

        refresh = (Button) findViewById(R.id.refresh);
        kepembayaran = (Button) findViewById(R.id.kepembayaran);

        hapus_keranjang = (LinearLayout) findViewById(R.id.hapus_keranjang);
        selesai_keranjang = (LinearLayout) findViewById(R.id.selesai_keranjang);

        back = (ImageView) findViewById(R.id.back);
        back2 = (LinearLayout) findViewById(R.id.back2);

        Intent i = getIntent();
        String kiriman2 = i.getStringExtra("nomor");
        nomor.setText(kiriman2);
        String kiriman14 = i.getStringExtra("tanggal");
        tanggal.setText(kiriman14);
        String kiriman3 = i.getStringExtra("nama_input");
        nama_input.setText(kiriman3);
        String kiriman4 = i.getStringExtra("rp_total");
        subtotal_sementara.setText(kiriman4);
        String kiriman5 = i.getStringExtra("total_non_rp");
        total_non_rp.setText(kiriman5);
        String kiriman6 = i.getStringExtra("nama_pelanggan");
        nama_pelanggan.setText(kiriman6);
        String kiriman8 = i.getStringExtra("nama_user");
        nama_user.setText(kiriman8);
//        String kiriman9 = i.getStringExtra("jumlah_item");
//        jumlah.setText(kiriman9);
//        String kiriman10 = i.getStringExtra("modal");
//        total_modal.setText(kiriman10);
//        String kiriman11 = i.getStringExtra("margin");
//        margin.setText(kiriman11);
        String kiriman12 = i.getStringExtra("posisi");
        posisi.setText(kiriman12);
        String kiriman13 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman13);
        String kiriman15 = i.getStringExtra("username");
        username.setText(kiriman15);

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                KasAdapter2();
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }

        });

        back2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String a = tanggal.getText().toString();
                String b = nomor.getText().toString();
                String c = nama_pelanggan.getText().toString();
//                String d = rp_uang.getText().toString();
//                String e = rp_kembalian.getText().toString();
                String f = nama_user.getText().toString();
                String g = posisi.getText().toString();
                String h = perusahaan.getText().toString();
                String j = username.getText().toString();

                Intent i = new Intent(getApplicationContext(), Cetak_sementara.class);
                i.putExtra("tanggal", "" + a + "");
                i.putExtra("nomor", "" + b + "");
                i.putExtra("nama_pelanggan", "" + c + "");
//                i.putExtra("rp_uang", "" + d + "");
//                i.putExtra("rp_kembalian", "" + e + "");
                i.putExtra("nama_user", "" + f + "");
                i.putExtra("posisi", "" + g + "");
                i.putExtra("perusahaan", "" + h + "");
                i.putExtra("username", "" + j + "");

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);
            }

        });

        hapus_keranjang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                hapus_keranjang.setVisibility(View.GONE);
                selesai_keranjang.setVisibility(View.VISIBLE);
                KasAdapter3();
            }

        });

        selesai_keranjang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                hapus_keranjang.setVisibility(View.VISIBLE);
                selesai_keranjang.setVisibility(View.GONE);
                KasAdapter2();
            }

        });

        selesai_keranjang.setVisibility(View.GONE);


        kepembayaran.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if (subtotal_sementara.getText().toString().equals("Rp 0")) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //nama_beli.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Belum ada item", Toast.LENGTH_LONG).show();
                }else{

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
                    String n = username.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Pembayaran_akhir.class);
                    i.putExtra("tanggal", "" + a + "");
                    i.putExtra("nomor", "" + b + "");
                    i.putExtra("nama_input", "" + c + "");
                    i.putExtra("rp_total", "" + d + "");
                    i.putExtra("total_non_rp", "" + e + "");
                    i.putExtra("nama_pelanggan", "" + f + "");
                    i.putExtra("nama_user", "" + g + "");
                    i.putExtra("jumlah_item", "" + h + "");
                    i.putExtra("modal", "" + j + "");
                    i.putExtra("margin", "" + k + "");
                    i.putExtra("posisi", "" + l + "");
                    i.putExtra("perusahaan", "" + m + "");
                    i.putExtra("username", "" + n + "");

                    startActivity(i);

                }




            }

        });


        KasAdapter2();

    }



    private void KasAdapter2(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "tampil_gabung_keranjang.php" )
                .addBodyParameter("nomor", nomor.getText().toString())
                //.addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                //.addBodyParameter("parameter", parameter.getText().toString())
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
                                map.put("nama_print",       responses.optString("nama_print"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("total",       responses.optString("total"));
                                map.put("parameter",       responses.optString("parameter"));
//                                map.put("keterangan",       responses.optString("keterangan"));
//                                map.put("pembayaran",       responses.optString("pembayaran"));

                                //map.put("tanggal",      responses.optString("tanggal"));

                                //jumlah1 += itemCount;

                                //subtotal += Integer.parseInt(responses.getString("total"));
                                //jumlah.setText(String.valueOf(itemCount));
                                aruskas.add(map);
                            }


                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //jumlah += itemCount;
//                        subtotal_sementara.setText("Rp "+formatter.format(subtotal));
//                        total_non_rp.setText(Integer.toString(subtotal));
//                        count_item();
//                        margin();
//                        modal();
                        //jumlah.setText(jumlah);
                        //subtotal = 0;

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }




    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_keranjang,
                new String[] {"id","nama_print","qty","harga", "total", "parameter"},
                new int[] {R.id.id_list, R.id.nama_list, R.id.qty_list, R.id.harga_list, R.id.total_list, R.id.parameter_list});

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

            }
        });

        //swipe_refresh.setRefreshing(false);
    }


    private void KasAdapter3(){

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
                                map.put("nama_print",       responses.optString("nama_print"));
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


                            Adapter2();

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




    private void Adapter2(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_penjualan,
                new String[] {"id","nama_print","qty","harga", "total"},
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
                            hapus_keranjang.setVisibility(View.GONE);
                            selesai_keranjang.setVisibility(View.VISIBLE);
                            KasAdapter3();
                            count_item();
                            modal();
                            margin();
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


}