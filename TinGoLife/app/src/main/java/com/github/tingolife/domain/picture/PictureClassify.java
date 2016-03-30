package com.github.tingolife.domain.picture;

import java.util.List;

/**
 * Created with com.github.tingolife.domain.picture
 * User:YangXiuFeng
 * Date:2016/3/19
 * Time:16:14
 */
public class PictureClassify {

    /**
     * status : true
     * tngou : [{"id":1,"title":"性感美女","keywords":"性感美女","description":"性感美女","name":"性感美女","seq":1},{"id":2,"title":"韩日美女","keywords":"韩日美女","description":"韩日美女","name":"韩日美女","seq":2},{"id":3,"title":"丝袜美腿","keywords":"丝袜美腿","description":"丝袜美腿","name":"丝袜美腿","seq":3},{"id":4,"title":"美女照片","keywords":"美女照片","description":"美女照片","name":"美女照片","seq":4},{"id":5,"title":"美女写真","keywords":"美女写真","description":"美女写真","name":"美女写真","seq":5},{"id":6,"title":"清纯美女","keywords":"清纯美女","description":"清纯美女","name":"清纯美女","seq":6},{"id":7,"title":"性感车模","keywords":"性感车模","description":"性感车模","name":"性感车模","seq":7}]
     */
    private boolean status;
    private List<TngouEntity> tngou;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTngou(List<TngouEntity> tngou) {
        this.tngou = tngou;
    }

    public boolean isStatus() {
        return status;
    }

    public List<TngouEntity> getTngou() {
        return tngou;
    }

    public static class TngouEntity {
        /**
         * id : 1
         * title : 性感美女
         * keywords : 性感美女
         * description : 性感美女
         * name : 性感美女
         * seq : 1
         */
        private int id;
        private String title;
        private String keywords;
        private String description;
        private String name;
        private int seq;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getKeywords() {
            return keywords;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public int getSeq() {
            return seq;
        }
    }
}
