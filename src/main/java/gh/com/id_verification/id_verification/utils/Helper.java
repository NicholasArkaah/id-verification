package gh.com.id_verification.id_verification.utils;


import gh.com.id_verification.id_verification.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class Helper {

    @Autowired
    private AppConfig APP_CONFIG;

    public static Date getSqlDate(){
        return Date.valueOf(LocalDate.now());
    }

    public static Timestamp getSqlTimeStamp(){
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static Boolean getSqlTimeStampBefore(Timestamp input){
        return Timestamp.valueOf(LocalDateTime.now()).before(input);
    }

    public static long getDaysBetweenNowAndTimestamp(Timestamp input){
        try {
            long timeDiff = Math.abs(input.getTime() - getSqlTimeStamp().getTime());
            return TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        }catch (Exception ex){
            log.error("Timestamp input exception : {}",ex.getMessage());
            return 0;
        }
    }

    public MediaType getContentMediaType(String filename) {
        switch (getExtensionByApacheCommonLib(filename).toLowerCase()){
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "xml":
                return MediaType.APPLICATION_XML;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.ALL;
        }
    }

    public String convertDataUnit(double sizeKb)
    {
        String hrSize = "";
        DecimalFormat dec = new DecimalFormat("0.00");
        double b = sizeKb*1024.0;
        double k = sizeKb;
        double m = sizeKb/1024.0;
        double g = sizeKb/1048576.0;
        double t = sizeKb/1073741824.0;

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if(k > 1){
            hrSize = dec.format(k).concat(" KB");
        }
        else {
            hrSize = dec.format(b).concat(" B");
        }
        return hrSize;

    }

    public boolean checkFileType(MultipartFile file){
        String [] allowedFormats = APP_CONFIG.getAllowed_file_formats().split(",");
        for(String fileExt : allowedFormats){
            System.out.println("Checking "+ fileExt+" with "+getExtensionByApacheCommonLib(file.getOriginalFilename()));
            if(getExtensionByApacheCommonLib(file.getOriginalFilename()).equalsIgnoreCase(fileExt)){
                return true;
            }
        }
        return false;
    }

    public boolean getFileTypeAllowed(MultipartFile file){
        String [] allowedFormats = APP_CONFIG.getAllowed_file_formats().split(",");
        for(String fileExt : allowedFormats){
            System.out.println("Checking "+ fileExt+" with "+getExtensionByApacheCommonLib(file.getOriginalFilename()));
            if(getExtensionByApacheCommonLib(file.getOriginalFilename()).equalsIgnoreCase(fileExt)){
                return true;
            }
        }
        return false;
    }

    public static String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename.replaceAll(" ",""));
    }

    public static String convertFileToBase64(MultipartFile file){
        try {
            byte[] image = Base64.encodeBase64(file.getBytes());
            return new String(image);
        }catch (IOException ex){
            return null;
        }
    }





}
