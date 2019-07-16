package app;

import crawler.BeijingThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class BeijingMain {
    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        BeijingThread thread = new BeijingThread();
        thread.run();
    }
}
