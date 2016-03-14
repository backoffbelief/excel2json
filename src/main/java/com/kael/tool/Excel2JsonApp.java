package com.kael.tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class Excel2JsonApp extends JFrame {

	private final class ClickActionLister implements ActionListener {
		private Excel2JsonApp app;
		
		public ClickActionLister(Excel2JsonApp app) {
			super();
			this.app = app;
		}


		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == jButton || e.getSource() == jButton0){
				JFileChooser jfc = new JFileChooser(jTextField.getText());
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setFileFilter(new FileFilter() {

					@Override
					public String getDescription() {
						return "xls";
					}

					private String getExtension(File f) {
						String name = f.getName();
						int index = name.lastIndexOf('.');
						if (index == -1){
							return "";
						}
						return name.substring(index + 1).toLowerCase();
					}

					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return true;
						}
						String extension = getExtension(f);
						return "xls".equals(extension.toLowerCase()) || "xlsx".equals(extension.toLowerCase());
					}
				});
				int result =  jfc.showDialog(new JLabel(), "选择文件夹");
				switch (result) {
				case JFileChooser.APPROVE_OPTION:
					File file = jfc.getSelectedFile();
					if(file == null)return ;
					if (file.isFile()) {
//							jlabel0.setText("选中的文件:"+ file.getAbsolutePath());
					}else if(file.isDirectory()){
						if(e.getSource() == jButton)
						jTextField.setText(file.getAbsolutePath());
						else if(e.getSource() == jButton0)
							jTextField0.setText(file.getAbsolutePath());
					}
					break;
				case JFileChooser.CANCEL_OPTION:
				case JFileChooser.ERROR_OPTION:

				default:
					break;
				}
				
			} else if(e.getSource() == jButton1){
				File file = new File(jTextField.getText());
				if(file.isDirectory()){
					try {
						Excel2JsonUtil.make(file, jTextField0.getText());
						JOptionPane.showMessageDialog(app, "","生成json成功!",JOptionPane.DEFAULT_OPTION);
					} catch (Exception e1) {
						if(e1 instanceof ReadExcelException){
							JOptionPane.showMessageDialog(app, ((ReadExcelException)e1).getMessage(),"生成json失败!",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			}
			
	}


	private JButton jButton;
	private JButton jButton0;
	private JButton jButton1;
	private JTextField jTextField;
	private JTextField jTextField0;
//	private JLabel jlabel2;
//	private Clipboard clipboard;
	
	public Excel2JsonApp() throws HeadlessException {
		setTitle("excel2json");
		setVisible(true);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(d.width / 2 - 300, d.height / 2 - 200, 600, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComp();
	}
	
	
	private void initComp(){
		
		JPanel jPanel0 = new JPanel(new BorderLayout());
		JPanel jPanel1 = new JPanel(new GridLayout(2, 1));
		jPanel1.add(new JLabel("excel目录"));
		jPanel1.add(new JLabel("输出的json目录"));
		
		jPanel0.add(jPanel1,BorderLayout.WEST);
		
		jTextField = new JTextField(25);
		jTextField0 = new JTextField(25);
		
		jTextField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				checkAndSet(jTextField.getText());
			}
			
		});
		jTextField0.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				checkAndSet(jTextField0.getText());
			}
			
		});
		
		JPanel jPanel2 = new JPanel(new GridLayout(2, 1));
		jPanel2.add(jTextField);
		jPanel2.add(jTextField0);
		jPanel0.add(jPanel2);
		
		add(jPanel0);
		
		jButton = new JButton("选择文件夹");
		jButton0 = new JButton("选择文件夹");
		
//		clipboard = this.getToolkit().getSystemClipboard();

		ClickActionLister click = new ClickActionLister(this);
		jButton.addActionListener(click);
		
		jButton0.addActionListener(click);
		jButton1 = new JButton("生成json");
		jButton1.addActionListener(click);
		JPanel jPanel = new JPanel(new GridLayout(2, 1));
		jPanel.add(jButton);
		jPanel.add(jButton0);
		add(jPanel, BorderLayout.EAST);
		
		add(jButton1,BorderLayout.SOUTH);
	}
	
	private void checkAndSet(String text) {
		if(null == text || "".equals(text.trim())){
			return ;
		}
	}


}
