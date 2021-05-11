package com.mine.wifi;

public class LoaBean {

    /**
     * msg : 获取成功
     * data : {"id":2184,"x":"10","y":"10"}
     */

    private String msg;
    private DataBean data;

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
         * id : 2184
         * x : 10
         * y : 10
         */

        private int id;
        private String x;
        private String y;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }
}
