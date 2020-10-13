package com.StackBuffers.baqala.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.ModelClasses.NoticeBoardModel;
import com.StackBuffers.baqala.NoticeboardExpandActivity;
import com.StackBuffers.baqala.R;

import java.util.List;

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.modelViewHolder> {

    List<NoticeBoardModel> list;
    Context context;


    public NoticeBoardAdapter(List<NoticeBoardModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.notice_board_item_layout, parent, false);
        return new modelViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, final int i) {

        holder.title.setText(list.get(i).getTitle());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), NoticeboardExpandActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", list.get(i).getTitle());
                intent.putExtra("description", list.get(i).getDescription());
                intent.putExtra("datetime", list.get(i).getDateTime());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView title;
        public modelViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.noticeboard_linear_item);
            title = itemView.findViewById(R.id.noticeBoard_title);

        }
    }
}
