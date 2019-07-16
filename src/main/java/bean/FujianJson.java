package bean;

import java.util.List;

public class FujianJson {

    /**
     * data : [{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"福州滨海新城万沙路延伸段（万新路-马漳路）道路工程（监理）-招标公告","M_ID":87672,"PLATFORM_CODE":"12350100MB02709751","PLATFORM_NAME":"福州市公共资源交易服务中心","TM":"2018-08-08T21:49:18","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"市政","PROTYPE":"G02","M_DATA_SOURCE":"0102","M_TM":"2018-08-08T21:55:06","M_PROJECT_TYPE":null,"RN":1},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"葛岭镇溪南村民住宅小区室外配套附属工程-招标公告","M_ID":87670,"PLATFORM_CODE":"12350100MB02709751","PLATFORM_NAME":"福州市公共资源交易服务中心","TM":"2018-08-08T21:04:37","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"房屋建筑","PROTYPE":"G01","M_DATA_SOURCE":"0102","M_TM":"2018-08-08T21:10:05","M_PROJECT_TYPE":null,"RN":2},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"福州市第二社会福利院全院监控系统改造工程","M_ID":87668,"PLATFORM_CODE":"111111122222229998","PLATFORM_NAME":"非交易中心","TM":"2018-08-08T20:28:22","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"房屋建设","PROTYPE":"A01","M_DATA_SOURCE":"9999","M_TM":null,"M_PROJECT_TYPE":"2","RN":3},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"福州滨海新城万沙路延伸段（万新路-马漳路）道路工程（施工）-招标公告","M_ID":87669,"PLATFORM_CODE":"12350100MB02709751","PLATFORM_NAME":"福州市公共资源交易服务中心","TM":"2018-08-08T20:22:43","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"市政","PROTYPE":"G02","M_DATA_SOURCE":"0102","M_TM":"2018-08-08T20:30:06","M_PROJECT_TYPE":null,"RN":4},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"福州市第二社会福利院院民生活区修缮工程","M_ID":87666,"PLATFORM_CODE":"111111122222229998","PLATFORM_NAME":"非交易中心","TM":"2018-08-08T20:10:26","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"房屋建设","PROTYPE":"A01","M_DATA_SOURCE":"9999","M_TM":null,"M_PROJECT_TYPE":"2","RN":5},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"屏山公园改造提升项目（施工）-招标公告","M_ID":87664,"PLATFORM_CODE":"12350100MB02709751","PLATFORM_NAME":"福州市公共资源交易服务中心","TM":"2018-08-08T18:25:15","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"市政","PROTYPE":"G02","M_DATA_SOURCE":"0102","M_TM":"2018-08-08T18:30:05","M_PROJECT_TYPE":null,"RN":6},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"闽侯S308线165K+640-177K+809（原X116线0K+000-12K+431）路段路面和涵洞整治工程1标段施工招标公告","M_ID":87648,"PLATFORM_CODE":"111111122222229998","PLATFORM_NAME":"非交易中心","TM":"2018-08-08T17:51:16","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"公路","PROTYPE":"A03","M_DATA_SOURCE":"9999","M_TM":null,"M_PROJECT_TYPE":"2","RN":7},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"福州大学旗山校区宏晖文体综合馆室外强弱电工程招标公告","M_ID":87629,"PLATFORM_CODE":"111111122222229998","PLATFORM_NAME":"非交易中心","TM":"2018-08-08T17:36:29","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"其他","PROTYPE":"A99","M_DATA_SOURCE":"9999","M_TM":null,"M_PROJECT_TYPE":"2","RN":8},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"长乐区航城街道（泮野村、下朱村、霞洲村）村庄污水管网工程（施工）-招标公告","M_ID":87651,"PLATFORM_CODE":"12350100MB02709751","PLATFORM_NAME":"福州市公共资源交易服务中心","TM":"2018-08-08T17:30:31","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"市政","PROTYPE":"G02","M_DATA_SOURCE":"0102","M_TM":"2018-08-08T17:35:05","M_PROJECT_TYPE":null,"RN":9},{"KIND":"GCJS","TITLE":"招标公告","GGTYPE":"1","NAME":"南台农贸市场升级改造工程(监理)","M_ID":87594,"PLATFORM_CODE":"111111122222229998","PLATFORM_NAME":"非交易中心","TM":"2018-08-08T17:29:22","PROCODE":null,"AREACODE":"350100","AREANAME":"福州市","PROTYPE_TEXT":"房屋建设","PROTYPE":"A01","M_DATA_SOURCE":"9999","M_TM":null,"M_PROJECT_TYPE":"2","RN":10}]
     * total : 1750
     * pageNo : 1
     */

