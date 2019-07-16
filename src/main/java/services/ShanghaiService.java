package services;

import bean.ShanghaiJson;
import bean.ShanghaicontentJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import utils.CompressUtils;
import utils.GetContentUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 上海
 */
public class ShanghaiService {

    /**
     *使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(ShanghaiService.class);

    /**
     * 获取对像集合
     * @param url
     * @return
     */
    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttp(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                ShanghaiJson shanghaiJson = gson.fromJson(html, ShanghaiJson.class);
                List<ShanghaiJson.ResultBean.ListBean> list = shanghaiJson.getResult().getList();
                for (ShanghaiJson.ResultBean.ListBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getTime();
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://222.66.64.149/publicity/constructionBulletin/findBulletinList/" + bean.getTenderProjectCode();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = bean.getProjectName();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "contents");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress("上海");
                        tender.setFrom("上海公共资源平台");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        //正文链接
                        tender.setFromurl(href);
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("上海");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }


    /**
     * 获取正文 class
     *
     * @param url
     * @return
     */
    public static void getCont(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                ShanghaicontentJson json = gson.fromJson(html, ShanghaicontentJson.class);
                ShanghaicontentJson shanghaicontentJson = new ShanghaicontentJson();
                shanghaicontentJson.setResult(json.getResult());
                String content = "<table class=\"\"><colgroup><col><col><col><col><col><col></colgroup><thead class=\"ant-table-thead\"><tr><th class=\"\"><span></span></th>" +
                        "</tr></thead><tbody class=\"ant-table-tbody\"><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\">" +
                        "<span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>招标编号</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getTenderProjectCode()) +
                        "</td><td class=\"\">标段号</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getSectionCode()) + "</td><td class=\"\">发布时间</td><td class=\"\">" +
                        getObjectValue(shanghaicontentJson.getResult().getNoticeSendTime())+
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\">" +
                        "</span>招标项目名称</td><td class=\"\" colspan=\"3\">" + getObjectValue(shanghaicontentJson.getResult().getProjectName())+ "</td><td class=\"\">建设地点</td><td class=\"\">" +
                        getObjectValue(shanghaicontentJson.getResult().getAddress())+
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\">" +
                        "</span>招标人</td><td class=\"\" colspan=\"3\">" + getObjectValue(shanghaicontentJson.getResult().getTenderName()) + "</td><td class=\"\">招标人地址</td><td class=\"\">" +
                        getObjectValue(shanghaicontentJson.getResult().getTenderAddress()) +
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\">" +
                        "</span>工程总投资</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getBidPrice()) + "</td><td class=\"\">本标段最高投标跟价</td><td class=\"\">" +
                        getObjectValue(shanghaicontentJson.getResult().getControlPrice())+
                        "</td><td class=\"\">施工工期</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getTimeLimit())+
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\">" +
                        "<td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>项目类别</td><td class=\"\" colspan=\"3\">" +
                        getObjectValue(shanghaicontentJson.getResult().getIndustriesType())+
                        "</td><td class=\"\">工程类别</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getTenderContent())+
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\">" +
                        "<td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>项目描述</td><td class=\"\" colspan=\"5\">" +
                        getObjectValue(shanghaicontentJson.getResult().getProjectScale())+
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\">" +
                        "</span具体描述</td><td class=\"\" colspan=\"5\">" + getObjectValue(shanghaicontentJson.getResult().getDescription()) +
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\">" +
                        "<td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>招标代理机构名称</td><td class=\"\" colspan=\"3\">--</td><td class=\"\">获取招标文件联系人</td>" +
                        "<td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getContactPerson())+
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\" rowspan=\"2\">" +
                        "<span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>获取招标文件时间</td><td class=\"\" colspan=\"3\" rowspan=\"2\">" +
                        getObjectValue(shanghaicontentJson.getResult().getDocTime()) +
                        "</td><td class=\"\">联系电话</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getContactInformation()) + "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\">" +
                        "<td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>传真</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getContactInformation()) +
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\">" +
                        "</span>获取招标文件地址</td><td class=\"\" colspan=\"5\">" + getObjectValue(shanghaicontentJson.getResult().getGetDocAddress()) + "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\">" +
                        "<td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>是否接受联合投标</td><td class=\"\" colspan=\"5\">" + getObjectValue(shanghaicontentJson.getResult().getSyndicatedFlag()) +
                        "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>获取招标文件时需提供材料（如有）</td>" +
                        "<td class=\"\" colspan=\"5\">" + getObjectValue(shanghaicontentJson.getResult().getSubmissions()) + "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\"><td class=\"\">" +
                        "<span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>提交投标文件地址</td><td class=\"\" colspan=\"3\">" + getObjectValue(shanghaicontentJson.getResult().getSetDocAddress()) +
                        "</td><td class=\"\">提交投标文件截止时间</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getDocSubmitEndTime()) + "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\">" +
                        "<td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>投标保证金</td><td class=\"\" colspan=\"3\">" + getObjectValue(shanghaicontentJson.getResult().getMarginAmount()) +
                        "</td><td class=\"\">投标文件工本费</td><td class=\"\">" + getObjectValue(shanghaicontentJson.getResult().getDocPrice()) + "</td></tr><tr class=\"ant-table-row  ant-table-row-level-0\">" +
                        "<td class=\"\"><span class=\"ant-table-row-indent indent-level-0\" style=\"padding-left: 0px;\"></span>备注</td><td class=\"\" colspan=\"5\">--</td></tr></tbody></table>";
                //压缩
                tender.setContent(CompressUtils.compress(content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getObjectValue(String objectValue){
        String value = objectValue!=null?objectValue:"--";
        return value;
    }
}
