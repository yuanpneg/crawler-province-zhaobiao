package app_city;

import crawler_city.HaerbinThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class HaerbinMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        HaerbinThread thread = new HaerbinThread();
        thread.run();
    }
}
