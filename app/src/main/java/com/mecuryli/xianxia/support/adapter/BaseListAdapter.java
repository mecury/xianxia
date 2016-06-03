package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mecuryli.xianxia.database.cache.Collection.BaseCollectionCache;
import com.mecuryli.xianxia.database.cache.ICache;
import com.mecuryli.xianxia.support.Settings;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/30.
 */
public abstract class BaseListAdapter<M,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
    protected List<M> mItems;
    protected Context mContext;
    protected ICache<M> mCache;

    protected boolean isCollection = false;  //“收藏”状态,false表示不收藏

    protected boolean isNoPicMode = false; //无图模式标志

    public BaseListAdapter(Context context, ICache<M> cache){
        mContext = context;
        mCache = cache;
        mItems = cache.getmList();

        isNoPicMode = Settings.getInstance().getBoolean(Settings.NO_PIC_MODE,false);

        if (cache instanceof BaseCollectionCache){
            isCollection = true;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected M getItem(int position) {
        return mItems.get(position);
    }
}
