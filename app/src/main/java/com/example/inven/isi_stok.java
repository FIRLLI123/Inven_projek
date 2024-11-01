package com.example.inven;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.inven.Data.Data_BayarEX;
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

public class isi_stok extends AppCompatActivity {

    TextView code, namaitem, hargamodal, totalhargamodal, harga, sisastok;
    ListView listdataoutlet1;
    Button tambah_item;
    EditText namabarang,namabarang_code;

    TextView nama_user, posisi, perusahaan, kategori;

    LinearLayout caridatabarang,caridatabarang_code;
    TextView keseluruhan,perdana,voucher,aksesoris,lainnya;
    TextView keseluruhan_k,makanan,minuman,cemilan,lainnya_k;
    TextView jumlah_item;

    LinearLayout klik_tanggal, cari_tanggal;
    TextView tanggal_awal, tanggal;

    TextView bidang;
    LinearLayout outlet, kuliner;
    LinearLayout code_item, nama_item;
    LinearLayout blank_gambar;



    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;


    public static String LINK, codelist, namaitemlist, hargamodallist, hargalist, sisastoklist, kategorilist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    private ProgressDialog pDialog;
    private Context context;

    private Drawable clearIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isi_stok);


        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        LINK = Config.host + "history.php";
        codelist = "";
        namaitemlist = "";
        hargamodallist = "";
        hargalist = "";
        sisastoklist = "";
        kategorilist = "";

        context = isi_stok.this;
        pDialog = new ProgressDialog(context);

        keseluruhan = (TextView) findViewById(R.id.keseluruhan);
        perdana = (TextView) findViewById(R.id.perdana);
        voucher = (TextView) findViewById(R.id.voucher);
        aksesoris = (TextView) findViewById(R.id.aksesoris);
        lainnya = (TextView) findViewById(R.id.lainnya);


        keseluruhan_k = (TextView) findViewById(R.id.keseluruhan_k);
        makanan = (TextView) findViewById(R.id.makanan);
        minuman = (TextView) findViewById(R.id.minuman);
        cemilan = (TextView) findViewById(R.id.cemilan);
        lainnya_k = (TextView) findViewById(R.id.lainnya_k);

        klik_tanggal = (LinearLayout) findViewById(R.id.klik_tanggal);
        cari_tanggal = (LinearLayout) findViewById(R.id.cari_tanggal);
        tanggal_awal = (TextView) findViewById(R.id.tanggal_awal);
        tanggal = (TextView) findViewById(R.id.tanggal);

        bidang = (TextView) findViewById(R.id.bidang);
        kuliner = (LinearLayout) findViewById(R.id.kuliner);
        outlet = (LinearLayout) findViewById(R.id.outlet);

        code_item = (LinearLayout) findViewById(R.id.code_item);
        nama_item = (LinearLayout) findViewById(R.id.nama_item);

        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);



        nama_user = (TextView) findViewById(R.id.nama_user);//1
        posisi = (TextView)findViewById(R.id.posisi);
        perusahaan = (TextView)findViewById(R.id.perusahaan);
        kategori = (TextView)findViewById(R.id.kategori);

        jumlah_item = (TextView)findViewById(R.id.jumlah_item);



        code = (TextView) findViewById(R.id.code);
        namaitem = (TextView) findViewById(R.id.namaitem);
        hargamodal = (TextView) findViewById(R.id.hargamodal);

        harga = (TextView) findViewById(R.id.harga);
        sisastok = (TextView) findViewById(R.id.sisastok);

        tambah_item = (Button) findViewById(R.id.tambah_item);



        namabarang = (EditText) findViewById(R.id.namabarang);
        namabarang_code = (EditText) findViewById(R.id.namabarang_code);



        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        caridatabarang = (LinearLayout) findViewById(R.id.caridatabarang);
        caridatabarang_code = (LinearLayout) findViewById(R.id.caridatabarang_code);
        //cekdetail1 = (Button) findViewById(R.id.cekdetail)


        namabarang_code.setCompoundDrawables(null, null, null, null);
        namabarang.setCompoundDrawables(null, null, null, null);


        // Initialize the clear (X) icon outside of TextWatcher
        clearIcon = getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
        if (clearIcon != null) {
            int ukuranIkon = getResources().getDimensionPixelSize(R.dimen.fab_margin); // Gunakan ukuran yang diinginkan
            clearIcon.setBounds(0, 0, ukuranIkon, ukuranIkon);
        }

        caridatabarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        caridatabarang_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        klik_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        cari_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesdasboard1();
            }
        });

        tanggal.setText(getCurrentDate());

        list();


        namabarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Show/hide the clear (X) icon based on text input
                namabarang.setCompoundDrawables(null, null,
                        charSequence.length() > 0 ? clearIcon : null, null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Call list() method after text has changed
                list();
            }
        });

        // Add a touch listener to handle the clear button click
        namabarang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable[] compoundDrawables = namabarang.getCompoundDrawables();
                    if (compoundDrawables.length > DRAWABLE_RIGHT && compoundDrawables[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (namabarang.getRight() - compoundDrawables[DRAWABLE_RIGHT].getBounds().width())) {
                            // Clear the text when clear (X) icon is clicked
                            namabarang.setText("");
                            return true;
                        }
                    }
                }
                return false;
            }
        });


        namabarang_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Show/hide the clear (X) icon based on text input
                namabarang_code.setCompoundDrawables(null, null,
                        charSequence.length() > 0 ? clearIcon : null, null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Call list() method after text has changed
                list();
            }
        });

        // Add a touch listener to handle the clear button click
        namabarang_code.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable[] compoundDrawables = namabarang_code.getCompoundDrawables();
                    if (compoundDrawables.length > DRAWABLE_RIGHT && compoundDrawables[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (namabarang_code.getRight() - compoundDrawables[DRAWABLE_RIGHT].getBounds().width())) {
                            // Clear the text when clear (X) icon is clicked
                            namabarang_code.setText("");
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        tambah_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nama_user.getText().toString().equals("null")) {

                    Toast.makeText(getApplicationContext(), "Tunggu sebentar, koneksi mu lemah", Toast.LENGTH_LONG).show();
                } else if (posisi.getText().toString().equals("Owner")) {

                    String a = nama_user.getText().toString();
                    String b = posisi.getText().toString();
                    String c = perusahaan.getText().toString();
                    String d = bidang.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Input_item_stok.class);
                    i.putExtra("nama_user", "" + a + "");
                    i.putExtra("posisi", "" + b + "");
                    i.putExtra("perusahaan", "" + c + "");
                    i.putExtra("bidang", "" + d + "");

                    startActivity(i);

                }else{

                    Toast.makeText(getApplicationContext(), "AKSES DIBATASI", Toast.LENGTH_LONG).show();

                }

            }
        });




        kategori.setText("");

