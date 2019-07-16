package utils;

import java.util.Random;

public class ContidUtils {

    /**
     * 生成随机数
     * @return
     */
    public String getRandom() {
        String sources = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 11; j++)
        {
            flag.append(sources.charAt(rand.nextInt(61)) + "");
        }
        System.out.println(flag.toString());
        return flag.toString();
    }
}
