package app;

import crawler.*;
import crawler_city.*;
import dao.BaseDao;
import dao.FormalDao;
import org.apache.commons.logging.LogFactory;

import pojo1.Cityregion;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class AllApp {

    public static void main(String[] args) {
        AllApp allApp = new AllApp();
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

        BaseDao baseDao = new BaseDao();
        FormalDao formalDao = new FormalDao();

        while (true) {
            System.out.println("任务开始");
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (hour >= 7 && hour <= 22) {
                if(hour >= 20){
                    System.out.println("删除30天前的数据");
                    //删除测试服
                    baseDao.deleteHistory(AllApp.getfoct());
                    //删除正式服
                    formalDao.deleteHistory(AllApp.getfoct());
                }
                try {
                    threadMendth();
                    System.out.println("开始修改经纬度");
                    allApp.updateTenderRegionLngLat();//改表经纬度城市id
                    //allApp.updateRegiontitle();
                    System.out.println("更改经纬度完成");
                    //删除重复数据
                    System.out.println("删除重复数据");
                    baseDao.deleteRepeat();
                    formalDao.deleteRepeat();
                    System.out.println("删除完成");
                    //baseDao.deleteIshandled();
                    //formalDao.deleteIshandled();
                    System.out.println("任务结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //allApp.updateTenderRegionLngLat();//改表经纬度城市id
            //baseDao.deleteIshandled();
            try {
                Thread.sleep(TimeUnit.MINUTES.toMillis(90));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void threadMendth() throws InterruptedException {


        // 开启线程池
        int threadSize = 2;
        ExecutorService threadPool = Executors.newFixedThreadPool(threadSize);
        //省 start
        threadPool.submit(new XuzhouThread()); //徐州
        threadPool.submit(new HubeiThread()); //湖北
        threadPool.submit(new ShiheziThread()); //石河子
        //市 start
        threadPool.submit(new GuangzhouThread()); //广州
        threadPool.submit(new WeinanThread()); //渭南
        threadPool.submit(new XianThread()); //西安
        threadPool.submit(new XianyangThread()); //咸阳
        threadPool.submit(new YananThread()); //延安
        threadPool.submit(new HanzhongThread()); //汉中
        threadPool.submit(new HegangThread()); // 鹤岗
        threadPool.submit(new ZhuhaiThread()); //珠海
        threadPool.submit(new DaqingThread()); // 大庆
        threadPool.submit(new AnkangThread()); //安康
        threadPool.submit(new QiqihaerThread()); // 齐齐哈尔
        threadPool.submit(new LishuiThread()); //丽水
        threadPool.submit(new DatongThread()); // 大同
        threadPool.submit(new LinfenThread()); // 临汾
        threadPool.submit(new LvliangThread()); // 吕梁
        threadPool.submit(new MeizhouThread()); // 梅州
        threadPool.submit(new ShuozhouThread()); // 朔州
        threadPool.submit(new ZhanjiangThread()); // 湛江
        threadPool.submit(new ZhaoqingThread()); // 肇庆
        threadPool.submit(new ChangzhiThread()); //长治
        threadPool.submit(new ChangshaThread()); //长沙
        threadPool.submit(new LoudiThread()); //娄底
        threadPool.submit(new SanmenxiaThread()); // 三门峡
        threadPool.submit(new ShangqiuThread()); //商丘
        threadPool.submit(new LuoyangThread()); //洛阳
        threadPool.submit(new ZhongshanThread()); //中山
        threadPool.submit(new ShantouThread()); //汕头
        //threadPool.submit(new YulinThread()); //玉林
        threadPool.submit(new LanzhouThread()); //兰州
        threadPool.submit(new ShenyangThread());  // 沈阳
        threadPool.submit(new FushunThread()); //抚顺
        threadPool.submit(new YunchengThread()); //运城
        threadPool.submit(new LiaochengThread()); //聊城
        threadPool.submit(new BaotouThread()); // 包头
        threadPool.submit(new AnyangThread()); //安阳
        threadPool.submit(new ChangjiThread()); // 昌吉
        threadPool.submit(new GannanThread()); // 甘南
        threadPool.submit(new GanziThread()); // 甘孜
        threadPool.submit(new HetianThread()); // 和田
        threadPool.submit(new HezeThread()); // 菏泽
        //threadPool.submit(new JiayuguanThread()); //嘉峪关
        threadPool.submit(new JiyuanThread()); //济源
        threadPool.submit(new KashiThread()); //喀什
        threadPool.submit(new KezilesuThread()); //克孜勒苏
        threadPool.submit(new LingshuiThread()); //陵水
        threadPool.submit(new LinxiaThread()); //临夏
        //threadPool.submit(new LinzhiThread()); //林芝
        threadPool.submit(new ShanghaiThread()); //上海
        threadPool.submit(new TumushukeThread()); //图木舒克
        threadPool.submit(new SipingThread()); //四平
        //threadPool.submit(new WujiaquThread()); //五家渠
        threadPool.submit(new YichunThread()); //伊春
        threadPool.submit(new YiyangThread()); //益阳
        threadPool.submit(new HengyangThread()); //衡阳
        threadPool.submit(new WulumuqiThread()); //乌鲁木齐
        threadPool.submit(new RikazeThread()); //日喀则
        threadPool.submit(new BoertalaThread()); //博尔塔拉
        threadPool.submit(new LinyiThread()); //临沂
        threadPool.submit(new RizhaoThread()); //日照
        threadPool.submit(new ZaozhuangThread()); //枣庄
        threadPool.submit(new KaifengThread()); //开封
        threadPool.submit(new YangzhouThread()); //扬州
        threadPool.submit(new QingdaoThread()); //青岛
        threadPool.submit(new DazhouThread()); //达州
        threadPool.submit(new AnshunThread()); //安顺
        threadPool.submit(new FoshanThread()); //佛山
        threadPool.submit(new JinanThread()); //济南
        //threadPool.submit(new HaerbinThread()); //哈尔滨
        threadPool.submit(new JieyangThread()); //揭阳
        threadPool.submit(new JiuquanThread()); //酒泉
        threadPool.submit(new QingyuanThread()); //清远
        threadPool.submit(new TianmenThread()); //天门
        //市 end

        threadPool.submit(new AnhuiThread()); //安徽
        threadPool.submit(new FujianggzyThread()); //福建
        threadPool.submit(new GansuThread()); //甘肃
        threadPool.submit(new HebeiThread()); //河北
        threadPool.submit(new HnggzyThread()); //河南
        threadPool.submit(new JiangxiggzyThread()); //江西
        threadPool.submit(new JsggzyThread()); //江苏
        threadPool.submit(new LiaoningThread()); //辽宁
        threadPool.submit(new NingxiaThread()); //宁夏
        //threadPool.submit(new SdggzyThread()); //山东
        threadPool.submit(new SxggzyThread()); //山西省
        threadPool.submit(new HunanggzyThread()); //湖南
        threadPool.submit(new SichuanThread()); //四川
        threadPool.submit(new YunnanThread()); //云南
        threadPool.submit(new GuangxiThread()); //广西
        threadPool.submit(new JilinThread()); //吉林
        threadPool.submit(new GuizhouThread()); //贵州
        threadPool.submit(new HainanThread());  //海南
        threadPool.submit(new QinghaiThread()); //青海
        threadPool.submit(new HeilongjiangThread()); //黑龙江
        threadPool.submit(new ShanxiThread()); //陕西
        threadPool.submit(new XizangThread()); //西藏
        threadPool.submit(new YangjiangThread()); //阳江
        //省 end

        threadPool.shutdown();

        while (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) ;
        System.out.println("执行完成~");

    }


    public void updateTenderRegionLngLat() {
        FormalDao formalDao = new FormalDao();
        List<String> citys = formalDao.selectCity();
        try {
            for (String city : citys) {
                //Thread.sleep(1000);
                String cityb = city.replace("省", "").replace("市", "")
                        .replace("区", "");
                if (!cityb.equals("") && null != cityb) {
                    Cityregion cityregion = formalDao.selectLonLatByTitle(cityb);
                    if (cityregion != null) {
                        System.out.println(cityb);
                        cityregion.setAddress(city);
                        formalDao.updateTenderRegionLngLat(cityregion);
                        System.out.println(cityregion.getTitle() + cityregion.getLetter());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    public void updateRegiontitle() {
//        BaseDao baseDao = new BaseDao();
//        List<String> stringList = baseDao.selectRegionTitleFromTender();
//        System.out.println(stringList.size());
//        for (String s : stringList) {
//            String cityb = s.replace("省", "").replace("市", "");
//            Cityregion cityregion = baseDao.selectRegionBytitle(cityb);
//            if (cityregion.getType() == 3) {
//                Cityregion cityregion1 = baseDao.selectRegionById(cityregion.getParentid());
//                System.out.println(cityregion1.getTitle());
//                UpdaterReg updaterReg = new UpdaterReg();
//                updaterReg.setRegionid(cityregion1.getId());
//                updaterReg.setNewcity(cityregion1.getTitle());
//                updaterReg.setOldcity(cityregion.getTitle());
//                updaterReg.setLng(cityregion1.getLon());
//                updaterReg.setLat(cityregion1.getLat());
//                baseDao.updateRegionInTender(updaterReg);
//            }
//        }
//    }


    public static long getfoct() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH, -30);
        try {
            return date.getTimeInMillis() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


}
