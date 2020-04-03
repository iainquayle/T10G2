

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.*;


/**
 * 
 * 
 * 
 * Currently this is just one big single class, however it will be broken down into multiple classes to reduce
 * the redunnant use of some of the code.
 * @author Osama Bamatraf
 *
 */
public class ImgResize {
	//will get rid of of the static refrences for the final version of this
	// this was made in order to easily test and make it work
	static String inImgPath = "C:\\Users\\owahe\\eclipse-workspace\\ImgProcessing\\src\\Untitled.png";
   static  String outImgPath1 = "C:\\Users\\owahe\\eclipse-workspace\\ImgProcessing\\src\\UntitledCrop.png";
  static   String outImgPath2 = "C:\\Users\\owahe\\eclipse-workspace\\ImgProcessing\\src\\UntitledResize.png";
  static  int reqWidth = 28;
  static  int reqHeight = 28;
   static  int reqX1;
   static int reqX2;
   static int reqX3;
   static int reqX4;
   static int reqY1;
   static int reqY2;
   static int reqY3;
   static int reqY4;

	/**
	 *Scans the image from left to right 
	 * in order to find the first black pixel that appears on the right side of the image.
	 * @param inImgPath path of original image
	 * @param h height of the image
	 * @param w width of the image
	 * @return
	 * @throws IOException
	 */
   
   public static  int[] imgScanLeftToRight(String inImgPath, int h, int w) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
		int reqX=0; //required x coordinate
		int reqY=0; //required y coordinate
		
		
		//loops through 
		
		for(int x = 0; x < w; x++)	{
			for(int y= 0; y < h; y++){
				
                Color c = new Color (inImg.getRGB(x,y));
                int check = c.getRed();  
         
                if(check ==0){    // checks for the brightness of the red color, if it's 0 then it's a black pixel
                	reqX = x;
                	reqY = y;
                    
                    break;
                }
            }
		}
		
		int[] coor = {reqX, reqY};
		
		return coor;
		
		
		
   }
	/**
	 * 
	 * Scans from bottom to top
	 * @param inImgPath
	 * @param h height
	 * @param w width
	 * @return
	 * @throws IOException
	 */
   public static  int[] imgScanBotToTop(String inImgPath, int h, int w) throws IOException {
		
 		File inFile = new File(inImgPath);
 		BufferedImage inImg = ImageIO.read(inFile);
 		int reqX=0;
 		int reqY=0;
 		
 		
 		
 		
 			for(int y= 0; y < h; y++){
 				for(int x = w; x >0; x--){
 				
                 Color c = new Color (inImg.getRGB(x,y));
                 int check = c.getRed();
             
                 if(check ==0){   // checks for the brightness of the red color, if it's 0 then it's a black pixel
                 	reqX = x;
                 	reqY = y;
                     
                     break;
                 }
             }
 		}
 		
 		int[] coor = {reqX, reqY};
 		
 		return coor;
   }
   
   /**
	 * 
	 * Scans from top to bottom
	 * @param inImgPath
	 * @param h height
	 * @param w width
	 * @return
	 * @throws IOException
	 */
   
   public static  int[] imgScanTopToBot(String inImgPath, int h, int w) throws IOException {
		
 		File inFile = new File(inImgPath);
 		BufferedImage inImg = ImageIO.read(inFile);
 		int reqX=0;
 		int reqY=0;
 		
 		
 		
 		
 			for(int y= h; y > 0; y--){
 				for(int x = 0; x <w; x++){
 				
                 Color c = new Color (inImg.getRGB(x,y));
                 int check = c.getRed();
             
                 if(check ==0){      // checks for the brightness of the red color, if it's 0 then it's a black pixel
                 	reqX = x;
                 	reqY = y;
                     
                     break;
                 }
             }
 		}
 		
 		int[] coor = {reqX, reqY};
 		
 		return coor;
   }
   
   
   /**
	 * Same genius observation as the previous one
	 * @param inImgPath
	 * @param h
	 * @param w
	 * @return
	 * @throws IOException
	 */
	public static int[] imgScanRightToLeft(String inImgPath, int h, int w) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
		int reqX=0;
		int reqY=0;
		
		
		
		
		for(int width = w; width> 0; width =width -1){
			for(int height= h; height > 0; height= height -1)  {
            	Color c = new Color (inImg.getRGB(width,height));
                int check = c.getRed();
                
                if(check <100){     // checks for the brightness of the red color, if it's 0 then it's a black pixel
                	reqX = width;
                	reqY = height;
                    
                    break;
                }else {
                	continue;
                }
            }
		}
		int[] coor = {reqX, reqY};
		
		return coor;
		
		
	}
	/**
	 * The following methods get x and y coordinates from the scanners
	 * @param inImgPath
	 * @return
	 * @throws IOException
	 */
	public static int getreqX1(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
		int x = 0;
		int y = 0;
		int h = inImg.getHeight();
		int w  = inImg.getWidth();
		
		int [] coordinates =imgScanLeftToRight(inImgPath, h, w);
		return coordinates[0];
		
		
		
		
		
	}
	
public static int getreqY1(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
		int x = 0;
		int y = 0;
		int h = inImg.getHeight();
		int w  = inImg.getWidth();
		
		int [] coordinates =imgScanLeftToRight(inImgPath, h, w);
		return coordinates [1];
		
		
		
	}

public static int getreqX2(String inImgPath) throws IOException {
	
	File inFile = new File(inImgPath);
	BufferedImage inImg = ImageIO.read(inFile);

	int h = inImg.getHeight()-1;
	int w  = inImg.getWidth()-1;
	
	
	int [] coordinates2 = imgScanRightToLeft(inImgPath, h, w);
	return coordinates2 [0];
	
	
}


