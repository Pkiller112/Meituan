package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanConsumer;
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;

public class FrmConsumerChange extends JDialog implements ActionListener{
	private BeanConsumer consumer=null;
	SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelName = new JLabel("姓名：");
	private JLabel labelAcc = new JLabel("账号：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labelPhone = new JLabel("电话：");
	private JLabel labelVip = new JLabel("会员截止日期：");
	private JLabel labelSex = new JLabel("性别：");
	private JComboBox cmbSex=new JComboBox(new String[]{"男","女"});
	private JTextField edtName = new JTextField(20);
	private JTextField edtAcc = new JTextField(20);
	private JTextField edtPwd = new JTextField(20);
	private JTextField edtPhone = new JTextField(20);
	private JTextField edtVip = new JTextField(20);
	private Map<String,Integer> mapsex=new HashMap<String,Integer>();
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmConsumerChange(JDialog f, String s, boolean b,BeanConsumer consumer) {
		super(f, s, b);
		this.consumer=consumer;
		mapsex.put("男", 0);
		mapsex.put("女", 1);
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
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
		this.edtName.setText(consumer.getConsumername());
		this.edtAcc.setText(consumer.getConsumeraccount());
		this.edtPwd.setText(consumer.getConsumerpwd());
		this.edtPhone.setText(consumer.getConsumerphone());
		this.cmbSex.setSelectedIndex(mapsex.get(consumer.getConsumersex()));
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
			String Sex=this.cmbSex.getSelectedItem().toString();
			String phone=this.edtPhone.getText();

			BeanConsumer b=new BeanConsumer();
			b.setConsumername(Name);
			b.setConsumeraccount(Acc);
			b.setConsumerpwd(Pwd);
			b.setConsumerphone(phone);
			b.setConsumersex(Sex);
			
			b.setConsumerid(this.consumer.getConsumerid());
			
			try {
				PPutil.ConsumerManager.changeConsumer(b);
				System.out.println("1");
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
