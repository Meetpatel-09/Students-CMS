package com.example.studentscms.ui.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscms.R;
import com.squareup.picasso.Picasso;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_feed_item_layout, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoticeAdapter.NoticeViewAdapter holder, int position) {
        NoticeData currentItem = list.get(position);

        holder.noticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        try {
            if (currentItem.getImage() != null)
                Picasso.get().load(currentItem.getImage()).into(holder.noticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private TextView noticeTitle, date, time;
        private ImageView noticeImage;

        public NoticeViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);

            noticeTitle = itemView.findViewById(R.id.notice_title);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            noticeImage = itemView.findViewById(R.id.notice_image);
        }
    }
}
