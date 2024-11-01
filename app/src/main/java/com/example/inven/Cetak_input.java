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

public class Cetak_input extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BluetoothHandler.HandlerInterface{

    TextView id_data, nomor, nama_input, tanggal, jam, nama_pelanggan, nama_user, posisi, perusahaan;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cetak_input);

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

        bayarList = new ArrayList<Data_BayarEX>();
        setupBluetooth();
        list();




    }



    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        list_bayar.setAdapter(null);

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
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("nama_beli",       responses.optString("nama_beli"));
                                map.put("nama_saldo",       responses.optString("nama_saldo"));
                                map.put("harga",       rupiahFormat.format(responses.optDouble("harga")));
                                map.put("isi",       responses.optString("isi"));
//                                map.put("pembayaran",       responses.optString("pembayaran"));

                                //map.put("tanggal",      responses.optString("tanggal"));

                                item.setId(responses.getString("id"));
                                item.setNama(responses.getString("nama_beli"));
                                item.setHarga(responses.getString("harga"));
                                item.setKeterangan(responses.getString("isi"));

                                //jumlah1 += itemCount;
                                //total_int += Integer.parseInt(responses.getString("harga"));
                                subTotal += Integer.parseInt(responses.getString("harga"));
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

    private void Adapter() {
        // Create a header view
        View headerView = getLayoutInflater().inflate(R.layout.list_header_saldo_cetak, null);

        // Add header view to the list view
        list_bayar.addHeaderView(headerView);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_saldo_cetak,
                new String[]{"id", "nama_beli", "nama_saldo", "harga","isi"},
                new int[]{R.id.id_list, R.id.nama_belilist, R.id.nama_saldolist, R.id.harga_list, R.id.isi_list});

        // Set the adapter to the list view
        list_bayar.setAdapter(simpleAdapter);
    }





    //-------------------------------------------------------------------------------------------------------------------



    protected void printDemo() {
        if (!mService.isAvailable()) {
            Log.i(TAG, "printText: perangkat tidak support bluetooth");
            return;
        }
        if (isPrinterReady) {

//            String a = namaoutletinputperdana1.getText().toString();
//            String b = tanggalinputperdana1.getText().toString();
//            String c = idoutletinputperdana1.getText().toString();
//            String d = namasalesinputperdana1.getText().toString();
            // TOTAL LINE 32

            mService.write(PrinterCommands.ESC_ENTER);
            mService.write(PrinterCommands.besar);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);

            mService.write(PrinterCommands.biasa);
            //mService.sendMessage("Wisma Barcode Jakarta Barat", "");
            mService.sendMessage("PT.REKA MITRAYASA KOMUNIKATAMA", "");
            //mService.sendMessage("MOONEE SHOP", "");
//            mService.sendMessage("AMINO OFFICIAL SHOP", "");
//             mService.sendMessage("IP SOLUTION DYNAMIC", "");
//            mService.sendMessage("YBIT STORE", "");
//            mService.sendMessage("Shared Hosting Premium", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.small);

            mService.sendMessage("Jl.Mampang Prapatan Raya No.98 C", "");
            mService.sendMessage("Jakarta Selatan 12790 Indonesia", "");
            mService.sendMessage("NPWP. 01.764.602.7-014.000", "");
            mService.sendMessage("Tel.(62-21)79191965", "");
            mService.sendMessage("Fax. (62-21)7902523", "");
            mService.write(PrinterCommands.FEED_LINE);
            mService.sendMessage("Struk", "");



//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage(""+d, ""); //Namasales
//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage(""+c, ""); //idoutlet
//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage(""+a, ""); //namaoutlet
//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage(""+b, ""); //tanggal
//            mService.write(PrinterCommands.small);

            String ans = "no" +"nama";

            int n = (32 - ("ITEM".length() + "NOMINAL".length()));
            ans = "ITEM" + new String(new char[n]).replace("\0", " ") + "NOMINAL";

            mService.sendMessage(""+ans, "");
            mService.sendMessage("................................", "");
            for(int i=0; i<bayarList.size(); i++) {

                Data_BayarEX data = bayarList.get(i);
                //int quantity = Integer.parseInt(bayarList.get(i).getJumlah());
                //int ket = Integer.parseInt(bayarList.get(i).getKeterangan());
                int sub = Integer.parseInt(bayarList.get(i).getHarga());
                String Ssub = ""+formatter.format(sub);
                String Snama = ""+bayarList.get(i).getNama();
                String Sket = ""+bayarList.get(i).getKeterangan();
                //String Spem = ""+bayarList.get(i).getpembayaran();
                //Double ket = Double.parseDouble(bayarList.get(i).getKeterangan());

                int Shh = Integer.parseInt(bayarList.get(i).getHarga());
                //String Sjml = ""+quantity+" x @"+formatter.format(Shh);

                int isi = (32 - Ssub.length());
                String pr = new String(new char[isi]).replace("\0", " ") + Ssub;
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

            // mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("TERIMA KASIH", "");

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("Harga Sudah Termasuk PPN 11%", "");



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


//
//
//
            mService.sendMessage("Barang yang sudah dibeli tidak  bisa di kembalikan/di tukar.", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);



        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(Cetak_input.this, DeviceActivity.class), RC_CONNECT_DEVICE);
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

}