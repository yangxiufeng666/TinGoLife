package com.github.tingolife.utils;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created with com.github.tingolife.utils
 * User:YangXiuFeng
 * Date:2016/4/21
 * Time:18:35
 */
public class Util {
    /**
     * <p>系统分享分享功能</p>
     * @param activityTitle
     *		  Activity的名字
     * @param msgTitle
     *		  消息标题
     * @param msgText
     *		  消息内容
     * @param imgPath
     *		  图片路径，不分享图片则传null
     */
    public void shareMsg(String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(Intent.createChooser(intent, activityTitle));
    }
}
