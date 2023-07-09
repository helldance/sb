package com.coordsafe.img;

import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.constants.Constants;

@Service("imgservice")
public class IMGService {
	private static final Log log = LogFactory.getLog(IMGService.class);
	
	@Autowired
    private ServletContext servletContext;
	
	public static void main(String[] args){
		IMGService imgservice = new IMGService();
		
		String srcPath = "C:/Users/hongbin/Pictures/kemu.JPG";
		File srcFile = new File(srcPath); 
		try {
			String fileName = imgservice.cutImage(srcFile, 64, 64);
			System.out.println(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String cutImage(File srcFile, int width, int height) throws IOException {   
	    //File srcFile = new File(srcPath);    
	    BufferedImage image = ImageIO.read(srcFile);    
	    int srcWidth = image.getWidth(null);    
	    int srcHeight = image.getHeight(null);    
	    int newWidth = 0, newHeight = 0;    
	    int x = 0, y = 0;    
	    double scale_w = (double)width/srcWidth;    
	    double scale_h = (double)height/srcHeight;    
	    //log.debug("scale_w="+scale_w+",scale_h="+scale_h);    
	    //按原比例缩放图片    
	    if(scale_w < scale_h) {    
	        newHeight = height;    
	        newWidth = (int)(srcWidth * scale_h);    
	        x = (newWidth - width)/2;    
	    } else {    
	        newHeight = (int)(srcHeight * scale_w);    
	        newWidth = width;    
	        y = (newHeight - height)/2;    
	    }    
	    BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);    
	    newImage.getGraphics().drawImage(    
	    image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);    
	    // 保存缩放后的图片   
	    String fileSufix = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);    
	    File destFile = new File(srcFile.getParent(), UUID.randomUUID().toString() + "." + fileSufix);    
	    // ImageIO.write(newImage, fileSufix, destFile);    
	    // 保存裁剪后的图片    
	    ImageIO.write(newImage.getSubimage(x, y, width, height), fileSufix, destFile);   
	    
	    // copy to other directory
	    File svFile = new File(servletContext.getRealPath(""));
	    String safeLinkPath = svFile.getParentFile().getAbsolutePath() + File.separator + Constants.SAFELINK_PATH + File.separator + "resources" + File.separator + "wardphotos";
	    String georeadyPath = svFile.getParentFile().getAbsolutePath() + File.separator + Constants.GEOREADY_PATH + File.separator + "resources" + File.separator + "wardphotos";
	    	    
	    FileUtils.copyFileToDirectory(destFile.getAbsolutePath(), safeLinkPath);
	    FileUtils.copyFileToDirectory(destFile.getAbsolutePath(), georeadyPath);
	    
	    log.info("saved image, " + safeLinkPath + ", " + georeadyPath);
	    
	    return destFile.getName();
	}

	public final String getGrayPicture(File srcFile) throws IOException {
		
		BufferedImage originalPic = ImageIO.read(srcFile);
		int imageWidth = originalPic.getWidth();
		int imageHeight = originalPic.getHeight();

		BufferedImage newPic = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_3BYTE_BGR);

		ColorConvertOp cco = new ColorConvertOp(ColorSpace
				.getInstance(ColorSpace.CS_GRAY), null);
		cco.filter(originalPic, newPic);
		
	    String fileSufix = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);    
	    File destFile = new File(srcFile.getParent(), UUID.randomUUID().toString() + "." + fileSufix);   
	    
	    ImageIO.write(newPic, fileSufix, destFile);
	    
	    copyFileToEachother(destFile);
	    
	    return destFile.getName();
		
	}
	
	private void copyFileToEachother (File destFile){
		// copy to other directory
	    File svFile = new File(servletContext.getRealPath(""));
	    String safeLinkPath = svFile.getParentFile().getAbsolutePath() + File.separator + Constants.SAFELINK_PATH + File.separator + "resources" + File.separator + "wardphotos";
	    String georeadyPath = svFile.getParentFile().getAbsolutePath() + File.separator + Constants.GEOREADY_PATH + File.separator + "resources" + File.separator + "wardphotos";
	    	    
	    try {
			FileUtils.copyFileToDirectory(destFile.getAbsolutePath(), safeLinkPath);
		    FileUtils.copyFileToDirectory(destFile.getAbsolutePath(), georeadyPath);
		    
		    log.info("saved image, " + safeLinkPath + ", " + georeadyPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
