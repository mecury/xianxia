package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.ui.reading.ReadingDetailActivity;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/13.
 * item的适配器
 */
public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ViewHolder> {

    private List<BookBean> items ;
    private Context mContext;

    public ReadingAdapter(List<BookBean> items, Context mContext){
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reading,parent, false);
        final ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookBean readingBean = getItem(position);
        holder.titles.setText(readingBean.getTitle());
        holder.info.setText(readingBean.toString());
        holder.imageView.setImageURI(Uri.parse(readingBean.getImage()));
        holder.parentView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(mContext.getString(R.string.id_book),readingBean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        if (Utils.hasString(readingBean.getEbook_url())){
            holder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.item_bg));
        }else{
            holder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }

    public BookBean getItem(int pos){
        return items.get(pos);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View parentView;
        SimpleDraweeView imageView;
        TextView titles;
        TextView info;
        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.bookImg);
            titles = (TextView) itemView.findViewById(R.id.bookTitle);
            info = (TextView) itemView.findViewById(R.id.bookInfo);
        }
    }
}
