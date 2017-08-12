package com.xiaoming.slience.mvp.Presenter;

import android.os.AsyncTask;

import com.xiaoming.slience.bean.MV;
import com.xiaoming.slience.utils.ACache;
import com.xiaoming.slience.mvp.view.VideoView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1316:44
 */

public class VideoPresenterImp implements VideoPresenter {

    private String Url;
    private int PAGE = 1;
    private VideoView mVideoView;
    private ACache mACache;

    public VideoPresenterImp(VideoView videoView, ACache aCache) {
        mVideoView = videoView;
        mACache = aCache;
    }

    @Override
    public void getDataFromServer(boolean isLoadMore, boolean isCache) {
        Url = "http://www.twoeggz.com/mv/index_1.html";
        JsoupUtils(Url, isLoadMore, isCache);
    }

    @Override
    public void getMoreDataFromServer(boolean isLoadMore, boolean isCache) {
        PAGE++;
        Url = "http://www.twoeggz.com/mv/index_" + PAGE + ".html";
        JsoupUtils(Url, isLoadMore, isCache);
    }

    private void JsoupUtils(final String url, final boolean isLoadMore, boolean isCache) {
        List<MV> mvs = (List<MV>) mACache.getAsObject(url);
        if (isCache && mvs != null) {
            mVideoView.hideProgress();
            mVideoView.onRequestData(mvs, isLoadMore);
        } else {
            if(!isCache){
                mVideoView.hideProgress();
            }
            new VideoDataTask().execute(url, isLoadMore);
        }
    }

    class VideoDataTask extends AsyncTask<Object, Integer, Object[]> {

        private String url;
        private boolean isLoadMore;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object[] doInBackground(Object[] params) {
            url = (String) params[0];
            isLoadMore = (boolean) params[1];
            List<MV> mvList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).timeout(10000).get();
                Elements elements = document.select("div.excerpts").select("article");
                for (Element element : elements) {
                    MV mv = new MV();
                    mv.title = element.select("a.thumbnail").select("img").attr("alt");
                    mv.imgUrl = element.select("a.thumbnail").select("img").attr("abs:src");
                    String eleUrl = element.select("h2").select("a").attr("abs:href");
                    Document doc = Jsoup.connect(eleUrl).timeout(10000).get();
                    mv.mvUrl = doc.select("div.content").select("article").select("video").attr("src");
                    mv.time = element.select("footer").select("time").text();
                    mvList.add(mv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Object[]{mvList, isLoadMore};
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object[] objects) {
            mVideoView.hideProgress();
            List<MV> mvList = (List<MV>) objects[0];
            boolean isLoadMore = (boolean) objects[1];
            if (mvList != null) {
                mVideoView.onRequestData(mvList, isLoadMore);
                mACache.put(url, (Serializable) mvList);
            }
            super.onPostExecute(objects);
        }
    }
}
