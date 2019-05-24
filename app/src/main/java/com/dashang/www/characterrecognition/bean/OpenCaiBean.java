package com.dashang.www.characterrecognition.bean;

/*
* 中将彩票的实体类
*
* 	"rows": 5,
	"code": "dlt",
	"info": "免费接口随机延迟3-6分钟，实时接口请访问www.opencai.net查询、购买或续费",
	"data":
			"expect": "2019058",
		"opencode": "03,10,13,14,32+07,11",
		"opentime": "2019-05-22 20:33:20",
		"opentimestamp": 1558528400
* */

import java.util.List;

public class OpenCaiBean {
    private int rows;
    private String code;
    private String info;
    private List<Data> data;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    //彩票信息
    public class Data{
        private String expect;
        private String opencode;
        private String opentime;
        private String opentimestamp;

        public String getExpect() {
            return expect;
        }

        public void setExpect(String expect) {
            this.expect = expect;
        }

        public String getOpencode() {
            return opencode;
        }

        public void setOpencode(String opencode) {
            this.opencode = opencode;
        }

        public String getOpentime() {
            return opentime;
        }

        public void setOpentime(String opentime) {
            this.opentime = opentime;
        }

        public String getOpentimestamp() {
            return opentimestamp;
        }

        public void setOpentimestamp(String opentimestamp) {
            this.opentimestamp = opentimestamp;
        }
    }

}
