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
	private int width = 28;
	private int height = 28;
	
	public ImgResize(byte[] img){
		
		imgData = img;
		
	}

 public byte[] reSize() throws IOException {
	 
	 ByteArrayInputStream inImg = new ByteArrayInputStream(imgData);
	 BufferedImage imgEdit = ImageIO.read(inImg);

     Image resizedImage = imgEdit.getScaledInstance(width, height, Image.SCALE_SMOOTH);
     BufferedImage imageFinal = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
     resizedImage.getGraphics().drawImage(resizedImage, 0, 0, new Color(0,0,0), null);

     ByteArrayOutputStream outImg = new ByteArrayOutputStream();

     ImageIO.write(imageFinal, "jpg", outImg);

     return outImg.toByteArray();
	 
	 
 }
 
}
