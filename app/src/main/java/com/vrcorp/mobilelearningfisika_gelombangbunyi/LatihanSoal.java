package com.vrcorp.mobilelearningfisika_gelombangbunyi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vrcorp.mobilelearningfisika_gelombangbunyi.materi.MekanismePendengaran;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.lang.String.valueOf;

public class LatihanSoal extends AppCompatActivity {
    LinearLayout soal, a_isi, b_isi, c_isi, d_isi, e_isi, pembahasan_isi, bahas_ly;
    CardView a, b, c,d,e,jawaban, pembahasan, next, cardJawab, home, close, hasil, tutup;
    Integer nomor=0, siapBahas=0, benar=0, noso=1;
    String kunci,jawab;
    boolean visible, maximal, sudahDijawab,siaplanjut;
    RelativeLayout penilaian;
    TextView nomorSoal, nilai, totalBenar;
    NodeList nList;
    ArrayList<String> jawabx,kuncix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan_soal);
        nomorSoal = findViewById(R.id.nomor_soal);
        soal = findViewById(R.id.soal);
        hasil = findViewById(R.id.hasil);
        penilaian = findViewById(R.id.penilaian);
        tutup = findViewById(R.id.close);
        home = findViewById(R.id.btn_home);
        close = findViewById(R.id.btn_close);
        nilai = findViewById(R.id.nilai);
        totalBenar = findViewById(R.id.total_benar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        jawabx=new ArrayList<String>();
        kuncix=new ArrayList<String>();
        for(int x=0;x<15;x++){
            jawabx.add(null);
            kuncix.add(null);
        }
        hasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maximal){
                    totalBenar.setText("Menjawab benar "+benar+" dari 15 soal");
                    penilaian.setVisibility(View.VISIBLE);
                    Integer ni = ((benar*2)/3)*10;
                    nilai.setText(Integer.toString(ni));
                }else{
                    Toast.makeText(v.getContext(),"Semua soal belum terjawab",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penilaian.setVisibility(View.GONE);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LatihanSoal.this, MainActivity.class);
                startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LatihanSoal.this, LatihanList.class);
                startActivity(intent);
            }
        });
        a_isi = findViewById(R.id.opsi_a_isi);
        b_isi = findViewById(R.id.opsi_b_isi);
        c_isi = findViewById(R.id.opsi_c_isi);
        d_isi = findViewById(R.id.opsi_d_isi);
        e_isi = findViewById(R.id.opsi_e_isi);
        jawaban = findViewById(R.id.cek_jawaban);
        pembahasan = findViewById(R.id.pembahasan);
        nomorSoal = findViewById(R.id.nomor_soal);
        bahas_ly = findViewById(R.id.jwb_ly);
        next = findViewById(R.id.next);
        pembahasan_isi = findViewById(R.id.pembahasan_isi);
        a = findViewById(R.id.opsi_a);
        b = findViewById(R.id.opsi_b);
        c = findViewById(R.id.opsi_c);
        d = findViewById(R.id.opsi_d);
        e = findViewById(R.id.opsi_e);
        ambilSoal(nomor,noso);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maximal){
                    Toast.makeText(getApplicationContext(),"Sudah diakhir soal",Toast.LENGTH_LONG).show();
                }else{
                    if(siaplanjut){
                        nomor++;
                        noso++;
                        siapBahas=0;
                        siaplanjut=false;
                        sudahDijawab=false;
                        clearJawaban();
                        ambilSoal(nomor, noso);
                        //Toast.makeText(getApplicationContext(),valueOf(nomor),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Silahkan Cek Jawaban terlebih dahulu",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        pembahasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(siapBahas>1){
                    if(visible){
                        bahas_ly.setVisibility(View.GONE);
                        visible=false;
                    }else{
                        bahas_ly.setVisibility(View.VISIBLE);
                        visible=true;
                    }
                }else{
                    visible=false;
                    Toast.makeText(getApplicationContext(),"Harap Cek jawaban terlebih dahulu",Toast.LENGTH_LONG).show();
                    bahas_ly.setVisibility(View.GONE);
                }
            }
        });
        jawaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekJawaban(kunci,jawab,cardJawab);
                siapBahas=2;
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sudahDijawab){
                        Toast.makeText(v.getContext(),"tidak dapat mengganti jawaban",Toast.LENGTH_SHORT).show();
                }else{
                    jawab="a";
                    cardJawab = a;
                    //siaplanjut=true;
                    pilihJawaban(a);
                }

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sudahDijawab){
                    Toast.makeText(v.getContext(),"tidak dapat mengganti jawaban",Toast.LENGTH_SHORT).show();
                }else{
                    jawab="b";
                    pilihJawaban(b);
                    //siaplanjut=true;
                    cardJawab = b;
                }

            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sudahDijawab){
                    Toast.makeText(v.getContext(),"tidak dapat mengganti jawaban",Toast.LENGTH_SHORT).show();
                }else{
                    jawab="c";
                    pilihJawaban(c);
                    cardJawab = c;
                   // siaplanjut=true;
                }

            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sudahDijawab){
                    Toast.makeText(v.getContext(),"tidak dapat mengganti jawaban",Toast.LENGTH_SHORT).show();
                }else{
                    jawab="d";
                    pilihJawaban(d);
                   // siaplanjut=true;
                    cardJawab=d;
                }

            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sudahDijawab){
                    Toast.makeText(v.getContext(),"tidak dapat mengganti jawaban",Toast.LENGTH_SHORT).show();
                }else{
                    jawab="e";
                    pilihJawaban(e);
                  //  siaplanjut=true;
                    cardJawab=e;
                }

            }
        });
    }
    public void clearChild(LinearLayout ly){
        if( ly.getChildCount() > 0)
            ly.removeAllViews();
    }
    public void ambilSoal(Integer nom, Integer noso){
        if(maximal){
            Toast.makeText(getApplicationContext(),"Sudah diakhir soal",Toast.LENGTH_LONG).show();
        }else{
            clearChild(soal);
            clearChild(a_isi);
            clearChild(b_isi);
            clearChild(c_isi);
            clearChild(d_isi);
            clearChild(e_isi);
            clearChild(pembahasan_isi);
            bahas_ly.setVisibility(View.GONE);
            siapBahas=0;
            try {
                InputStream is = getAssets().open("soal_latihan.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                Element element = doc.getDocumentElement();
                nList = doc.getElementsByTagName("soal");
                Log.d("Total Data", "ambilSoal: "+nList.getLength());
                element.normalize();
                Log.d("TES2", "ambilSoal: "+nList.item(nom).getTextContent());
                if(nom<nList.getLength()){
                    Node node = nList.item(nom);
                    muatsoal("isi",node,soal,nom);
                    muatsoal("a",node,a_isi,nom);
                    muatsoal("b",node,b_isi,nom);
                    muatsoal("c",node,c_isi,nom);
                    muatsoal("d",node,d_isi,nom);
                    muatsoal("e",node,e_isi,nom);
                    muatsoal("kunci",node,e_isi,nom);
                    muatsoal("pembahasan",node,pembahasan_isi,nom);
                    nomorSoal.setText("Latihan Soal No : "+noso);
                    if(nList.getLength() - nom == 1){
                        maximal=true;
                    }else{
                        maximal=false;
                    }
                }else{
                    maximal=true;
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }
    public void cekJawaban(String kunci, String jawaban, CardView pilihian){
        if(siapBahas>0){
            if(kunci.equals(jawaban)){
                pilihian.setCardBackgroundColor(getResources().getColor(R.color.green_400));
                benar++;
            }else{
                pilihian.setCardBackgroundColor(getResources().getColor(R.color.red_400));
                if(a.getTag().equals(kunci)){
                    a.setCardBackgroundColor(getResources().getColor(R.color.green_400));
                }
                if(b.getTag().equals(kunci)){
                    b.setCardBackgroundColor(getResources().getColor(R.color.green_400));
                }
                if(c.getTag().equals(kunci)){
                    c.setCardBackgroundColor(getResources().getColor(R.color.green_400));
                }
                if(d.getTag().equals(kunci)){
                    d.setCardBackgroundColor(getResources().getColor(R.color.green_400));
                }
                if(e.getTag().equals(kunci)){
                    e.setCardBackgroundColor(getResources().getColor(R.color.green_400));
                }
            }
            siapBahas=2;
            siaplanjut=true;
            sudahDijawab=true;
        }else{
            Toast.makeText(getApplicationContext(),"Harap pilih jawaban terlebih dahulu",Toast.LENGTH_LONG).show();
        }
    }
    public void pilihJawaban(CardView cd){
        clearJawaban();
        cd.setCardBackgroundColor(getResources().getColor(R.color.yellow_400));
        siapBahas=1;
    }
    public void clearJawaban(){
        a.setCardBackgroundColor(getResources().getColor(R.color.white));
        b.setCardBackgroundColor(getResources().getColor(R.color.white));
        c.setCardBackgroundColor(getResources().getColor(R.color.white));
        d.setCardBackgroundColor(getResources().getColor(R.color.white));
        e.setCardBackgroundColor(getResources().getColor(R.color.white));
    }
    public void muatsoal(String tipe, Node node, LinearLayout apa, Integer nosoal){
        Log.d("TIPE2", "muatsoal: "+node.getNodeName());
        NodeList isi=node.getChildNodes();
        for (int o =0;o<isi.getLength();o++){
            if (isi.item(o).getNodeName().equals(tipe)) {
                if(isi.item(o).getNodeName().equals("kunci")){
                    kunci=isi.item(o).getTextContent();
                }else{
                    NodeList jenis = isi.item(o).getChildNodes();
                    for(int i=0;i<jenis.getLength();i++){
                        Node jeniss=jenis.item(i);
                        if(jeniss.getNodeName().equals("teks")){
                            // add TextView
                            TextView tv = new TextView(this);
                            tv.setText(jeniss.getTextContent());
                            tv.setId(i);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //tv.setTextSize(16);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen._13sdp));
                            tv.setPadding(7, 3, 5, 3);
                            apa.addView(tv);
                        }
                        if(jeniss.getNodeName().equals("gambar")){
                            // add ImageView
                            Display display = getWindowManager().getDefaultDisplay();
                            ImageView iv = new ImageView(this);
                            int resourceId = getResources().getIdentifier (jeniss.getTextContent(), "drawable", "com.vrcorp.mobilelearningfisika_gelombangbunyi");
                            iv.setImageResource(resourceId);
                            iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            //iv.setLayoutParams(parms);
                            iv.setAdjustViewBounds(true);
                            // float in = getResources().getDimension(R.dimen._90sdp);
                            apa.addView(iv);
                        }
                    }
                }

            }
        }
    }
}