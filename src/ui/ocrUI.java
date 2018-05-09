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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

public class ocrUI {

	private JFrame frmOcr;

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
	 * Create the application.
	 */
	public ocrUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOcr = new JFrame();
		frmOcr.getContentPane().setFont(new Font("宋体", Font.PLAIN, 14));
		frmOcr.getContentPane().setBackground(Color.WHITE);
		frmOcr.setResizable(false);
		frmOcr.setBackground(SystemColor.activeCaption);
		frmOcr.setTitle("ocr\u6587\u5B57\u8BC6\u522B");
		frmOcr.setFont(new Font("宋体", Font.PLAIN, 14));
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
		
		Button btn_selectpic = new Button("\u9009\u62E9\u56FE\u7247");
		btn_selectpic.setFont(new Font("宋体", Font.PLAIN, 16));
		btn_selectpic.setBackground(Color.LIGHT_GRAY);
		btn_selectpic.setBounds(73, 57, 114, 33);
		frmOcr.getContentPane().add(btn_selectpic);
		
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
		
		JComboBox cmb_selectlang = new JComboBox();
		cmb_selectlang.setForeground(Color.BLACK);
		cmb_selectlang.setBackground(Color.LIGHT_GRAY);
		cmb_selectlang.setFont(new Font("宋体", Font.PLAIN, 16));
		cmb_selectlang.setModel(new DefaultComboBoxModel(new String[] {"\u7B80\u4F53\u4E2D\u6587", "\u5176\u4ED6\u8BED\u8A00"}));
		cmb_selectlang.setBounds(73, 212, 137, 27);
		frmOcr.getContentPane().add(cmb_selectlang);
		
		Button translate = new Button("\u5F00\u59CB\u8F6C\u6362");
		translate.setBackground(Color.LIGHT_GRAY);
		translate.setFont(new Font("宋体", Font.PLAIN, 16));
		translate.setBounds(77, 486, 114, 33);
		frmOcr.getContentPane().add(translate);
		
		JComboBox cmb_selectform = new JComboBox();
		cmb_selectform.setModel(new DefaultComboBoxModel(new String[] {".xls", "other"}));
		cmb_selectform.setFont(new Font("宋体", Font.PLAIN, 16));
		cmb_selectform.setBackground(Color.LIGHT_GRAY);
		cmb_selectform.setBounds(73, 350, 137, 27);
		frmOcr.getContentPane().add(cmb_selectform);
	}
}
