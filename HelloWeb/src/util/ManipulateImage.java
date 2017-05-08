package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class ManipulateImage {
	// Decode String into an Image
    public static boolean convertStringtoImage(String encodedImageStr, String fileName) {
 
        try {
            // Decode String using Base64 Class
            byte[] imageByteArray = Base64.decodeBase64(encodedImageStr); 
 
            // Write Image into File system - Make sure you update the path
            FileOutputStream imageOutFile = new FileOutputStream("E:/xx/officeTool/UploadedImages/" + fileName);
            imageOutFile.write(imageByteArray);
 
            imageOutFile.close();
 
            System.out.println("Image Successfully Stored");
            return true;
        } catch (FileNotFoundException fnfe) {
            System.out.println("Image Path not found" + fnfe);
        } catch (IOException ioe) {
            System.out.println("Exception while converting the Image " + ioe);
        }
        return false;
    }
}
