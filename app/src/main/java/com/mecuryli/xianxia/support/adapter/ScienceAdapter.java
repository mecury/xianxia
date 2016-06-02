package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.database.cache.cache.ICache;
import com.mecuryli.xianxia.database.cache.table.NewsTable;
import com.mecuryli.xianxia.database.cache.table.ScienceTable;
import com.mecuryli.xianxia.model.science.ArticleBean;
import com.mecuryli.xianxia.ui.support.WebViewUrlActivity;

/**
 * Created by 海飞 on 2016/5/16.
 */
public class ScienceAdapter extends BaseListAdapter<ArticleBean, ScienceAdapter.ViewHolder> {


    public ScienceAdapter(Context context, ICache<ArticleBean> cache) {
        super(context, cache);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item_science,null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ArticleBean articleBean = getItem(position);
        holder.title.setText(articleBean.getTitle());
        holder.description.setText(articleBean.getSummary());
        holder.info.setText(articleBean.getInfo());
        holder.image.setImageURI(Uri.parse(articleBean.getImage_info().getUrl()));
        holder.comment.setText(String.valueOf(articleBean.getReplies_count()));
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击跳转到webView浏览器
                Intent intent = new Intent(mContext, WebViewUrlActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(mContext.getString(R.string.id_url),articleBean.getUrl());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        if (isCollection){
            holder.collect_cb.setVisibility(View.GONE);
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setText(R.string.text_remove);
            holder.text.setTextSize(20);
            holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(holder.parentView,R.string.notify_remove_from_collection,Snackbar.LENGTH_SHORT).
                            setAction(mContext.getString(R.string.text_ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mItems.contains(articleBean)==false){
                                        return;
                                    }
                                    //将“收藏”中将要删除的项的is_collected置为0
                                    mCache.execSQL(ScienceTable.updateCollectionFlag(articleBean.getTitle(),0));
                                    //根据title删除表中的项
                                    mCache.execSQL(ScienceTable.deleteCollectionFlag(articleBean.getTitle()));
                                    mItems.remove(position);
                                    notifyDataSetChanged();
                                }
                            }).show();
                }
            });
            return;
        }

        holder.collect_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                articleBean.setIs_collected(isChecked ? 1 : 0);
                mCache.execSQL(NewsTable.updateCollectionFlag(articleBean.getTitle(), isChecked ? 1 : 0));
                if (isChecked) {
                    mCache.addToCollection(articleBean);
                } else {
                    mCache.execSQL(NewsTable.deleteCollectionFlag(articleBean.getTitle()));
                }
            }
        });

        holder.collect_cb.setChecked(articleBean.getIs_collected() == 1 ? true:false);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View parentView;
        private TextView title;
        private TextView description;
        private TextView info;
        private TextView comment;
        private SimpleDraweeView image;
        private CheckBox collect_cb;
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            title = (TextView) parentView.findViewById(R.id.title);
            description = (TextView) parentView.findViewById(R.id.description);
            info = (TextView) parentView.findViewById(R.id.info);
            image = (SimpleDraweeView) parentView.findViewById(R.id.image);
            comment = (TextView) parentView.findViewById(R.id.comment);
            collect_cb = (CheckBox) parentView.findViewById(R.id.collect_cb);
            text = (TextView) parentView.findViewById(R.id.text);
        }
    }
}
