
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
/**
 * 
 * @author Osama Bamatraf
 * 
 * This is the drawing pad class
 *
 */

public class DrawingPad {
	
	/**
	 * main method to run the drawing pad
	 * @param args
	 */
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    final DrawPad drawPad = new DrawPad();
    frame.add(drawPad, BorderLayout.CENTER);
    JButton button = new JButton("Done");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
			drawPad.done();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      }
    });
    frame.add(button, BorderLayout.SOUTH);
    frame.setSize(512, 512);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
  }

}

/**
 * drawpad object
 * 
 *
 */
class DrawPad extends JComponent {
  Image image;
  Graphics2D g2D;
  int currX, currY, oldX, oldY;
  public DrawPad() {
    setDoubleBuffered(false);
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        oldX = e.getX();
        oldY = e.getY();
      }
    });
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();
        if (g2D != null)
          g2D.drawLine(oldX, oldY, currX, currY);
        repaint();
        oldX = currX;
        oldY = currY;
      }
    });
  }
  
  
  /**
   * 
   * create the stroke lines for drawing
   * and sets the width
   * 
   */

  public void paintComponent(Graphics g) {
     if (image == null) {
        image = createImage(getSize().width, getSize().height);
      g2D = (Graphics2D) image.getGraphics();
      g2D.setStroke(new BasicStroke(5));
      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON);
      try {
		done();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
     
     
    g.drawImage(image, 0, 0, null);
  }
  
  /**
   * this method saves the image into a png file
   */
  public void save(){
	    try {
	        BufferedImage newImg = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	        // Draw the image on to the buffered image
	        Graphics2D imgGraphics = newImg.createGraphics();
	        imgGraphics.drawImage(image, 0, 0, null);
	        javax.imageio.ImageIO.write(newImg , "PNG", new File("hello.png"));
	        imgGraphics.dispose();
	    } catch (Exception ex) {
	        Logger.getLogger(DrawingPad.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
  
  /**
   * this method calls the save method and runs the image processing part 
   * in order to crop and rezise the saved image
   * @throws IOException
   */
  public void done() throws IOException {
	save();
	ProcessImg p = new ProcessImg();
	p.run();
	g2D.setPaint(Color.white);
    g2D.fillRect(0, 0, getSize().width, getSize().height);
    g2D.setPaint(Color.black);
    repaint();
  }
}
