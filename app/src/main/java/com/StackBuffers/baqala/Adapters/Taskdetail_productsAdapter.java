package com.StackBuffers.baqala.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.ModelClasses.Taskdetail_productsModel;
import com.StackBuffers.baqala.R;

import java.util.List;

public class Taskdetail_productsAdapter extends RecyclerView.Adapter<Taskdetail_productsAdapter.MyviewHolder> {

    List<Taskdetail_productsModel> list;
    Context context;

    public Taskdetail_productsAdapter(List<Taskdetail_productsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_taskdetail_products, parent, false);
        return new MyviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.productsName.setText(list.get(position).getProductsName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView productsName;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            productsName = itemView.findViewById(R.id.taskdetail_base_text);


        }
    }


}
