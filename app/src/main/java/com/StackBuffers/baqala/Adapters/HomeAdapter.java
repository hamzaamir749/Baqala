package com.StackBuffers.baqala.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.DesitinationNearbyActivity;
import com.StackBuffers.baqala.DestinationMapsActivity;
import com.StackBuffers.baqala.HelperClasses.SharedHelper;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.StackBuffers.baqala.ModelClasses.BitmapList;
import com.StackBuffers.baqala.ModelClasses.HomeModel;
import com.StackBuffers.baqala.R;
import com.StackBuffers.baqala.StartActivity;
import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.StaticMapCriteria;
import com.mapbox.api.staticmap.v1.models.StaticMarkerAnnotation;
import com.mapbox.geojson.Point;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyviewHolder> {


    List<HomeModel> list;
    Context context;
    List<StaticMarkerAnnotation> marker = new ArrayList<>();
    UserSessionManager userSessionManager;
    SharedHelper sharedHelper = new SharedHelper();


    public HomeAdapter(List<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
        userSessionManager = new UserSessionManager(context);
        userSessionManager.bitmapList = new BitmapList();
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_home_view, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.mName.setText(list.get(position).getName());
        holder.cityName.setText(list.get(position).getCity());
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

        Picasso.with(context).load(imageUrl).placeholder(R.drawable.baqala).into(holder.map);

        holder.navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSessionManager.bitmapList.setName(list.get(position).getName());
                userSessionManager.bitmapList.setAddress(list.get(position).getCity());
                Intent i = new Intent(context.getApplicationContext(), DesitinationNearbyActivity.class);
                sharedHelper.putKey(context,"homeboolean","true");
                sharedHelper.putKey(context,"route_id",String.valueOf(list.get(position).getRouteId()));
                i.putExtra("lats",list.get(position).getLat());
                i.putExtra("longs",list.get(position).getLng());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    private void showPOPUP() {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyviewHolder extends RecyclerView.ViewHolder {

        ImageView map;
        TextView mName, cityName, mTime;
        ImageButton navBtn;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            map = itemView.findViewById(R.id.home_map);
            mName = itemView.findViewById(R.id.map_name);
            cityName = itemView.findViewById(R.id.map_cityname);

            navBtn = itemView.findViewById(R.id.nav_btn);

        }


    }


}
