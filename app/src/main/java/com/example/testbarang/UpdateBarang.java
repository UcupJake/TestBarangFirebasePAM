package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateBarang extends AppCompatActivity {

    private DatabaseReference database;

    // variable fields EditText and Button
    private  Button btSubmit;
    private  EditText etKode, etNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        etKode = (EditText) findViewById(R.id.editNo);
        etNama = (EditText) findViewById(R.id.editNama);
        btSubmit = (Button) findViewById(R.id.btnOk);

        //mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        final Barang barang = (Barang) getIntent().getSerializableExtra("data");

        if (barang != null) {
            etKode.setText(barang.getKode());
            etNama.setText(barang.getNama());
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    barang.setKode(etKode.getText().toString());
                    barang.setNama(etNama.getText().toString());
                    updateBarang(barang);
                }
            });
        } else  {
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!kosong(etKode.getText().toString()) &&
                            !kosong(etNama.getText().toString())) {
                        submitBrg(new Barang(etKode.getText().toString(),
                                etNama.getText().toString()));
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Data barang harus lengkap",
                                Toast.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etNama.getWindowToken(), 0);
                }
            });
        }

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(etKode.getText().toString().isEmpty()) &&
                        !(etNama.getText().toString().isEmpty()))
                    submitBrg(new Barang(etKode.getText().toString(),
                            etNama.getText().toString()));
                else
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong",
                            Toast.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etKode.getWindowToken(), 0);
            }
        });
    }

    public void submitBrg(Barang brg){
        /* Berikut ini adalah kode yang digunakan untuk mengirimkan data ke
         * Firebase Realtime Database dan juga kita set OnSuccessListener yang
         * berisikode yang akan dijalankan ketika data berhasil ditambahkan
         */
        database.child("Barang").push().setValue(brg).addOnSuccessListener(this,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        etKode.setText("");
                        etNama.setText("");
                        Toast.makeText(getApplicationContext(), "Data berhasil ditambahkan",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean kosong(String s) {
        // Cek apakah ada isian atau fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updateBarang(Barang barang) {
        /**
         * Baris kode yang digunakan untuk meng-update data barang
         * yang sudah dimasukkan di Firebase Realtime Database
         */
        database.child("Barang").child(barang.getKode()).setValue(barang)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        /**
                         * Baris kode yang akan dipanggil apabila proses update barang sukses
                         */
                        Toast.makeText(getApplicationContext(), "Data Berhasil diubah",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }


    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, TambahData.class);
    }
}
