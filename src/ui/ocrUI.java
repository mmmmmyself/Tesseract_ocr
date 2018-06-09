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
	 * ��������
	 * @throws Exception 
	 */
	public ocrUI() throws Exception {
		initialize();
		ws = exportExcelUtil.createExcel("test1.xls");
	}
	
	/**
	 * �ļ�·��ѡ����
	 */
	static WritableSheet ws;
	int flag=1;
	public static String filepath;
	public static void fileChooser() throws Exception {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JPG & PNG Images", "jpg", "png");
	    //�����ļ�����
	    chooser.setFileFilter(filter);
	    //����ֻ��ѡ��Ŀ¼
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    //��ѡ�������
	    int returnVal = chooser.showOpenDialog(new JPanel());  
        //��������ļ�����·�� ������ȱʡһ��/��ʶ���ʱ����ַ�����Ҫ�ټ�һ��/���߰�/ת��Ϊ\��
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("��򿪵��ļ�����Ŀ¼��: " +
	            chooser.getSelectedFile().getAbsoluteFile() + "\n");
	       textArea.append("��򿪵��ļ�����Ŀ¼��: " +
		            chooser.getSelectedFile().getAbsoluteFile() + "\n");
	       filepath =chooser.getSelectedFile().getAbsoluteFile().toString();
	       filepath += '\\';
	       
	       //������ʱ�ļ���
	       String Savepath =chooser.getSelectedFile().getAbsoluteFile() +"\\temp";
           Savepath = Savepath.replace("\\", "\\\\");
           CreateFileUtil.createFile(Savepath);
          }
	}
	
	/**
	 * ͼƬ����ת����
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
                System.out.println("��ǰ��ȡ�����ļ�����ͼƬ��" + "\n");
                textArea2.append("��ǰ��ȡ�����ļ�����ͼƬ��" + "\n");
                //exportExcelUtil.closeWrite();
          	  break;
            }
            //ͼƬ�и�
            ImgCutUtil.cut(0, 0, 600, 75, Abspath, readingPhoto);  
            //ͼƬ��ֵ��
            //BufferedImage imgsrc = ImgInverseUtil.file2img(Abspath);
            //ͼƬ��ɫ
            //BufferedImage img = ImgInverseUtil.img_inverse(imgsrc);
            //ͼƬ����
            //ImgInverseUtil.img2file(img,"jpg",Abspath);
            
            //���ʶ����
            System.out.println("���ڶ�ȡ"+f1.getName());
            textArea2.append(Abspath + "\n");
            String result = OCRHelper.recognizeText(new File(readingPhoto), "png");
            result = result.replaceAll("\r|\n", ""); 
            exportExcelUtil.exportExcel(result,ws);
            System.out.println(result);
            textArea2.append(result + "\n");
	    }
	}
	
	/**
	 * ������
	 */
	public static void fileExport() throws Exception {
		exportExcelUtil.closeWrite();
		System.out.println("�ɹ��������ݵ�Excel�ļ���!!!");
		textArea3.append("�ɹ��������ݵ�Excel�ļ���!!!");
//		File[] imagefile = ImgReadUtil.myreader(filepath);
//		for (File f1 : imagefile){
//            String Abspath = filepath +f1.getName(); 
//            String result = OCRHelper.recognizeText(new File(Abspath), "png");
//            result = result.replaceAll("\r|\n", ""); 
            //������뵽��          
            //exportExcelUtil.exportExcel(result,ws);
            //exportExcelUtil.exportExcel("test.xls", OCRHelper.recognizeText(new File(Abspath), "png"));
//	    }
	}
	
	/**
	 * ʵ�ִ���
	 */
	public  void initialize() {
		frmOcr = new JFrame();
		frmOcr.getContentPane().setFont(new Font("����", Font.PLAIN, 14));
		frmOcr.getContentPane().setBackground(Color.WHITE);
		frmOcr.setResizable(false);
		frmOcr.setBackground(SystemColor.activeCaption);
		frmOcr.setTitle("ocr\u6587\u5B57\u8BC6\u522B");//ocr����ʶ��
		frmOcr.setFont(new Font("����", Font.PLAIN, 14));
		frmOcr.setBounds(100, 100, 720, 616);
		frmOcr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOcr.getContentPane().setLayout(null);
		
		//4����ǩͼƬ
		//ѡ��ͼƬ·��
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ocrUI.class.getResource("/img/selectpic.PNG")));
		lblNewLabel.setBounds(10, 10, 212, 33);
		frmOcr.getContentPane().add(lblNewLabel);
		//ѡ��ͼƬ��������
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(ocrUI.class.getResource("/img/selectlang.png")));
		lblNewLabel_1.setBounds(10, 158, 212, 33);
		frmOcr.getContentPane().add(lblNewLabel_1);
		//ͼƬ����ת��
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(ocrUI.class.getResource("/img/translate.png")));
		lblNewLabel_2.setBounds(10, 293, 143, 33);
		frmOcr.getContentPane().add(lblNewLabel_2);
		//��������
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(ocrUI.class.getResource("/img/export.png")));
		lblNewLabel_3.setBounds(10, 435, 143, 33);
		frmOcr.getContentPane().add(lblNewLabel_3);	
		
		//�ָ���
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 146, 694, 2);
		frmOcr.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 281, 694, 2);
		frmOcr.getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 423, 694, 2);
		frmOcr.getContentPane().add(separator_2);
		
		//�ı�����
		//��ʾͼƬ·��ѡ�����ı���
		textArea = new TextArea();
		textArea.setBounds(246, 10, 440, 115);
		frmOcr.getContentPane().add(textArea);
		//��ʾͼƬ��������ѡ�����ı���
		textArea1 = new TextArea();
		textArea1.setBounds(246, 160, 440, 115);
		frmOcr.getContentPane().add(textArea1);
		//��ʾͼƬ����ת������ı���
		textArea2 = new TextArea();
		textArea2.setBounds(246, 295, 440, 115);
		frmOcr.getContentPane().add(textArea2);
		//��ʾת�������������ı���
		textArea3 = new TextArea();
		textArea3.setBounds(246, 435, 440, 115);
		frmOcr.getContentPane().add(textArea3);
		
		//ͼƬ·��ѡ��ť
		Button btn_selectpic = new Button("\u9009\u62E9\u56FE\u7247");//ѡ��ͼƬ
		btn_selectpic.setFont(new Font("����", Font.PLAIN, 16));
		btn_selectpic.setBackground(Color.LIGHT_GRAY);
		btn_selectpic.setBounds(73, 57, 114, 33);
		frmOcr.getContentPane().add(btn_selectpic);
		btn_selectpic.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {   //�ļ�·��ѡ����
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

		//ѡ��ͼƬ�������������б��
		cmb_selectlang = new JComboBox();
		field1 = new JTextField(20);
		cmb_selectlang.setForeground(Color.BLACK);
		cmb_selectlang.setBackground(Color.LIGHT_GRAY);
		cmb_selectlang.setFont(new Font("����", Font.PLAIN, 16));
		cmb_selectlang.addItem("\u7B80\u4F53\u4E2D\u6587");//��������
		cmb_selectlang.addItem("\u82f1\u6587");//Ӣ��
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
					System.out.println("��ѡ���ͼƬ����������: \u7B80\u4F53\u4E2D\u6587" + "\n");
					textArea1.append("��ѡ���ͼƬ����������: \u7B80\u4F53\u4E2D\u6587" + "\n");
				}else{
					field1.setText(item);
					System.out.println("��ѡ���ͼƬ����������: " + item + "\n");
					textArea1.append("��ѡ���ͼƬ����������: " + item + "\n");
				}
			}
		});
		
		//ͼƬ����ת����ť
		Button translate = new Button("\u5F00\u59CB\u8F6C\u6362");//��ʼת��
		translate.setBackground(Color.LIGHT_GRAY);
		translate.setFont(new Font("����", Font.PLAIN, 16));
		translate.setBounds(73, 350, 114, 33);
		frmOcr.getContentPane().add(translate);
		translate.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {   //ͼƬ����ת����
				  try {
					  fileTranslate();
			      } catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			      }
			  }
		});
		
		//��������ť
		Button importxls = new Button("\u5bfc\u51fa\u5230\u8868");//��������
		importxls.setBackground(Color.LIGHT_GRAY);
		importxls.setFont(new Font("����", Font.PLAIN, 16));
		importxls.setBounds(77, 486, 114, 33);
		frmOcr.getContentPane().add(importxls);
		importxls.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {   //������
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

