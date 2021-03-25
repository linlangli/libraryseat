package io.github.grooters.libraryseat.view.viewinter;

import java.util.List;

import io.github.grooters.libraryseat.model.ChildPost;

/**
 * Create by 李林浪 in 2018/11/12
 * Elegant Code...
 */
public interface ChildPostViewInter {
    public void initPost(List<ChildPost> childPosts);
    public void showResult(String result);
}
