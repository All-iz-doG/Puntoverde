
package com.example.punto_verde;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.punto_verde.model.ImagePlace;
import com.example.punto_verde.model.Place;

import java.util.ArrayList;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.ViewHolder> implements ItemClickListener {

    private static final String TAG = "ADAPTER";
    private Context context;
    private ArrayList<Place> places;
    private ArrayList<ImagePlace> imagePlaces;

    public GreenAdapter(Context context, ArrayList<Place> places) {
        this.context = context;
        this.places = places;
    }

    public GreenAdapter(Context context) {
        this.context = context;
        places = new ArrayList<>();
        imagePlaces = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.green_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull GreenAdapter.ViewHolder holder, int position) {
        Place place = places.get(position);
        holder.name.setText(place.getName());
        holder.category.setText(place.getCategory());

        for (ImagePlace image : imagePlaces){
            if (image.getPlaceID() == place.getId()){
                Glide.with(context)
                        .load(image.getUrl())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.img);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
    }

    public void addPlace(ArrayList<Place> places){
        for (Place place : places){
            this.places.add(place);
            notifyDataSetChanged();
        }
    }

    public void addImage(ArrayList<ImagePlace> images){
        for (ImagePlace image : images){
            this.imagePlaces.add(image);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v, int position) {
        Place p = places.get(position);
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("id",p.getId());
        intent.putExtra("name",p.getName());
        intent.putExtra("category",p.getCategory());
        intent.putExtra("calification",p.getCalification());
        intent.putExtra("description",p.getDescription());
        intent.putExtra("horario",p.getSchedule());
        intent.putExtra("contacto",p.getContact());
        intent.putExtra("provincia",p.getProvince());
        intent.putExtra("lat",p.getLatitud());
        intent.putExtra("lng",p.getLongitud());
        context.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView name;
        private TextView category;

        private ItemClickListener listener;

        public ViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);

            this.listener = listener;

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);

            img.setOnClickListener(this);
            name.setOnClickListener(this);
            category.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view , getLayoutPosition());
        }
    }
}

interface ItemClickListener{
    void onClick(View v, int position);
}

