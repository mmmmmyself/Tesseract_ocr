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
     * @param fileName        ����ļ������ƣ�������Ϊ����·����Ҳ������Ϊ���·��
     * @param output        ���ݵ����ݣ����⼴ΪrecognizeText�Ľ��
     */

	public static void exportExcel(String fileName,String output) {
    	//��ȡһ�ν����Ĺ�˾���ƺͱ��
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
            WritableSheet ws = wwb.createSheet("��˾���Ƽ�����б�", 0);        // ����һ��������
            String[] title = {"��˾����","��ҵע���"};  

            //    ���õ�Ԫ������ָ�ʽ
            WritableFont wf = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf.setAlignment(Alignment.CENTRE); 
            ws.setColumnView(0, 300);
            ws.setColumnView(1, 300);
            //�������
            for(int i=0;i<title.length;i++)
            {
                ws.addCell(new Label(i,0, title[i], wcf));  
            }
            
            //ɸѡ����˾���ƺͱ��
            int j = 0;
        	while (true) {
        		if (j==0) {
        		name_location[0] = output.indexOf(58);        		
        		number_location[0] = output.indexOf(58,name_location[0]+1);
        		end_location[0] = output.indexOf('˾');
        		}
        		else if (j!=0) {
        		name_location[j] = output.indexOf(58,number_location[j-1]+1);
        		number_location[j] = output.indexOf(58,name_location[j]+1);
        		end_location[j] = output.indexOf('˾',end_location[j-1]+1);
        		}
        		String number = output.substring(name_location[j]+1,number_location[j]-5);
        		String name = output.substring(number_location[j]+1,end_location[j]+1);
        		//����ű��浽Data��(����һ����)
        		Data d0 = new Data(number,name);
        		content.add(d0);
        		if (number_location[j] == output.lastIndexOf(58)){break;}
        		j++;
        	}
        	
        	
            //    ������ݵ�����
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

    	String output = "��ҵע���:913302055612570177 ��ҵ����:��������Ľ�е����������޹�˾ ��ҵע���:913302010847810855 ��ҵ����:����̫ƽ������Ƽ����޹�˾ ��ҵע���:91120222671480180P ��ҵ����:���ʱװ���ۣ�������޹�˾";
        exportExcel(fileName, output);
        System.out.println("�ɹ��������ݵ�Excel�ļ�(" + fileName + ")��!!!");
    }*/
}