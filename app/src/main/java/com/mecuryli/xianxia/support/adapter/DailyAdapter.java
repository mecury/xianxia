package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.cache.cache.DailyCache;
import com.mecuryli.xianxia.cache.table.DailyTable;
import com.mecuryli.xianxia.model.Daily.DailyBean;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/18.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder>{

    private List<DailyBean> mItems;
    private Context mContext;
    private DailyCache mCache;

    public DailyAdapter(DailyCache cache, Context context){
        this.mContext = context;
        mCache = cache;
        mItems = cache.getmList();  //从缓存中得到数据
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_daily, null);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DailyBean dailyBean =getItem(position);
        String images = dailyBean.getImage();
        Uri uri = Uri.parse(images);
        holder.title.setText(dailyBean.getTitle());
        holder.image.setImageURI(uri);
        holder.info.setText("未定");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.collect_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dailyBean.setIs_collected(isChecked ? 1:0);
                mCache.execSQL(DailyTable.updateCollectionFlag(dailyBean.getTitle(),isChecked ?1:0));
                if (isChecked){
                    mCache.addToCollection(dailyBean);
                }else{
                    mCache.execSQL(DailyTable.deleteCollectionFlag(dailyBean.getTitle()));
                }
            }
        });
    }

    private DailyBean getItem(int position){
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private SimpleDraweeView image;
        private TextView info;
        private CheckBox collect_cb;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (SimpleDraweeView) itemView.findViewById(R.id.image);
            info = (TextView) itemView.findViewById(R.id.info);
            collect_cb = (CheckBox) itemView.findViewById(R.id.collect_cb);
        }
    }
}
