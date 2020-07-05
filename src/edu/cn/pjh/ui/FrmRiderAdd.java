package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;


public class FrmRiderAdd extends JDialog implements ActionListener{
	private BeanRider rider=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelName = new JLabel("����������");
	private JLabel labelBorn = new JLabel("����(yyyy-MM-dd)��");
	private JLabel labelSex = new JLabel("�Ա�");
	private JLabel labelStatus = new JLabel("״̬��");
	private JComboBox cmbSex=new JComboBox(new String[]{"��","Ů"});
	private JComboBox cmbPub=new JComboBox(new String[]{"����","Ա��","����"});
	private JTextField edtName = new JTextField(20);
	private JTextField edtBorn = new JTextField(20);
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmRiderAdd(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelBorn);
		workPane.add(edtBorn);
		workPane.add(labelSex);
		workPane.add(this.cmbSex);
		workPane.add(labelStatus);
		workPane.add(this.cmbPub);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(360, 180);
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
			String Born=this.edtBorn.getText();
			String Sex=this.cmbSex.getSelectedItem().toString();
			String Status=this.cmbPub.getSelectedItem().toString();		
			try {
				PPutil.RiderManager.createRider(Name,Status,Born,Sex);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
