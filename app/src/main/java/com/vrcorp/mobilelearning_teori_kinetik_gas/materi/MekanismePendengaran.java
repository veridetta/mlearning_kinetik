package com.vrcorp.mobilelearning_teori_kinetik_gas.materi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codesgood.views.JustifiedTextView;
//import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.vrcorp.mobilelearning_teori_kinetik_gas.BuildConfig;
import com.vrcorp.mobilelearning_teori_kinetik_gas.LatihanSoal;
import com.vrcorp.mobilelearning_teori_kinetik_gas.MainActivity;
import com.vrcorp.mobilelearning_teori_kinetik_gas.MateriListActivity;
import com.vrcorp.mobilelearning_teori_kinetik_gas.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MekanismePendengaran extends AppCompatActivity {
    LinearLayout konten;
    CardView home, close;
    SimpleExoPlayer player;
    Integer cobaa = 0, tugas = 0, gambarid=0;
    Intent intent;
    String materi, judul;
    Uri URI;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "SelectImageActivity";
    private static final int CHOOSE_FILE_REQUESTCODE = 8777;
    private static final int PICKFILE_RESULT_CODE = 8778;
    public static final int PICK_IMAGE = 1;
    ArrayList<Uri> uris = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mekanisme_pendengaran);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        handlePermission();
        intent = getIntent();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
