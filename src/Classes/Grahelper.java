package Classes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Grahelper {

	public  BufferedImage resizeImage(BufferedImage img, int newWidth, int newHeight) {
		BufferedImage originalImage = img;
		Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
		originalImage.flush();
		originalImage=null;
		Graphics g = newImage.getGraphics();
		g.drawImage(resizedImage, 0, 0, null);
		g.dispose();		
		return newImage;
	}
	
	public  BufferedImage createThumbnail(BufferedImage img, int newWidth, int newHeight) {
		BufferedImage originalImage = img;	
		BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
			newWidth, newHeight, Scalr.OP_ANTIALIAS);	
		originalImage.flush();
		originalImage=null;
		return thumbImg;
	}
	
	public BufferedImage sresizeImage(String inputImagePath, int scaledWidth, int scaledHeight) throws IOException {       
        FileInputStream inputStream = new FileInputStream(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputStream);       
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());    
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        inputStream.close();
        inputImage.flush();
        inputImage=null;
       return outputImage;
    }
	
	public BufferedImage resize(BufferedImage img, int newW, int newH) {
	    int w = img.getWidth();
	    int h = img.getHeight();
	    BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
	    Graphics2D g = dimg.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
	    g.dispose();
	  return dimg;			
	}

	public static void fresizeImage(String inputPath, String outputPath, int scaledWidth, int scaledHeight) throws IOException {
       FileInputStream inputStream = new FileInputStream(inputPath);
        BufferedImage inputImage = ImageIO.read(inputStream);     
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());      
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        FileOutputStream outputStream = new FileOutputStream(outputPath);     
        ImageIO.write(outputImage, "png", outputStream); 
        inputStream.close();
        outputStream.close();
    }
	
	public static BufferedImage toBufferedImage(Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB; // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }


}

