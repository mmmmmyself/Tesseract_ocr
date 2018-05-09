package Util;
import java.awt.Rectangle;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.util.Iterator;  
  
import javax.imageio.ImageIO;  
import javax.imageio.ImageReadParam;  
import javax.imageio.ImageReader;  
import javax.imageio.stream.ImageInputStream;  
  
/** 
 * 图片切割
 *  
 * @author mhh
 * @Date 2017年5月2日
 * @motto 辅助工具类-图片切割
 * @Version 1.0 
 */  


public class ImgCutUtil {  
  
    public static void main(String[] args) {  
       //测试用
       // ImgCutUtil.cut(30, 50, 300, 400, "d:/1.jpg", "d:/100.jpg");  
  
    }  
  
    /** 
     * 图片裁切 
     * @param x1 选择区域左上角的x坐标 
     * @param y1 选择区域左上角的y坐标 
     * @param width 选择区域的宽度 
     * @param height 选择区域的高度 
     * @param sourcePath 源图片路径 
     * @param descpath 裁切后图片的保存路径 
     */  
    public static void cut(int x1, int y1, int width, int height,  
            String sourcePath, String descpath) {  
  
        FileInputStream is = null;  
        ImageInputStream iis = null;  
        try {  
            is = new FileInputStream(sourcePath);  
            String fileSuffix = sourcePath.substring(sourcePath  
                    .lastIndexOf(".") + 1);  
            Iterator<ImageReader> it = ImageIO  
                    .getImageReadersByFormatName(fileSuffix);  
            ImageReader reader = it.next();  
            iis = ImageIO.createImageInputStream(is);  
            reader.setInput(iis, true);  
            ImageReadParam param = reader.getDefaultReadParam();  
            Rectangle rect = new Rectangle(x1, y1, width, height);  
            param.setSourceRegion(rect);  
            BufferedImage bi = reader.read(0, param);  
            ImageIO.write(bi, fileSuffix, new File(descpath));  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } finally {  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                is = null;  
            }  
            if (iis != null) {  
                try {  
                    iis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                iis = null;  
            }  
        }  
  
    }  
  
}  