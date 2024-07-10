package org.example.vegetableshopping.utils;

import lombok.experimental.UtilityClass;
import org.example.vegetableshopping.exception.CustomerErrorException;
import org.example.vegetableshopping.exception.SystemErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class FileUtil {

    public File convertMultiPartToFile(MultipartFile multipartFile) {

        if (multipartFile == null) {
            throw new CustomerErrorException("MultipartFile is null");
        }

        String currentDir = System.getProperty("user.dir");
        String fileName = multipartFile.getOriginalFilename();
        Path path = Paths.get(currentDir, fileName);
        File file = path.toFile();

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new SystemErrorException("Error convert multipart file to file: " + fileName);
        }

        return file;
    }

}
