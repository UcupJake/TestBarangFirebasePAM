package com.example.testbarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LihatBarang extends AppCompatActivity {

    /**
     * Mendefinisikan variable yang akan dipakai
     */
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Barang> daftarBarang;

    private Button update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_barang);

        /* Inisialisasi RecyclerView & komponennya
         */
        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        database = FirebaseDatabase.getInstance().getReference();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBarang();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deleteBarang();
            }
        });

        /**
         * Mengambil data barang dari Firebase Realtime DB
         */
        database.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarBarang = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
//                    update.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            updateBarang();
//                        }
//                    });


                    Barang barang = noteDataSnapshot.getValue(Barang.class);
                    barang.setKode(noteDataSnapshot.getKey());
                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarBarang.add(barang);
                }

                /**
                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new AdapterLihatBarang(daftarBarang,
                        LihatBarang.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails()+""+databaseError.getMessage());
            }
        });
    }

    public void updateBarang() {
        Intent intent = new Intent(this, UpdateBarang.class);
        startActivity(intent);
    }

//    public void deleteBarang(Barang barang) {
//        /**
//         * Kode ini akan mendelete data di Firebase Realtime DB
//         * berdasarkan key barang.
//         * Jika sukses akan memunculkan Toast.
//         */
//        if (database!=null) {
//            database.child("Barang").child(barang.getKode()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    Toast.makeText(LihatBarang.this, "Data barang berhasil dihapus",
//                            Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, LihatBarang.class);
    }

//    public void update(String key,) {
//
//    }
}
