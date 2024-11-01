package com.example.inven;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import org.w3c.dom.Text;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;

public class Contact extends AppCompatActivity {

    LinearLayout call;

    TextView id_data, nomor, nama_user, posisi, perusahaan, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        nama_user = (TextView) findViewById(R.id.nama_user);
        posisi = (TextView) findViewById(R.id.posisi);
        perusahaan = (TextView) findViewById(R.id.perusahaan);

        call = (LinearLayout) findViewById(R.id.call);



        Intent i = getIntent();
        String kiriman = i.getStringExtra("nama_user");
        nama_user.setText(kiriman);
        String kiriman2 = i.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("perusahaan");
        perusahaan.setText(kiriman3);

        call.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String nama_user_alert = nama_user.getText().toString();
                String posisi_alert = posisi.getText().toString();
                String perusahaan_alert = perusahaan.getText().toString();

                String noHp = "6282249495858";
                String pesan = "Hallo Admin Lussi, bisa bantu saya?\nsaya adalah "+nama_user_alert+"\nPosisi : "+posisi_alert+"\n Nomor :"+perusahaan_alert;


                String pakePesandanNomor =
                        "https://api.whatsapp.com/send?phone=" + noHp + "&text=" + pesan;
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(pakePesandanNomor));
                startActivity(i);

            }

        });





    }
}