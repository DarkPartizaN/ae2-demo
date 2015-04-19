package aftergames.demo;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import aftergames.engine.App;
import aftergames.engine.utils.ResourceUtils;
import aftergames.engine.utils.StringUtils;

/**
 *
 * @author KiQDominaN
 */
public class AfterEngine2DemoSE extends App {

    public static void main(String[] args) {
        AfterEngine2DemoSE app = new AfterEngine2DemoSE();

        int width = 800; //Default width
        int height = 640; //Default height
        boolean fullscreen = false;

        String launchParams = new String();

        if (args.length > 0) {
            for (String arg : args) launchParams = StringUtils.concat(launchParams, arg, " "); //Collect launch params

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-width")) width = Integer.valueOf(args[++i]);
                if (args[i].equals("-height")) height = Integer.valueOf(args[++i]);
                if (args[i].equals("-fullscreen")) {
                    fullscreen = true;

                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    width = dim.width;
                    height = dim.height;
                }
                if (args[i].equals("-log")) enableLog();
            }
        }

        System.out.println(StringUtils.concat("Starting ", app.getClass().getSimpleName(), (launchParams.isEmpty()) ? " with no launch options" : " with launch options: \"", launchParams, "\""));

        app.screen = new DemoScreen(width, height, fullscreen);
        app.screen.init();
        app.screen.start();
    }

    //Enables external log file
    private static void enableLog() {
        String date = new SimpleDateFormat("HHmmssddMMyyyy").format(new Date());

        try {
            //File to save
            File file = new File(StringUtils.concat(ResourceUtils.root_dir, "/logs/"));
            if (!file.exists()) file.mkdir();
            file = new File(StringUtils.concat(ResourceUtils.root_dir, "/logs/", "log_", date, ".txt"));
            if (!file.exists()) file.createNewFile();

            PrintStream ps = new PrintStream(file);

            System.setOut(ps);
            System.setErr(ps);
        } catch (IOException ex) {
        }
    }

}