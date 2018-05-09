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
    	 //tesseract所在目录
            String tessPath="F:/Tesseract-OCR";  
           File outputFile = new File(imageFile.getParentFile(), "output");  
           StringBuffer strB = new StringBuffer();  
           //用命令行形式调用tesseract
          String[] cm=new String[]{tessPath+"/tesseract",imageFile.getAbsolutePath(),outputFile.getAbsolutePath(),"-l","chi_sim"};  
        /*  System.out.println("执行的命令是    ");  
          for(String str:cm){  
           System.out.print(str+" ");  
          }  */
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
        public static void main(String[] args) {  
            try {  
            	//path后改为从界面上读取的，可能需要稍作修改
                String path = "C:\\Users\\Administrator\\Desktop\\大三下\\实训\\天猫工商信息执照\\";
            	File[] imagefile = ImgReadUtil.myreader(path);
                for (File f1 : imagefile){
                    String Abspath = path +f1.getName();
                    //图片二值化
                    BufferedImage imgsrc = ImgInverseUtil.file2img(Abspath);
                    //图片反色
                    //BufferedImage img = ImgInverseUtil.img_inverse(imgsrc);
                    //图片保存
                    //ImgInverseUtil.img2file(img,"jpg",Abspath);
                    //图片切割
                    //ImgCutUtil.cut(0, 0, 600, 75, Abspath, Abspath);  
                    
                    //输出识别结果
                    System.out.println(recognizeText(new File(Abspath), "png"));  
                }
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
  
}  