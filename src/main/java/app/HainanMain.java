package app;

import crawler.HainanThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class HainanMain {
    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        HainanThread thread = new HainanThread();
        thread.run();
    }
}
