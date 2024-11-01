package com.example.inven;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class List_data_saldo extends AppCompatActivity {
    ImageView hapus;
    TextView nama_belilist, hargalist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_data_saldo);






        hapus = (ImageView) findViewById(R.id.hapus_data_list);
        nama_belilist = (TextView) findViewById(R.id.nama_belilist);
        hargalist = (TextView) findViewById(R.id.harga_list);












        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert_hapus();
            }
        });







    }


    public void alert_hapus() {

        String namaitem_alert = nama_belilist.getText().toString();
        String qty_alert = hargalist.getText().toString();

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
                .setMessage("Yakin ingin hapus?? \n"+"Item : "+namaitem_alert+" "+qty_alert+"")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        hapus_input();
//                        count_item();
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



//    private void hapus_input() {
//        pDialog.setMessage("Delete Process...");
//        showDialog();
//        pDialog.setCanceledOnTouchOutside(false);
//        AndroidNetworking.post(Config.host + "hapus_input_elektrik.php")
//                .addBodyParameter("id", id_data.getText().toString())
//
//                .setPriority(Priority.MEDIUM)
//                .build()
//
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//
//                        Log.d("response", response.toString());
//
//                        if (response.optString("response").toString().equals("success")) {
//                            hideDialog();
//                            //gotoCourseActivity();
//                            Toast.makeText(getApplicationContext(), "Delete Data Penjualan Berhasil",
//                                    Toast.LENGTH_LONG).show();
//                            KasAdapter2();
//                            nama_pelanggan.setText("Tanpa Nama");
//                            nama_beli.setText("");
//                            //nama_saldo.setText("");
//                            hargamodal.setText("");
//                            //saldo_sebelumnya.setText("");
//                            harga.setText("");
//                            saldo_saat_ini.setText("0");
//                            isi.setText("");
//                            //
//
//
//                        } else {
//                            hideDialog();
//                            Toast.makeText(getApplicationContext(), "Gagal",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                    }
//                });
//    }









}