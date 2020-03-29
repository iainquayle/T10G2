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
	
	
	/**
	 * 
	 * @param inImgPath
	 * @return
	 * @throws IOException
	 */
	
	// this is a mess that is still under construction
	public int getHitbox(String inImgPath) throws IOException {
		File inFile = new File(inImgPath);
        
		BufferedImage inImg = ImageIO.read(inFile);
		
		int reqX1=0;
		int reqY1=0;
		int reqX2=0;
		int reqY2=0;
		for(int y = 0; y < inImg.getHeight(); y++){
            for(int x = 0; x < inImg.getWidth(); x++){
               
                Color c = new Color(inImg.getRGB(x, y));
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                
                if((r == 0) && (g == 0) && (b == 0)){
                	reqX1 = x;
                	reqY1 = y;
                    
                    break;
                }else {
                	continue;
                }
            }
		}
               
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
		}
         return 0;
        		
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
	        String inImgPath = "C:\\Users\\owahe\\eclipse-workspace\\ImgProcessing\\src\\Untitled.png";
	        String outImgPath1 = "C:\\Users\\owahe\\eclipse-workspace\\ImgProcessing\\src\\UntitledResize.png";
	       
	 
	        try {
	            
	            int reqWidth = 28;
	            int reqHeight = 28;
	            ImgResize.resize(inImgPath, outImgPath1, reqWidth, reqHeight);
	 
	          
	        } catch (IOException ex) {
	            System.out.println("Error resizing the image.");
	            ex.printStackTrace();
	        }
	    }
 
}
