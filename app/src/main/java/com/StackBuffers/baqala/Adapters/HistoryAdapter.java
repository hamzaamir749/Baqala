package com.StackBuffers.baqala.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.DesitinationNearbyActivity;
import com.StackBuffers.baqala.ModelClasses.HistoryModel;
import com.StackBuffers.baqala.R;
import com.StackBuffers.baqala.TaskDetailActivity;
import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.StaticMapCriteria;
import com.mapbox.api.staticmap.v1.models.StaticMarkerAnnotation;
import com.mapbox.geojson.Point;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    List<HistoryModel> list;
    Context context;
    List<StaticMarkerAnnotation> marker = new ArrayList<>();

    public HistoryAdapter(List<HistoryModel> list, Context context) {
        this.list = list;
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_history_view, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.hName.setText(list.get(position).getName());
        holder.hCity.setText(list.get(position).getCity());
        holder.hStatus.setText(list.get(position).gettStatus());
        String statusType=list.get(position).gettStatus();
        if (statusType.equals("COMPLETED"))
        {
            holder.histroy_card.setCardBackgroundColor(context.getResources().getColor(R.color.colorgreen));
        }else  if (statusType.equals("CANCELLED"))
        {
            holder.histroy_card.setCardBackgroundColor(context.getResources().getColor(R.color.colorred));
        }
        else  if (statusType.equals("PENDING"))
        {
            holder.histroy_card.setCardBackgroundColor(context.getResources().getColor(R.color.colormaroon));
        }
        holder.hStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (list.get(position).gettStatus().equals("COMPLETED"))
               {
                   Intent i = new Intent(context.getApplicationContext(), TaskDetailActivity.class);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   i.putExtra("route_id",list.get(position).getRoute_id());
                   context.startActivity(i);

               }

            }
        });




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

        Picasso.with(context).load(imageUrl).placeholder(R.drawable.baqala).into(holder.hMap);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public   class  MyViewHolder  extends  RecyclerView.ViewHolder{

        ImageView hMap;
        TextView hName,hCity,hStatus;
        CardView histroy_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hMap = itemView.findViewById(R.id.history_map);
            hName = itemView.findViewById(R.id.history_task_name);
            hCity = itemView.findViewById(R.id.history_task_cityname);
            hStatus = itemView.findViewById(R.id.status);
            histroy_card=itemView.findViewById(R.id.histroy_card);


        }
    }
}
