package com.mecuryli.xianxia.ui.science;

import android.support.v7.widget.RecyclerView;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ScienceApi;
import com.mecuryli.xianxia.database.cache.cache.ScienceCache;
import com.mecuryli.xianxia.support.adapter.ScienceAdapter;
import com.mecuryli.xianxia.ui.support.BaseListFragment;

/**
 * Created by 海飞 on 2016/5/16.
 */
public class ScienceFragment extends BaseListFragment {

    
    private String mCategory;
    private String mUrl;
    @Override
    protected void onCreateCache() {
        cache = new ScienceCache(handler,mCategory,mUrl);
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new ScienceAdapter(getContext(),cache);
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
        mUrl = ScienceApi.science_channel_url+ScienceApi.channel_tag[getArguments().getInt(getString(R.string.id_pos))];
        mCategory = getArguments().getString(getString(R.string.id_category));
    }


    /*private View parentView;
    private ScienceBean scienceBean;
    private List<ArticleBean> items = new ArrayList<>();
    private List<ArticleBean> tmpItems = new ArrayList<>();
    private PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ScienceAdapter adapter;
    private RequestQueue queue;
    private String url;

    private ImageView sad_face;
    private ProgressBar progressBar;

    private String category;
    private cache cache;

    private Thread thread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_commont_list, null);
        initData();
        return parentView;
    }

    private void initData() {
        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
        url = ScienceApi.science_channel_url + ScienceApi
                .channel_tag[getArguments().getInt(getString(R.string.id_pos))];
        category = getArguments().getString(getString(R.string.id_category));
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        adapter = new ScienceAdapter(getContext(), items);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        Utils.DLog("Science====>" + category);
        loadNewsFromNet();
        sad_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sad_face.setVisibility(View.GONE);
                loadNewsFromNet();
            }
        });
        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNewsFromNet();
            }
        });
        cache = new cache(xianxiaApplication.AppContext);
        loadCache();
    }

    private void loadNewsFromNet() {
        refreshView.setRefreshing(true);
        new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
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
                        if (response.isSuccessful() == false) {
                            handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                            return;
                            }
                        Gson gson = new Gson();
                        ArticleBean[] articleBeans = (gson.fromJson(response.body().string(), ScienceBean.class)).getResult();
                        for(ArticleBean articleBean: articleBeans){
                            tmpItems.add(articleBean);
                            }
                        handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                    }
                });
            }
        }).start();
    };

    private Handler handler = new Handler(new Handler.Callback() {
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
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tmpItems.clear();
                List<Object> tmpList = cache.loadFromCache(category);
                for (int i=0; i<tmpList.size(); i++){
                    tmpItems.add((ArticleBean) tmpList.get(i));
                }
                tmpList.clear();
                items.clear();
                items.addAll(tmpItems);
                tmpItems.clear();
                if (progressBar.getVisibility() == View.VISIBLE){
                    if (items == null){
                        handler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_NET);
                    }
                }
                handler.sendEmptyMessage(CONSTANT.ID_UPDATE_UI);
            }
        });
        thread.start();
    }*/
}

























































