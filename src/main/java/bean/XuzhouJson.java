package bean;

import java.util.List;

public class XuzhouJson {
    /**
     * RowCount : 12
     * Table : [{"rownum":"1","area":"徐州市","xmbh":"E3203010319000830001001","jyfl":"工程","href":"/jyxx/003001/003001001/20190430/175ffd32-9acb-4cea-b38c-a634710250aa.html","title":"徐州市第三十六中学迁建项目教学楼、人防工程及地下停车场","infodate":"2019-04-30"},{"rownum":"2","area":"徐州市","xmbh":"E3203010319002580002001","jyfl":"工程","href":"/jyxx/003001/003001001/20190430/f069138e-772f-449b-a351-d2f93decac34.html","title":"徐州市泉山区海峡东南郡北侧路（姚庄路）市政工程监理","infodate":"2019-04-30"},{"rownum":"3","area":"徐州市","xmbh":"E3203010319002496002002","jyfl":"工程","href":"/jyxx/003001/003001001/20190430/439e4316-bdc5-4a29-be2e-52934561acb6.html","title":"徐州经济技术开发区东三环路以西片区棚户区（城中村）改造一期工程监理（二标段）","infodate":"2019-04-30"},{"rownum":"4","area":"徐州市","xmbh":"E3203010319002496002001","jyfl":"工程","href":"/jyxx/003001/003001001/20190430/376c75dd-d9fc-4da7-a07b-87e60788332d.html","title":"徐州经济技术开发区东三环路以西片区棚户区（城中村）改造一期工程监理（一标段）","infodate":"2019-04-30"},{"rownum":"5","area":"徐州市","xmbh":"E3203010319001052002001","jyfl":"工程","href":"/jyxx/003001/003001001/20190430/edc109dc-5720-4c22-ba0c-dcdb9a1cc6c2.html","title":"李集镇姚庄农民集中住房区项目李集镇姚庄农民集中居住区项目（二期）","infodate":"2019-04-30"},{"rownum":"6","area":"徐州市","xmbh":"E3203010319002604001001","jyfl":"工程","href":"/jyxx/003001/003001001/20190430/b4769324-732e-40de-bfd0-8f59877d1d4a.html","title":"徐州利国医院病房楼项目","infodate":"2019-04-30"},{"rownum":"7","area":"徐州市","xmbh":"E3203010319000830001002","jyfl":"工程","href":"/jyxx/003001/003001001/20190430/adbbb89c-e555-4331-a455-c24d955a1907.html","title":"徐州市第三十六中学迁建项目及地下停车场项目监理","infodate":"2019-04-30"},{"rownum":"8","area":"徐州市","xmbh":"E3203010319002599001001","jyfl":"工程","href":"/jyxx/003001/003001001/20190430/72548363-1e57-4acf-9133-22ca9ad0da2b.html","title":"变电北苑棚户区改造工程新沂市变电北苑棚户区改造工程监理","infodate":"2019-04-30"},{"rownum":"9","area":"徐州市","xmbh":"E3203010319002600001001","jyfl":"工程","href":"/jyxx/003001/003001001/20190429/fb1cf392-65e3-42ea-b08e-78b1a3b20a45.html","title":"新沂市高铁花园棚户区改造项目监理","infodate":"2019-04-29"},{"rownum":"10","area":"徐州市","xmbh":"E3203010319002545002001","jyfl":"工程","href":"/jyxx/003001/003001001/20190429/7b71c493-3e9f-4a66-98fb-cbe655a13778.html","title":"邳州经济开发区实验小学项目监理","infodate":"2019-04-29"},{"rownum":"11","area":"徐州市","xmbh":"3203811508260102-BE-001","jyfl":"工程","href":"/jyxx/003001/003001001/20190429/4468f8c9-02a7-4442-b722-d2682ed21bb8.html","title":"江苏省新沂中等专业学校二期工程江苏省新沂中等专业学校二期运动场工程监理","infodate":"2019-04-29"},{"rownum":"12","area":"新沂市","xmbh":"E3203010319002597001001","jyfl":"工程","href":"/jyxx/003001/003001001/20190429/9b1f4755-2e17-4491-a87e-b8a495c987ed.html","title":"新沂市棋盘镇农产品航空配送中心（1#车间、2#车间）钢结构厂房主体工程【重新公告】","infodate":"2019-04-29"}]
     */

    private int RowCount;
    private List<TableBean> Table;

    public int getRowCount() {
        return RowCount;
    }

    public void setRowCount(int RowCount) {
        this.RowCount = RowCount;
    }

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * rownum : 1
         * area : 徐州市
         * xmbh : E3203010319000830001001
         * jyfl : 工程
         * href : /jyxx/003001/003001001/20190430/175ffd32-9acb-4cea-b38c-a634710250aa.html
         * title : 徐州市第三十六中学迁建项目教学楼、人防工程及地下停车场
         * infodate : 2019-04-30
         */

        private String rownum;
        private String area;
        private String xmbh;
        private String jyfl;
        private String href;
        private String title;
        private String infodate;

        public String getRownum() {
            return rownum;
        }

        public void setRownum(String rownum) {
            this.rownum = rownum;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getXmbh() {
            return xmbh;
        }

        public void setXmbh(String xmbh) {
            this.xmbh = xmbh;
        }

        public String getJyfl() {
            return jyfl;
        }

        public void setJyfl(String jyfl) {
            this.jyfl = jyfl;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfodate() {
            return infodate;
        }

        public void setInfodate(String infodate) {
            this.infodate = infodate;
        }
    }




}
