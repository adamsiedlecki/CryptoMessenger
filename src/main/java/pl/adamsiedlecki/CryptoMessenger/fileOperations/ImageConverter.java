package pl.adamsiedlecki.CryptoMessenger.fileOperations;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ImageConverter {

    // Source: https://www.javagists.com/convert-an-image-to-base64-string-in-java
    public static String encodeFileToBase64Binary(File file) throws Exception {
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }

    public static File decodeBase64BinaryToFile(String base64Binary, String pathForFile) {
        byte[] bytes = Base64.decodeBase64(base64Binary);
        File file;
        try {
            FileUtils.writeByteArrayToFile(file = new File(pathForFile), bytes);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File("encryption.jpg");
    }


}
