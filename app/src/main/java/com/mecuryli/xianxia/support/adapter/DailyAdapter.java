package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.model.Daily.DailyItem;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/18.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder>{

    private List<DailyItem> dailyItems;
    private Context mContext;
    public DailyAdapter(Context context, List<DailyItem> items){
        this.mContext = context;
        this.dailyItems = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_daily, null);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DailyItem dailyItem =getItem(position);
        String[] images = dailyItem.getImages();
        Uri uri = Uri.parse(images[0]);
        holder.title.setText(dailyItem.getTitle());
        holder.image.setImageURI(uri);
        holder.info.setText("未定");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private DailyItem getItem(int position){
        return dailyItems.get(position);
    }

    @Override
    public int getItemCount() {
        return dailyItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private SimpleDraweeView image;
        private TextView info;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (SimpleDraweeView) itemView.findViewById(R.id.image);
            info = (TextView) itemView.findViewById(R.id.info);
        }
    }
}
