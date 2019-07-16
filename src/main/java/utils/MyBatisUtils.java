package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtils {

    private static SqlSessionFactory factory;

    static {

    }

    public static SqlSession openSession() {
        return factory.openSession();
    }

    public static SqlSession openSession(boolean autoCommit) {
        return factory.openSession(autoCommit);
    }

    public static void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }

    //正式服数据源
    public static SqlSession getFormalFactory(boolean autoCommit){
        try {
            Properties properties = new Properties();
            properties.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
            properties.setProperty(
                    "jdbc.url",
                    "jdbc:mysql://120.55.52.30:3306/lezhu_new?characterEncoding=utf-8&amp;autoReconnect=true");
            properties.setProperty("jdbc.username", "bigdata");
            properties.setProperty("jdbc.password", "lezhu.31Ga3");
//            properties.setProperty(
//                    "jdbc.url",
//                    "jdbc:mysql://114.55.128.139:3306/lezhu_dev4?characterEncoding=utf-8&amp;autoReconnect=true;useSSL=false");
//            properties.setProperty("jdbc.username", "lezhu7");
//            properties.setProperty("jdbc.password", "wPqacIZu");
            factory = new SqlSessionFactoryBuilder().build(
                    Resources.getResourceAsStream("mybatis-config.xml"),properties
            );
            return factory.openSession(autoCommit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    //本地数据源
    public static SqlSession getBaseFactory(boolean autoCommit){
        try {
            Properties properties = new Properties();
            properties.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
            properties.setProperty(
                    "jdbc.url",
                    "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&amp;autoReconnect=true;useSSL=false");
            properties.setProperty("jdbc.username", "root");
            properties.setProperty("jdbc.password", "ok");
            factory = new SqlSessionFactoryBuilder().build(
                    Resources.getResourceAsStream("mybatis-config.xml"),properties
            );
            return factory.openSession(autoCommit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

}
