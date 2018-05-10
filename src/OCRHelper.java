import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import Util.ImgCutUtil;
import Util.ImgInverseUtil;
import Util.ImgReadUtil;


public class OCRHelper {  
     public static synchronized String recognizeText(File imageFile, String imageFormat) throws Exception { 
    	   //tesseract����Ŀ¼
           String tessPath="F:/Tesseract-OCR";  
           File outputFile = new File(imageFile.getParentFile(), "output");  
           StringBuffer strB = new StringBuffer();  
           //����������ʽ����tesseract
           String[] cm=new String[]{tessPath+"/tesseract",imageFile.getAbsolutePath(),outputFile.getAbsolutePath(),"-l","chi_sim"};  
           /*  System.out.println("ִ�е�������    ");  
           for(String str:cm){  
           System.out.print(str+" ");  
           }  */
           Process pb = Runtime.getRuntime().exec(cm);  
           int w = pb.waitFor();  
           if (w == 0) {  
               BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+".txt"), "UTF-8"));  
               System.out.println("���ڶ�ȡ"+outputFile  
                      .getAbsolutePath()  
                      + ".txt �ļ�");  
               String str;  
               while ((str = in.readLine()) != null) {  
                  strB.append(str);  
               }  
               System.out.println("��ȡ��� ����� "+strB.toString());  
               in.close();  
           } else {  
               String msg;  
               switch (w) {  
               case 1:  
                  msg = "��ȡ�ļ����󣬿�������Ϊ�ļ�������.";  
                  break;  
               case 29:  
                  msg = "�޷�ʶ��ͼƬ";  
                  break;  
               case 31:  
                  msg = "ͼƬ��ʽ��֧�� ";  
                  break;  
               default:  
                  msg = "���ִ���";  
               }  
//             tempImage.delete();  
               throw new RuntimeException(msg);  
           }  
//         new File(outputFile.getAbsolutePath() + ".txt").delete();  
           return strB.toString();  
        }  
        public static void main(String[] args) {  
            try {  
            	//path���Ϊ�ӽ����϶�ȡ�ģ�������Ҫ�����޸�
                String path = "C:\\Users\\Administrator\\Desktop\\������\\ʵѵ\\��è������Ϣִ��\\";
            	File[] imagefile = ImgReadUtil.myreader(path);
                for (File f1 : imagefile){
                    String Abspath = path +f1.getName();
                    //ͼƬ��ֵ��
                    BufferedImage imgsrc = ImgInverseUtil.file2img(Abspath);
                    //ͼƬ��ɫ
                    //BufferedImage img = ImgInverseUtil.img_inverse(imgsrc);
                    //ͼƬ����
                    //ImgInverseUtil.img2file(img,"jpg",Abspath);
                    //ͼƬ�и�
                    //ImgCutUtil.cut(0, 0, 600, 75, Abspath, Abspath);  
                    
                    //���ʶ����
                    System.out.println(recognizeText(new File(Abspath), "png"));  
                }
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
  
}  