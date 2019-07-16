package app_city;

import crawler_city.LinyiThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class LinyiMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        LinyiThread thread = new LinyiThread();
        thread.run();
    }
}
