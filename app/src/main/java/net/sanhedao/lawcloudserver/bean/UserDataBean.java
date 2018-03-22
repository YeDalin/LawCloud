package net.sanhedao.lawcloudserver.bean;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class UserDataBean {


    /**
     * code : 11300
     * msg : 获取成功
     * data : {"username":"叶","province":"24","city":"276","area":"2668","sex":"0","introduction":"一帆风顺","address":"四川省-泸州市-市辖区"}
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
         * username : 叶
         * province : 24
         * city : 276
         * area : 2668
         * sex : 0
         * introduction : 一帆风顺
         * address : 四川省-泸州市-市辖区
         */

        private String username;
        private String province;
        private String city;
        private String area;
        private String sex;
        private String introduction;
        private String address;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
