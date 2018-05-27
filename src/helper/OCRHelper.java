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
     * ͼƬʶ�� 
     * @param imageFile File����,�ļ���λ�� 
     * @param imageFormat ͼ���ʽ 
     */  
     public static synchronized String recognizeText(File imageFile, String imageFormat) throws Exception { 
    	 
    	   File outputFile = new File(imageFile.getParentFile(), "output");  
           StringBuffer strB = new StringBuffer();
           ITesseract instance = new Tesseract(); 
         //instance.setDatapath("F:\\Tesseract-OCR\\tessdata");
           instance.setLanguage("chi_sim");
           
           String result=instance.doOCR(imageFile);
           
           //������
           System.out.println(result);
           
//         new File(outputFile.getAbsolutePath() + ".txt").delete();  
           return result;  
        }  

  
}  