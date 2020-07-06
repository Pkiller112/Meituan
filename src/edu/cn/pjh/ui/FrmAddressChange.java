package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanAddress;
import edu.cn.pjh.model.BeanConsumer;
import edu.cn.pjh.util.BaseException;

public class FrmAddressChange extends JDialog implements ActionListener{
	BeanAddress address=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelPro = new JLabel("省：");
	private JLabel labelCity = new JLabel("市：");
	private JLabel labelArea = new JLabel("区：");
	private JLabel labelInfo = new JLabel("详细地址：");
	private JLabel labelComm = new JLabel("联系人：");
	private JLabel labelPho = new JLabel("联系电话：");
	private JTextField edtPro = new JTextField(20);
	private JTextField edtCity = new JTextField(20);
	private JTextField edtArea = new JTextField(20);
	private JTextField edtInfo  = new JTextField(20);
	private JTextField edtComm  = new JTextField(20);
	private JTextField edtPho = new JTextField(20);
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmAddressChange(JDialog f, String s, boolean b,BeanAddress address) {
		super(f, s, b);
		this.address=address;
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
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
		this.edtPro.setText(address.getProvince());
		this.edtCity.setText(address.getCity());
		this.edtArea.setText(address.getArea());
		this.edtInfo.setText(address.getContent());
		this.edtComm.setText(address.getCommuicate());
		this.edtPho.setText(address.getPhone());
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
			
			this.address.setProvince(pro);
			this.address.setCity(city);
			this.address.setArea(area);
			this.address.setContent(info);
			this.address.setCommuicate(comm);
			this.address.setPhone(pho);
			try {
				PPutil.AddressManager.changeAddress(this.address);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
