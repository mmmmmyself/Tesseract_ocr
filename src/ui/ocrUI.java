package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.JSeparator;

import java.awt.Button;
import java.awt.event.*;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdk.nashorn.internal.ir.Flags;

import jxl.Cell;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.SheetSettings;
import jxl.format.CellFormat;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.WritableCell;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import Util.*;

import helper.*;

public class ocrUI {

	private JFrame frmOcr;
    private JTextField field1;
    private JComboBox cmb_selectlang;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ocrUI window = new ocrUI();
					window.frmOcr.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 创建窗体
	 * @throws Exception 
	 */
	public ocrUI() throws Exception {
		initialize();
		ws = exportExcelUtil.createExcel("test1.xls");
	}
	
	/**
	 * 文件选择器
	 */
	   static WritableSheet ws;
	   int flag=1;
	public static String filepath;
	public static void fileChooser() throws Exception {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JPG & PNG Images", "jpg", "png");
	    //设置文件类型
	    chooser.setFileFilter(filter);
	    //设置只能选择目录
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    //打开选择器面板
	    int returnVal = chooser.showOpenDialog(new JPanel());  
        //返回输出文件绝对路径 （这里缺省一个/，识别的时候的字符串需要再加一个/或者把/转化为\）
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("你打开的文件所在目录是: " +
	            chooser.getSelectedFile().getAbsoluteFile());
	       filepath =chooser.getSelectedFile().getAbsoluteFile().toString();
	       filepath += '\\';
           File[] imagefile = ImgReadUtil.myreader(filepath);
           for (File f1 : imagefile){
              String Abspath = filepath +f1.getName();
              String reg=".*png.*";
              if (!f1.getName().matches(reg)) 
              {
                  System.out.println("当前读取到的文件不是图片。");
                  exportExcelUtil.closeWrite();
            	  break;
              }
               //图片二值化
               //BufferedImage imgsrc = ImgInverseUtil.file2img(Abspath);
               //图片反色
               //BufferedImage img = ImgInverseUtil.img_inverse(imgsrc);
               //图片保存
               //ImgInverseUtil.img2file(img,"jpg",Abspath);
               //图片切割
               //ImgCutUtil.cut(0, 0, 600, 75, Abspath, Abspath);  
               
               //输出识别结果
               System.out.println(Abspath);
               String result = OCRHelper.recognizeText(new File(Abspath), "png");
               result = result.replaceAll("\r|\n", ""); 
               System.out.println(result);
               //结果导入到表          
               exportExcelUtil.exportExcel(result,ws);
               //exportExcelUtil.exportExcel("test.xls", OCRHelper.recognizeText(new File(Abspath), "png"));
	    }}
	}
	

	/**
	 * 实现窗体
	 */
	public  void initialize() {
		frmOcr = new JFrame();
		frmOcr.getContentPane().setFont(new Font("宋体", Font.PLAIN, 14));
		frmOcr.getContentPane().setBackground(Color.WHITE);
		frmOcr.setResizable(false);
		frmOcr.setBackground(SystemColor.activeCaption);
		frmOcr.setTitle("ocr\u6587\u5B57\u8BC6\u522B");//ocr文字识别
		frmOcr.setFont(new Font("宋体", Font.PLAIN, 14));
		frmOcr.setBounds(100, 100, 720, 616);
		frmOcr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOcr.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ocrUI.class.getResource("/img/selectpic.PNG")));
		lblNewLabel.setBounds(10, 10, 212, 33);
		frmOcr.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(ocrUI.class.getResource("/img/selectlang.png")));
		lblNewLabel_1.setBounds(10, 158, 212, 33);
		frmOcr.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(ocrUI.class.getResource("/img/selectform.png")));
		lblNewLabel_2.setBounds(10, 293, 212, 33);
		frmOcr.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(ocrUI.class.getResource("/img/translate.png")));
		lblNewLabel_3.setBounds(10, 435, 143, 33);
		frmOcr.getContentPane().add(lblNewLabel_3);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 146, 694, 2);
		frmOcr.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 281, 694, 2);
		frmOcr.getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 423, 694, 2);
		frmOcr.getContentPane().add(separator_2);
		
		
		//图片路径选择按钮
		Button btn_selectpic = new Button("\u9009\u62E9\u56FE\u7247");//选择图片
		btn_selectpic.setFont(new Font("宋体", Font.PLAIN, 16));
		btn_selectpic.setBackground(Color.LIGHT_GRAY);
		btn_selectpic.setBounds(73, 57, 114, 33);
		frmOcr.getContentPane().add(btn_selectpic);
		btn_selectpic.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {   //显示文件选择器
				  try {
					fileChooser();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 /* ImgReadUtil.myreader(null);
				  ImgInverseUtil.file2img(null);
				  ImgInverseUtil.img_inverse(null);
				  ImgInverseUtil.img2file(null, null, null);
				  ImgCutUtil.cut(30, 50, 300, 400, sourcePath, descpath);;
				  */
			  }
		});

		//选择图片语言
		cmb_selectlang = new JComboBox();
		field1 = new JTextField(20);
		cmb_selectlang.setForeground(Color.BLACK);
		cmb_selectlang.setBackground(Color.LIGHT_GRAY);
		cmb_selectlang.setFont(new Font("宋体", Font.PLAIN, 16));
		cmb_selectlang.addItem("\u7B80\u4F53\u4E2D\u6587");//简体中文
		cmb_selectlang.addItem("\u82f1\u6587");//英文
		cmb_selectlang.setBounds(73, 212, 137, 27);
		frmOcr.getContentPane().add(cmb_selectlang);
		frmOcr.getContentPane().add(field1);
		cmb_selectlang.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String item=(String) cmb_selectlang.getSelectedItem();
				if("\u7B80\u4F53\u4E2D\u6587".equals(item)){
					field1.setText("");
				}else{
					field1.setText(item);
				}
			}
		});
		
		//转换按钮
		Button translate = new Button("\u5F00\u59CB\u8F6C\u6362");//开始转换
		translate.setBackground(Color.LIGHT_GRAY);
		translate.setFont(new Font("宋体", Font.PLAIN, 16));
		translate.setBounds(77, 486, 114, 33);
		frmOcr.getContentPane().add(translate);
		translate.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {
				 //OCRHelper.recognizeText();
			  }
		});
		
		//导出到表按钮
		Button importxls = new Button("\u5bfc\u51fa\u5230\u8868");//导出到表
		importxls.setBackground(Color.LIGHT_GRAY);
		importxls.setFont(new Font("宋体", Font.PLAIN, 16));
		importxls.setBounds(73, 350, 114, 33);
		frmOcr.getContentPane().add(importxls);
		importxls.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {
				  //exportExcel("test.xls",);
			  }
		});
		
		/*cmb_selectform = new JComboBox();
		field2 = new JTextField(20);
		cmb_selectform.addItem(".xls");
		cmb_selectform.addItem("other");
		cmb_selectform.setFont(new Font("宋体", Font.PLAIN, 16));
		cmb_selectform.setBackground(Color.LIGHT_GRAY);
		cmb_selectform.setBounds(73, 350, 137, 27);
		frmOcr.getContentPane().add(cmb_selectform);
		frmOcr.getContentPane().add(field2);
		cmb_selectform.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String item=(String) cmb_selectform.getSelectedItem();
				if(".xls".equals(item)){
					field2.setText("");
				}else{
					field2.setText(item);
				}
			}
		});*/
	}
}
