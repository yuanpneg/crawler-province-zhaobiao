package bean;

import java.util.List;

public class JiangxiJson {

    /**
     * RowCount : 22
     * Table : [{"categorynum":"002002002","infoid":"7d7b2f58-0a5c-416a-af80-46ca91662912","postdate":"2018-08-03","title":"[新建县]流湖镇桥头魏家至荣坊魏家公路重建工程招标公告"},{"categorynum":"002002002","infoid":"2a479b75-51eb-45f9-9861-aa79c11b7bcd","postdate":"2018-07-30","title":"[南昌县]富山街-清湖（由富山街-赣东堤3公里维修、三干桥-杨家1公里重建组成）工程招标公告"},{"categorynum":"002002002","infoid":"79fdefb6-6e8e-413b-828f-53c61d405548","postdate":"2018-07-18","title":"[南昌市本级]S416乔乐至西山段公路改建工程(K0+000～K11+335.5)施工招标公告"},{"categorynum":"002002002","infoid":"25794bb6-9787-4957-ab24-b1792c231e38","postdate":"2018-07-18","title":"[安义县]安义县果田至赤岗（果田至石鼻段）农村道路改造工程（设计、施工）EPC总承包项目招标公告"},{"categorynum":"002002002","infoid":"1ea164d2-8567-42fd-8c14-ff91d8c32f1e","postdate":"2018-07-18","title":"[南昌县]南昌县冈上镇冈上至曲湖公路重建工程招标公告"},{"categorynum":"002002002","infoid":"ab3b4cf8-4e1d-405a-8599-de40e9fcf8d1","postdate":"2018-07-17","title":"[南昌县]南昌县冈上镇赣东大堤至晋安公路改造工程、南昌县冈上镇杨市线至石湖公路路面维修工程招标公告"},{"categorynum":"002002002","infoid":"5ea1aff8-646a-4aa1-9cc0-0e1f5d5db5c3","postdate":"2018-06-08","title":"[南昌县]广福镇港湾桥重建工程招标公告"},{"categorynum":"002002002","infoid":"2fbbd766-1ff3-4ba4-b1b9-733b7c068001","postdate":"2018-05-18","title":"[湾里区]罗亭工业园松山路至李家村路桥工程招标公告"},{"categorynum":"002002002","infoid":"ecaa5d0c-f4f6-4caf-8233-f52efeb9c6f5","postdate":"2018-03-30","title":"[湾里区]太平垦殖场2015年垦区公路建设项目招标公告"},{"categorynum":"002002002","infoid":"9f804019-cff9-498b-857a-505ff87edac0","postdate":"2018-03-26","title":"[南昌县]黄马乡（白城-岭前）路面维修工程、黄马乡蓝园大道(广梁线)-上洛路面维修工程招标公告"},{"categorynum":"002002002","infoid":"a4ca6aea-98f6-418b-8cbb-1280c9177898","postdate":"2018-03-08","title":"[进贤县]进贤县2018年县道升级改造工程勘察设计招标公告"},{"categorynum":"002002002","infoid":"974a351a-4d7c-4b6d-8e25-4c9a4edc4957","postdate":"2018-02-13","title":"[湾里区]罗亭至梅岭公路升级改造工程施工招标公告"},{"categorynum":"002002002","infoid":"4f292e42-a6b0-4940-8f76-adba6a5b868d","postdate":"2018-02-11","title":"[南昌县]南昌县幽兰镇（2017年度）主干公路修建工程招标公告"},{"categorynum":"002002002","infoid":"c79f3fe4-6f89-43ef-9237-afa38dfa2d18","postdate":"2018-02-06","title":"[新建县]新建区乐化镇港田桥、幸福桥、泉岗桥危桥改造工程招标公告"},{"categorynum":"002002002","infoid":"df0fec14-86f9-462f-8141-18c14cb84f7b","postdate":"2017-12-05","title":"[湾里区]湾里区太平镇太平村旅游基础设施项目施工招标公告"},{"categorynum":"002002002","infoid":"809ca785-8736-4f61-b1f9-e64e0e337bc9","postdate":"2017-11-24","title":"[新建区]樵舍镇常丰村竹山桥危桥重建工程施工招标公告"},{"categorynum":"002002002","infoid":"6beac908-5029-4502-83fe-8f3402a93b75","postdate":"2017-11-24","title":"[新建区]樵舍镇建新村高堂桥危桥改建工程施工招标公告"},{"categorynum":"002002002","infoid":"6582e114-b6e8-42f1-bf31-11c0e3cff1b3","postdate":"2017-11-24","title":"[新建区]樵舍镇波汾村王家桥危桥改建工程施工招标公告"},{"categorynum":"002002002","infoid":"391d959c-3664-473a-b8f2-f45ed4c7ca54","postdate":"2017-11-22","title":"[南昌县]南昌县冈上镇105国道至黄台村公路改造工程"},{"categorynum":"002002002","infoid":"57cb0e02-5cea-4bc0-a1d3-705b0e4c7e70","postdate":"2017-11-15","title":"[南昌县]塔城乡湾里-庄山公路（2017年计划）维修工程"},{"categorynum":"002002002","infoid":"99d5b6cc-f3ce-485b-8b65-c3ff886085a8","postdate":"2017-11-14","title":"[南昌市本级]南昌市S523方家至凰岭亭（X075进里线）大中修工程(K3+310～K7+297.44)施工招标公告"},{"categorynum":"002002002","infoid":"cf4b8819-459a-441f-be6c-97f5bce9c47a","postdate":"2017-11-10","title":"[南昌市本级]S101（原X053）乐饭线新乙桥危桥重建工程施工招标公告"}]
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
         * categorynum : 002002002
         * infoid : 7d7b2f58-0a5c-416a-af80-46ca91662912
         * postdate : 2018-08-03
         * title : [新建县]流湖镇桥头魏家至荣坊魏家公路重建工程招标公告
         */

        private String categorynum;
        private String infoid;
        private String postdate;
        private String title;

        public String getCategorynum() {
            return categorynum;
        }

        public void setCategorynum(String categorynum) {
            this.categorynum = categorynum;
        }

        public String getInfoid() {
            return infoid;
        }

        public void setInfoid(String infoid) {
            this.infoid = infoid;
        }

        public String getPostdate() {
            return postdate;
        }

        public void setPostdate(String postdate) {
            this.postdate = postdate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
