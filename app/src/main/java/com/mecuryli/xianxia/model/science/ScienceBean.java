package com.mecuryli.xianxia.model.science;

import java.io.Serializable;

/**
 * Created by 海飞 on 2016/5/16.
 */
public class ScienceBean implements Serializable {
        private ArticleBean[] result;


        public ArticleBean[] getResult() {
            return result;
        }

        public void setResult(ArticleBean[] result) {
            this.result = result;
        }
}
