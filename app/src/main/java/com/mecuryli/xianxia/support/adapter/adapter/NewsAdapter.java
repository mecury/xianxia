package com.mecuryli.xianxia.support.adapter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.model.NewsBean;
import com.mecuryli.xianxia.ui.news.WebViewActivity;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/9.
 * 包含title，description，date的news适配器
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsBean> items; //news的list列表
    private Context mContext;

    public NewsAdapter(Context context,List<NewsBean> items){
        this.items = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_news,null);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url",getItem(vh.position).getLink());
                mContext.startActivity(intent);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsBean newsBean = getItem(position);
        holder.description.setText(newsBean.getDescription());
        holder.title.setText(newsBean.getTitle());
        holder.date.setText(newsBean.getPubTime());
        holder.position = position;
    }

    private NewsBean getItem(int position){
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        TextView date;
        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
