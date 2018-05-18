package Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class exportExcelUtil {

     /**
     * 导出数据为XLS格式
     * @param fileName        输出文件的名称，可以设为绝对路径，也可以设为相对路径
     * @param output        数据的内容，在外即为recognizeText的结果
     */

	public static void exportExcel(String fileName,String output) {
    	//截取一次结果后的公司名称和编号
    	int size = 100;
    	Vector<Data> content = new Vector<Data>();
    	int [] name_location= new int[size];
    	int [] number_location= new int[size];
    	int [] end_location= new int[size];

    	
    	WritableWorkbook wwb;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fileName);
            wwb = Workbook.createWorkbook(fos);
            WritableSheet ws = wwb.createSheet("公司名称及编号列表", 0);        // 创建一个工作表
            String[] title = {"公司名称","企业注册号"};  

            //    设置单元格的文字格式
            WritableFont wf = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf.setAlignment(Alignment.CENTRE); 
            ws.setColumnView(0, 300);
            ws.setColumnView(1, 300);
            //添加列名
            for(int i=0;i<title.length;i++)
            {
                ws.addCell(new Label(i,0, title[i], wcf));  
            }
            
            //筛选出公司名称和编号
            int j = 0;
        	while (true) {
        		if (j==0) {
        		name_location[0] = output.indexOf(58);        		
        		number_location[0] = output.indexOf(58,name_location[0]+1);
        		end_location[0] = output.indexOf('司');
        		}
        		else if (j!=0) {
        		name_location[j] = output.indexOf(58,number_location[j-1]+1);
        		number_location[j] = output.indexOf(58,name_location[j]+1);
        		end_location[j] = output.indexOf('司',end_location[j-1]+1);
        		}
        		String number = output.substring(name_location[j]+1,number_location[j]-5);
        		String name = output.substring(number_location[j]+1,end_location[j]+1);
        		//将编号保存到Data中(保存一组结果)
        		Data d0 = new Data(number,name);
        		content.add(d0);
        		if (number_location[j] == output.lastIndexOf(58)){break;}
        		j++;
        	}
        	
        	
            //    填充数据的内容
            Data[] p = new Data[content.size()];
            for (int i = 0; i < content.size(); i++){
                p[i] = (Data)content.get(i);
                ws.addCell(new Label(0, i + 1, p[i].getName()));
                ws.addCell(new Label(1, i + 1, p[i].getNumber()));
                if(i == 0)
                    wcf = new WritableCellFormat();
            }
            wwb.write();
            wwb.close();
        } catch (IOException e){
        } catch (RowsExceededException e){
        } catch (WriteException e){}
    }

    


    /*public static void main(String [] args){
        String fileName = "test.xls";

    	String output = "企业注册号:913302055612570177 企业名称:宁波中哲慕尚店子商务有限公司 企业注册号:913302010847810855 企业名称:宁波太平鸟网络科技有限公司 企业注册号:91120222671480180P 企业名称:绫致时装销售（天津）有限公司";
        exportExcel(fileName, output);
        System.out.println("成功导出数据到Excel文件(" + fileName + ")了!!!");
    }*/
}