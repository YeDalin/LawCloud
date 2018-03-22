package net.sanhedao.lawcloudserver.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class HomePageDataBean {


    /**
     * code : 12300
     * msg : 获取成功
     * data : {"wallet":"140","time":0,"number":"1","myorder":[{"ordernumber":"F992E9A6-0B36-258C-7C6E","describe":"吞吞吐吐天天","createtime":"2018-03-10","nickname":"社会你鑫姐","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * wallet : 140
         * time : 0
         * number : 1
         * myorder : [{"ordernumber":"F992E9A6-0B36-258C-7C6E","describe":"吞吞吐吐天天","createtime":"2018-03-10","nickname":"社会你鑫姐","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png"}]
         */

        private String wallet;
        private int time;
        private String number;
        private List<MyorderBean> myorder;

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public List<MyorderBean> getMyorder() {
            return myorder;
        }

        public void setMyorder(List<MyorderBean> myorder) {
            this.myorder = myorder;
        }

        public static class MyorderBean {
            /**
             * ordernumber : F992E9A6-0B36-258C-7C6E
             * describe : 吞吞吐吐天天
             * createtime : 2018-03-10
             * nickname : 社会你鑫姐
             * headportrait : http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png
             */

            private String ordernumber;
            private String describe;
            private String createtime;
            private String nickname;
            private String headportrait;

            public String getOrdernumber() {
                return ordernumber;
            }

            public void setOrdernumber(String ordernumber) {
                this.ordernumber = ordernumber;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHeadportrait() {
                return headportrait;
            }

            public void setHeadportrait(String headportrait) {
                this.headportrait = headportrait;
            }
        }
    }
}
