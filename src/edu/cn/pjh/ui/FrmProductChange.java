package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanProduct;
import edu.cn.pjh.util.BaseException;

public class FrmProductChange extends JDialog implements ActionListener{
BeanProduct product;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelName = new JLabel("商品名：");
	private JLabel labelPrice = new JLabel("原价：");
	private JLabel labelVipprice = new JLabel("会员价：");
	private JLabel labelNum = new JLabel("数量：");
	private JTextField edtName = new JTextField(20);
	private JTextField edtPrice = new JTextField(20);
	private JTextField edtVipprice = new JTextField(20);
	private JTextField edtNum = new JTextField(20);
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmProductChange(JDialog f, String s, boolean b,BeanProduct product) {
		super(f, s, b);
		this.product=product;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelPrice);
		workPane.add(edtPrice);
		workPane.add(labelVipprice);
		workPane.add(edtVipprice);
		workPane.add(labelNum);
		workPane.add(edtNum);
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
		
		this.edtName.setText(product.getProductname());
		this.edtPrice.setText(String.valueOf(product.getProduct_price()));
		this.edtVipprice.setText(String.valueOf(product.getProduct_vipprice()));
		this.edtNum.setText(String.valueOf(product.getNum()));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			String name=this.edtName.getText();
			float price=Float.parseFloat(this.edtPrice.getText());
			float vipprice=Float.parseFloat(this.edtVipprice.getText());
			int num=Integer.parseInt(this.edtNum.getText());
			this.product.setProductname(name);
			this.product.setProduct_price(price);
			this.product.setProduct_vipprice(vipprice);
			this.product.setNum(num);
			try {
				PPutil.ProductManager.changeProduct(this.product);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
