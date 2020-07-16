package com.vrcorp.mobilelearningfisika_gelombangbunyi.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vrcorp.mobilelearningfisika_gelombangbunyi.MainActivity;
import com.vrcorp.mobilelearningfisika_gelombangbunyi.R;
import com.vrcorp.mobilelearningfisika_gelombangbunyi.model.MateriModel;

import java.util.ArrayList;
import java.util.List;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.MyViewHolder> {

    private ArrayList<String> islamijudulList = new ArrayList<>();
    private ArrayList<String> islamikategoriList = new ArrayList<>();
    private ArrayList<String> islamiphotoList = new ArrayList<>();
    private ArrayList<String> islamiurlList = new ArrayList<>();
    private ArrayList<String> islamipenerbitList = new ArrayList<>();
    private ArrayList<String> islamiwaktuList = new ArrayList<>();
    private ArrayList<String> islamiDes = new ArrayList<>();
    private ArrayList<Integer> islamifavList = new ArrayList<>();

    private Context context;
    int success=0, favoritStatus=0, total;
    boolean isLoadingAdded;
    public MateriAdapter(Context context, ArrayList<String> islamijudulList,
                        ArrayList<String> islamikategoriList,
                        ArrayList<String> islamiphotoList,
                        ArrayList<String> islamiurlList,
                        ArrayList<String> islamipenerbitList,
                        ArrayList<String> islamiwaktuList,
                        ArrayList<String> islamiDes,
                        ArrayList<Integer> islamifavList) {
        this.context = context;
        //this.baperModelList = baperModelList;
        this.islamijudulList = islamijudulList;
        this.islamikategoriList = islamikategoriList;
        this.islamiphotoList = islamiphotoList;
        this.islamiurlList = islamiurlList;
        this.islamipenerbitList = islamipenerbitList;
        this.islamiwaktuList = islamiwaktuList;
        this.islamiDes= islamiDes;
        this.islamifavList=islamifavList;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tNama;
        ImageView bg;
        CardView card;
        public MyViewHolder(View view) {
            super(view);
            tNama = view.findViewById(R.id.txt_materi);
            card = view.findViewById(R.id.card_materi);
            bg =view.findViewById(R.id.bg_materi);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_materi, parent, false);
        return new MyViewHolder(itemView);
    }
    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //final BaperModel Baper = baperModelList.get(position);
        this.total = total;
        holder.tNama.setText(islamijudulList.get(position));
        Glide.with(holder.bg.getContext())
                .load(getImage(islamiphotoList.get(position)))
                .apply(RequestOptions.centerCropTransform()
                        .centerCrop()
                )
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.bg.setImageDrawable(resource);
                    }
                });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    masukAktiviti(Class.forName(islamiurlList.get(position)),false,position);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return islamijudulList.size();
    }
    public void swap(List<MateriModel> datas){
        //baperModelList.clear();
        //baperModelList.addAll(datas);
        notifyDataSetChanged();
    }
    public void masukAktiviti(Class<?> to, boolean finish,int positino) {
        Intent intent = new Intent(context, to);
        intent.putExtra("materi",islamiphotoList.get(positino));
        intent.putExtra("judul",islamijudulList.get(positino));
        context.startActivity(intent);
    }

}
