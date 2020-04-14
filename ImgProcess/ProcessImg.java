import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ProcessImg {
	private String inImgPath = "hello.png";
	private String outImgPath1 = "helloCrop.png";
	private String outImgPath2 = "helloResize.png";
	private final int reqWidth = 28;
	private final int reqHeight = 28;
	private int reqX1;
	private int reqX2;
	private int reqX3;
    private int reqX4;
    private int reqY1;
    private int reqY2;
    private int reqY3;
    private int reqY4;


	public static void main(String[] args) throws IOException{

        ProcessImg p = new ProcessImg();
  	      p.run();

    }
	
	/**
	 * this method runs the image processing and gets all the values to be used
	 * @throws IOException
	 */
	public void run() throws IOException {
    	ImgScanner scan = new ImgScanner();
		ImgOperations operate = new ImgOperations();
		reqX1 = scan.getreqX1(inImgPath);
    	reqX2 = scan.getreqX2(inImgPath);
    	reqX3 = scan.getreqX3(inImgPath);
    	reqX4 = scan.getreqX4(inImgPath);
    	reqY1 = scan.getreqY1(inImgPath);
    	reqY2 = scan.getreqY2(inImgPath);
    	reqY3 = scan.getreqY3(inImgPath);
    	reqY4 = scan.getreqY4(inImgPath);

    	int width = reqX1-reqX2;
    	int height = reqY4-reqY3;
    	if (width< 50 && height < 50) {  // this is done in case the the cropped image is too this in case of someone drawing 1
    		width = 50;
    		height = 50;
    	} else if (height < 50) {
    		height = width;

		} else if (width < 50) {
			width = height;
		}

	
		

		operate.cropImg(inImgPath,outImgPath1, reqX2,reqY3, width, height);
        operate.resize(outImgPath1, outImgPath2, reqWidth, reqHeight);

    }


}
