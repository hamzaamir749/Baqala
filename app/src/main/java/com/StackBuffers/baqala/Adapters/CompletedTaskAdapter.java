package com.StackBuffers.baqala.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.ModelClasses.CompletdTaskModel;
import com.StackBuffers.baqala.R;
import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.StaticMapCriteria;
import com.mapbox.api.staticmap.v1.models.StaticMarkerAnnotation;
import com.mapbox.geojson.Point;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter <CompletedTaskAdapter.MyviewHolder>{

    List<CompletdTaskModel> list;
    Context context;
    List<StaticMarkerAnnotation> marker = new ArrayList<>();

    public CompletedTaskAdapter(List<CompletdTaskModel> list, Context context) {
        this.list = list;
        this.context = context;
    }




    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_view_completd, parent, false);
        return new MyviewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.cName.setText(list.get(position).getName());
        holder.cCity.setText(list.get(position).getCity());
        holder.cStatus.setText(list.get(position).getcStatus());


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

        Picasso.with(context).load(imageUrl).placeholder(R.drawable.baqala).into(holder.cMap);






    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class  MyviewHolder extends  RecyclerView.ViewHolder{

        ImageView cMap;
        TextView cName,cCity,cStatus;




        public MyviewHolder (@NonNull View itemView) {
            super(itemView);

            cMap = itemView.findViewById(R.id.completed_map);
            cName= itemView.findViewById(R.id.completd_task_name);
            cCity = itemView.findViewById(R.id.completd_task_cityname);
            cStatus = itemView.findViewById(R.id.status_completd);


        }
    }

}
