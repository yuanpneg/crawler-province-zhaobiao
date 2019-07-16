package services;

import bean.Tender;
import bean.ZhejiangJson;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 浙江省 详情页面无法获取
 */
public class ZhejiangService {

    public List<Tender> getList(String url, String city) {

        List<Tender> tenderList = new ArrayList<>();

        try{
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if(responseWrap.isSuccess()){
                String html = responseWrap.body;
                Gson gson = new Gson();
                ZhejiangJson zhejiangJson = gson.fromJson(html,ZhejiangJson.class);
                List<ZhejiangJson.ResultBean.RecordsBean> list = zhejiangJson.getResult().getRecords();
                for(ZhejiangJson.ResultBean.RecordsBean bean : list){
                    Tender tender = new Tender();
                    String daytime = bean.getDate().split(" ")[0];
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String title =bean.getTitle();
                    if (new BaseDao().selectTitle(title) != 0) continue;
                    if(title.indexOf("采购") != -1 ){
                        continue;
                    }
                    int tit = new BaseDao().selectUrl(bean.getLink());
                    GetContentUtil.getCont(bean.getLink(), tender, "detail_contect","utf-8"); //获取正文
                    tender.setTitle(title);
                    tender.setYeWuType("建设工程");
                    tender.setFrom("湖北省公共资源交易电子服务平台");
                    tender.setFromurl(bean.getLink());
                    tender.setAddress(city);
                    tender.setCatid(1);
                    tender.setStatus(1);
                    tenderList.add(tender);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenderList;
    }
}
