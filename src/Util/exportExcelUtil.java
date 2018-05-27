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
	 * 表格创建
	 * 
	 * @param fileName
	 *            输出文件的名称，可以设为绝对路径，也可以设为相对路径
	 * @return
	 */
	static WritableWorkbook wwb;
	static FileOutputStream fos;

	public static WritableSheet createExcel(String fileName) throws Exception {

		fos = new FileOutputStream(fileName);
		wwb = Workbook.createWorkbook(fos);
		WritableSheet ws = wwb.createSheet("公司名称及编号列表", 0); // 创建一个工作表
		String[] title = { "公司名称", "企业注册号" };
        
		// 设置单元格的文字格式
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);
		ws.setColumnView(0, 300);
		ws.setColumnView(1, 300);
		// 添加列名
		for (int i = 0; i < title.length; i++) {
			ws.addCell(new Label(i, 0, title[i], wcf));
		}

		return ws;
	}

	
	/**
	 * 导出数据为XLS格式
	 * 
	 * @param fileName
	 *            输出文件的名称，可以设为绝对路径，也可以设为相对路径
	 * @param output
	 *            数据的内容，在外即为recognizeText的结果
	 */
	static int row = 0;

	public static void exportExcel(String output, WritableSheet ws)
			throws Exception {
		// 截取一次结果后的公司名称和编号
		int size = 100;
		Vector<Data> content = new Vector<Data>();
		int name_location = 0;
		int number_location = 0;
		int end_location = 0;

		// 筛选出公司名称和编号

		name_location = output.indexOf(58);
		number_location = output.indexOf(58, name_location + 1);
		end_location = output.indexOf('司');

		String number = output.substring(name_location + 1, number_location - 5);
		String name = output.substring(number_location + 1, end_location + 1);
		// 将编号保存到Data中(保存一组结果)
		Data d0 = new Data(number, name);
		content.add(d0);

		// 填充数据的内容
		Data p = new Data();

		p = (Data) content.get(0);
		//System.out.println(ws);
		ws.addCell(new Label(0, row + 1, p.getName()));
		ws.addCell(new Label(1, row + 1, p.getNumber()));
		row++;
		System.out.println("成功导出数据到Excel文件了!!!");
		//wwb.write();
		//wwb.close();

	}
	
	public static void  closeWrite() throws WriteException, IOException {
		wwb.write();
		wwb.close();
	}

	public static void main(String[] args) throws Exception {
		String fileName = "test.xls";

		String output = "企业注丹丑号 : 913302055612570177企业名称 : 1波中哲慕尚电子商务有限公司:";
		WritableSheet ws = createExcel(fileName);
		exportExcel(output,ws);
		System.out.println("成功导出数据到Excel文件(" + fileName + ")了!!!");
	}

}