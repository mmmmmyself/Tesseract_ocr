package helper;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import ui.ocrUI;
import Util.ImgCutUtil;
import Util.ImgInverseUtil;
import Util.ImgReadUtil;


public class OCRHelper {  
    /** 
     * 图片识别 
     * @param imageFile File类型,文件的位置 
     * @param imageFormat 图像格式 
     */  
     public static synchronized String recognizeText(File imageFile, String imageFormat) throws Exception { 
    	 /*  
    	 //tesseract所在目录
           String tessPath="D:/Program Files (x86)/Tesseract-OCR";  
           File outputFile = new File(imageFile.getParentFile(), "output");  
           StringBuffer strB = new StringBuffer();  
           //用命令行形式调用tesseract
           String[] cm=new String[]{tessPath+"/tesseract",imageFile.getAbsolutePath(),outputFile.getAbsolutePath(),"-l","chi_sim"};  
         */
    	 
    	   File outputFile = new File(imageFile.getParentFile(), "output");  
           StringBuffer strB = new StringBuffer();
           ITesseract instance = new Tesseract(); 
           instance.setLanguage("chi_sim");
           
           String[] cm=new String[]{instance.doOCR(imageFile)};
           System.out.println("执行的命令是    ");  
           for(String str:cm){  
           System.out.print(str+" ");  
           }  
           Process pb = Runtime.getRuntime().exec(cm);  
           int w = pb.waitFor();  
           if (w == 0) {  
               BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+".txt"), "UTF-8"));  
               System.out.println("正在读取"+outputFile  
                      .getAbsolutePath()  
                      + ".txt 文件");  
               String str;  
               while ((str = in.readLine()) != null) {  
                  strB.append(str);  
               }  
               System.out.println("读取完成 结果是 "+strB.toString());  
               in.close();  
           } else {  
               String msg;  
               switch (w) {  
               case 1:  
                  msg = "获取文件错误，可能是因为文件名问题.";  
                  break;  
               case 29:  
                  msg = "无法识别图片";  
                  break;  
               case 31:  
                  msg = "图片格式不支持 ";  
                  break;  
               default:  
                  msg = "出现错误！";  
               }  
//             tempImage.delete();  
               throw new RuntimeException(msg);  
           }  
//         new File(outputFile.getAbsolutePath() + ".txt").delete();  
           return strB.toString();  
        }  

  
}  