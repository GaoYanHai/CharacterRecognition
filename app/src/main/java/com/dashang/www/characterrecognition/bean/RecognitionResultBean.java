package com.dashang.www.characterrecognition.bean;

/*
 *
 * 识别结果的实体类
 * */

import java.util.List;

public class RecognitionResultBean {

    private long log_id;
    private int words_result_num;
    private List<WorldsBean> words_result;

    @Override
    public String toString() {
        return "RecognitionResultBean{" +
                "log_id=" + log_id +
                ", words_result_num=" + words_result_num +
                ", words_result=" + words_result +
                '}';
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WorldsBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WorldsBean> words_result) {
        this.words_result = words_result;
    }

    public static class WorldsBean {

        private String worlds;

        @Override
        public String toString() {
            return "WorldsBean{" +
                    "worlds='" + worlds + '\'' +
                    '}';
        }

        public String getWorlds() {
            return worlds;
        }

        public void setWorlds(String worlds) {
            this.worlds = worlds;
        }

    }


}
