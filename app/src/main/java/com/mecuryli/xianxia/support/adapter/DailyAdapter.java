package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.database.cache.ICache;
import com.mecuryli.xianxia.database.table.DailyTable;
import com.mecuryli.xianxia.model.Daily.DailyBean;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.DailyAdapter.ViewHolder;

/**
 * Created by 海飞 on 2016/5/18.
 */
public class DailyAdapter extends BaseListAdapter<DailyBean, ViewHolder> {
    public DailyAdapter(Context context, ICache<DailyBean> cache) {
        super(context, cache);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = View.inflate(mContext, R.layout.item_daily, null);
        ViewHolder vh = new ViewHolder(parentView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final DailyBean dailyBean =getItem(position);
        String images = dailyBean.getImage();
        Uri uri = Uri.parse(images);
        holder.title.setText(dailyBean.getTitle());
        holder.image.setImageURI(uri);
        holder.info.setText("未定");

        //点击当前item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //当isCOllection为true时，此时fragment为的类型为CollectionFragment
        if (isCollection){
            Utils.DLog("通过Collection来到这里");
            holder.collect_cb.setVisibility(View.GONE);
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setText(R.string.text_remove);
            holder.text.setTextSize(20);
            holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            //当在收藏界面时，删除选项的点击事件
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(holder.parentView,R.string.notify_remove_from_collection,Snackbar.LENGTH_SHORT).
                            setAction(mContext.getString(R.string.text_ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //当收藏表中包含当前dailyBean
                                    if (mItems.contains(dailyBean) == false){
                                        return;
                                    }
                                    //将“收藏”中将要删除的项的is_collected置为0
                                    mCache.execSQL(DailyTable.updateCollectionFlag(dailyBean.getTitle(),0));
                                    //再将选中的项删除
                                    mCache.execSQL(DailyTable.deleteCollectionFlag(dailyBean.getTitle()));
                                    mItems.remove(position);
                                    notifyDataSetChanged();
                                }
                            }).show();
                }
            });
            return;
        }
        //根据单选框的状态，来决定是否将当前项加入收藏表中
        holder.collect_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dailyBean.setIs_collected(isChecked ? 1:0);
                Log.e("is_collected===>",dailyBean.getIs_collected() + "");
                mCache.execSQL(DailyTable.updateCollectionFlag(dailyBean.getTitle(),isChecked ?1:0));
                if (isChecked){
                    mCache.addToCollection(dailyBean);
                }else{
                    mCache.execSQL(DailyTable.deleteCollectionFlag(dailyBean.getTitle()));
                }
            }
        });

        //根据dailyBean的is_collected是否为1（是否被点击了），改变单选框状态
        holder.collect_cb.setChecked(dailyBean.getIs_collected() == 1 ? true:false);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View parentView;
        private TextView title;
        private SimpleDraweeView image;
        private TextView info;
        private CheckBox collect_cb; //决定是否收藏该项的单选框
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            title = (TextView) parentView.findViewById(R.id.title);
            image = (SimpleDraweeView) parentView.findViewById(R.id.image);
            info = (TextView) parentView.findViewById(R.id.info);
            collect_cb = (CheckBox) parentView.findViewById(R.id.collect_cb);
            text = (TextView) parentView.findViewById(R.id.text);
        }
    }
}
