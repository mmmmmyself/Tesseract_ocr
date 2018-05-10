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
     * ��������ΪXLS��ʽ
     * @param fileName        �ļ������ƣ�������Ϊ����·����Ҳ������Ϊ���·��
     * @param output        ���ݵ����ݣ����⼴ΪrecognizeText�Ľ��
     */

    public static void exportExcel(String fileName,String output) {
    	//��ȡһ�ν����Ĺ�˾���ƺͱ��
    	int name_location = output.indexOf(58);
    	int number_location = output.indexOf(58,name_location+1);
    	int end_location = output.indexOf('˾');
    	String number = output.substring(name_location+1,number_location-5);
    	String name = output.substring(number_location+1,end_location+1);
    	//����ű��浽Data��(����һ����)
    	Data d0 = new Data(number,name);
    	Vector<Data> content = new Vector<Data>();
    	content.add(d0);
    	//System.out.println(content.get(0));
    	WritableWorkbook wwb;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fileName);
            wwb = Workbook.createWorkbook(fos);
            WritableSheet ws = wwb.createSheet("��˾���Ƽ�����б�", 10);        // ����һ��������

            //    ���õ�Ԫ������ָ�ʽ
            WritableFont wf = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,
                    UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf.setAlignment(Alignment.CENTRE); 
            ws.setRowView(1, 500);

            //    ������ݵ�����
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

    	String output = "���:123456 ��ҵ����:4321��˾";
    	
        exportExcel(fileName, output);
        System.out.println("�ɹ��������ݵ�Excel�ļ�(" + fileName + ")��!!!");

    }
}