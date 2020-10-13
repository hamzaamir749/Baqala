package com.StackBuffers.baqala.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.StepThreeFilter;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.StackBuffers.baqala.ModelClasses.CheckinStepsModel2;
import com.StackBuffers.baqala.ModelClasses.ClientCategoryList;
import com.StackBuffers.baqala.ModelClasses.StepThreeList;
import com.StackBuffers.baqala.ModelClasses.StepThreeModel;
import com.StackBuffers.baqala.R;

import java.util.ArrayList;
import java.util.List;

public class StepThreeAdapter extends RecyclerView.Adapter<StepThreeAdapter.modelViewHolder> implements Filterable {
   public List<StepThreeModel> list;
    Context context;
    UserSessionManager userSessionManager;
    List<String> cc = new ArrayList<>();
    public List<StepThreeModel> filterList;
    StepThreeFilter filter;

    public StepThreeAdapter(List<StepThreeModel> list, Context context) {
        this.list = list;
        this.filterList=list;
        this.context = context;
        userSessionManager = new UserSessionManager(context);
        userSessionManager.clientCategoryList = new ClientCategoryList();
        cc = userSessionManager.clientCategoryList.getList();
        userSessionManager.stepThreeList=new StepThreeList();
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.step_three_item_layout, parent, false);
        return new modelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.shelfSpace.setText(list.get(position).getShelf());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.step_three_layout, null);
                RadioButton rbpaosyes = dialogView.findViewById(R.id.dialouge_availableshelf_yes);
                RadioButton rbpaosno = dialogView.findViewById(R.id.dialouge_availableshelf_no);
                RadioButton rbneyes = dialogView.findViewById(R.id.dialouge_nearexpiry_yes);
                RadioButton rbneno = dialogView.findViewById(R.id.dialouge_nearexpiry_no);
                RadioButton rbscout = dialogView.findViewById(R.id.dialouge_rb_outofstock);
                RadioButton rbscover = dialogView.findViewById(R.id.dialouge_rb_overstock);
                RadioButton rbssyes = dialogView.findViewById(R.id.dialouge_shelfspace_yes);
                RadioButton rbssno = dialogView.findViewById(R.id.dialouge_shelfspace_no);
                Button btnAdd = dialogView.findViewById(R.id.dialouge_checkin_product_add);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCanceledOnTouchOutside(true);
                dialogBuilder.show();

                String productavailable, productExpire, productStock, productSpace;
                if (rbpaosyes.isChecked()) {
                    productavailable = "yes";
                } else {

                    productavailable = "no";
                }


                if (rbneyes.isChecked()) {
                    productExpire = "yes";
                } else {

                    productExpire = "no";
                }


                if (rbscout.isChecked()) {
                    productStock = "Out of Stock";

                } else {
                    productStock = "Over Stock";
                }
                if (rbssyes.isChecked()) {
                    productSpace = "yes";
                } else {
                    productSpace = "no";
                }
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.item.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        holder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                        holder.shelfSpace.setTextColor(context.getResources().getColor(R.color.colorWhite));
                        CheckinStepsModel2 model2 = new CheckinStepsModel2(list.get(position).getName(), cc.get(0), productavailable, productExpire, productStock, productSpace);
                        userSessionManager.stepThreeList.setList(model2);
                        dialogBuilder.dismiss();
                    }
                });

            }
        });
    }
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter=new StepThreeFilter(this,filterList);
        }
        return filter;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        TextView name, shelfSpace;
        ImageView img;

        public modelViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.stepthree_card);
            name = itemView.findViewById(R.id.stepthree_name);
            shelfSpace = itemView.findViewById(R.id.stepthree_shelf);
            img = itemView.findViewById(R.id.stepthree_img);
        }
    }
}
