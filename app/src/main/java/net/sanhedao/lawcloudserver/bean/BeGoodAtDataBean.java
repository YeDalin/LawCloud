package net.sanhedao.lawcloudserver.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class BeGoodAtDataBean {

    private int code;
    private String msg;
    private List<BeGoodAtData> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BeGoodAtData> getData() {
        return data;
    }

    public void setData(List<BeGoodAtData> data) {
        this.data = data;
    }

    public class BeGoodAtData{
        private String id;
        private String name;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
