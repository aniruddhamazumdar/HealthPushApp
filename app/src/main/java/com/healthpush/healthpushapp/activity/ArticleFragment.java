package com.healthpush.healthpushapp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.HealthPushApplication;
import com.healthpush.healthpushapp.model.Article;
import com.healthpush.healthpushapp.request.PractoGsonRequest;

import java.util.ArrayList;
import java.util.List;

import dev.dworks.libs.actionbarplus.ActionBarFragment;

/**
 * Created by ravikiran on 27/03/15.
 */
public class ArticleFragment extends ActionBarFragment implements Response.ErrorListener, Response.Listener<Article> {


    private View root;
    private ArticleAdapter mAdapter;
    private ArrayList<Article.ArticleData> lists;
    private GridView mGrid;

    public static ArticleFragment show(FragmentManager fm, Bundle args,int container) {
        final FragmentTransaction ft = fm.beginTransaction();

        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        ft.replace(container, fragment);
        ft.commitAllowingStateLoss();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_articles, container, false);
        mGrid = (GridView) root.findViewById(R.id.grid);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            mAdapter = new ArticleAdapter(getActionBarActivity(), R.layout.item_article);
            mGrid.setAdapter(mAdapter);
            restartLoading();
    }


    private void restartLoading(){
        if (mAdapter != null){
            //getArticles();
            lists = new ArrayList<Article.ArticleData>();
            Article.ArticleData articleData = new Article.ArticleData();
            articleData.name = "kira";
            lists.add(articleData);
            articleData = new Article.ArticleData();
            articleData.name = "ravi";
            lists.add(articleData);

            mAdapter.setData(lists,true);


        }
    }

    private void getArticles(){
        String url = "";
        final ArrayMap<String, String> params = new ArrayMap<String, String>();

        PractoGsonRequest<Article> request;
        request = new PractoGsonRequest<Article>(Request.Method.GET, url , Article.class,
                "", params, this, this);
        HealthPushApplication.getInstance().addToRequestQueue(request, "");
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(Article article) {

        if (article != null){


        }

    }

    private class ArticleAdapter extends ArrayAdapter<Article.ArticleData> {

        private int layout;
        private LayoutInflater inflater;
        private Context mContext;

        public ArticleAdapter(Context context, int resource) {
            super(context, resource);
            this.mContext = context;
            this.inflater = LayoutInflater.from(context);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void setData(List<Article.ArticleData> data, boolean add) {
            if (!add) {
                clear();
            }
                if (data != null) {
                    for (Article.ArticleData article : data) {
                        add(article);
                    }
                }

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView articleTxt;
            ImageView articleImage;
            convertView = inflater.inflate(R.layout.item_article, parent, false);
            articleTxt = (TextView) convertView.findViewById(R.id.article_txt);
            articleImage = (ImageView) convertView.findViewById(R.id.article_image);
            Article.ArticleData data = (Article.ArticleData) getItem(position);
         //   Picasso.with(mContext).load(data.url).into(articleImage);
            articleTxt.setText(data.name);

            return convertView;
        }

    }

    public static class ViewHolder {
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            } else {
                view.getTag();
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }
}
