package util;

import java.io.File;

public class Utils {
    public static File getFile(String filename) {
        File file = new File(Utils.class.getClassLoader().getResource(filename).getFile());

        System.out.println("file = " + file + " exists " + file.exists());

        return file;
    }
}
