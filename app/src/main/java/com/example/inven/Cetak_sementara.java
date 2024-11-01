package com.example.inven;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Calendar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.inven.Data.Data_BayarEX;
import com.example.inven.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.example.inven.Until.BluetoothHandler;
import com.example.inven.Until.PrinterCommands;
import com.zj.btsdk.BluetoothService;


import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Cetak_sementara extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BluetoothHandler.HandlerInterface {

    private static final byte[] GS_E = { 0x1B, 0x45 };
    TextView id_data, nomor, nama_input, tanggal, jam, nama_pelanggan, nama_user, posisi, perusahaan,username;
    TextView total, uang, kembalian;

    public static String idlist;
    public static String namabelilist;
    public static String hargalist;
    public static ImageView hapuss;
    public static String qtysebelumnyalist;
    public static String qtylist;
    public static String qtysaatinilist;
    public static String hargamodallist;
    public static String totallist;

    ListView list_bayar;

    Button testpirnt;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    NumberFormat formatter = new DecimalFormat("#,###,###,###");
    int subTotal = 0;


    private static final String TAG = Cetak_input.class.getSimpleName();
    Runnable r;public static final int RC_BLUETOOTH = 0;
    public static final int RC_CONNECT_DEVICE = 1;
    public static final int RC_ENABLE_BLUETOOTH = 2;



    private BluetoothService mService = null;
    private boolean isPrinterReady = false;

    private List<Data_BayarEX> bayarList;
    TextView alamat;
    TextView pt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cetak_sementara);

        list_bayar = (ListView) findViewById(R.id.list_bayar);

        id_data = (TextView) findViewById(R.id.id_data);//1
        nomor = (TextView) findViewById(R.id.nomor);//1
        nama_input = (TextView) findViewById(R.id.nama_input);//1
        tanggal = (TextView) findViewById(R.id.tanggal);//1
        jam = (TextView) findViewById(R.id.jam);//1
        total = (TextView) findViewById(R.id.total);//1
        nama_pelanggan = (TextView) findViewById(R.id.nama_pelanggan);//1
        uang = (TextView) findViewById(R.id.uang);//1
        kembalian = (TextView) findViewById(R.id.kembalian);//1
        nama_user = (TextView) findViewById(R.id.nama_user);//1

        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);
        username = (TextView)findViewById(R.id.username);

        alamat = (TextView)findViewById(R.id.alamat);
        pt = (TextView)findViewById(R.id.pt);

        jam.setText(jamotomatisBayangan());


        testpirnt = (Button) findViewById(R.id.testprint);//1


        testpirnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printDemo();
            }
        });

        Intent i = getIntent();
        String kiriman = i.getStringExtra("tanggal");
        tanggal.setText(kiriman);
        String kiriman2 = i.getStringExtra("nomor");
        nomor.setText(kiriman2);
        String kiriman3 = i.getStringExtra("nama_pelanggan");
        nama_pelanggan.setText(kiriman3);
        String kiriman4 = i.getStringExtra("rp_uang");
        uang.setText(kiriman4);
        String kiriman5 = i.getStringExtra("rp_kembalian");
        kembalian.setText(kiriman5);
        String kiriman6 = i.getStringExtra("nama_user");
        nama_user.setText(kiriman6);
        String kiriman7 = i.getStringExtra("posisi");
        posisi.setText(kiriman7);
        String kiriman8 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman8);
        String kiriman9 = i.getStringExtra("username");
        username.setText(kiriman9);

        bayarList = new ArrayList<Data_BayarEX>();
        setupBluetooth();
        list();

        user();





    }


