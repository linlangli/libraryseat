package io.github.grooters.libraryseat.presenter.preinterface;

import android.content.Context;

import java.util.List;

import io.github.grooters.libraryseat.model.MainPost;

public interface PostPreInter {
    public void requestPost(int id);
    public void replayPost(String number,String content,int mainPostId);
    public void createPost(String number,String title,String content);
}