public static int getreqY2(String inImgPath) throws IOException {
	
	File inFile = new File(inImgPath);
	BufferedImage inImg = ImageIO.read(inFile);

	int h = inImg.getHeight()-1;
	int w  = inImg.getWidth()-1;
	
	
	int [] coordinates2 =imgScanRightToLeft(inImgPath, h, w);
	return coordinates2 [1];
	
}

public static int getreqX3(String inImgPath) throws IOException {
	
	File inFile = new File(inImgPath);
	BufferedImage inImg = ImageIO.read(inFile);

	int h = inImg.getHeight()-1;
	int w  = inImg.getWidth()-1;
	
	
	int [] coordinates2 = imgScanTopToBot(inImgPath, h, w);
	return coordinates2 [0];
	
	
}
public static int getreqY3(String inImgPath) throws IOException {
	
	File inFile = new File(inImgPath);
	BufferedImage inImg = ImageIO.read(inFile);

	int h = inImg.getHeight()-1;
	int w  = inImg.getWidth()-1;
	
	
	int [] coordinates2 = imgScanTopToBot(inImgPath, h, w);
	return coordinates2 [1];
	
	
}
public static int getreqX4(String inImgPath) throws IOException {
	
	File inFile = new File(inImgPath);
	BufferedImage inImg = ImageIO.read(inFile);

	int h = inImg.getHeight()-1;
	int w  = inImg.getWidth()-1;
	
	
	int [] coordinates2 = imgScanBotToTop(inImgPath, h, w);
	return coordinates2 [0];
	
	
}
public static int getreqY4(String inImgPath) throws IOException {
	
	File inFile = new File(inImgPath);
	BufferedImage inImg = ImageIO.read(inFile);

	int h = inImg.getHeight()-1;
	int w  = inImg.getWidth()-1;
	
	
	int [] coordinates2 = imgScanBotToTop(inImgPath, h, w);
	return coordinates2 [1];
	
	
}

/**
 * 
 * @param inImgPath the original image path
 * @param outImgPath path of cropped image
 * @param x1 beginning of x coordinate of the crop
 * @param y1 beginning of y coordinate of the crop
 * @param x2 width of crop
 * @param y2 height of crop
 * @throws IOException
 */
	public static void cropImg(String inImgPath, String outImgPath, int x1, int y1, int x2, int y2 ) throws IOException {
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
		inImg = inImg.getSubimage(x1,y1,x2,y2);
		BufferedImage newImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D cropper = newImg.createGraphics();
		cropper.drawImage(inImg, 0, 0, null);
	    cropper.dispose();
	      
	       String fileType = outImgPath.substring(outImgPath.lastIndexOf(".") + 1);
	       ImageIO.write(newImg, fileType, new File(outImgPath));
		
		
		
	}
		
    
	
	
	
	
	
	
	
/**
 * 
 * @param inImgPath the file path of the original image file
 * @param outImgPath the file path of the new image
 * @param reqWidth this should be the width required for the data
 * @param reqHeight this should the height required for the data
 * @throws IOException
 * 
 * This method takes in an image file which contains the already cropped  image and will resize it into a size that
 * the neural net will be able  to process.
 * 
 *
 */
	
	public static void resize(String inImgPath, String outImgPath, int reqWidth, int reqHeight) throws IOException {
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
        BufferedImage outImg = new BufferedImage(reqWidth, reqHeight, inImg.getType());
 
        
       Graphics2D resizer = outImg.createGraphics();
      
   
       // this is to improve the quality of the resized image
     
       
       resizer.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
       resizer.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
       resizer.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      
       resizer.drawImage(inImg, 0, 0, reqWidth, reqHeight, null);
       resizer.dispose();
      
       String fileType = outImgPath.substring(outImgPath.lastIndexOf(".") + 1);
       ImageIO.write(outImg, fileType, new File(outImgPath));
    }
	/**
	 * 
	 * @return
	 */
	// don't worry this will happen will do it after I get the other stuff to work properly
	
	public byte[] getByteArray() {
		return null;
	}
	/**
	 * 
	 * @return
	 */
	// don't worry this will happen too
	public float[] byteToFloat() {
		return null;
	}
	
	 /**
	  * this is just for testing purposes now will remove later and
	  * will actually make it a proper class 
	  * @param args
	  */
	 /**
	 * @param args
	 */
	public static void main(String[] args) {
	        
	       
	 
	        try {
	        	
	        	reqX1 = getreqX1(inImgPath);
	        	reqX2 = getreqX2(inImgPath);
	        	reqX3 = getreqX3(inImgPath);
	        	reqX4 = getreqX4(inImgPath);
	        	reqY1 = getreqY1(inImgPath);
	        	reqY2 = getreqY2(inImgPath);
	        	reqY3 = getreqY3(inImgPath);
	        	reqY4 = getreqY4(inImgPath);
	        	System.out.println(reqX1);
	    		System.out.println(reqX2);
	        	System.out.println(reqX3);
	    		System.out.println(reqX4);
	    		System.out.println(reqY1);
	    		System.out.println(reqY2);
	    		System.out.println(reqY3);
	    		System.out.println(reqY4);
	    		
	    		File inFile = new File(inImgPath);
	    		BufferedImage inImg = ImageIO.read(inFile);
	    		//System.out.println(inImg.getHeight());
	            
	    		ImgResize.cropImg(inImgPath,outImgPath1, reqX2,reqY3, (reqX1-reqX2), (reqY4-reqY3));
	            ImgResize.resize(outImgPath1, outImgPath2, reqWidth, reqHeight);
	            
	 
	          
	        } catch (IOException ex) {
	            System.out.println("Error resizing the image.");
	            ex.printStackTrace();
	        }
	    }
 
}
