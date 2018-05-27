package Util;

import helper.OCRHelper;

import java.io.File;
import java.io.FileNotFoundException;
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
	 * ��񴴽�
	 * 
	 * @param fileName
	 *            ����ļ������ƣ�������Ϊ����·����Ҳ������Ϊ���·��
	 * @return
	 */
	static WritableWorkbook wwb;
	static FileOutputStream fos;

	public static WritableSheet createExcel(String fileName) throws Exception {

		fos = new FileOutputStream(fileName);
		wwb = Workbook.createWorkbook(fos);
		WritableSheet ws = wwb.createSheet("��˾���Ƽ�����б�", 0); // ����һ��������
		String[] title = { "��˾����", "��ҵע���" };
        
		// ���õ�Ԫ������ָ�ʽ
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);
		ws.setColumnView(0, 300);
		ws.setColumnView(1, 300);
		// �������
		for (int i = 0; i < title.length; i++) {
			ws.addCell(new Label(i, 0, title[i], wcf));
		}

		return ws;
	}

	
	/**
	 * ��������ΪXLS��ʽ
	 * 
	 * @param fileName
	 *            ����ļ������ƣ�������Ϊ����·����Ҳ������Ϊ���·��
	 * @param output
	 *            ���ݵ����ݣ����⼴ΪrecognizeText�Ľ��
	 */
	static int row = 0;

	public static void exportExcel(String output, WritableSheet ws)
			throws Exception {
		// ��ȡһ�ν����Ĺ�˾���ƺͱ��
		int size = 100;
		Vector<Data> content = new Vector<Data>();
		int name_location = 0;
		int number_location = 0;
		int end_location = 0;

		// ɸѡ����˾���ƺͱ��

		name_location = output.indexOf(58);
		number_location = output.indexOf(58, name_location + 1);
		end_location = output.indexOf('˾');

		String number = output.substring(name_location + 1, number_location - 5);
		String name = output.substring(number_location + 1, end_location + 1);
		// ����ű��浽Data��(����һ����)
		Data d0 = new Data(number, name);
		content.add(d0);

		// ������ݵ�����
		Data p = new Data();

		p = (Data) content.get(0);
		//System.out.println(ws);
		ws.addCell(new Label(0, row + 1, p.getName()));
		ws.addCell(new Label(1, row + 1, p.getNumber()));
		row++;
		System.out.println("�ɹ��������ݵ�Excel�ļ���!!!");
		//wwb.write();
		//wwb.close();

	}
	
	public static void  closeWrite() throws WriteException, IOException {
		wwb.write();
		wwb.close();
	}

	public static void main(String[] args) throws Exception {
		String fileName = "test.xls";

		String output = "��ҵע����� : 913302055612570177��ҵ���� : 1������Ľ�е����������޹�˾:";
		WritableSheet ws = createExcel(fileName);
		exportExcel(output,ws);
		System.out.println("�ɹ��������ݵ�Excel�ļ�(" + fileName + ")��!!!");
	}

}