package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanConsumer;
import edu.cn.pjh.util.BaseException;

public class FrmAddressAdd extends JDialog implements ActionListener{
	BeanConsumer consumer=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelPro = new JLabel("ʡ��");
	private JLabel labelCity = new JLabel("�У�");
	private JLabel labelArea = new JLabel("����");
	private JLabel labelInfo = new JLabel("��ϸ��ַ��");
	private JLabel labelComm = new JLabel("��ϵ�ˣ�");
	private JLabel labelPho = new JLabel("��ϵ�绰��");
	private JTextField edtPro = new JTextField(20);
	private JTextField edtCity = new JTextField(20);
	private JTextField edtArea = new JTextField(20);
	private JTextField edtInfo  = new JTextField(20);
	private JTextField edtComm  = new JTextField(20);
	private JTextField edtPho = new JTextField(20);
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmAddressAdd(JDialog f, String s, boolean b,BeanConsumer consumer) {
		super(f, s, b);
		this.consumer=consumer;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelPro);
		workPane.add(edtPro);
		workPane.add(labelCity);
		workPane.add(edtCity);
		workPane.add(labelArea);
		workPane.add(edtArea);
		workPane.add(labelInfo);
		workPane.add(edtInfo);
		workPane.add(labelComm);
		workPane.add(edtComm);
		workPane.add(labelPho);
		workPane.add(edtPho);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(350, 300);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			String pro=this.edtPro.getText();
			String city=this.edtCity.getText();
			String area=this.edtArea.getText();
			String info=this.edtInfo.getText();
			String comm=this.edtComm.getText();
			String pho=this.edtPho.getText();
			try {
				PPutil.AddressManager.createAddress(this.consumer.getConsumerid(),pro,city,area,info,comm,pho);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
