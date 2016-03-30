package com.github.tingolife.constant;

/**
 * Created with com.github.tingolife.constant
 * User:YangXiuFeng
 * Date:2016/3/19
 * Time:15:57
 */
public class TinGoApi {
    public static final String PIC_CLASSIFY_API = "http://www.tngou.net/tnfs/api/classify";//取得图片分类，可以通过分类id取得热词列表
    /**
     * page	否	int	请求页数，默认page=1
     rows	否	int	每页返回的条数，默认rows=20
     id	否	int	分类ID，默认返回的是全部。这里的ID就是指分类的ID
     */
    public static final String PIC_LIST_API = "http://www.tngou.net/tnfs/api/list";//取得图片列表，也可以用分类id作为参数
    /*通过http://tnfs.tngou.net/image/+列表获取的img(如：/ext/160318/3115aed4b35439691acb30475502043c.jpg)，如要尺寸拼接到后面(如：_180x120)*/
    public static final String PIC_SHOW = "http://tnfs.tngou.net/image";//取得图片列表，也可以用分类id作为参数
    /**最新图片
     * rows	否	int	返回最新关键词的条数，默认rows=20
     classify	否	int	分类ID，取得该分类下的最新数据
     id	是	long	当前最新的图库关键词id
     * **/
    public static final String PIC_LATEST = "http://www.tngou.net/tnfs/api/news";
    /**
     * 通过图片列表的图片ID获取图片详情：id	是	long	图库的id
     */
    public static final String PIC_DETAIL = "http://www.tngou.net/tnfs/api/show";
}
