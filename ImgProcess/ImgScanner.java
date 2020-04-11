import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgScanner {

	/**
	 *Scans the image from left to right 
	 * in order to find the first black pixel that appears on the right side of the image.
	 * @param inImgPath path of original image
	 * @param h height of the image
	 * @param w width of the image
	 * @return
	 * @throws IOException
	 */
	   public    int[] imgScanLeftToRight(String inImgPath, int h, int w) throws IOException {
			
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
	   public    int[] imgScanBotToTop(String inImgPath, int h, int w) throws IOException {
			
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
	   
	   public    int[] imgScanTopToBot(String inImgPath, int h, int w) throws IOException {
			
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
		public   int[] imgScanRightToLeft(String inImgPath, int h, int w) throws IOException {
			
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
	public   int getreqX1(String inImgPath) throws IOException {
			
			File inFile = new File(inImgPath);
			BufferedImage inImg = ImageIO.read(inFile);
			int x = 0;
			int y = 0;
			int h = inImg.getHeight();
			int w  = inImg.getWidth();
			
			int [] coordinates =imgScanLeftToRight(inImgPath, h, w);
			return coordinates[0];
			
			
			
			
			
		}
		
	public   int getreqY1(String inImgPath) throws IOException {
			
			File inFile = new File(inImgPath);
			BufferedImage inImg = ImageIO.read(inFile);
			int x = 0;
			int y = 0;
			int h = inImg.getHeight();
			int w  = inImg.getWidth();
			
			int [] coordinates =imgScanLeftToRight(inImgPath, h, w);
			return coordinates [1];
			
			
			
		}

	public   int getreqX2(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);

		int h = inImg.getHeight()-1;
		int w  = inImg.getWidth()-1;
		
		
		int [] coordinates2 = imgScanRightToLeft(inImgPath, h, w);
		return coordinates2 [0];
		
		
	}


	public   int getreqY2(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);

		int h = inImg.getHeight()-1;
		int w  = inImg.getWidth()-1;
		
		
		int [] coordinates2 =imgScanRightToLeft(inImgPath, h, w);
		return coordinates2 [1];
		
	}

	public   int getreqX3(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);

		int h = inImg.getHeight()-1;
		int w  = inImg.getWidth()-1;
		
		
		int [] coordinates2 = imgScanTopToBot(inImgPath, h, w);
		return coordinates2 [0];
		
		
	}
	public   int getreqY3(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);

		int h = inImg.getHeight()-1;
		int w  = inImg.getWidth()-1;
		
		
		int [] coordinates2 = imgScanTopToBot(inImgPath, h, w);
		return coordinates2 [1];
		
		
	}
	public   int getreqX4(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);

		int h = inImg.getHeight()-1;
		int w  = inImg.getWidth()-1;
		
		
		int [] coordinates2 = imgScanBotToTop(inImgPath, h, w);
		return coordinates2 [0];
		
		
	}
	public   int getreqY4(String inImgPath) throws IOException {
		
		File inFile = new File(inImgPath);
		BufferedImage inImg = ImageIO.read(inFile);

		int h = inImg.getHeight()-1;
		int w  = inImg.getWidth()-1;
		
		
		int [] coordinates2 = imgScanBotToTop(inImgPath, h, w);
		return coordinates2 [1];
		
		
	}

}
