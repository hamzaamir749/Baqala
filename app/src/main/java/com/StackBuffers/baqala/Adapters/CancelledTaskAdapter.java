package com.StackBuffers.baqala.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.ModelClasses.CancelledTaskModel;
import com.StackBuffers.baqala.ModelClasses.CompletdTaskModel;
import com.StackBuffers.baqala.R;
import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.StaticMapCriteria;
import com.mapbox.api.staticmap.v1.models.StaticMarkerAnnotation;
import com.mapbox.geojson.Point;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CancelledTaskAdapter extends RecyclerView.Adapter<CancelledTaskAdapter.MyviewHolder> {

    List<CancelledTaskModel> list;
    Context context;
    List<StaticMarkerAnnotation> marker = new ArrayList<>();

    public CancelledTaskAdapter(List<CancelledTaskModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_view_cancelled, parent, false);
        return new CancelledTaskAdapter.MyviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {



        holder.canName.setText(list.get(position).getName());
        holder.canCity.setText(list.get(position).getCity());
        holder.canStatus.setText(list.get(position).getCanStatus());


        MapboxStaticMap staticImage = MapboxStaticMap.builder()
                .accessToken("pk.eyJ1IjoiaGFtemFhbWlyNzQ5IiwiYSI6ImNqdDE0ODY4eDExdGo0OXBoMHl4YjZrNG0ifQ.3hAWqNtZfhod-bXzrqIg7Q")
                .styleId(StaticMapCriteria.STREET_STYLE)
                .cameraPoint(Point.fromLngLat(list.get(position).getLng(), list.get(position).getLat())) // Image's centerpoint on map
                .cameraZoom(13)
                .width(320) // Image width
                .height(320) // Image height
                .retina(true) // Retina 2x image will be returned
                .build();

        String imageUrl = staticImage.url().toString();

        Picasso.with(context).load(imageUrl).placeholder(R.drawable.baqala).into(holder.canMap);



    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public  class  MyviewHolder extends  RecyclerView.ViewHolder{
        ImageView canMap;
        TextView canName,canCity,canStatus;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);


            canMap = itemView.findViewById(R.id.cancelled_map);
            canName= itemView.findViewById(R.id.cancelled_task_name);
            canCity = itemView.findViewById(R.id.cancelled_task_cityname);
            canStatus = itemView.findViewById(R.id.status_cancelled);




        }
    }
}
