package pl.adamsiedlecki.CryptoMessenger.fileOperations;

import pl.adamsiedlecki.CryptoMessenger.config.Config;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.LocalTime;

// This is a class designed to clean the folders where user unencrypted data can stay
public class FileCleaner {

    public static void scheduleClean(){

        while(true){
            int hour = LocalDateTime.now().getHour();
            int minute = LocalTime.now().getMinute();
            int second = LocalTime.now().getSecond();
            if(hour==1&&minute==2&&second==3){
                delete(new File(Config.getImagePath()));
                File directory = new File(Config.getImagePath());
                directory.mkdir();
            }else if(hour==12&&minute==13&&second==14){
                delete(new File(Config.getImagePath()));
                File directory = new File(Config.getImagePath());
                directory.mkdir();
            }
            else if(hour==8&&minute==24&&second==14){
                delete(new File(Config.getImagePath()));
                File directory = new File(Config.getImagePath());
                directory.mkdir();
            }
        }

    }

    // Source: https://stackoverflow.com/questions/7768071/how-to-delete-directory-content-in-java
    private static boolean delete(File file) {
        File[] flist = null;
        if(file == null){
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        }

        if (!file.isDirectory()) {
            return false;
        }

        flist = file.listFiles();
        if (flist != null && flist.length > 0) {
            for (File f : flist) {
                if (!delete(f)) {
                    return false;
                }
            }
        }

        return file.delete();
    }
}
