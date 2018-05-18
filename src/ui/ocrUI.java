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

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Util.*;

import helper.*;

public class ocrUI {

	private JFrame frmOcr;
    private JTextField field1,field2;
    private JComboBox cmb_selectlang,cmb_selectform;
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
	 */
	public ocrUI() {
		initialize();
	}
	
	/**
	 * �ļ�ѡ����
	 */
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
	            chooser.getSelectedFile().getAbsoluteFile());
	       filepath =chooser.getSelectedFile().getAbsoluteFile().toString();
	       filepath += '\\';
           File[] imagefile = ImgReadUtil.myreader(filepath);
           for (File f1 : imagefile){
              String Abspath = filepath +f1.getName();
               //ͼƬ��ֵ��
               //BufferedImage imgsrc = ImgInverseUtil.file2img(Abspath);
               //ͼƬ��ɫ
               //BufferedImage img = ImgInverseUtil.img_inverse(imgsrc);
               //ͼƬ����
               //ImgInverseUtil.img2file(img,"jpg",Abspath);
               //ͼƬ�и�
               //ImgCutUtil.cut(0, 0, 600, 75, Abspath, Abspath);  
               
               //���ʶ����
              //System.out.println(Abspath);
              System.out.println(OCRHelper.recognizeText(new File(Abspath), "png"));  
             // exportExcelUtil.exportExcel("test.xls", OCRHelper.recognizeText(new File(Abspath), "png"));
	    }}
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
		frmOcr.setTitle("ocr\u6587\u5B57\u8BC6\u522B");
		frmOcr.setFont(new Font("����", Font.PLAIN, 14));
		frmOcr.setBounds(100, 100, 720, 616);
		frmOcr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOcr.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ocrUI.class.getResource("/img/selectpic.PNG")));
		lblNewLabel.setBounds(10, 10, 212, 33);
		frmOcr.getContentPane().add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 146, 694, 2);
		frmOcr.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 281, 694, 2);
		frmOcr.getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 423, 694, 2);
		frmOcr.getContentPane().add(separator_2);
		
		
		
		//ͼƬ·��ѡ��ť
		Button btn_selectpic = new Button("\u9009\u62E9\u56FE\u7247");//ѡ��ͼƬ
		btn_selectpic.setFont(new Font("����", Font.PLAIN, 16));
		btn_selectpic.setBackground(Color.LIGHT_GRAY);
		btn_selectpic.setBounds(73, 57, 114, 33);
		frmOcr.getContentPane().add(btn_selectpic);
		btn_selectpic.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {   //��ʾ�ļ�ѡ����
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

		//ͼƬѡ���ļ�
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
		
		cmb_selectlang = new JComboBox();
		field1 = new JTextField(20);
		cmb_selectlang.setForeground(Color.BLACK);
		cmb_selectlang.setBackground(Color.LIGHT_GRAY);
		cmb_selectlang.setFont(new Font("����", Font.PLAIN, 16));
		cmb_selectlang.addItem("\u7B80\u4F53\u4E2D\u6587");//��������
		cmb_selectlang.addItem("\u5176\u4ED6\u8BED\u8A00");//��������
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
		
		Button translate = new Button("\u5F00\u59CB\u8F6C\u6362");//��ʼת��
		translate.setBackground(Color.LIGHT_GRAY);
		translate.setFont(new Font("����", Font.PLAIN, 16));
		translate.setBounds(77, 486, 114, 33);
		frmOcr.getContentPane().add(translate);
		translate.addMouseListener(new MouseAdapter()
		{
			  public void mouseClicked(MouseEvent e)
			  {
				 // OCRHelper.recognizeText();
			  }
		});
		
		cmb_selectform = new JComboBox();
		field2 = new JTextField(20);
		cmb_selectform.addItem(".xls");
		cmb_selectform.addItem("other");
		cmb_selectform.setFont(new Font("����", Font.PLAIN, 16));
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
		});	
	}
}
