package com.vrcorp.mobilelearning_teori_kinetik_gas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;


import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class LatihanSoal extends AppCompatActivity {
    LinearLayout soal;
    CardView home, close, hasil, selesai;
    Integer gambarid=0, nomorsoal=1;
    String kunci;
    TextView nomorSoal, nama, kelas;
    NodeList nList;
    ArrayList<String> jawabx,kuncix;
    ArrayList<Uri> uris = new ArrayList<Uri>();
    private static final String TAG = "SelectImageActivity";
    /*
    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    //Image Uri will not be null for RESULT_OK
                    Log.d("Gambar",result.getData().getData().toString());
                    ImageView img = findViewById(gambarid-1);
                    img.setImageURI(result.getData().getData());
                    //Uri uri = Uri.parse(img.getDrawable().toString());
                    //Uri uri = FileProvider.getUriForFile(MekanismePendengaran.this,
                    //      "com.vrcorp.mobilelearning_teori_kinetik_gas",file);
                    uris.add(Uri.parse(result.getData().getDataString()));
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(LatihanSoal.this, ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LatihanSoal.this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan_soal);
        nomorSoal = findViewById(R.id.nomor_soal);
        soal = findViewById(R.id.soal_ly);
        home = findViewById(R.id.btn_home);
        close = findViewById(R.id.btn_close);
        selesai = findViewById(R.id.selesai);
        nama=findViewById(R.id.nama);
        kelas=findViewById(R.id.kelas);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
//                    .penaltyDeath()
                .build());
        if(BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaS = nama.getText().toString();
                String kelasS = kelas.getText().toString();
                sendEmail("# Latihan Soal", "Nama Lengkap : " + namaS + "\n Kelas : " + kelasS + " \n ");
            }
        });
        try {
            InputStream is = getAssets().open("soal_latihan.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            nList = doc.getElementsByTagName("soal");
            Log.d("Total Data", "ambilSoal: "+nList.getLength());
            element.normalize();
            for (int o = 0; o < nList.getLength(); o++) {
                    TextView textView = new TextView(this);
                    textView.setText("Soal Nomor "+nomorsoal);
                    //textView.setId(nomorsoal);
                    LinearLayout.LayoutParams ly = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    ly.setMargins(10,3,2,3);
                    textView.setMinLines(1);
                    textView.setLayoutParams(ly);
                    //tv.setTextSize(16);
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    textView.setTextColor(getResources().getColor(R.color.black));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._14sdp));
                    textView.setPadding(4, 3, 0, 3);
                    textView.setIncludeFontPadding (false);
                    soal.addView(textView);
                    NodeList jenis = nList.item(o).getChildNodes();
                    for(int i=0;i<jenis.getLength();i++){
                        Node jeniss=jenis.item(i);
                        if(jeniss.getNodeName().equals("teks")){
                            // add TextView
                            TextView tv = new TextView(this);
                            tv.setText(jeniss.getTextContent());
                            //tv.setId(i);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //tv.setTextSize(16);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen._13sdp));
                            tv.setPadding(7, 3, 5, 3);
                            soal.addView(tv);
                        }
                        if(jeniss.getNodeName().equals("gambar")){
                            // add ImageView
                            Display display = getWindowManager().getDefaultDisplay();
                            ImageView iv = new ImageView(this);
                            int resourceId = getResources().getIdentifier (jeniss.getTextContent(), "drawable", "com.vrcorp.mobilelearning_teori_kinetik_gas");
                            iv.setImageResource(resourceId);
                            iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            //iv.setLayoutParams(parms);
                            iv.setAdjustViewBounds(true);
                            // float in = getResources().getDimension(R.dimen._90sdp);
                            soal.addView(iv);
                        }
                    }

                    ImageView ivx = new ImageView(soal.getContext());
                    ivx.setId(gambarid);
                    ivx.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //iv.setLayoutParams(parms);
                    ivx.setAdjustViewBounds(true);
                    // float in = getResources().getDimension(R.dimen._90sdp);
                    soal.addView(ivx);

                    Button button2 = new Button(this);
                    LinearLayout.LayoutParams paramsx2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    paramsx2.setMargins(5, 2, 2, 5);
                    button2.setLayoutParams(paramsx2);
                    button2.setText("Upload Gambar");
                    button2.setBackgroundColor(getResources().getColor(R.color.blue_400));
                    button2.setTextColor(getResources().getColor(R.color.white));
                    button2.setTextSize(getResources().getDimension(R.dimen._9sdp));
                    button2.setPadding(4, 3, 3, 4);
                    button2.setGravity(Gravity.CENTER);
                    button2.setTag(gambarid);
                    soal.addView(button2);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("requestCode", gambarid);
                            Integer tag = Integer.parseInt(v.getTag().toString());
                            Matisse.from(LatihanSoal.this)
                                    .choose(MimeType.ofAll())
                                    .countable(true)
                                    .maxSelectable(9)
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .showPreview(true)
                                    .imageEngine(new GlideEngine())
                                    .showPreview(false) // Default is `true`
                                    .forResult(tag);
                            /*ImagePicker.Companion.with(LatihanSoal.this)
                                    .crop()
                                    .cropOval()
                                    .maxResultSize(512,512,true)
                                    .galleryOnly()
                                    .createIntentFromDialog(new Function1(){
                                        public Object invoke(Object idGambar) {
                                            this.invoke((Intent) idGambar);
                                            return gambarid;
                                        }
                                        public final void invoke(@NotNull Intent it) {
                                            Intrinsics.checkNotNullParameter(it, "it");
                                            launcher.launch(it);
                                        }
                                    });*/
                        }
                    });
                nomorsoal++;
                gambarid++;
            }
        }catch (Exception e){e.printStackTrace();}

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("request", String.valueOf(requestCode));
        Log.d("request", String.valueOf(resultCode));
        if (resultCode == RESULT_OK) {
            List<Uri> mSelected;
            mSelected = Matisse.obtainResult(data);
            String urlp = getRealPathFromUri(LatihanSoal.this, mSelected.get(0));
            //Uri gambar = Uri.fromFile(new File(urlp));
            Uri photoURI = FileProvider.getUriForFile(LatihanSoal.this, LatihanSoal.this.getApplicationContext().getPackageName() + ".provider", new File(urlp));
            Uri p = Uri.parse(String.valueOf(photoURI));
            ImageView img = findViewById(requestCode);
            Log.d("Gambar", String.valueOf(p));
            Log.d("Asli", urlp);
            //img.setImageURI(Uri.parse(urlp));
            Glide.with(this).load(mSelected.get(0)).into(img);
            //Uri uri = Uri.parse(img.getDrawable().toString());
            //Uri uri = FileProvider.getUriForFile(MekanismePendengaran.this,
            //      "com.vrcorp.mobilelearning_teori_kinetik_gas",file);
            uris.add(mSelected.get(0));
        }
    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
/*
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Log.d("Gambar",data.getData().toString());
            ImageView img = findViewById(gambarid-1);
            img.setImageURI(data.getData());
            //Uri uri = Uri.parse(img.getDrawable().toString());
            //Uri uri = FileProvider.getUriForFile(MekanismePendengaran.this,
            //      "com.vrcorp.mobilelearning_teori_kinetik_gas",file);
            uris.add(Uri.parse(data.getDataString()));
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
    */
    protected void sendEmail(String subject, String isi) {
        Log.i("Send email", "");
        String[] TO = {"ridafebry22@gmail.com"};
        String[] CC = {"inc.vr.corp@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("image/*");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, isi);

        emailIntent.putExtra(Intent.EXTRA_STREAM, uris);
        Log.d(TAG, "sendEmail: " + uris);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LatihanSoal.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}