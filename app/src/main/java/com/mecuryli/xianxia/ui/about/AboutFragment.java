package com.mecuryli.xianxia.ui.about;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 海飞 on 2016/6/7.
 */
public class AboutFragment extends PreferenceFragment implements  Preference.OnPreferenceClickListener {

    private Preference mCheckUpdate;
    private Preference mStarProject;
    private Preference mShare;
    private Preference mBlog;
    private Preference mGitHub;
    private Preference mEmail;

    private final String CHECK_UPDATE = "check_update";
    private final String STAR_PROJECT = "star_project";
    private final String SHARE = "share";
    private final String BLOG = "blog";
    private final String GITHUB = "github";
    private final String EMAIL = "email";

    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        mCheckUpdate = findPreference(CHECK_UPDATE);
        mStarProject = findPreference(STAR_PROJECT);
        mShare = findPreference(SHARE);
        mBlog = findPreference(BLOG);
        mGitHub = findPreference(GITHUB);
        mEmail = findPreference(EMAIL);


        mCheckUpdate.setOnPreferenceClickListener(this);
        mStarProject.setOnPreferenceClickListener(this);
        mShare.setOnPreferenceClickListener(this);
        mBlog.setOnPreferenceClickListener(this);
        mGitHub.setOnPreferenceClickListener(this);
        mEmail.setOnPreferenceClickListener(this);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressbar);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(mCheckUpdate == preference){
            progressBar.setVisibility(View.VISIBLE);

            Request.Builder builder = new Request.Builder();
            builder.url(CONSTANT.VERSION_URL);
            Request request = builder.build();
            HttpUtil.enqueue(request, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Snackbar.make(getView(),"Fail to get version info,please check your network setting",Snackbar.LENGTH_SHORT).show();
                    handle.sendEmptyMessage(1);
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    String latestVersion = response.body().string();
                    if(CONSTANT.CURRENT_VERSION.equals(latestVersion.trim())){
                        Snackbar.make(getView(), getString(R.string.notify_current_is_latest),Snackbar.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(getView(), getString(R.string.notify_find_new_version)+latestVersion,Snackbar.LENGTH_SHORT).show();
                    }
                    handle.sendEmptyMessage(1);
                }
            });

        }else if(mStarProject == preference){
            Utils.copyToClipboard(getView(), getString(R.string.project_url));
        }else if(mShare == preference){
            Utils.copyToClipboard(getView(), getString(R.string.text_share_info));
        }else if(mBlog == preference){
            Utils.copyToClipboard(getView(),getString(R.string.author_blog));
        }else if(mGitHub == preference){
            Utils.copyToClipboard(getView(),getString(R.string.author_github));
        }else if(mEmail == preference){
            Utils.copyToClipboard(getView(),getString(R.string.author_email));
        }
        return false;
    }

    private Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            progressBar.setVisibility(View.GONE);
            return false;
        }
    });
}