//-------------------------------------------------------------------------------------
        keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom11));
        keseluruhan_k.setTextColor(getResources().getColor(R.color.birutua));

        makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
        makanan.setTextColor(getResources().getColor(R.color.abutua));

        minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
        minuman.setTextColor(getResources().getColor(R.color.abutua));

        cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
        cemilan.setTextColor(getResources().getColor(R.color.abutua));

        lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
        lainnya_k.setTextColor(getResources().getColor(R.color.abutua));


        //----------------------------------------------------------------------------------------------


        keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
        keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

        perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
        perdana.setTextColor(getResources().getColor(R.color.abutua));

        voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
        voucher.setTextColor(getResources().getColor(R.color.abutua));

        aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
        aksesoris.setTextColor(getResources().getColor(R.color.abutua));

        lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
        lainnya.setTextColor(getResources().getColor(R.color.abutua));

        jumlah_item();








        keseluruhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("");

                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
                keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

                perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
                perdana.setTextColor(getResources().getColor(R.color.abutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
                voucher.setTextColor(getResources().getColor(R.color.abutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
                aksesoris.setTextColor(getResources().getColor(R.color.abutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("perdana");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                perdana.setBackground(getResources().getDrawable(R.drawable.custom11));
                perdana.setTextColor(getResources().getColor(R.color.birutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
                voucher.setTextColor(getResources().getColor(R.color.abutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
                aksesoris.setTextColor(getResources().getColor(R.color.abutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("voucher");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
                perdana.setTextColor(getResources().getColor(R.color.abutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom11));
                voucher.setTextColor(getResources().getColor(R.color.birutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
                aksesoris.setTextColor(getResources().getColor(R.color.abutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });

        aksesoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("aksesoris");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
                perdana.setTextColor(getResources().getColor(R.color.abutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
                voucher.setTextColor(getResources().getColor(R.color.abutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom11));
                aksesoris.setTextColor(getResources().getColor(R.color.birutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });



        lainnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("lainnya");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                perdana.setBackground(getResources().getDrawable(R.drawable.custom10));
                perdana.setTextColor(getResources().getColor(R.color.abutua));

                voucher.setBackground(getResources().getDrawable(R.drawable.custom10));
                voucher.setTextColor(getResources().getColor(R.color.abutua));

                aksesoris.setBackground(getResources().getDrawable(R.drawable.custom10));
                aksesoris.setTextColor(getResources().getColor(R.color.abutua));

                lainnya.setBackground(getResources().getDrawable(R.drawable.custom11));
                lainnya.setTextColor(getResources().getColor(R.color.birutua));

                prosesdasboard1();

            }
        });


//-----------------------------------------------------------------------------



        keseluruhan_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("");

                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom11));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.birutua));

                makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                makanan.setTextColor(getResources().getColor(R.color.abutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
                minuman.setTextColor(getResources().getColor(R.color.abutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
                cemilan.setTextColor(getResources().getColor(R.color.abutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya_k.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("makanan");
                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.abutua));



                makanan.setBackground(getResources().getDrawable(R.drawable.custom11));
                makanan.setTextColor(getResources().getColor(R.color.birutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
                minuman.setTextColor(getResources().getColor(R.color.abutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
                cemilan.setTextColor(getResources().getColor(R.color.abutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya_k.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("minuman");
                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.abutua));



                makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                makanan.setTextColor(getResources().getColor(R.color.abutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom11));
                minuman.setTextColor(getResources().getColor(R.color.birutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
                cemilan.setTextColor(getResources().getColor(R.color.abutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya_k.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });

        cemilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("cemilan");
                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.abutua));



                makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                makanan.setTextColor(getResources().getColor(R.color.abutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
                minuman.setTextColor(getResources().getColor(R.color.abutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom11));
                cemilan.setTextColor(getResources().getColor(R.color.birutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                lainnya_k.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });



        lainnya_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kategori.setText("lainnya");
                keseluruhan_k.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan_k.setTextColor(getResources().getColor(R.color.abutua));



                makanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                makanan.setTextColor(getResources().getColor(R.color.abutua));

                minuman.setBackground(getResources().getDrawable(R.drawable.custom10));
                minuman.setTextColor(getResources().getColor(R.color.abutua));

                cemilan.setBackground(getResources().getDrawable(R.drawable.custom10));
                cemilan.setTextColor(getResources().getColor(R.color.abutua));

                lainnya_k.setBackground(getResources().getDrawable(R.drawable.custom11));
                lainnya_k.setTextColor(getResources().getColor(R.color.birutua));

                prosesdasboard1();

            }
        });



        Intent i = getIntent();
        String kiriman = i.getStringExtra("nama_user");
        nama_user.setText(kiriman);
        String kiriman2 = i.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman3);
        String kiriman4 = i.getStringExtra("bidang");
        bidang.setText(kiriman4);

        prosesdasboard1();




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
                jumlah_item();
                list();

                if(bidang.getText().toString().equals("Kuliner")){

                    kuliner.setVisibility(View.VISIBLE);
                    outlet.setVisibility(View.GONE);

                }else if(bidang.getText().toString().equals("Outlet")){

                    kuliner.setVisibility(View.GONE);
                    outlet.setVisibility(View.VISIBLE);

                }else{


                }

                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

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
                tanggal.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "data_barang_ambil_tanggal.php" )
                .addBodyParameter("code", namabarang_code.getText().toString())
                .addBodyParameter("kategori", kategori.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                .addBodyParameter("namaitem", namabarang.getText().toString())
                .addBodyParameter("tanggalawal", tanggal_awal.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("code",         responses.optString("code"));
                                map.put("namaitem",         responses.optString("namaitem"));
                                map.put("modal_ubah",       responses.optString("modal_ubah"));
                                map.put("harga_ubah",       responses.optString("harga_ubah"));
                                map.put("sisastok",       responses.optString("sisastok"));
                                map.put("kategori",       responses.optString("kategori"));



                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        ttl.setText("Total : Rp "+formatter.format(total));
//                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter() {
        if (aruskas.isEmpty()) {
            // Jika aruskas kosong, tampilkan blank_gambar
            // Misalnya:
             blank_gambar.setVisibility(View.VISIBLE);
             listdataoutlet1.setVisibility(View.GONE);
        } else {
            blank_gambar.setVisibility(View.GONE);
            listdataoutlet1.setVisibility(View.VISIBLE);
            // Jika ada data dalam aruskas, tampilkan data dalam listview
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_data_barang_ambil,
                    new String[]{"code", "namaitem", "modal_ubah", "harga_ubah", "sisastok", "kategori"},
                    new int[]{R.id.code_list, R.id.namaitem_list, R.id.hargamodal_list, R.id.harga_list, R.id.sisa_stok_list, R.id.kategori_list});

            listdataoutlet1.setAdapter(simpleAdapter);

            listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    codelist = ((TextView) view.findViewById(R.id.code_list)).getText().toString();
                    namaitemlist = ((TextView) view.findViewById(R.id.namaitem_list)).getText().toString();
                    hargamodallist = ((TextView) view.findViewById(R.id.hargamodal_list)).getText().toString();
                    hargalist = ((TextView) view.findViewById(R.id.harga_list)).getText().toString();
                    sisastoklist = ((TextView) view.findViewById(R.id.sisa_stok_list)).getText().toString();
                    kategorilist = ((TextView) view.findViewById(R.id.kategori_list)).getText().toString();

                    // Remove commas from the string values
                    hargamodallist = hargamodallist.replace(",", "");
                    hargalist = hargalist.replace(",", "");
                    sisastoklist = sisastoklist.replace(",", "");

                    // Parse string values to numbers
//                double hargamodalValue = Double.parseDouble(hargamodallist);
//                double hargaValue = Double.parseDouble(hargalist);
//                int sisastokValue = Integer.parseInt(sisastoklist);
                    // Set the parsed values to the appropriate TextViews

                    code.setText(codelist);
                    namaitem.setText(namaitemlist);
                    hargamodal.setText(String.valueOf(hargamodallist)); // Set as plain numeric value
                    harga.setText(String.valueOf(hargalist)); // Set as plain numeric value
                    sisastok.setText(String.valueOf(sisastoklist)); // Set as plain numeric value
                    kategori.setText(kategorilist);


                    if (posisi.getText().toString().equals("Owner")) {

                        String a = code.getText().toString();
                        String b = namaitem.getText().toString();
                        String c = hargamodal.getText().toString();
                        String d = harga.getText().toString();
                        String e = sisastok.getText().toString();
                        String f = nama_user.getText().toString();
                        String g = posisi.getText().toString();
                        String h = perusahaan.getText().toString();
                        String j = kategori.getText().toString();
                        String k = bidang.getText().toString();
                        Intent i = new Intent(getApplicationContext(), Input_isi_stok.class);
                        i.putExtra("code", "" + a + "");
                        i.putExtra("namaitem", "" + b + "");
                        i.putExtra("hargamodal", "" + c + "");
                        i.putExtra("harga", "" + d + "");
                        i.putExtra("sisastok", "" + e + "");
                        i.putExtra("nama_user", "" + f + "");
                        i.putExtra("posisi", "" + g + "");
                        i.putExtra("perusahaan", "" + h + "");
                        i.putExtra("kategori", "" + j + "");
                        i.putExtra("bidang", "" + k + "");
                        startActivity(i);


                    } else {

                        Toast.makeText(getApplicationContext(), "AKSES DI BATASI", Toast.LENGTH_SHORT).show();



                    }

                }
            });
        }
    }



    private void jumlah_item() {

        AndroidNetworking.post(Config.host + "jumlah_item_fisik.php")
                .addBodyParameter("kategori", kategori.getText().toString())
                .addBodyParameter("perusahaan", perusahaan.getText().toString())
                .addBodyParameter("tanggalawal", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalakhir", tanggal.getText().toString())
                //.addBodyParameter("namaitem", namabarang.getText().toString())

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
                        jumlah_item.setText((response.optString("jumlah_item")));
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