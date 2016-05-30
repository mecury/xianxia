package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.cache.cache.ReadingCache;
import com.mecuryli.xianxia.cache.table.ReadingTable;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.ui.reading.ReadingDetailActivity;
import com.mecuryli.xianxia.xianxiaApplication;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/13.
 * item的适配器
 */
public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ViewHolder> {

    private List<BookBean> items ;
    private Context mContext;
    private ReadingCache cache;
    public ReadingAdapter(List<BookBean> mItems, Context mContext){
        this.items = mItems;
        this.mContext = mContext;
        cache = new ReadingCache(xianxiaApplication.AppContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reading,parent, false);
        final ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookBean bookBean = getItem(position);
        holder.titles.setText(bookBean.getTitle());
        holder.info.setText(bookBean.getInfo());
        holder.imageView.setImageURI(Uri.parse(bookBean.getImage()));
        holder.parentView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(mContext.getString(R.string.id_book),bookBean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        if(Utils.hasString(bookBean.getEbook_url())) {
            holder.parentView.setBackground(mContext.getResources().getDrawable(R.drawable.item_bg_selected,null)); //setBackgroundColor(mContext.getResources().getColor(R.color.item_bg));
        }
        else {
            holder.parentView.setBackground(mContext.getResources().getDrawable(R.drawable.item_bg, null));
        }

        holder.collect_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bookBean.setIs_collected(isChecked ? 1 : 0);
                cache.execSQL(ReadingTable.updateCollectionFlag(bookBean.getTitle(), isChecked ? 1 : 0));
                if(isChecked){
                    cache.addToCollection(bookBean);
                }else{
                    cache.execSQL(ReadingTable.deleteCollectionFlag(bookBean.getTitle()));
                }
            }
        });
        holder.collect_cb.setChecked(bookBean.getIs_collected() ==1 ? true:false);
    }

    public BookBean getItem(int pos){
        return items.get(pos);
    }

    @Override
    public int getItemCount() {
        Utils.DLog(items.size()+"");
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View parentView;
        private SimpleDraweeView imageView;
        private TextView titles;
        private TextView info;
        private CheckBox collect_cb;
        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.bookImg);
            titles = (TextView) itemView.findViewById(R.id.bookTitle);
            info = (TextView) itemView.findViewById(R.id.bookInfo);
            collect_cb = (CheckBox) parentView.findViewById(R.id.collect_cb);
        }
    }
}
