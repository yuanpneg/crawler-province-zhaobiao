package app_city;

import crawler_city.ShenyangThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class ShenyangMain {
    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        ShenyangThread thread = new ShenyangThread();
        thread.run();
    }
}
