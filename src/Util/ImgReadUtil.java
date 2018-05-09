package Util;
import java.io.File;  
import java.util.ArrayList;  
import java.util.List;  

import javax.swing.JOptionPane;
  
/** 
 * 递归读取某个目录下的所有文件 
 *  
 * @author mhh
 * @Date 2017年4月18日
 * @motto 辅助工具类-读取文件
 * @Version 1.0 
 */  
public class ImgReadUtil {  
    public static File[] myreader(String fileDir) {  
		// TODO Auto-generated method stub
        List<File> fileList = new ArrayList<File>();  
        File file = new File(fileDir);  
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹  
        if (files == null) {// 如果目录为空，直接退出  
        	JOptionPane.showMessageDialog(null, "该目录下没有文件 ", "错误 ", JOptionPane.ERROR_MESSAGE);
        }  
        // 遍历，目录下的所有文件  
        for (File f : files) {  
            if (f.isFile()) {  
                fileList.add(f);  
            } else if (f.isDirectory()) {  
                System.out.println(f.getAbsolutePath());  
                myreader(f.getAbsolutePath());  
            }  
        }  
        return files;
    }

	}  

