package bean;

import java.util.List;

public class NingxiaJson {
    /**
     * RowCount : 18
     * Table : [{"categorynum":"002001001","infoid":"04aedf83-9d33-41f6-9030-a962b1538ca4","title":"[银川市]银川物流港五号路（十号路-燕庆路）道路工程、银川物流港六号路（九号路-友爱中心路）道路工程、银川物流港六号路（十号路-燕庆路）道路工程、银川物流港九号路（...","infodate":"08-09"},{"categorynum":"002001001","infoid":"d303e7b7-2c81-4f6b-80f4-95930214aaac","title":"[银川市]永宁县李俊镇美丽小城镇建设项目施工及监理招标公告[正在报名]","infodate":"08-09"},{"categorynum":"002001001","infoid":"c8bb6783-837c-4e41-bab5-f3a8c57adff1","title":"[银川市]贺兰县2018年全国新增千亿斤粮食生产能力规划田间工程公开招标公告[正在报名]","infodate":"08-08"},{"categorynum":"002001001","infoid":"1baffc70-0f40-401e-abdb-1df2d22b5278","title":"[银川市]阿瓦隆温室园区外网配套供热系统项目锅炉采购及安装招标公告","infodate":"08-08"},{"categorynum":"002001001","infoid":"c12b543f-1f47-4af2-adc6-9ea01186d4be","title":"[银川市]亚洲开发银行贷款宁夏中部节水特色农业示范项目兵沟节水生态农业综合示范区五虎墩节水灌溉示范工程生态防护林、主管道及作业道路工程招标公告[正在报名]","infodate":"08-07"},{"categorynum":"002001001","infoid":"0af9b12a-93c9-415e-9185-0a462c3cc8b7","title":"[银川市]亚洲开发银行贷款宁夏中部节水特色农业示范项目兵沟节水生态农业综合示范区外围生态防护林工程（标段-2）招标公告[正在报名]","infodate":"08-07"},{"categorynum":"002001001","infoid":"38d42230-9627-4ec2-8881-66b2fde8cca3","title":"[银川市]亚洲开发银行贷款宁夏中部节水特色农业示范项目兵沟节水生态农业综合示范区智能日光冷棚（大拱棚）工程招标公告[正在报名]","infodate":"08-07"},{"categorynum":"002001001","infoid":"103a3265-7222-4408-b4ab-3d4532965a68","title":"[银川市]永宁县望远民生城项目区道路及给排水工程设计招标公告","infodate":"08-07"},{"categorynum":"002001001","infoid":"5d542916-5f09-4d4a-864e-1a81961558ad","title":"[银川市]西夏区镇北堡特色小镇城市建设规划招标公告[正在报名]","infodate":"08-07"},{"categorynum":"002001001","infoid":"d3166c26-f086-42c1-8eed-8befd26772b5","title":"[银川市]灵武市人民医院迁扩建PPP项目二次设计二次招标公告[重新招标]","infodate":"08-07"},{"categorynum":"002001001","infoid":"2dc11ee8-6253-4449-b808-d96e5ad7f3ed","title":"[银川市]望远工业园区银川洛客办公空间改造装修工程[正在报名]","infodate":"08-07"},{"categorynum":"002001001","infoid":"74daad52-d229-428d-a1ed-0bda8068c7ef","title":"[银川市]西夏区2014-2017年薄弱学校改造项目施工[正在报名]","infodate":"08-06"},{"categorynum":"002001001","infoid":"14ac0ae3-cbfb-4433-af63-4f70e659117a","title":"[银川市]宁夏青少年足球训练基地和体育科技监测中心项目足球馆-变配电室工程招标公告[正在报名]","infodate":"08-06"},{"categorynum":"002001001","infoid":"ebffa522-c022-453e-83ec-a41305127226","title":"[银川市]银川市（沈阳路、宝湖路、怀远路、满城街、凤翔街、万寿路、哈尔滨路、宁朔街）城市地下综合管廊及道路桥梁工程高低压配电柜采购项目高低压配电柜采购项目招标公告","infodate":"08-06"},{"categorynum":"002001001","infoid":"7a652e3c-9206-4af3-911a-ef517e6fa90b","title":"[银川市]西夏区2018年薄弱学校综合用房项目监理招标公告一标段：西夏区华西希望中学综合用房、西夏区华西希望中学教师周转宿舍监理。、二标段：西夏区第八小学综合楼、西...","infodate":"08-06"},{"categorynum":"002001001","infoid":"4e9ee54f-de71-4443-8068-37a0bb70f66c","title":"[银川市]西夏区2018年薄弱学校综合用房项目施工资格预审公告（代招标公告）[正在报名]","infodate":"08-06"},{"categorynum":"002001001","infoid":"c95df1ff-1e83-48da-abf8-6669eb4a31bb","title":"[银川市]永宁县利用以色列政府贷款实施农田水利建设项目国内配套工程招标公告[正在报名]","infodate":"08-06"},{"categorynum":"002001001","infoid":"06ca531a-baaf-4cfd-a2eb-129b6fad8feb","title":"[银川市]国网宁夏检修公司原银川电厂厂房复建施工及监理招标公告[正在报名]","infodate":"08-03"}]
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
         * categorynum : 002001001
         * infoid : 04aedf83-9d33-41f6-9030-a962b1538ca4
         * title : [银川市]银川物流港五号路（十号路-燕庆路）道路工程、银川物流港六号路（九号路-友爱中心路）道路工程、银川物流港六号路（十号路-燕庆路）道路工程、银川物流港九号路（...
         * infodate : 08-09
         */

        private String categorynum;
        private String infoid;
        private String title;
        private String infodate;

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
