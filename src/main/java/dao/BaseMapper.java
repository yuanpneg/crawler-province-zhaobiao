package dao;


import bean.Tender;
import org.apache.ibatis.annotations.Param;

import pojo1.*;


import java.util.List;


public interface BaseMapper {

    // 插入
    int insertTender(Tender tender);

    //插入正文表
    int insertTenderContent(Tender tender);

    //查询数据库中所有的城市
    List<String> selectCity();

    Cityregion selectLonLatByTitle(String sd);

    void updateTenderRegionLngLat(Cityregion cityregion);

    void deleteIshandled();

    List<String> selectRegionTitleFromTender();

    Cityregion selectRegionBytitle(String s);

    Cityregion selectRegionById(int id);

    void updateRegionInTender(UpdaterReg reg);

    void deleteHistory(@Param("asd") int daytime);

    int selecttitle(String city);

    int selectTitle(String title);

    int selectUrl(String selectUrl);

    void updateFormalId(Tender tender);

    void deleteRepeat();

    /**
     *     查询徐州的是否存在，徐州的title可能相同但是addtime不同

     */
    int selectTitleAndTime(@Param("title") String title,
                           @Param("addtime") Long addtime);


}
