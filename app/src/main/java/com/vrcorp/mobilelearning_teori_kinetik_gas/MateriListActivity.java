package com.vrcorp.mobilelearning_teori_kinetik_gas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vrcorp.mobilelearning_teori_kinetik_gas.adapter.MateriAdapter;
import com.vrcorp.mobilelearning_teori_kinetik_gas.model.MateriModel;

import java.util.ArrayList;
import java.util.List;

public class MateriListActivity extends AppCompatActivity {
    private List<MateriModel> materiDataList;
    private ArrayList<String> materijudulList;
    private ArrayList<String> materigambarList;
    private ArrayList<String> materipenerbitList;
    private ArrayList<String> materiwaktuList;
    private ArrayList<String> materiurlList;
    private ArrayList<String> materikategoriList;
    private ArrayList<String> materiDesList;
    private ArrayList<Integer> materifavList;
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
        materijudulList= new ArrayList<>();
        materigambarList= new ArrayList<String>();
        materipenerbitList = new ArrayList<>();
        materiwaktuList = new ArrayList<>();
        materiurlList = new ArrayList<>();
        materikategoriList = new ArrayList<>();
        materiDesList = new ArrayList<>();
        materifavList = new ArrayList<Integer>();
        materiDataList= new ArrayList<MateriModel>();
        mDataAdapter = new MateriAdapter( MateriListActivity.this, materijudulList,
                materikategoriList,
                materigambarList, materiurlList,materipenerbitList
                ,materiwaktuList,materiDesList, materifavList);
        materijudulList.add("GAS");
        materijudulList.add("SIFAT-SIFAT GAS");
        materijudulList.add("GAS NYATA YANG IDEAL (GAS IDEAL)");
        materijudulList.add("SISTEM TERBUKA, TERTUTUP, DAN TERISOLASI");
        materijudulList.add("BESARAN MAKROSKOPIS GAS");
        materijudulList.add("HUKUM BOYLE");
        materijudulList.add("HUKUM GAY-LUSSAC");
        materijudulList.add("HUKUM CHARLES");
        materijudulList.add("PERSAMAAN BOYLE-GAY LUSSAC");
        materijudulList.add("HUKUM AVOGADRO");
        materijudulList.add("PERSAMAAN GAS IDEAL");
        materijudulList.add("MIKROSKOPIS PARTIKEL GAS IDEAL");
        materijudulList.add("TEKANAN MAKROSKOPIS DAN MIKROSKOPIS");
        materijudulList.add("KECEPATAN EFEKTIK PARTIKEL GAS IDEAL");
        materijudulList.add("ENERGI DALAM GAS IDEAL");
        materijudulList.add("GAS NYATA TIDAK IDEAL");
        materijudulList.add("DAFTAR PUSTAKA");
        materigambarList.add("gas01");
        materigambarList.add("sifat_sifat_gas02");
        materigambarList.add("gas_nyata_ideal03");
        materigambarList.add("sistem_terbuka_tertutup_dan_terisolasi04");
        materigambarList.add("besaran_maskroskopis_gas05");
        materigambarList.add("hukum_boyle06");
        materigambarList.add("hukum_gay_lussac07");
        materigambarList.add("hukum_charles08");
        materigambarList.add("persamaan_boyle_gay_lussac09");
        materigambarList.add("hukum_avogadro10");
        materigambarList.add("persamaan_gas_ideal11");
        materigambarList.add("mikroskopis_partikel_gas_ideal12");
        materigambarList.add("tekanan_makroskopis_mikroskopis13");
        materigambarList.add("kecepatan_efektif_partikel_gas_ideal14");
        materigambarList.add("energi_dalam_gas_ideal15");
        materigambarList.add("gas_nyata_tidak_ideal16");
        materigambarList.add("daftarpustaka");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        materiurlList.add("com.vrcorp.mobilelearning_teori_kinetik_gas.materi.MekanismePendengaran");
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.VERTICAL, false);
        rc_cari.setLayoutManager(mLayoutManager);
        rc_cari.setAdapter(mDataAdapter);
    }
}