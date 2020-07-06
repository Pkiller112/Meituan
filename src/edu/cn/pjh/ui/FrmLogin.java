package edu.cn.pjh.ui;



import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.cn.pjh.control.ConsumerManager;
import edu.cn.pjh.control.SystemUserManager;
import edu.cn.pjh.model.BeanConsumer;
import edu.cn.pjh.model.BeanUser;
import edu.cn.pjh.util.BaseException;

public class FrmLogin extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnRegister = new JButton("注册");
	private JButton btnLogin = new JButton("登陆");
	private JButton btnCancel = new JButton("退出");

	private JComboBox cmbState=new JComboBox(new String[]{"管理员","用户"});
	private JLabel labelUser = new JLabel("用户：");
	private JLabel labelPwd = new JLabel("密码：");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);

	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(cmbState);
		toolBar.add(btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);

		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 140);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.btnRegister.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			if("管理员".equals(this.cmbState.getSelectedItem().toString())) {//管理员登录
				SystemUserManager sum=new SystemUserManager();
				String userid=this.edtUserId.getText();
				String pwd=new String(this.edtPwd.getPassword());
				try {
					BeanUser user=sum.loadUser(userid);
					if(pwd.equals(user.getUserpwd())){
						SystemUserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",JOptionPane.ERROR_MESSAGE);
				}				
			}
			//用户登录
			else {
				ConsumerManager sum=new ConsumerManager();
				String csmracc=this.edtUserId.getText();
				String pwd=new String(this.edtPwd.getPassword());
				try {
					BeanConsumer user=sum.loadConsumer(csmracc);
					if(pwd.equals(user.getConsumerpwd())){
						ConsumerManager.currentConsumer=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",JOptionPane.ERROR_MESSAGE);
				}				
			}
			
			
		} 
		else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		}
		else if(e.getSource()==this.btnRegister){
			if("管理员".equals(this.cmbState.getSelectedItem().toString())) {
				FrmRegister dlg=new FrmRegister(this,"注册",true);
				dlg.setVisible(true);
			}
			else {
				FrmConsumerAdd dlg=new FrmConsumerAdd(this,"注册",true);
				dlg.setVisible(true);
			}
			
		}
	}

}
