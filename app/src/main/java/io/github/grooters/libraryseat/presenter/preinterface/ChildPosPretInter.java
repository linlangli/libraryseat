package io.github.grooters.libraryseat.presenter.preinterface;

import android.content.Context;

import java.util.List;

import io.github.grooters.libraryseat.model.ChildPost;

/**
 * Create by 李林浪 in 2018/11/12
 * Elegant Code...
 */
public interface ChildPosPretInter {
    public void requestPost(int mainPostId);
    public void replayPost(String number,String content,int mainPostId);
}
