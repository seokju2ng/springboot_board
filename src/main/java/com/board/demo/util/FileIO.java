package com.board.demo.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileIO {
    private static final String REPOSITORY_PATH = "/Users/seokjung/Desktop/spring_board/src/main/resources/static/img/profile/";

    public static boolean saveImage(String folder, String filename, byte[] imgBytes) {
        File uploadPath = new File(REPOSITORY_PATH, folder);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        try {
            // Get the file and save it somewhere
            Path path = Paths.get(uploadPath + "/" + filename);
            Files.write(path, imgBytes);
        } catch (IOException e) {
            log.error("upload image fail: " + e.toString());
            return false;
        }
        return true;
    }

    public static void loadImage(String middlePath, String imageFileName, HttpServletResponse response) throws IOException {
        OutputStream out = response.getOutputStream();
        String path = REPOSITORY_PATH + middlePath + "/" + imageFileName;
        File imageFile = new File(path);
        FileInputStream in = new FileInputStream(imageFile);

        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Content-disposition", "attachment;fileName="+imageFileName);

        byte[] buffer = new byte[1024 * 8];
        while(true) {
            int count = in.read(buffer);
            if(count == -1)
                break;
            out.write(buffer, 0, count);
        }
        in.close();
        out.close();
    }
}
