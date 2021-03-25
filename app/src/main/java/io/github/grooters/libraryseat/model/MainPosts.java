package io.github.grooters.libraryseat.model;

import java.util.List;

/**
 * Create by 李林浪 in 2018/11/11
 * Elegant Code...
 */
public class MainPosts {
    private List<MainPost> mainPosts;

    public MainPosts(List<MainPost> mainPosts) {
        this.mainPosts = mainPosts;
    }

    public MainPosts() {
    }

    public List<MainPost> getMainPosts() {
        return mainPosts;
    }

    public void setMainPosts(List<MainPost> mainPosts) {
        this.mainPosts = mainPosts;
    }
}
