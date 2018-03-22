package net.sanhedao.lawcloudserver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class OrderListDataBean implements Serializable{


    /**
     * code : 12300
     * msg : 获取成功
     * data : [{"uid":"114","ordernumber":"D923F536-9C25-3410-62C4","describe":"哈哈哈哈回家啊啊聚聚啊啊哈哈就哈哈哈啊哈哈啥回答富爸爸解答解答检查不洗第几集对吧超级低等级唱吧唱吧","createtime":"18-03-13","nickname":"小鑫","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"1","goodstype":"64"},{"uid":"112","ordernumber":"6F161209-DAC5-7BEA-BFE8","describe":"ink女X5","createtime":"18-03-13","nickname":"LV3170","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"66"},{"uid":"112","ordernumber":"C3082E28-21A0-A22C-8545","describe":"ink女X5","createtime":"18-03-13","nickname":"LV3170","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"66"},{"uid":"112","ordernumber":"024614E1-219F-69A0-2575","describe":"ink女X5","createtime":"18-03-13","nickname":"LV3170","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"66"},{"uid":"112","ordernumber":"C455A4CF-6B15-D614-8546","describe":"ink女X5","createtime":"18-03-13","nickname":"LV3170","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"66"},{"uid":"112","ordernumber":"1E5FBAC7-09E0-A414-934F","describe":"ink女X5","createtime":"18-03-13","nickname":"LV3170","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"66"},{"uid":"112","ordernumber":"C0DCED38-E085-6493-BED6","describe":"ink女X5","createtime":"18-03-13","nickname":"LV3170","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"66"},{"uid":"112","ordernumber":"9F10E5A9-7F00-34BC-828C","describe":"ink女X5","createtime":"18-03-13","nickname":"LV3170","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"66"},{"uid":"114","ordernumber":"92659E72-46C6-EEF7-48BD","describe":"一样一样一样","createtime":"18-03-13","nickname":"小鑫","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"59"},{"uid":"114","ordernumber":"5073FC19-A3BE-3782-31F9","describe":"才几点就放假经常大结局吃才发货火锅刚刚粗呵呵","createtime":"18-03-13","nickname":"小鑫","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"59"},{"uid":"114","ordernumber":"37E71B02-DF6E-0BF5-EF92","describe":"才几点就放假经常大结局吃才发货火锅刚刚粗呵呵","createtime":"18-03-13","nickname":"小鑫","headportrait":"http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png","urgent":"0","goodstype":"59"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * uid : 114
         * ordernumber : D923F536-9C25-3410-62C4
         * describe : 哈哈哈哈回家啊啊聚聚啊啊哈哈就哈哈哈啊哈哈啥回答富爸爸解答解答检查不洗第几集对吧超级低等级唱吧唱吧
         * createtime : 18-03-13
         * nickname : 小鑫
         * headportrait : http://fwy1.sanhedao.com.cn/Upload/face/201801/5a5c278620b0d.png
         * urgent : 1
         * goodstype : 64
         */

        private String uid;
        private String ordernumber;
        private String describe;
        private String createtime;
        private String nickname;
        private String headportrait;
        private String urgent;
        private String goodstype;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

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

        public String getUrgent() {
            return urgent;
        }

        public void setUrgent(String urgent) {
            this.urgent = urgent;
        }

        public String getGoodstype() {
            return goodstype;
        }

        public void setGoodstype(String goodstype) {
            this.goodstype = goodstype;
        }
    }
}
