package com.StackBuffers.baqala.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.ModelClasses.CheckinStepsModel2;
import com.StackBuffers.baqala.R;

import java.util.List;

public class CustomAdapterRecycler extends RecyclerView.Adapter<CustomAdapterRecycler.modelViewHolder> {

    Context context;
    List<CheckinStepsModel2> list;

    public CustomAdapterRecycler(Context context, List<CheckinStepsModel2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_adapter_layout, parent, false);
        return new modelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        holder.item.setText(list.get(position).getpName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        public modelViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.base_text);
        }
    }
}
