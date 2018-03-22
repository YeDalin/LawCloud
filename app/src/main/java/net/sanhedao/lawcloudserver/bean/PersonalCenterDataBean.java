package net.sanhedao.lawcloudserver.bean;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class PersonalCenterDataBean {

    private int code;
    private String msg;
    private PersonalData data;


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

    public PersonalData getData() {
        return data;
    }

    public void setData(PersonalData data) {
        this.data = data;
    }

    public class PersonalData{
        private String name;
        private String face;
        private String wallet;
        private String coin;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getMoney() {
            return wallet;
        }

        public void setMoney(String money) {
            this.wallet = money;
        }

        public String getLawyerspot() {
            return coin;
        }

        public void setLawyerspot(String lawyerspot) {
            this.coin = lawyerspot;
        }
    }

}
