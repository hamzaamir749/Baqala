package com.StackBuffers.baqala.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.ModelClasses.AddStoreModel;
import com.StackBuffers.baqala.R;
import com.StackBuffers.baqala.StoreInfoActivity;

import java.util.List;

public class AddStoreAdapter extends RecyclerView.Adapter<AddStoreAdapter.modelViewHolder> {

    List<AddStoreModel> list;
    Context context;

    public AddStoreAdapter(List<AddStoreModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_layout_addstore, parent, false);
        return new modelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, final int i) {

        holder.sname.setText(list.get(i).getsName());
        holder.viewstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), StoreInfoActivity.class);
                intent.putExtra("sname", list.get(i).getsName());
                intent.putExtra("cname",list.get(i).getcName());
                intent.putExtra("sphone",list.get(i).getsPhone());
                intent.putExtra("saddress",list.get(i).getsAddress());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout viewstore;
        TextView sname;


        public modelViewHolder(@NonNull View itemView) {
            super(itemView);

            viewstore = itemView.findViewById(R.id.rl_view_store);
            sname = itemView.findViewById(R.id.tv_store_name);

        }
    }

}
