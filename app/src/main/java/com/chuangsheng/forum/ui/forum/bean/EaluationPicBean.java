package com.chuangsheng.forum.ui.forum.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/8.
 */

public class EaluationPicBean implements Serializable {
    public int height;
    public int width;
    public int x;
    public int y;
    public int attachmentId;
    public String imageId;
    //        原图
    public String imageUrl;
    //        缩略图
    public String smallImageUrl;

    @Override
    public String toString() {
        return "EaluationPicBean{" +
                "attachmentId=" + attachmentId +
                ", imageId='" + imageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                '}';
    }
}
