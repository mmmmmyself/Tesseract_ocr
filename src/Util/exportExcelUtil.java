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
     * @param fileName        文件的名称，可以设为绝对路径，也可以设为相对路径
     * @param output        数据的内容，在外即为recognizeText的结果
     */

    public static void exportExcel(String fileName,String output) {
    	//截取一次结果后的公司名称和编号
    	int name_location = output.indexOf(58);
    	int number_location = output.indexOf(58,name_location+1);
    	int end_location = output.indexOf('司');
    	String number = output.substring(name_location+1,number_location-5);
    	String name = output.substring(number_location+1,end_location+1);
    	//将编号保存到Data中(保存一组结果)
    	Data d0 = new Data(number,name);
    	Vector<Data> content = new Vector<Data>();
    	content.add(d0);
    	//System.out.println(content.get(0));
    	WritableWorkbook wwb;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fileName);
            wwb = Workbook.createWorkbook(fos);
            WritableSheet ws = wwb.createSheet("公司名称及编号列表", 10);        // 创建一个工作表

            //    设置单元格的文字格式
            WritableFont wf = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,
                    UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf.setAlignment(Alignment.CENTRE); 
            ws.setRowView(1, 500);

            //    填充数据的内容
            Data[] p = new Data[content.size()];
            for (int i = 0; i < content.size(); i++){
                p[i] = (Data)content.get(i);
                ws.addCell(new Label(1, i + 1, p[i].getName(), wcf));
                ws.addCell(new Label(2, i + 1, p[i].getNumber(), wcf));
                if(i == 0)
                    wcf = new WritableCellFormat();
            }

            wwb.write();
            wwb.close();
        } catch (IOException e){
        } catch (RowsExceededException e){
        } catch (WriteException e){}
    }

    


    public static void main(String [] args){
        String fileName = "test.xls";

    	String output = "编号:123456 企业名称:4321公司";
    	
        exportExcel(fileName, output);
        System.out.println("成功导出数据到Excel文件(" + fileName + ")了!!!");

    }
}