//                    .penaltyDeath()
                .build());
        if(BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
        materi = intent.getStringExtra("materi");
        judul = intent.getStringExtra("judul");
        konten = findViewById(R.id.konten);
        home = findViewById(R.id.btn_home);
        close = findViewById(R.id.btn_close);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MekanismePendengaran.this, MainActivity.class);
                startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MekanismePendengaran.this, MateriListActivity.class);
                startActivity(intent);
            }
        });
        Log.d("MATERI", "onCreate: " + materi);
        try {
            final InputStream is = getAssets().open(materi + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();
            NodeList nList = doc.getElementsByTagName("materi");
            Log.d("Kompetensi", "onCreate: " + nList.item(0).getTextContent());
            Integer nomor = 1;
            NodeList node = nList.item(0).getChildNodes();
            for (int i = 0; i < node.getLength(); i++) {
                Node anak = node.item(i);
                Log.d("MODE", "onCreate: " + anak.getNodeName());
                if (anak.getNodeName().equals("judul")) {
                    // add TextView
                    TextView tv = new TextView(this);
                    tv.setText(anak.getTextContent());
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setId(nomor);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    //tv.setTextSize(16);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._16sdp));
                    tv.setPadding(5, 3, 0, 3);
                    konten.addView(tv);
                }
                if (anak.getNodeName().equals("subjudul")) {
                    // add TextView
                    TextView tv = new TextView(this);
                    tv.setText(anak.getTextContent());
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setId(nomor);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    //tv.setTextSize(16);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._14sdp));
                    tv.setPadding(7, 3, 0, 3);
                    konten.addView(tv);
                }
                ;
                if (anak.getNodeName().equals("url")) {
                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayout.setPadding(3, 3, 3, 3);
                    konten.addView(linearLayout);
                    // add TextView
                    final String isi = anak.getTextContent();
                    TextView tv = new TextView(this);
                    tv.setText("Buka di Web Browser");
                    //tv.setTypeface(null, Typeface.BOLD);
                    tv.setId(nomor);
                    LinearLayout.LayoutParams ly = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    ly.setMargins(10, 3, 2, 3);
                    tv.setLayoutParams(ly);
                    //tv.setTextSize(16);
                    tv.setTextColor(getResources().getColor(R.color.blue_400));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._12sdp));
                    tv.setPadding(4, 3, 0, 3);
                    linearLayout.addView(tv);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(isi);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
                }
                ;
                if (anak.getNodeName().equals("teks")) {
                    // add TextView
                    JustifiedTextView tv = new JustifiedTextView(this);
                    tv.setText(anak.getTextContent());
                    tv.setId(nomor);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    //tv.setTextSize(16);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._13sdp));
                    tv.setPadding(7, 3, 5, 3);
                    konten.addView(tv);
                }
                if (anak.getNodeName().equals("pustaka")) {
                    // add TextView
                    TextView tv = new TextView(this);
                    tv.setText(anak.getTextContent());
                    tv.setId(nomor);
                    tv.setMinLines(1);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    //tv.setTextSize(16);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._13sdp));
                    tv.setPadding(7, 3, 5, 3);
                    konten.addView(tv);
                }
                if (anak.getNodeName().equals("ket")) {
                    Log.d("MODE", "onCreate: " + anak.getTextContent());
                    // add TextView
                    TextView tv = new TextView(this);
                    tv.setText(anak.getTextContent());
                    tv.setGravity(Gravity.CENTER);
                    tv.setId(nomor);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    //tv.setTextSize(16);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._13sdp));
                    tv.setPadding(5, 3, 0, 3);
                    konten.addView(tv);
                }
                if (anak.getNodeName().equals("gambar")) {
                    // add ImageView
                    Display display = getWindowManager().getDefaultDisplay();
                    ImageView iv = new ImageView(konten.getContext());
                    int resourceId = getResources().getIdentifier(anak.getTextContent(), "drawable", "com.vrcorp.mobilelearning_teori_kinetik_gas");
                    iv.setImageResource(resourceId);
                    iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //iv.setLayoutParams(parms);
                    iv.setAdjustViewBounds(true);
                    // float in = getResources().getDimension(R.dimen._90sdp);
                    konten.addView(iv);
                }
                if (anak.getNodeName().equals("video")) {
                    //add video
                    PlayerView vv = new PlayerView(this);
                    int resourceId = getResources().getIdentifier(anak.getTextContent(), "raw", "com.vrcorp.mobilelearning_teori_kinetik_gas");
                    String path = "android.resource://" + getPackageName() + "/" + resourceId;
                    //vv.setVideoURI(Uri.parse(path));
                    //MediaController mediaController = new MediaController(vv.getContext());
                    //mediaController.setAnchorView(vv);
                    //vv.setMediaController(mediaController);
                    vv.setLayoutParams(new FrameLayout.LayoutParams(1000, 500));
                    konten.addView(vv);
                    player = ExoPlayerFactory.newSimpleInstance(
                            new DefaultRenderersFactory(this),
                            new DefaultTrackSelector(), new DefaultLoadControl());

                    String videoPath = RawResourceDataSource.buildRawResourceUri(resourceId).toString();
                    Uri uri = RawResourceDataSource.buildRawResourceUri(resourceId);
                    ExtractorMediaSource audioSource = new ExtractorMediaSource(
                            uri,
                            new DefaultDataSourceFactory(this, "MyExoplayer"),
                            new DefaultExtractorsFactory(),
                            null,
                            null
                    );
                    player.prepare(audioSource);
                    vv.setPlayer(player);
                    vv.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                    final Integer[] z = {0};
                    vv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            z[0]++;
                            if (z[0] > 0) {
                                player.setPlayWhenReady(true);
                            }
                        }
                    });
                }
                if (anak.getNodeName().equals("openweb")){
                    String urlString = anak.getTextContent();
                    final Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    JustifiedTextView tv = new JustifiedTextView(this);
                    tv.setText("Open Link Website");
                    tv.setId(nomor);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    //tv.setTextSize(16);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._13sdp));
                    tv.setPadding(7, 3, 5, 3);
                    konten.addView(tv);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                MekanismePendengaran.this.startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                // Chrome browser presumably not installed so allow user to choose instead
                                intent.setPackage(null);
                                MekanismePendengaran.this.startActivity(intent);
                            }
                        }
                    });
                }
                if (anak.getNodeName().equals("web")) {
                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    konten.addView(linearLayout);
                    WebView wb = new WebView(konten.getContext());
                    wb.setWebChromeClient(new WebChromeClient());
                    wb.clearCache(true);
                    wb.clearHistory();
                    wb.getSettings().setJavaScriptEnabled(true);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        wb.getSettings().setMediaPlaybackRequiresUserGesture(false);
                    }
                    wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                    wb.getSettings().setPluginState(WebSettings.PluginState.ON);
                    wb.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                    String path = "file:///android_asset/www/" + anak.getTextContent() + ".html";
                    wb.loadUrl(path);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        linearLayout.addView(wb, 0, new LinearLayout.LayoutParams(
                                new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT)));
                    }
                }
                if (anak.getNodeName().equals("coba")) {
                    cobaa++;
                    NodeList anak2 = anak.getChildNodes();
                    LinearLayout linearLayout2 = new LinearLayout(this);
                    linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayout2.setBackgroundColor(getResources().getColor(R.color.orange_400));
                    linearLayout2.setPadding(3, 3, 3, 3);
                    konten.addView(linearLayout2);
                    // add TextView
                    TextView tv2 = new TextView(this);
                    tv2.setText("   Coba Yuk!");
                    //tv.setTypeface(null, Typeface.BOLD);
                    tv2.setId(nomor);
                    LinearLayout.LayoutParams ly2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    ly2.setMargins(10, 3, 2, 3);
                    tv2.setLayoutParams(ly2);
                    tv2.setTypeface(Typeface.DEFAULT_BOLD);
                    tv2.setTextColor(getResources().getColor(R.color.white));
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._16sdp));
                    tv2.setPadding(10, 3, 0, 3);
                    linearLayout2.addView(tv2);
                    for (int u = 0; u < anak2.getLength(); u++) {
                        final Node anaknya = anak2.item(u);
                        if (anaknya.getNodeName().equals("judul")) {
                            LinearLayout linearLayout = new LinearLayout(this);
                            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.setBackgroundColor(getResources().getColor(R.color.blue_400));
                            linearLayout.setPadding(3, 3, 3, 3);
                            konten.addView(linearLayout);
                            // add TextView
                            TextView tv = new TextView(this);
                            tv.setText(anaknya.getTextContent());
                            //tv.setTypeface(null, Typeface.BOLD);
                            tv.setId(u);
                            LinearLayout.LayoutParams ly = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            ly.setMargins(10, 3, 2, 3);
                            tv.setLayoutParams(ly);
                            //tv.setTextSize(16);
                            tv.setTextColor(getResources().getColor(R.color.white));
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen._14sdp));
                            tv.setPadding(4, 3, 0, 3);
                            linearLayout.addView(tv);
                        }
                        if (anaknya.getNodeName().equals("soal")) {
                            LinearLayout linearLayout3 = new LinearLayout(this);
                            linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout3.setBackgroundColor(getResources().getColor(R.color.blue_400));
                            linearLayout3.setPadding(3, 3, 3, 3);
                            konten.addView(linearLayout3);
                            LinearLayout linearLayout4 = new LinearLayout(this);
                            linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout4.setBackgroundColor(getResources().getColor(R.color.white));
                            linearLayout4.setPadding(3, 3, 3, 3);
                            linearLayout3.addView(linearLayout4);

                            TextView tv = new TextView(this);
                            tv.setText(anaknya.getTextContent());
                            //tv.setTypeface(null, Typeface.BOLD);
                            tv.setId(u);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //tv.setTextSize(16);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen._13sdp));
                            tv.setPadding(10, 5, 0, 3);
                            linearLayout4.addView(tv);

                        }
                        if (anaknya.getNodeName().equals("web")) {
                            LinearLayout linearLayout = new LinearLayout(this);
                            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            konten.addView(linearLayout);
                            WebView wb = new WebView(konten.getContext());
                            wb.setMinimumHeight(R.dimen._180sdp);
                            wb.setWebChromeClient(new WebChromeClient());
                            wb.clearCache(true);
                            wb.clearHistory();
                            wb.getSettings().setJavaScriptEnabled(true);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                wb.getSettings().setMediaPlaybackRequiresUserGesture(false);
                            }
                            wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            wb.getSettings().setPluginState(WebSettings.PluginState.ON);
                            wb.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                            String path = "file:///android_asset/www/" + anak.getTextContent() + ".html";
                            wb.loadUrl(path);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                linearLayout.addView(wb, 0, new LinearLayout.LayoutParams(
                                        new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT)));
                            }
                        }
                        if (anaknya.getNodeName().equals("ket")) {
                            Log.d("MODE", "onCreate: " + anak.getTextContent());
                            // add TextView
                            TextView tv = new TextView(this);
                            tv.setText(anak.getTextContent());
                            tv.setGravity(Gravity.CENTER);
                            tv.setId(nomor);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //tv.setTextSize(16);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen._13sdp));
                            tv.setPadding(5, 3, 0, 3);
                            konten.addView(tv);
                        }
                        if (anaknya.getNodeName().equals("gambar")) {
                            // add ImageView
                            Display display = getWindowManager().getDefaultDisplay();
                            ImageView iv = new ImageView(konten.getContext());
                            int resourceId = getResources().getIdentifier(anaknya.getTextContent(), "drawable", "com.vrcorp.mobilelearning_teori_kinetik_gas");
                            iv.setImageResource(resourceId);
                            iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            //iv.setLayoutParams(parms);
                            iv.setAdjustViewBounds(true);
                            // float in = getResources().getDimension(R.dimen._90sdp);
                            konten.addView(iv);
                        }
                    }
                    final EditText editText2 = new EditText(this);
                    editText2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText2.setMinLines(1);
                    editText2.setHint("Nama Lengkap");
                    konten.addView(editText2);
                    final EditText editText3 = new EditText(this);
                    editText3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText3.setMinLines(1);
                    editText3.setHint("Kelas");
                    konten.addView(editText3);
                    final EditText editText = new EditText(this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setMinLines(4);
                    editText.setHint("Masukan Jawaban");
                    konten.addView(editText);
                    ImageView iv = new ImageView(konten.getContext());
                    iv.setId(gambarid);
                    iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //iv.setLayoutParams(parms);
                    iv.setAdjustViewBounds(true);
                    // float in = getResources().getDimension(R.dimen._90sdp);
                    konten.addView(iv);
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
                    konten.addView(button2);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer tag = Integer.parseInt(v.getTag().toString());
                            Matisse.from(MekanismePendengaran.this)
                                    .choose(MimeType.ofAll())
                                    .countable(true)
                                    .maxSelectable(9)
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .showPreview(true)
                                    .imageEngine(new GlideEngine())
                                    .showPreview(false) // Default is `true`
                                    .forResult(tag);
                            /*
                            ImagePicker.with(MekanismePendengaran.this)
                                    .crop()                    //Crop image(Optional), Check Customization for more option
                                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                                    .saveDir(getExternalFilesDir(null))
                                    .start();

                             */
                        }
                    });
                    gambarid++;
                    Button button = new Button(this);
                    LinearLayout.LayoutParams paramsx = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    paramsx.setMargins(5, 2, 2, 5);
                    button.setLayoutParams(paramsx);
                    button.setText("Kirim Jawaban");
                    button.setBackgroundColor(getResources().getColor(R.color.blue_400));
                    button.setTextColor(getResources().getColor(R.color.white));
                    button.setTextSize(getResources().getDimension(R.dimen._9sdp));
                    button.setPadding(4, 3, 3, 4);
                    button.setGravity(Gravity.CENTER);
                    konten.addView(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String isi = editText.getText().toString();
                            String nama = editText2.getText().toString();
                            String kelas = editText3.getText().toString();
                            sendEmail("#" + cobaa + " " + judul + " Coba Ke " + cobaa, "Nama Lengkap : " + nama + "\n Kelas : " + kelas + " \n " + isi);
                        }
                    });

                }
                if (anak.getNodeName().equals("tugas")) {
                    tugas++;
                    NodeList anak2 = anak.getChildNodes();
                    LinearLayout linearLayout2 = new LinearLayout(this);
                    linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayout2.setBackgroundColor(getResources().getColor(R.color.orange_400));
                    linearLayout2.setPadding(3, 3, 3, 3);
                    konten.addView(linearLayout2);
                    // add TextView
                    TextView tv2 = new TextView(this);
                    tv2.setText("Tugas");
                    //tv.setTypeface(null, Typeface.BOLD);
                    tv2.setId(nomor);
                    LinearLayout.LayoutParams ly2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    ly2.setMargins(10, 3, 2, 3);
                    tv2.setLayoutParams(ly2);
                    tv2.setTypeface(Typeface.DEFAULT_BOLD);
                    tv2.setTextColor(getResources().getColor(R.color.white));
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen._16sdp));
                    tv2.setPadding(10, 3, 0, 3);
                    linearLayout2.addView(tv2);
                    for (int u = 0; u < anak2.getLength(); u++) {
                        Node anaknya = anak2.item(u);
                        if (anaknya.getNodeName().equals("judul")) {
                            LinearLayout linearLayout = new LinearLayout(this);
                            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.setBackgroundColor(getResources().getColor(R.color.blue_400));
                            linearLayout.setPadding(3, 3, 3, 3);
                            konten.addView(linearLayout);
                            // add TextView
                            TextView tv = new TextView(this);
                            tv.setText(anaknya.getTextContent());
                            //tv.setTypeface(null, Typeface.BOLD);
                            tv.setId(u);
                            LinearLayout.LayoutParams ly = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            ly.setMargins(10, 3, 2, 3);
                            tv.setLayoutParams(ly);
                            //tv.setTextSize(16);
                            tv.setTextColor(getResources().getColor(R.color.white));
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen._14sdp));
                            tv.setPadding(4, 3, 0, 3);
                            linearLayout.addView(tv);
                        }
                        if (anaknya.getNodeName().equals("soal")) {
                            LinearLayout linearLayout3 = new LinearLayout(this);
                            linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout3.setBackgroundColor(getResources().getColor(R.color.blue_400));
                            linearLayout3.setPadding(3, 3, 3, 3);
                            konten.addView(linearLayout3);
                            LinearLayout linearLayout4 = new LinearLayout(this);
                            linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout4.setBackgroundColor(getResources().getColor(R.color.white));
                            linearLayout4.setPadding(3, 3, 3, 3);
                            linearLayout3.addView(linearLayout4);

                            TextView tv = new TextView(this);
                            tv.setText(anaknya.getTextContent());
                            //tv.setTypeface(null, Typeface.BOLD);
                            tv.setId(u);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //tv.setTextSize(16);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen._13sdp));
                            tv.setPadding(10, 5, 0, 3);
                            linearLayout4.addView(tv);

                        }
                        if (anak.getNodeName().equals("web")) {
                            LinearLayout linearLayout = new LinearLayout(this);
                            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            konten.addView(linearLayout);
                            WebView wb = new WebView(konten.getContext());
                            wb.setMinimumHeight(R.dimen._260sdp);
                            wb.setWebChromeClient(new WebChromeClient());
                            wb.clearCache(true);
                            wb.clearHistory();
                            wb.getSettings().setJavaScriptEnabled(true);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                wb.getSettings().setMediaPlaybackRequiresUserGesture(false);
                            }
                            wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            wb.getSettings().setPluginState(WebSettings.PluginState.ON);
                            wb.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                            String path = "file:///android_asset/www/" + anak.getTextContent() + ".html";
                            wb.loadUrl(path);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                linearLayout.addView(wb, 0, new LinearLayout.LayoutParams(
                                        new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT)));
                            }
                        }
                        if (anak.getNodeName().equals("ket")) {
                            Log.d("MODE", "onCreate: " + anak.getTextContent());
                            // add TextView
                            TextView tv = new TextView(this);
                            tv.setText(anak.getTextContent());
                            tv.setGravity(Gravity.CENTER);
                            tv.setId(nomor);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            //tv.setTextSize(16);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen._13sdp));
                            tv.setPadding(5, 3, 0, 3);
                            konten.addView(tv);
                        }
                        if (anak.getNodeName().equals("gambar")) {
                            // add ImageView
                            Display display = getWindowManager().getDefaultDisplay();
                            ImageView iv = new ImageView(konten.getContext());
                            int resourceId = getResources().getIdentifier(anak.getTextContent(), "drawable", "com.vrcorp.mobilelearning_teori_kinetik_gas");
                            iv.setImageResource(resourceId);
                            iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            //iv.setLayoutParams(parms);
                            iv.setAdjustViewBounds(true);
                            // float in = getResources().getDimension(R.dimen._90sdp);
                            konten.addView(iv);
                        }
                    }
                    final EditText editText2 = new EditText(this);
                    editText2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText2.setMinLines(1);
                    editText2.setHint("Nama Lengkap");
                    konten.addView(editText2);
                    final EditText editText3 = new EditText(this);
                    editText3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText3.setMinLines(1);
                    editText3.setHint("Kelas");
                    konten.addView(editText3);
                    final EditText editText = new EditText(this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setMinLines(4);
                    editText.setHint("Masukan Jawaban");
                    konten.addView(editText);

                    Button button = new Button(this);
                    LinearLayout.LayoutParams paramsx = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    paramsx.setMargins(5, 2, 2, 5);
                    button.setLayoutParams(paramsx);
                    button.setText("Kirim Jawaban");
                    button.setBackgroundColor(getResources().getColor(R.color.blue_400));
                    button.setTextColor(getResources().getColor(R.color.white));
                    button.setTextSize(getResources().getDimension(R.dimen._9sdp));
                    button.setPadding(4, 3, 3, 4);
                    button.setGravity(Gravity.CENTER);
                    konten.addView(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String isi = editText.getText().toString();
                            String nama = editText2.getText().toString();
                            String kelas = editText3.getText().toString();
                            sendEmail("#" + cobaa + " " + judul + " Tugas Ke " + cobaa, "Nama Lengkap : " + nama + "\n Kelas : " + kelas + " \n " + isi);
                        }
                    });
                }
                nomor++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ukuranGambar(ImageView imageView) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
    }

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
            Toast.makeText(MekanismePendengaran.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void handlePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    SELECT_PICTURE);
        }
    }
