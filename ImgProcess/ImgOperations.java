import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgOperations {
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
		public  void cropImg(String inImgPath, String outImgPath, int x1, int y1, int x2, int y2 ) throws IOException {
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
		
		public  void resize(String inImgPath, String outImgPath, int reqWidth, int reqHeight) throws IOException {
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
}
