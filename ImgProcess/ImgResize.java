package ImgProcess;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.*;

public class ImgResize {
	
	byte[] imgData;
	int width;
	int height;
	
	public ImgResize(byte[] img, int widthReq, int heightReq){
		
		imgData = img;
		this.width = widthReq;
		this.height = heightReq;
	}

 public byte[] reSize() throws IOException {
	 
	 ByteArrayInputStream inImg = new ByteArrayInputStream(imgData);
	 BufferedImage imgEdit = ImageIO.read(inImg);
     if(height == 0) {
         height = (width * imgEdit.getHeight())/ imgEdit.getWidth(); 
     }
     if(width == 0) {
         width = (height * imgEdit.getWidth())/ imgEdit.getHeight();
     }
     Image resizedImage = imgEdit.getScaledInstance(width, height, Image.SCALE_SMOOTH);
     BufferedImage imageFinal = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
     resizedImage.getGraphics().drawImage(resizedImage, 0, 0, new Color(0,0,0), null);

     ByteArrayOutputStream buffer = new ByteArrayOutputStream();

     ImageIO.write(imageFinal, "jpg", buffer);

     return buffer.toByteArray();
	 
	 
 }
 
}
