package ui;

import helper.OCRHelper;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.write.WritableSheet;
import Util.CreateFileUtil;
import Util.ImgCutUtil;
import Util.ImgInverseUtil;
import Util.ImgReadUtil;
import Util.exportExcelUtil;

public class ocrUI {

	private static TextArea textArea,textArea1,textArea2,textArea3;
	private static JFrame frmOcr;
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
	 * 文件路径选择器
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
	            chooser.getSelectedFile().getAbsoluteFile() + "\n");
	       textArea.append("你打开的文件所在目录是: " +
		            chooser.getSelectedFile().getAbsoluteFile() + "\n");
	       filepath =chooser.getSelectedFile().getAbsoluteFile().toString();
	       filepath += '\\';
	       
	       //创建临时文件夹
	       String Savepath =chooser.getSelectedFile().getAbsoluteFile() +"\\temp";
           Savepath = Savepath.replace("\\", "\\\\");
           CreateFileUtil.createFile(Savepath);
          }
	}
	
	/**
	 * 图片文字转换器
	 */

	public static void fileTranslate() throws Exception {
		File[] imagefile = ImgReadUtil.myreader(filepath);
		for (File f1 : imagefile){
            String Abspath = filepath +f1.getName();
            String Savepath = filepath + "temp";
            String readingPhoto = Savepath+"\\"+f1.getName();
            String reg=".*png.*";
            if (!f1.getName().matches(reg)) 
            {
                System.out.println("当前读取到的文件不是图片。" + "\n");
                textArea2.append("当前读取到的文件不是图片。" + "\n");
                //exportExcelUtil.closeWrite();
          	  break;
            }
            //图片切割
            ImgCutUtil.cut(0, 0, 600, 75, Abspath, readingPhoto);  
            //图片二值化
            //BufferedImage imgsrc = ImgInverseUtil.file2img(Abspath);
            //图片反色
            //BufferedImage img = ImgInverseUtil.img_inverse(imgsrc);
            //图片保存
            //ImgInverseUtil.img2file(img,"jpg",Abspath);
            
            //输出识别结果
            System.out.println("正在读取"+f1.getName());
            textArea2.append(Abspath + "\n");
            String result = OCRHelper.recognizeText(new File(readingPhoto), "png");
            result = result.replaceAll("\r|\n", ""); 
            exportExcelUtil.exportExcel(result,ws);
            System.out.println(result);
            textArea2.append(result + "\n");
	    }
	}
	
	/**
	 * 导出器
	 */
	public static void fileExport() throws Exception {
		exportExcelUtil.closeWrite();
		System.out.println("成功导出数据到Excel文件了!!!");
		textArea3.append("成功导出数据到Excel文件了!!!");
//		File[] imagefile = ImgReadUtil.myreader(filepath);
//		for (File f1 : imagefile){
//            String Abspath = filepath +f1.getName(); 
//            String result = OCRHelper.recognizeText(new File(Abspath), "png");
//            result = result.replaceAll("\r|\n", ""); 
            //结果导入到表          
            //exportExcelUtil.exportExcel(result,ws);
            //exportExcelUtil.exportExcel("test.xls", OCRHelper.recognizeText(new File(Abspath), "png"));
//	    }
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
		
		//4个标签图片
		//选择图片路径
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ocrUI.class.getResource("/img/selectpic.PNG")));
		lblNewLabel.setBounds(10, 10, 212, 33);
		frmOcr.getContentPane().add(lblNewLabel);
		//选择图片文字语言
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(ocrUI.class.getResource("/img/selectlang.png")));
		lblNewLabel_1.setBounds(10, 158, 212, 33);
		frmOcr.getContentPane().add(lblNewLabel_1);
		//图片文字转换
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(ocrUI.class.getResource("/img/translate.png")));
		lblNewLabel_2.setBounds(10, 293, 143, 33);
		frmOcr.getContentPane().add(lblNewLabel_2);
		//导出到表
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(ocrUI.class.getResource("/img/export.png")));
		lblNewLabel_3.setBounds(10, 435, 143, 33);
		frmOcr.getContentPane().add(lblNewLabel_3);	
		
		//分割线
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 146, 694, 2);
		frmOcr.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 281, 694, 2);
		frmOcr.getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 423, 694, 2);
		frmOcr.getContentPane().add(separator_2);
		
		//文本区域
		//显示图片路径选择结果文本框
		textArea = new TextArea();
		textArea.setBounds(246, 10, 440, 115);
		frmOcr.getContentPane().add(textArea);
		//显示图片文字语言选择结果文本框
		textArea1 = new TextArea();
		textArea1.setBounds(246, 160, 440, 115);
		frmOcr.getContentPane().add(textArea1);
		//显示图片文字转换结果文本框
		textArea2 = new TextArea();
		textArea2.setBounds(246, 295, 440, 115);
		frmOcr.getContentPane().add(textArea2);
		//显示转换结果导出情况文本框
		textArea3 = new TextArea();
		textArea3.setBounds(246, 435, 440, 115);
		frmOcr.getContentPane().add(textArea3);
		
		//图片路径选择按钮
		Button btn_selectpic = new Button("\u9009\u62E9\u56FE\u7247");//选择图片
		btn_selectpic.setFont(new Font("宋体", Font.PLAIN, 16));
		btn_selectpic.setBackground(Color.LIGHT_GRAY);
		btn_selectpic.setBounds(73, 57, 114, 33);
		frmOcr.getContentPane().add(btn_selectpic);
		btn_selectpic.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {   //文件路径选择器
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

		//选择图片文字语言下拉列表框
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
					System.out.println("你选择的图片文字语言是: \u7B80\u4F53\u4E2D\u6587" + "\n");
					textArea1.append("你选择的图片文字语言是: \u7B80\u4F53\u4E2D\u6587" + "\n");
				}else{
					field1.setText(item);
					System.out.println("你选择的图片文字语言是: " + item + "\n");
					textArea1.append("你选择的图片文字语言是: " + item + "\n");
				}
			}
		});
		
		//图片文字转换按钮
		Button translate = new Button("\u5F00\u59CB\u8F6C\u6362");//开始转换
		translate.setBackground(Color.LIGHT_GRAY);
		translate.setFont(new Font("宋体", Font.PLAIN, 16));
		translate.setBounds(73, 350, 114, 33);
		frmOcr.getContentPane().add(translate);
		translate.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {   //图片文字转换器
				  try {
					  fileTranslate();
			      } catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			      }
			  }
		});
		
		//导出到表按钮
		Button importxls = new Button("\u5bfc\u51fa\u5230\u8868");//导出到表
		importxls.setBackground(Color.LIGHT_GRAY);
		importxls.setFont(new Font("宋体", Font.PLAIN, 16));
		importxls.setBounds(77, 486, 114, 33);
		frmOcr.getContentPane().add(importxls);
		importxls.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {   //导出器
				  try {
					  fileExport();
			      } catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			      }
			  }
		});
	}
}

