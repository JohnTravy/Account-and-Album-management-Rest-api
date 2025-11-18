package com.travy.SpringRestAPI.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class AppUtil {

  public static String pathz = "C:\\Users\\gworl\\Documents\\VISUAL STUDIO PROJECTS\\SpringRestAPI\\src\\main\\resources\\static\\uploads\\";


   public static String get_photo_upload_path(String filename, String folder_name, Long album_id) throws IOException {
     String folderPath = pathz + album_id + "\\" + folder_name ;
     Files.createDirectories(Paths.get(folderPath));
     return new File(folderPath).getAbsolutePath() + "\\" + filename;
}
    
    public static BufferedImage getThumbnail(MultipartFile originalFile, Integer width) throws IOException{

        BufferedImage thumbImg = null;
        BufferedImage img = ImageIO.read(originalFile.getInputStream());
        thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC,  width, Scalr.OP_ANTIALIAS);
        return thumbImg;
        
    }

    public static boolean delete_photo_from_path(String filename, String folder_name, Long album_id){

      try {
        File f = new File(pathz + album_id + "\\" + folder_name + "\\" + filename);
        if(f.delete()){

          return true;

        }else{

          return false;

        }

      } catch (Exception e) {
        
        e.printStackTrace();
        return false;

      }

      
    }



    public static Resource getFileAsResource(Long album_id, String folder_name, String file_name) throws IOException{

      String location =  pathz + album_id + "\\" + folder_name + "\\" + file_name ;
      
      Path path = Paths.get(location).toAbsolutePath();

      if(Files.exists(path)){
        
        return new UrlResource(path.toUri());

      }else{

        return null;

      }


    }

}
