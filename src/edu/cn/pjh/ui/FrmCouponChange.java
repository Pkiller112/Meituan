package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanCoupon;
import edu.cn.pjh.util.BaseException;

public class FrmCouponChange extends JDialog implements ActionListener{
	private BeanCoupon coupon;
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelReduce = new JLabel("优惠金额：");
	private JLabel labelTimes = new JLabel("集单要求数：");
	private JLabel labelBegin = new JLabel("生效日期(yyyy-MM-dd)：");
	private JLabel labelDead = new JLabel("失效日期(yyyy-MM-dd)：");
	private JTextField edtReduce = new JTextField(20);
	private JTextField edtTimes = new JTextField(20);
	private JTextField edtBegin = new JTextField(20);
	private JTextField edtDead = new JTextField(20);
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmCouponChange(JDialog f, String s, boolean b,BeanCoupon coupon) {
		super(f, s, b);
		this.coupon=coupon;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelReduce);
		workPane.add(edtReduce);
		workPane.add(labelTimes);
		workPane.add(edtTimes);
		workPane.add(labelBegin);
		workPane.add(edtBegin);
		workPane.add(labelDead);
		workPane.add(edtDead);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(400, 200);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
		this.edtReduce.setText(String.valueOf(coupon.getReduce()));
		this.edtTimes.setText(String.valueOf(coupon.getNeedtime()));
		this.edtBegin.setText(sd.format(coupon.getBeginTime()));
		this.edtDead.setText(sd.format(coupon.getDeadTime()));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			float reduce=Float.parseFloat(this.edtReduce.getText());
			int needtimes=Integer.parseInt(this.edtTimes.getText());
			String begin=this.edtBegin.getText();
			String dead=this.edtDead.getText();
			this.coupon.setReduce(reduce);
			this.coupon.setNeedtime(needtimes);
			try {
				this.coupon.setBeginTime(sd.parse(begin));
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				this.coupon.setDeadTime(sd.parse(dead));
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				PPutil.CouponManager.changeCoupon(this.coupon);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
