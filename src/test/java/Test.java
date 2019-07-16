import com.google.gson.Gson;
import http.HttpUtils;
import okhttp3.FormBody;


public class Test {


    @org.junit.Test
    public void gongchengbing() {


        String url = "https://www.gongchengbing.com/contact-machine/809523";

        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        FormBody body = formBodyBuilder.build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .headers(HttpUtils.getCommonHeaders())
                .header("Cookie", "_ga=GA1.2.1700293802.1530501206; gr_user_id=c709b84c-18de-431f-b9c7-ecff91c2bd97; GCB_SESSION=3cj15fnngquhapv76m7b2e75m7; _gid=GA1.2.1114336440.1548644004; Hm_lvt_9dad948a77ebf881ddff1c0dee2d9028=1548644005; LXB_REFER=www.baidu.com; gr_session_id_a4d52792123bb1c9=ba00fe1b-dd49-4451-80fd-6028f50b136d; gr_session_id_a4d52792123bb1c9_ba00fe1b-dd49-4451-80fd-6028f50b136d=true; remember_token_member=ZskU5mpOcJtlgN3wiQ9W9ogxwBnuKAY5k26XjrbeR1q-5HKdSa1kWBXCOtuWr9gODv2BJcIT5SQPe5xtk4CiiQ9M9oM90VC9Yk49xi3Glq_YDgf2mX6DGOQhBHibfZnHrp8OHbjCd5YC52IHfJN9zNvmxlVT7oB6; _gat=1; Hm_lpvt_9dad948a77ebf881ddff1c0dee2d9028=1548666906")
                .header("Host", "www.gongchengbing.com")
                .url(url)
                .post(body)
                .build();
        HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttp(request, "utf-8");

        String html = responseWrap.body;

        Gson gson = new Gson();



    }

}
