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

public class FrmConsumerAdd extends JDialog implements ActionListener{
	private BeanConsumer Consumer=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelName = new JLabel("������");
	private JLabel labelAcc = new JLabel("�˺ţ�");
	private JLabel labelPwd = new JLabel("���룺");
	private JLabel labelPhone = new JLabel("�绰��");
	private JLabel labelVip = new JLabel("��Ա��ֹ���ڣ�");
	private JLabel labelSex = new JLabel("�Ա�");
	private JComboBox cmbSex=new JComboBox(new String[]{"��","Ů"});
	private JTextField edtName = new JTextField(20);
	private JTextField edtAcc = new JTextField(20);
	private JTextField edtPwd = new JTextField(20);
	private JTextField edtPhone = new JTextField(20);
	private JTextField edtVip = new JTextField(20);
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmConsumerAdd(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelAcc);
		workPane.add(edtAcc);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPhone);
		workPane.add(edtPhone);
		workPane.add(labelSex);
		workPane.add(this.cmbSex);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(400, 300);
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
			String Name=this.edtName.getText();
			String Acc=this.edtAcc.getText();
			String Pwd=this.edtPwd.getText();
			String Phone=this.edtPhone.getText();
			String Sex=this.cmbSex.getSelectedItem().toString();
			try {
				PPutil.ConsumerManager.createConsumer(Name,Acc,Pwd,Phone,Sex);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
