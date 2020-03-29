package ImgProcessing;

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
 * stupid dumb class work you stupid idiot.
 * work in progress
 * @author Osama Bamatraf
 *
 */
public class ImgResize {
	
	static String inImgPath = "C:\\Users\\owahe\\eclipse-workspace\\ImgProcessing\\src\\Untitled.png";
   static  String outImgPath1 = "C:\\Users\\owahe\\eclipse-workspace\\ImgProcessing\\src\\UntitledResize.png";
  static   String outImgPath2 = "C:\\Users\\owahe\\eclipse-workspace\\ImgProcessing\\src\\UntitledCrop.png";
  static  int reqWidth = 28;
  static  int reqHeight = 28;
  static  int reqX1;
   static int reqX2;
   static int reqY1;
   static int reqY2;
    /**
    static BufferedImage inImg;
    public ImgResize() throws IOException {
    	
        
		
    	
    }
    */
    
    /**
	 * 
	 * @param inImgPath
	 * @return
	 * @throws IOException
	 */
	
	// this is a mess that is still under construction
	public static  int[] imgScanTopToBot(String inImgPath, int i, int j, int h, int w) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
		int reqX=0;
		int reqY=0;
		
		
		
		
		for(int y= i; y < h; y++){
            for(int x = j; x < w; x++){
               
                Color c = new Color(inImg.getRGB(x, y));
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                
                
                if((r == 0) && (g == 0) && (b == 0)){
                	reqX = x;
                	reqY = y;
                    
                    break;
                }else {
                	continue;
                }
            }
		}
		int[] coor = {reqX, reqY};
		
		return coor;
		
		
		
		
               /**
        		 for(int y2 = inImg.getHeight(); y2 >0 ; y2--){
        			 for(int x2 = inImg.getWidth(); x2 >0 ; x2--)  {
                       
                        Color c2 = new Color(inImg.getRGB(x2, y2));
                        int r2 = c2.getRed();
                        int g2 = c2.getGreen();
                        int b2 = c2.getBlue();
                        
                        if((r2 == 0) && (g2 == 0) && (b2 == 0)){
                        	reqX2 = x2;
                        	reqY2 = y2;
                            
                            break;
                        }else {
                        	continue;
                        }
                       
                }
		} */
       
	}
	
	public static int[] imgScanBotToTop(String inImgPath, int i, int j, int h, int w) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
		int reqX=0;
		int reqY=0;
		
		
		
		
		for(int height= h; height > i; height--){
            for(int width = w; width> j; width--){
               
                Color c = new Color(inImg.getRGB(width, height));
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                
                if((r < 10) && (g < 10) && (b < 10)){
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
	
	public static void getHitBox(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);
		int x = 0;
		int y = 0;
		int h = inImg.getHeight();
		int w  = inImg.getWidth();
		int [] coordinates;
		coordinates = imgScanTopToBot(inImgPath, x, y, h, w);
		reqX1 = coordinates[0];
		reqY1 = coordinates[1];
		
		coordinates = imgScanBotToTop(inImgPath, x, y, h, w);
		reqX2 = coordinates[0];
		reqY2 = coordinates[1];
		
		
		
	}
	public static void cropImg(String inImgPath, String outImgPath, int x1, int x2, int y1, int y2 ) throws IOException {
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
 * This method takes in an image file which contains the already cropped (hopefully that works) image and will resize it into a size that
 * the neural net will be able  to process.
 * 
 * Also YAY it works.
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
	 public static void main(String[] args) {
	        
	       
	 
	        try {
	        	System.out.println(reqX1);
	    		System.out.println(reqX2);
	    		System.out.println(reqY1);
	    		System.out.println(reqY2);
	            
	            ImgResize.resize(inImgPath, outImgPath1, reqWidth, reqHeight);
	            ImgResize.cropImg(inImgPath,outImgPath2, reqX1,reqX2, reqY1, reqY2);
	 
	          
	        } catch (IOException ex) {
	            System.out.println("Error resizing the image.");
	            ex.printStackTrace();
	        }
	    }
 
}