    private int total;
    private int pageNo;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * KIND : GCJS
         * TITLE : 招标公告
         * GGTYPE : 1
         * NAME : 福州滨海新城万沙路延伸段（万新路-马漳路）道路工程（监理）-招标公告
         * M_ID : 87672.0
         * PLATFORM_CODE : 12350100MB02709751
         * PLATFORM_NAME : 福州市公共资源交易服务中心
         * TM : 2018-08-08T21:49:18
         * PROCODE : null
         * AREACODE : 350100
         * AREANAME : 福州市
         * PROTYPE_TEXT : 市政
         * PROTYPE : G02
         * M_DATA_SOURCE : 0102
         * M_TM : 2018-08-08T21:55:06
         * M_PROJECT_TYPE : null
         * RN : 1
         */

        private String KIND;
        private String TITLE;
        private String GGTYPE;
        private String NAME;
        private double M_ID;
        private String PLATFORM_CODE;
        private String PLATFORM_NAME;
        private String TM;
        private Object PROCODE;
        private String AREACODE;
        private String AREANAME;
        private String PROTYPE_TEXT;
        private String PROTYPE;
        private String M_DATA_SOURCE;
        private String M_TM;
        private Object M_PROJECT_TYPE;
        private int RN;

        public String getKIND() {
            return KIND;
        }

        public void setKIND(String KIND) {
            this.KIND = KIND;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getGGTYPE() {
            return GGTYPE;
        }

        public void setGGTYPE(String GGTYPE) {
            this.GGTYPE = GGTYPE;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public double getM_ID() {
            return M_ID;
        }

        public void setM_ID(double M_ID) {
            this.M_ID = M_ID;
        }

        public String getPLATFORM_CODE() {
            return PLATFORM_CODE;
        }

        public void setPLATFORM_CODE(String PLATFORM_CODE) {
            this.PLATFORM_CODE = PLATFORM_CODE;
        }

        public String getPLATFORM_NAME() {
            return PLATFORM_NAME;
        }

        public void setPLATFORM_NAME(String PLATFORM_NAME) {
            this.PLATFORM_NAME = PLATFORM_NAME;
        }

        public String getTM() {
            return TM;
        }

        public void setTM(String TM) {
            this.TM = TM;
        }

        public Object getPROCODE() {
            return PROCODE;
        }

        public void setPROCODE(Object PROCODE) {
            this.PROCODE = PROCODE;
        }

        public String getAREACODE() {
            return AREACODE;
        }

        public void setAREACODE(String AREACODE) {
            this.AREACODE = AREACODE;
        }

        public String getAREANAME() {
            return AREANAME;
        }

        public void setAREANAME(String AREANAME) {
            this.AREANAME = AREANAME;
        }

        public String getPROTYPE_TEXT() {
            return PROTYPE_TEXT;
        }

        public void setPROTYPE_TEXT(String PROTYPE_TEXT) {
            this.PROTYPE_TEXT = PROTYPE_TEXT;
        }

        public String getPROTYPE() {
            return PROTYPE;
        }

        public void setPROTYPE(String PROTYPE) {
            this.PROTYPE = PROTYPE;
        }

        public String getM_DATA_SOURCE() {
            return M_DATA_SOURCE;
        }

        public void setM_DATA_SOURCE(String M_DATA_SOURCE) {
            this.M_DATA_SOURCE = M_DATA_SOURCE;
        }

        public String getM_TM() {
            return M_TM;
        }

        public void setM_TM(String M_TM) {
            this.M_TM = M_TM;
        }

        public Object getM_PROJECT_TYPE() {
            return M_PROJECT_TYPE;
        }

        public void setM_PROJECT_TYPE(Object M_PROJECT_TYPE) {
            this.M_PROJECT_TYPE = M_PROJECT_TYPE;
        }

        public int getRN() {
            return RN;
        }

        public void setRN(int RN) {
            this.RN = RN;
        }
    }
}
