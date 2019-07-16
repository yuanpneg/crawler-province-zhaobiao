package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.HttpUtils;
import okhttp3.Request;
import pojo1.LngLat;
import pojo1.LngLatBean;

public class GetLngLatUtils {
    /**
     * 获取经纬度的方法
     */
    public LngLat getLngLat(String city){
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+city+"&output=json&ak=6xssQ7pGB8fsvW90w9jqZHH3&mcode=E9:B1:66:E2:A3:1D:4B:39:95:39:C5:F4:62:7D:BE:9A:EE:29:B9:03;com.cz.nongyetong";
        Request request = new Request.Builder().url(url).build();
        HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
        if (responseWrap.isSuccess()) {
            String html = responseWrap.body;
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            LngLat lngLat = new LngLat();
            LngLatBean lngLatBean = gson.fromJson(html,LngLatBean.class);
            if (lngLatBean.getResult()!=null){
                String lng = String.valueOf(lngLatBean.getResult().getLocation().getLng());
                String lat = String.valueOf(lngLatBean.getResult().getLocation().getLat());
                lngLat.setLat(lat);
                lngLat.setLng(lng);
                lngLat.setCity(city);
                return lngLat;
            }else {
                return null;
            }
        }
        return null;
    }






}
