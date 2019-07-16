package dao;

import bean.Tender;
import org.apache.ibatis.session.SqlSession;
import pojo1.Cityregion;
import pojo1.UpdaterReg;
import utils.MyBatisUtils;

import java.util.List;

public class FormalDao {

    //SqlSession sqlSession = MyBatisUtils.getFormalFactory(false);
    //BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);

    // 插入附件
    public int insertTender(Tender tender) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            int flag = mapper.insertTender(tender);
            sqlSession.commit();
            sqlSession.close();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(tender.getTitle());
            System.out.println(tender.getFrom());
            sqlSession.rollback();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
        return -1;
    }

    // 插入正文表
    public int insertTenderContent(Tender tender) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            int flag = mapper.insertTenderContent(tender);
            sqlSession.commit();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(tender.getTitle());
            System.out.println(tender.getFrom());
            sqlSession.rollback();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
        return -1;
    }

    //查询数据库中所有城市
    public List<String> selectCity() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            List<String> citys = mapper.selectCity();
            if (citys != null) {
                return citys;
            }
            sqlSession.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
        return null;
    }


    public Cityregion selectLonLatByTitle(String asd) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            Cityregion stringList = mapper.selectLonLatByTitle(asd);
            sqlSession.close();
            return stringList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
        return null;

    }

    public void updateTenderRegionLngLat(Cityregion cityregion) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            mapper.updateTenderRegionLngLat(cityregion);
            sqlSession.commit();
            sqlSession.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    public void deleteIshandled() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            mapper.deleteIshandled();
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }


    public List<String> selectRegionTitleFromTender() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            List<String> stringList = mapper.selectRegionTitleFromTender();
            return stringList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
        return null;
    }

    public Cityregion selectRegionBytitle(String s) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            return mapper.selectRegionBytitle(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
        return null;
    }

    public Cityregion selectRegionById(int id) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            Cityregion cityregion = mapper.selectRegionById(id);
            return cityregion;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession(sqlSession);

        }
        return null;
    }


    public void updateRegionInTender(UpdaterReg reg) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            mapper.updateRegionInTender(reg);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            MyBatisUtils.closeSession(sqlSession);

        }
    }

    public void deleteHistory(long daytime) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            mapper.deleteHistory((int) daytime);
            sqlSession.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            MyBatisUtils.closeSession(sqlSession);

        }
    }

    public int selecttitle(String url) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            return mapper.selecttitle(url);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            MyBatisUtils.closeSession(sqlSession);

        }
        return -1;
    }

    public int selectUrl(String url){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            int flag = mapper.selectUrl(url);
            sqlSession.close();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            MyBatisUtils.closeSession(sqlSession);

        }
        return -1;
    }

    public int selectTitle(String title){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            int flag = mapper.selectTitle(title);
            sqlSession.close();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            MyBatisUtils.closeSession(sqlSession);

        }
        return -1;
    }

    //删除重复
    public void  deleteRepeat(){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getFormalFactory(false);
            BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
            mapper.deleteRepeat();
            sqlSession.commit();
            sqlSession.close();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }
}
