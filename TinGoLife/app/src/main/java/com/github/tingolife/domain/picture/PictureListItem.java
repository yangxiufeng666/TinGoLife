package com.github.tingolife.domain.picture;

import java.util.List;

/**
 * Created with com.github.tingolife.domain.picture
 * User:YangXiuFeng
 * Date:2016/3/19
 * Time:16:19
 */
public class PictureListItem {

    /**
     * total : 690
     * status : true
     * tngou : [{"id":704,"title":"气质逼人的美女性感长腿好美","time":1458306701000,"count":87,"galleryclass":3,"img":"/ext/160318/7c51e5e48c705e737cb90cc0e47f134c.jpg","fcount":0,"rcount":0,"size":60},{"id":703,"title":"修长的丝袜美腿","time":1458306237000,"count":101,"galleryclass":1,"img":"/ext/160318/3115aed4b35439691acb30475502043c.jpg","fcount":0,"rcount":0,"size":10},{"id":702,"title":"诱惑人心的超短裙美女苗条身材性感长腿","time":1458305868000,"count":40,"galleryclass":4,"img":"/ext/160318/961040f6a28e0b5531f443acd8debc15.jpg","fcount":0,"rcount":0,"size":10},{"id":701,"title":"漂亮女神那极品黑丝长腿太美了","time":1458305823000,"count":34,"galleryclass":3,"img":"/ext/160318/555718425bff0a8d14fa1bd43dd555cc.jpg","fcount":0,"rcount":0,"size":12},{"id":700,"title":"苗条身材美女性感花纹黑丝长腿魔鬼身材","time":1458305579000,"count":37,"galleryclass":1,"img":"/ext/160318/bbdcabe782e108b7bb2034595cebc91f.jpg","fcount":0,"rcount":0,"size":14},{"id":699,"title":"太诱人了美女齐逼紧身包臀短裙","time":1458305262000,"count":23,"galleryclass":3,"img":"/ext/160318/f75dbfdaf12f034446b5d3829816d9b9.jpg","fcount":0,"rcount":0,"size":10},{"id":698,"title":"女模美女性感超薄肉丝长腿学生妹制服","time":1458305098000,"count":24,"galleryclass":3,"img":"/ext/160318/6cfd7f20db5ed8b6ec2c87cd509032c2.jpg","fcount":0,"rcount":0,"size":11},{"id":697,"title":"肉感身材美貌性感短裙黑丝长腿","time":1458305051000,"count":13,"galleryclass":3,"img":"/ext/160318/1a65d93ff47e21209362844c203caf14.jpg","fcount":0,"rcount":0,"size":6},{"id":696,"title":"女秘书性感肉丝腿迷人深情的身姿","time":1458304934000,"count":13,"galleryclass":3,"img":"/ext/160318/0473ef83b3f55bf33b21e5cf940f5cc0.jpg","fcount":0,"rcount":0,"size":15},{"id":695,"title":"白色天使陈博优美气质好身材","time":1458208139000,"count":120,"galleryclass":4,"img":"/ext/160317/f15072618137c330115b232276f00ef7.jpg","fcount":0,"rcount":0,"size":3}]
     */
    private int total;
    private boolean status;
    private List<TngouEntity> tngou;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTngou(List<TngouEntity> tngou) {
        this.tngou = tngou;
    }

    public int getTotal() {
        return total;
    }

    public boolean isStatus() {
        return status;
    }

    public List<TngouEntity> getTngou() {
        return tngou;
    }

    public static class TngouEntity {
        /**
         * id : 704
         * title : 气质逼人的美女性感长腿好美
         * time : 1458306701000
         * count : 87
         * galleryclass : 3
         * img : /ext/160318/7c51e5e48c705e737cb90cc0e47f134c.jpg
         * fcount : 0
         * rcount : 0
         * size : 60
         */
        private int id;
        private String title;
        private long time;
        private int count;
        private int galleryclass;
        private String img;
        private int fcount;
        private int rcount;
        private int size;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setGalleryclass(int galleryclass) {
            this.galleryclass = galleryclass;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public long getTime() {
            return time;
        }

        public int getCount() {
            return count;
        }

        public int getGalleryclass() {
            return galleryclass;
        }

        public String getImg() {
            return img;
        }

        public int getFcount() {
            return fcount;
        }

        public int getRcount() {
            return rcount;
        }

        public int getSize() {
            return size;
        }
    }
}