/**
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECT_PICTURE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            //  Show your own message here
                        } else {
                            showSettingsAlert();
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
*/
/**
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Image Path : " + resultCode + requestCode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (requestCode == PICK_IMAGE) {
                    if (resultCode == -1) {
                        // Get the url from data
                        final Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            String path = getPathFromURI(selectedImageUri);
                            URI = Uri.parse("file://" + path.toString());
                            //Uri.parse("file://" + attachmentFile);
                            Log.d(TAG, "Image Path : " + path + "path uri " + selectedImageUri + "urii" + URI);
                            // Set the image in ImageView
                        }
                    }
                }
            }
        }).start();

    }
*/
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
        List<Uri> mSelected;
        mSelected = Matisse.obtainResult(data);
        ImageView img = findViewById(requestCode);
        //img.setImageURI(Uri.parse(urlp));
        Glide.with(this).load(mSelected.get(0)).into(img);
        //Uri uri = Uri.parse(img.getDrawable().toString());
        //Uri uri = FileProvider.getUriForFile(MekanismePendengaran.this,
        //      "com.vrcorp.mobilelearning_teori_kinetik_gas",file);
        uris.add(mSelected.get(0));
    }
}
/*
public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
        //Image Uri will not be null for RESULT_OK
        Log.d("Gambar",data.getData().toString());
        //Uri fileUri = data.getData();
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
    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openAppSettings(MekanismePendengaran.this);
                    }
                });
        alertDialog.show();
    }

    public static void openAppSettings(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }
}