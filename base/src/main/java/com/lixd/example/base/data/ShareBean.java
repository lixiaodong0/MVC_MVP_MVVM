package com.lixd.example.base.data;

import java.util.List;

public class ShareBean<T> {
    /**
     * [
     * "iOS",
     * "前端",
     * "Android",
     * "瞎推荐",
     * "拓展资源",
     * "休息视频",
     * "福利"
     * ]
     */
    public List<String> category;
    public boolean error;
    public T results;
}