//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setIcon(R.mipmap.ic_launcher)
//                .setTitle(R.string.app_name)
//                .setMessage("Kembali ke menu utama")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String a = username.getText().toString();
//
//
//                        Intent i = new Intent(getApplicationContext(), Menu.class);
//                        i.putExtra("username", "" + a + "");
//
//
//
//                        startActivity(i);
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                })
//                .show();
//    }


    public String jamotomatisBayangan(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        //SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
        String strdate1 = sdf1.format(c1.getTime());

//        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
//        sdf1.setTimeZone(tzInAmerica);
//        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;


    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); list_bayar.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "tampil_cetak.php" )
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
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("nama_print",       responses.optString("nama_print"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("isi",       responses.optString("isi"));
                                map.put("total_harga",       responses.optString("total_harga"));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));



                                item.setId(responses.getString("id"));
                                item.setNama(responses.getString("nama_print"));
                                item.setJumlah(responses.getString("qty"));
                                item.setKeterangan(responses.getString("isi"));
                                item.setHarga(responses.getString("harga"));

                                subTotal += Integer.parseInt(responses.getString("total"));
                                //jumlah.setText(String.valueOf(itemCount));
                                aruskas.add(map);
                                bayarList.add(item);
                            }


                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //jumlah += itemCount;
                        total.setText("Rp "+formatter.format(subTotal));

                        //jumlah.setText(jumlah);
                        subTotal = 0;

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }




    private void Adapter(){

        View headerView = getLayoutInflater().inflate(R.layout.list_header_fisik_cetak, null);

        list_bayar.addHeaderView(headerView);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_penjualan_cetak,
                new String[] {"id","nama_print","qty","harga","isi","total_harga", "total"},
                new int[] {R.id.id_list, R.id.nama_list, R.id.qty_list, R.id.harga_list, R.id.isi_list, R.id.total_harga_list, R.id.total_list});

        list_bayar.setAdapter(simpleAdapter);
        //swipe_refresh.setRefreshing(false);
    }

    //-------------------------------------------------------------------------------------------------------------------



    protected void printDemo() {
        if (!mService.isAvailable()) {
            Log.i(TAG, "printText: perangkat tidak support bluetooth");
            return;
        }
        if (isPrinterReady) {

            String a = pt.getText().toString();
            String b = alamat.getText().toString();
            String c = tanggal.getText().toString();
            String d = jam.getText().toString();
            String e = nama_user.getText().toString();
            String f = uang.getText().toString();
            String g = kembalian.getText().toString();
            String h = perusahaan.getText().toString();
            // TOTAL LINE 32

            mService.write(PrinterCommands.ESC_ENTER);
            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.write(PrinterCommands.GS_EXCLAMATION_MARK); // Set font size
            mService.write(new byte[]{(byte) 0x9}); // Double-width and double-height
            mService.sendMessage("" + a, "");
            mService.sendMessage("" + b, "");
            mService.sendMessage("Status : Pending" , "");
            mService.write(PrinterCommands.GS_EXCLAMATION_MARK); // Reset font size
            mService.write(new byte[]{(byte) 0x00}); // Normal size
            //mService.sendMessage("MOONEE SHOP", "");
//            mService.sendMessage("AMINO OFFICIAL SHOP", "");
//             mService.sendMessage("IP SOLUTION DYNAMIC", "");
//            mService.sendMessage("YBIT STORE", "");
//            mService.sendMessage("Shared Hosting Premium", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.small);

//            mService.sendMessage("Jl.Mampang Prapatan Raya No.98 C", "");
//            mService.sendMessage("Jakarta Selatan 12790 Indonesia", "");
//            mService.sendMessage("NPWP. 01.764.602.7-014.000", "");
//            mService.sendMessage("Tel.(62-21)79191965", "");
//            mService.sendMessage("Fax. (62-21)7902523", "");
            mService.write(PrinterCommands.FEED_LINE);
            mService.sendMessage("Struk", "");



            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage("Tanggal : "+c+" | "+d, ""); //Namasales
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage("Nama admin : "+e, ""); //idoutlet
//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage(""+a, ""); //namaoutlet
//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage(""+b, ""); //tanggal
            mService.write(PrinterCommands.small);

            String ans = "no" +"nama";

            int n = (32 - ("ITEM".length() + "HARGA".length()));
            ans = "ITEM" + new String(new char[n]).replace("\0", " ") + "HARGA";

            mService.sendMessage(""+ans, "");
            mService.sendMessage("................................", "");
            for(int i=0; i<bayarList.size(); i++) {

                Data_BayarEX data = bayarList.get(i);
                int quantity = Integer.parseInt(bayarList.get(i).getJumlah());
                //int ket = Integer.parseInt(bayarList.get(i).getKeterangan());
                int sub = quantity * Integer.parseInt(bayarList.get(i).getHarga());
                String Ssub = ""+formatter.format(sub);
                String Snama = ""+bayarList.get(i).getNama();
                String Sket = ""+bayarList.get(i).getKeterangan();
                String Spem = ""+bayarList.get(i).getpembayaran();
                //Double ket = Double.parseDouble(bayarList.get(i).getKeterangan());

                int Shh = Integer.parseInt(bayarList.get(i).getHarga());
                String Sjml = ""+quantity+" x @"+formatter.format(Shh);

                int isi = (32 - (Sjml.length() + Ssub.length()));
                String pr = Sjml + new String(new char[isi]).replace("\0", " ") + Ssub;
                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(""+Snama, "");

                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage(""+pr, "");

                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(""+Sket, "");
//
//                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//                mService.sendMessage("Pembayaran : ("+Spem+")", "");

                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(" ", "");

                subTotal += sub;



            }

            mService.sendMessage("................................", "");

            String subp = ""+formatter.format(subTotal);
            int isi = (32 - ("SubTotal".length() + subp.length()));
            String pr = "SubTotal" + new String(new char[isi]).replace("\0", " ") + subp;

            mService.sendMessage(""+pr, "");
            subTotal = 0;


            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage("Uang : "+f, ""); //idoutlet
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage("Kembalian : "+g, ""); //idoutlet

            // mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("TERIMA KASIH", "");

//            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
//            mService.sendMessage("Harga Sudah Termasuk PPN 11%", "");



//            mService.write(PrinterCommands.FEED_LINE);
//            mService.sendMessage("Klik NoIndihome untuk Informasi Berlangganan dan Pengaturan Layanan.", "");
//            mService.sendMessage("Klik Domain untuk mengecek DDNS telah berfungsi, tanpa setting di modem.", "");
//            mService.sendMessage("DDNS hanya berfungsi dengan baik pada paket IndiHome Gamer.", "");
//            mService.sendMessage("Optional = Anda bisa mensetting DDNS Client di modem.", "");
//            mService.sendMessage("Info lebih lanjut, silahkan kirim email ke : admin@ip-solutiondynamic.com", "");

//                        mService.write(PrinterCommands.FEED_LINE);
//            mService.sendMessage("Klik Hostinger untuk Informasi Berlangganan dan Pengaturan Layanan.", "");
//            mService.sendMessage("Klik Domain untuk mengecek DDNS telah berfungsi, tanpa setting di website.", "");
//            mService.sendMessage("DDNS hanya berfungsi dengan baik pada paket Hostinger Premium.", "");
//            mService.sendMessage("Optional = Anda bisa mensetting DDNS Hostinger @rekamitrayasa.com.", "");
//            mService.sendMessage("Info lebih lanjut, silahkan kirim email ke : abusee@hostinger.com", "");


            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);

//
//
//
            mService.write(GS_E); // Enable bold text

            mService.sendMessage(" Whatsapp", "");
            mService.sendMessage(""+h, "");

            //mService.write(GS_E); // Disable bold text
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);



        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(Cetak_sementara.this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }

    }



    @AfterPermissionGranted(RC_BLUETOOTH)
    private void setupBluetooth() {
        String[] params = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};
        if (!EasyPermissions.hasPermissions(this, params)) {
            EasyPermissions.requestPermissions(this, "You need bluetooth permission",
                    RC_BLUETOOTH, params);
            return;
        }
        mService = new BluetoothService(this, new BluetoothHandler(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_ENABLE_BLUETOOTH:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: bluetooth aktif");
                } else
                    Log.i(TAG, "onActivityResult: bluetooth harus aktif untuk menggunakan fitur ini");
                break;
            case RC_CONNECT_DEVICE:
                if (resultCode == RESULT_OK) {
                    String address = data.getExtras().getString(DeviceActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice mDevice = mService.getDevByMac(address);
                    mService.connect(mDevice);
                    //Toast.makeText(MainActivity.this,""+address,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }





    private void requestBluetooth() {
        if (mService != null) {
            if (!mService.isBTopen()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, RC_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    public void onDeviceConnected() {
        isPrinterReady = true;
    }

    @Override
    public void onDeviceConnecting() {

    }

    @Override
    public void onDeviceConnectionLost() {
        isPrinterReady = false;
    }

    @Override
    public void onDeviceUnableToConnect() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }



    private void user() {
//        pDialog.setMessage("Verifikasi...");
//        showDialog();
//        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "user_cetak.php")
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
                        alamat.setText((response.optString("alamat")));
                        pt.setText((response.optString("nomor")));
//                        posisi.setText((response.optString("posisi")));
//                        perusahaan.setText((response.optString("perusahaan")));
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


}