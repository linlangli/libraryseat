package io.github.grooters.libraryseat.presenter.preinterfacel;

import android.util.Log;
import java.io.IOException;
import io.github.grooters.libraryseat.api.PostApi;
import io.github.grooters.libraryseat.model.MainPosts;
import io.github.grooters.libraryseat.presenter.preinterface.PostPreInter;
import io.github.grooters.libraryseat.view.viewinter.PostViewInter;
import io.github.grooters.lller.quote.fastjson.FastJsoNer;
import io.github.grooters.lller.quote.okhttp.OkHttPer;

public class PostPreImpL implements PostPreInter {

    private MainPosts mainPosts;
    private PostViewInter postViewInter;
    private static final String TAG=PostPreImpL.class.getSimpleName();

    public PostPreImpL(PostViewInter postViewInter) {
        this.postViewInter=postViewInter;
    }
    @Override
    public void requestPost(int id) {
        mainPosts=new MainPosts();
        Log.i(TAG,"url："+PostApi.REQUEST_MAIN_POST+"?id="+id);
        OkHttPer.getOkHeePer().getByAsync(PostApi.REQUEST_MAIN_POST+"?id="+id, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                Log.i(TAG,"json："+json);
                if(json.equals("0")){
                    postViewInter.showResult("不存在任何帖子");
                }
                mainPosts = (MainPosts) FastJsoNer.getFastJsoNer().toObject(json, mainPosts);
                postViewInter.initPost(mainPosts.getMainPosts());
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                Log.i(TAG,"errorInfo："+errorInfo);
                postViewInter.showResult(errorInfo);
            }
        });
    }

    @Override
    public void createPost(String number, String title, String content) {
        Log.d(TAG,"url:"+PostApi.CREATE_POST+"?usernumber="+number+"&title="+title+"&content="+content);
        OkHttPer.getOkHeePer().getByAsync(PostApi.CREATE_POST+"?usernumber="+number+"&title="+title+"&content="+content, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                Log.i(TAG,"json："+json);
                requestPost(1);
                postViewInter.showResult("发帖成功");
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                Log.i(TAG,"errorInfo："+errorInfo);
                postViewInter.showResult(errorInfo);
            }
        });
    }

    @Override
    public void replayPost(String number, String content,int mainPostId) {
        OkHttPer.getOkHeePer().getByAsync(PostApi.REPLAY_POST+"?usernumber="+number+"&content="+content+"&superid="+mainPostId, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                Log.i(TAG,"json："+json);
                postViewInter.showResult("回复成功");
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                Log.i(TAG,"errorInfo："+errorInfo);
                postViewInter.showResult(errorInfo);
            }
        });
    }
}
