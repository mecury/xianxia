package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.cache.cache.ICache;
import com.mecuryli.xianxia.cache.table.NewsTable;
import com.mecuryli.xianxia.cache.table.ScienceTable;
import com.mecuryli.xianxia.model.news.NewsBean;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.ui.support.WebViewUrlActivity;

/**
 * Created by 海飞 on 2016/5/9.
 * 包含title，description，date的news适配器
 */
public class NewsAdapter extends BaseListAdapter<NewsBean,NewsAdapter.ViewHolder> {

    public NewsAdapter(Context context, ICache<NewsBean> cache) {
        super(context, cache);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_news,null);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewUrlActivity.class);
                intent.putExtra("url",getItem(vh.position).getLink());
                mContext.startActivity(intent);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NewsBean newsBean = getItem(position);
        holder.description.setText(newsBean.getDescription());
        holder.title.setText(newsBean.getTitle());
        holder.date.setText(newsBean.getPubTime());
        holder.position = position;
        //跳转到webView加载的详情界面
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewUrlActivity.class);
                intent.putExtra("url",getItem(position).getLink());
                mContext.startActivity(intent);
            }
        });
        if (isCollection){
            holder.collect_cb.setVisibility(View.GONE);
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(holder.parentView,R.string.notify_remove_from_collection,Snackbar.LENGTH_SHORT)
                            .setAction(mContext.getString(R.string.text_ok),new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    if (mItems.contains(newsBean) == false){
                                        return;
                                    }
                                    mCache.execSQL(ScienceTable.updateCollectionFlag(newsBean.getTitle(),0));
                                    mCache.execSQL(ScienceTable.deleteCollectionFlag(newsBean.getTitle()));
                                    mItems.remove(position);
                                    notifyDataSetChanged();
                                }
                            }).show();
                }
            });
            return;
        }
        //收藏单选框点击事件
        holder.collect_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                newsBean.setIs_collected(isChecked ? 1 : 0);
                Log.e("newsAdatpter:" , newsBean.getIs_collected() +"");
                mCache.execSQL(NewsTable.updateCollectionFlag(newsBean.getTitle(), isChecked ? 1 : 0));
                if (isChecked) {
                    mCache.addToCollection(newsBean); //将newsBean添加到“收藏”表中
                } else {
                    mCache.execSQL(NewsTable.deleteCollectionFlag(newsBean.getTitle()));
                }
            }
        });

        Utils.DLog("newAdapter:" + newsBean.getIs_collected());
        holder.collect_cb.setChecked(newsBean.getIs_collected() == 1 ? true:false);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View parentView;
        TextView title;
        TextView description;
        TextView date;
        CheckBox collect_cb;
        TextView text;
        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);
            collect_cb = (CheckBox) itemView.findViewById(R.id.collect_cb);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
