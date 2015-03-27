package com.healthpush.healthpushapp.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.HealthPushApplication;
import com.healthpush.healthpushapp.model.Article;
import com.healthpush.healthpushapp.request.PractoGsonRequest;
import com.squareup.picasso.Picasso;

import dev.dworks.libs.actionbarplus.app.ActionBarGridFragment;

/**
 * Created by ravikiran on 27/03/15.
 */
public class ArticleFragment extends ActionBarGridFragment implements Response.ErrorListener, Response.Listener<Article> {


    private View root;
    private ArticleAdapter mAdapter;

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
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
            mAdapter = new ArticleAdapter(getActionBarActivity(), R.layout.item_article);
            mAdapter.notifyDataSetChanged();
            setGridAdapter(mAdapter);
            setGridShown(false);
            restartLoading();
    }


    private void restartLoading(){
        if (mAdapter != null){
            //getArticles();
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


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView articleTxt;
            ImageView articleImage;
            convertView = inflater.inflate(R.layout.item_article, parent, false);
            articleTxt = (TextView) convertView.findViewById(R.id.article_txt);
            articleImage = (ImageView) convertView.findViewById(R.id.article_image);
            Article.ArticleData data = (Article.ArticleData) getItem(position);
            Picasso.with(mContext).load(data.url).into(articleImage);
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
