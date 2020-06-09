package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Button bTambah, bLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bTambah = (Button) findViewById(R.id.btnTambah);
        bLihat = (Button) findViewById(R.id.btnLihat);

        bTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TambahData.getActIntent(MainActivity.this));
            }
        });

        bLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dilanjutkan pada pertemuan selanjutnya
                // Untuk pertemuan minggu ini boleh dilanjutkan :)
                startActivity(LihatBarang.getActIntent(MainActivity.this));
            }
        });
    }
}
