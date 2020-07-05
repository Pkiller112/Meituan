package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanCoupon;
import edu.cn.pjh.model.BeanScheme;
import edu.cn.pjh.util.BaseException;

public class FrmSchemeChange extends JDialog implements ActionListener{
	BeanScheme scheme=new BeanScheme();
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelNeed = new JLabel("要求金额：");
	private JLabel labelReduce = new JLabel("优惠金额：");
	private JLabel labelif = new JLabel("是否与优惠券叠加：");
	private JTextField edtReduce = new JTextField(20);
	private JTextField edtNeed = new JTextField(20);
	private JComboBox cmbif=new JComboBox(new String[]{"是","否"});
	private Map<String,Integer> mapif=new HashMap<String,Integer>();
	
	
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmSchemeChange(JDialog f, String s, boolean b,BeanScheme scheme) {
		super(f, s, b);
		this.scheme=scheme;
		mapif.put("是", 0);
		mapif.put("否", 1);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelNeed);
		workPane.add(edtNeed);
		workPane.add(labelReduce);
		workPane.add(edtReduce);
		workPane.add(labelif);
		workPane.add(this.cmbif);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(360, 180);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
		this.edtNeed.setText(String.valueOf(scheme.getNeedprice()));
		this.edtReduce.setText(String.valueOf(scheme.getReduce()));
		this.cmbif.setSelectedIndex(mapif.get(scheme.getIfcanusecoupon()));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			System.out.println(this.edtReduce.getText());
			float reduce=Float.parseFloat(this.edtReduce.getText());
			System.out.println(reduce);
			float need=Float.parseFloat(this.edtNeed.getText());
			String ifcpn=this.cmbif.getSelectedItem().toString();
			this.scheme.setIfcanusecoupon(ifcpn);
			this.scheme.setNeedprice(need);
			this.scheme.setReduce(reduce);
			try {
				PPutil.SchemeManager.changeScheme(this.scheme);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
