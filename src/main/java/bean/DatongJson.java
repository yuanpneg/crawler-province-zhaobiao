package bean;

import java.util.List;

public class DatongJson {


    /**
     * total : 2208
     * records : 22077
     * rows : [{"noticeSendTime":"2018-09-07 13:40","id":"42841AE430CCA9BA77171EC5329CFB12","title":"大同五中等六所学校外墙砖维修项目招标公告","type":"招标公告","typeMain":"1"},{"noticeSendTime":"2018-09-07 09:00","id":"61D1C949B4B9A3945C453DFC1ECBB508","title":"湖东车辆段旧锅炉房环保设施升级改造工程项目变更公告","type":"招标公告","typeMain":"1"},{"noticeSendTime":"2018-09-07 09:00","id":"BFC22836BF3375CB81C7ED3B2B8D52B5","title":"湖东车辆段新锅炉房环保设施升级改造工程项目变更公告","type":"招标公告","typeMain":"1"},{"noticeSendTime":"2018-09-07 09:00","id":"2A5A4CBBE18CF268B48693E4C06C36F0","title":"柳村站修作业场实施区域集中供热改造工程项目变更公告","type":"招标公告","typeMain":"1"},{"noticeSendTime":"2018-09-07 09:00","id":"AF4B0EE10EB76CB97F35F4E4EEF9489C","title":"湖东车辆段预分解库烟尘治理改造工程项目变更公告","type":"招标公告","typeMain":"1"},{"noticeSendTime":"2018-09-07 09:00","id":"3762631399FB0581AD5439F7022BAB9C","title":"湖东车辆段大同检修车间污水处理设施升级改造工程招标公告","type":"招标公告","typeMain":"1"},{"noticeSendTime":"2018-09-07 09:00","id":"6FDC16CF4B8F5E38AE42351C784EC70A","title":"大同市兴国寺街北延道路建设工程施工招标公告","type":"招标公告","typeMain":"1"},{"noticeSendTime":"2018-09-07 09:00","id":"E506B8CC34096AFAE175DA5F36E08830","title":"大同市永安路、科创路一期、科创路二期道路建设工程设计招标公告","type":"招标公告","typeMain":"1"},{"noticeSendTime":"2018-09-06 14:00","id":"82A882D7EE0E806A3FE725A980144FF0","title":"浑源县蔡村镇中心幼儿园建设项目中标候选人公示表","type":"中标公告","typeMain":"1"},{"noticeSendTime":"2018-09-06 09:00","id":"951D7881B782C0965EC941FC877F560F","title":"阳高县县城污水处理厂提标改造工程招标公告","type":"招标公告","typeMain":"1"}]
     */

    private int total;
    private int records;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * noticeSendTime : 2018-09-07 13:40
         * id : 42841AE430CCA9BA77171EC5329CFB12
         * title : 大同五中等六所学校外墙砖维修项目招标公告
         * type : 招标公告
         * typeMain : 1
         */

        private String noticeSendTime;
        private String id;
        private String title;
        private String type;
        private String typeMain;

        public String getNoticeSendTime() {
            return noticeSendTime;
        }

        public void setNoticeSendTime(String noticeSendTime) {
            this.noticeSendTime = noticeSendTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeMain() {
            return typeMain;
        }

        public void setTypeMain(String typeMain) {
            this.typeMain = typeMain;
        }
    }
}
