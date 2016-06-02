package com.mecuryli.xianxia.ui.news;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.cache.cache.NewsCache;
import com.mecuryli.xianxia.support.adapter.NewsAdapter;
import com.mecuryli.xianxia.ui.support.BaseListFragment;

/**
 * Created by 海飞 on 2016/5/9.
 * 新闻列表界面的fragment
 */

public class NewsFragment extends BaseListFragment {

    private String mUrl;
    private String mCategory;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreateCache() {
        cache = new NewsCache(getContext(),handler,mCategory,mUrl);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new NewsAdapter(getContext(),cache);
    }

    @Override
    protected void loadFromNet() {
        cache.load();
    }

    @Override
    protected void loadFromCache() {
        cache.loadFromCache();
    }

    @Override
    protected boolean hasData() {
        return cache.hasData();
    }

    @Override
    protected void getArgs() {
        mUrl = getArguments().getString(getString(R.string.id_url));
        mCategory = getArguments().getString(getString(R.string.id_category));
    }



    /*private View parentView;
    private ProgressBar progressBar;
    private PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private List<NewsBean> items = new ArrayList<>();
    private List<NewsBean> tmpItems = new ArrayList<>();
    private NewsAdapter adapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView sad_face;

    private NewsCache cache;

    private String url;
    private String category;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_news,null);
        init();
        return parentView;
    }

    void init(){
        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
        Utils.DLog("ReadingFragment====>"+items.size());
        adapter = new NewsAdapter(getContext(), items);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
        url = getArguments().getString(getString(R.string.id_url));
        category = getArguments().getString(getString(R.string.id_category));
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));  //item 间的分割线

        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                loadNewsFromNet();
            }
        });
        sad_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sad_face.setVisibility(View.GONE);
                loadNewsFromNet();
            }
        });

        cache = new NewsCache(xianxiaApplication.AppContext);
        loadCache();
    }


    //由网络加载
    private void loadNewsFromNet(){
        refreshView.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                Request request = builder.build();
                HttpUtil.enqueue(request, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()==false){
                            handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                            return;
                        }
                        InputStream is = new ByteArrayInputStream(response.body().string().getBytes(StandardCharsets.UTF_8));

                        try {
                            tmpItems.addAll(SAXNewsParse.parse(is));
                            is.close();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                    }
                });
            }
        }).start();
    }

    private Handler handler = new Handler(new Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            refreshView.setRefreshing(false);
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    Utils.DLog(getString(R.string.Text_Net_Exception));
                    break;
                case CONSTANT.ID_SUCCESS:
                    cache.cache(tmpItems, category);
                    loadCache();
                    break;
                case CONSTANT.ID_LOAD_FROM_NET:
                    loadNewsFromNet();
                    break;
                case CONSTANT.ID_UPDATE_UI:
                    if (items.isEmpty()){
                        sad_face.setVisibility(View.VISIBLE);
                    }else{
                        sad_face.setVisibility(View.GONE);
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });

    private synchronized void loadCache(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tmpItems.clear();
                List<Object> tmpList = cache.loadFromCache(category);
                for (Object object : tmpList){
                    tmpItems.add((NewsBean) object);
                }
                tmpList.clear();
                items.clear();
                items.addAll(tmpItems);
                tmpItems.clear();
                if (progressBar.getVisibility() == View.VISIBLE){
                    if (items.isEmpty()){
                        handler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_NET);
                    }
                }
                handler.sendEmptyMessage(CONSTANT.ID_UPDATE_UI);
            }
        });
        thread.start();
    }*/
}
