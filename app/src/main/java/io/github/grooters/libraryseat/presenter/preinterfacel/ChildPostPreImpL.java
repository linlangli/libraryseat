package io.github.grooters.libraryseat.presenter.preinterfacel;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import io.github.grooters.libraryseat.api.PostApi;
import io.github.grooters.libraryseat.model.ChildPost;
import io.github.grooters.libraryseat.model.ChildPosts;
import io.github.grooters.libraryseat.model.MainPosts;
import io.github.grooters.libraryseat.presenter.preinterface.ChildPosPretInter;
import io.github.grooters.libraryseat.view.viewinter.ChildPostViewInter;
import io.github.grooters.lller.quote.fastjson.FastJsoNer;
import io.github.grooters.lller.quote.okhttp.OkHttPer;


/**
 * Create by 李林浪 in 2018/11/12
 * Elegant Code...
 */
public class ChildPostPreImpL implements ChildPosPretInter{
    private ChildPosts childPosts;
    private ChildPostViewInter childPostViewInter;
    private static final String TAG="ChildPostPreImpL";

    public ChildPostPreImpL(ChildPostViewInter childPostViewInter) {
        this.childPostViewInter=childPostViewInter;
        childPosts=new ChildPosts();
    }

    @Override
    public void requestPost(int mainPostId) {
        OkHttPer.getOkHeePer().getByAsync(PostApi.REQUEST_CHILD_POST+"?mainPostId="+mainPostId, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                Log.d(TAG,"json:"+json);
                if(json.equals("0")){
                    childPostViewInter.showResult("获取帖子失败");
                    return;
                }
                childPosts = (ChildPosts) FastJsoNer.getFastJsoNer().toObject(json, childPosts);
                childPostViewInter.initPost(childPosts.getChildPosts());
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                childPostViewInter.showResult(errorInfo);
            }
        });
    }

    @Override
    public void replayPost(String number, String content,int mainPostId) {
        OkHttPer.getOkHeePer().getByAsync(PostApi.REPLAY_POST+"?usernumber="+number+"&content="+content+"&superid="+mainPostId, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                childPosts = (ChildPosts) FastJsoNer.getFastJsoNer().toObject(json, childPosts);
                childPostViewInter.initPost(childPosts.getChildPosts());
                childPostViewInter.showResult("回复成功");
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                childPostViewInter.showResult(errorInfo);
            }
        });
    }
}
