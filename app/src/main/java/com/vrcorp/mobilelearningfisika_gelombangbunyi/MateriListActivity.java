package com.vrcorp.mobilelearningfisika_gelombangbunyi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vrcorp.mobilelearningfisika_gelombangbunyi.adapter.MateriAdapter;
import com.vrcorp.mobilelearningfisika_gelombangbunyi.model.MateriModel;

import java.util.ArrayList;
import java.util.List;

public class MateriListActivity extends AppCompatActivity {
    private List<MateriModel> materiDataList;
    private ArrayList<String> islamijudulList;
    private ArrayList<String> islamigambarList;
    private ArrayList<String> islamipenerbitList;
    private ArrayList<String> islamiwaktuList;
    private ArrayList<String> islamiurlList;
    private ArrayList<String> islamikategoriList;
    private ArrayList<String> islamiDesList;
    private ArrayList<Integer> islamifavList;
    RecyclerView rc_cari;
    MateriAdapter mDataAdapter;
    CardView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        rc_cari = findViewById(R.id.rc_matlis);
        home = findViewById(R.id.btn_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MateriListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        islamijudulList= new ArrayList<>();
        islamigambarList= new ArrayList<String>();
        islamipenerbitList = new ArrayList<>();
        islamiwaktuList = new ArrayList<>();
        islamiurlList = new ArrayList<>();
        islamikategoriList = new ArrayList<>();
        islamiDesList = new ArrayList<>();
        islamifavList = new ArrayList<Integer>();
        materiDataList= new ArrayList<MateriModel>();
        mDataAdapter = new MateriAdapter( MateriListActivity.this, islamijudulList,
                islamikategoriList,
                islamigambarList, islamiurlList,islamipenerbitList
                ,islamiwaktuList,islamiDesList, islamifavList);
        islamijudulList.add("MEKANISME PENDENGARAN PADA MANUSIA");
        islamijudulList.add("KARAKTERISTIK GELOMBANG BUNYI");
        islamijudulList.add("CEPAT RAMBAT GELOMBANG BUNYI");
        islamijudulList.add("SIFAT-SIFAT UMUM GELOMBANG BUNYI");
        islamijudulList.add("RESONANSI BUNYI");
        islamijudulList.add("EFEK DOPPLER");
        islamijudulList.add("PELAYANGAN");
        islamijudulList.add("SUMBER BUNYI");
        islamijudulList.add("ENERGI DAN INTENSITAS BUNYI");
        islamijudulList.add("MANFAAT GELOMBANG BUNYI DALAM KEHIDUPAN");
        islamijudulList.add("DAFTAR PUSTAKA");
        islamigambarList.add("mekanisme");
        islamigambarList.add("karakteristik");
        islamigambarList.add("cepatrambat");
        islamigambarList.add("sifatgelombang");
        islamigambarList.add("resonansi");
        islamigambarList.add("efekdropler");
        islamigambarList.add("pelayangan");
        islamigambarList.add("sumberbunyi");
        islamigambarList.add("energidan");
        islamigambarList.add("manfaat");
        islamigambarList.add("daftarpustaka");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        islamiurlList.add("com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran");
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.VERTICAL, false);
        rc_cari.setLayoutManager(mLayoutManager);
        rc_cari.setAdapter(mDataAdapter);
    }
}