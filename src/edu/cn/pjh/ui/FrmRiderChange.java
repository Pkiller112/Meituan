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
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;

public class FrmRiderChange extends JDialog implements ActionListener{
	private BeanRider rider=null;
	SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
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
	private Map<String,Integer> mapsex=new HashMap<String,Integer>();
	private Map<String,Integer> mappub=new HashMap<String,Integer>();
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmRiderChange(JDialog f, String s, boolean b,BeanRider rider) {
		super(f, s, b);
		this.rider=rider;
		mapsex.put("��", 0);
		mapsex.put("Ů", 1);
		mappub.put("����", 0);
		mappub.put("Ա��", 1);
		mappub.put("����", 2);
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
		
		this.edtName.setText(rider.getRidername());
		this.edtBorn.setText(sd.format(rider.getRiderborn()));
		this.cmbSex.setSelectedIndex(mapsex.get(rider.getRidersex()));
		this.cmbPub.setSelectedIndex(mappub.get(rider.getRiderStatus()));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			
			String Name=this.edtName.getText();
			Date Born=new Date();
			String Sex=this.cmbSex.getSelectedItem().toString();
			String Status=this.cmbPub.getSelectedItem().toString();
			try{
				Born=sd.parse(this.edtBorn.getText());
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "ʱ���������","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			BeanRider b=new BeanRider();
			b.setRiderid(this.rider.getRiderid());
			b.setRiderborn(Born);
			b.setRidername(Name);
			b.setRidersex(Sex);
			b.setRiderStatus(Status);
			
			
			try {
				PPutil.RiderManager.changeRider(b);
				this.rider=b;
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanRider getRider() {
		return rider;
	}
}
