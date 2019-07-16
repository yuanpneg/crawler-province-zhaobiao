package bean;

import java.util.List;

public class LoudiJson {


    /**
     * code : 0
     * count : 566
     * data : [{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313020001000222&type=2&pid=1#title_2\" title=\"娄星区水洞底镇新禾中学综合楼新建工程\" target=\"_blank\">娄星区水洞底镇新禾中学综合楼新建工程<\/a>","ProjectType":"<span style=\"color: #e70012;\">[房屋建筑(公开招标)]<\/span>","PublishTime":"2019-03-26"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313820001000206&type=4&pid=1#title_4\" title=\"涟源市湄江镇2018年托林至深坑窄路加宽等11个交通基础设施项目（第二次）\" target=\"_blank\">涟源市湄江镇2018年托林至深坑窄路加宽等11个交通基础设施项目（第二次）中标候选人公示<\/a>","ProjectType":"<span style=\"color: #e70012;\">[公路(公开招标)]<\/span>","PublishTime":"2019-03-26"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313010001000208&type=4&pid=1#title_4\" title=\"娄底市中心城区路网完善PPP项目工程华星路勘察、设计\" target=\"_blank\">娄底市中心城区路网完善PPP项目工程华星路勘察、设计招标中标候选人公示<\/a>","ProjectType":"<span style=\"color: #e70012;\">[园林绿化(公开招标)]<\/span>","PublishTime":"2019-03-25"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313820001000206&type=4&pid=1#title_4\" title=\"涟源市湄江镇2018年托林至深坑窄路加宽等11个交通基础设施项目（第二次）\" target=\"_blank\">涟源市湄江镇2018年托林至深坑窄路加宽等11个交通基础设施项目（第二次）中标候选人公示<\/a>","ProjectType":"<span style=\"color: #e70012;\">[公路(公开招标)]<\/span>","PublishTime":"2019-03-25"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313820001000210&type=4&pid=1#title_4\" title=\"涟源市2018年度公路安全保障工程\" target=\"_blank\">涟源市2018年度公路安全保障工程中标候选人公示<\/a>","ProjectType":"<span style=\"color: #e70012;\">[公路(公开招标)]<\/span>","PublishTime":"2019-03-25"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313210001000216&type=3&pid=1#title_3\" title=\"双峰县农村基础设施扶贫项目（一期）Y192女寺至白玉堂公路（旅游资源产业路）改建工程\" target=\"_blank\">双峰县农村基础设施扶贫项目（一期）Y192女寺至白玉堂公路（旅游资源产业路）改建工程项目招标补充（更正）文件（一）<\/a>","ProjectType":"<span style=\"color: #e70012;\">[公路(公开招标)]<\/span>","PublishTime":"2019-03-25"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313010001000209&type=3&pid=1#title_3\" title=\"秋浦街（娄涟公路-东二环线）道路新建工程项目设计招标（第二次）\" target=\"_blank\">秋浦街（娄涟公路-东二环线）道路新建工程项目设计（第二次）流标公告<\/a>","ProjectType":"<span style=\"color: #e70012;\">[市政(公开招标)]<\/span>","PublishTime":"2019-03-22"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313020001000218&type=2&pid=1#title_2\" title=\"湖南省孙水流域系统治理工程（娄星区段）\" target=\"_blank\">湖南省孙水流域系统治理工程（娄星区段）<\/a>","ProjectType":"<span style=\"color: #e70012;\">[水利(公开招标)]<\/span>","PublishTime":"2019-03-22"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313210001000220&type=2&pid=1#title_2\" title=\"双峰县疾病预防控制中心建设工程项目施工\" target=\"_blank\">双峰县疾病预防控制中心建设工程项目施工<\/a>","ProjectType":"<span style=\"color: #e70012;\">[房屋建筑(公开招标)]<\/span>","PublishTime":"2019-03-22"},{"NoticeTitle":"<a href=\"/Trading/main/ViewsPage.aspx?code=A4313820001000207&type=4&pid=1#title_4\" title=\"涟源市荷塘镇中心学校义务教育学校建设项目\" target=\"_blank\">中标候选人公示<\/a>","ProjectType":"<span style=\"color: #e70012;\">[房屋建筑(公开招标)]<\/span>","PublishTime":"2019-03-21"}]
     */

    private String code;
    private int count;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * NoticeTitle : <a href="/Trading/main/ViewsPage.aspx?code=A4313020001000222&type=2&pid=1#title_2" title="娄星区水洞底镇新禾中学综合楼新建工程" target="_blank">娄星区水洞底镇新禾中学综合楼新建工程</a>
         * ProjectType : <span style="color: #e70012;">[房屋建筑(公开招标)]</span>
         * PublishTime : 2019-03-26
         */

        private String NoticeTitle;
        private String ProjectType;
        private String PublishTime;

        public String getNoticeTitle() {
            return NoticeTitle;
        }

        public void setNoticeTitle(String NoticeTitle) {
            this.NoticeTitle = NoticeTitle;
        }

        public String getProjectType() {
            return ProjectType;
        }

        public void setProjectType(String ProjectType) {
            this.ProjectType = ProjectType;
        }

        public String getPublishTime() {
            return PublishTime;
        }

        public void setPublishTime(String PublishTime) {
            this.PublishTime = PublishTime;
        }
    }
}
