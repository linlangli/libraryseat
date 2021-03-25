package io.github.grooters.libraryseat.view.viewinter;

import java.util.List;

import io.github.grooters.libraryseat.model.MainPost;

public interface PostViewInter {
    public void showResult(String result);
    public void initPost(List<MainPost> mainPosts);
}
