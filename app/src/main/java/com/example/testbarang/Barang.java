package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import android.os.Bundle;

public class Barang implements Serializable {
    private String kode;
    private String nama;

    public Barang() {

    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kd) {
        this.kode = kd;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public String toString() {
        return " "+kode+"\n"+
                " "+nama;
    }

    public Barang(String kd, String nm) {
        kode = kd;
        nama = nm;
    }
}

//public class Barang extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_barang);
//    }
//}
