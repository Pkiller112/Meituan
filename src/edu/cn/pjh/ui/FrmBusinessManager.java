package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanBusiness;
import edu.cn.pjh.util.BaseException;

public class FrmBusinessManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加");
	private Button btnChange = new Button("修改");
	private Button btnDelete = new Button("删除");
	private Button btnMore = new Button("进入商家");
	private Button btnCou = new Button("查看优惠券");
	private Button btnSch = new Button("查看满减");
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Object tblTitle[]={"商家编号","商家名","商家星级","平均消费","总销售"};
	private Object tblData[][];
	List<BeanBusiness> businesses=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			businesses=PPutil.BusinessManager.searchBusiness(this.edtKeyword.getText());
			tblData =new Object[businesses.size()][5];
			for(int i=0;i<businesses.size();i++){
				tblData[i][0]=businesses.get(i).getBusinessid();
				tblData[i][1]=businesses.get(i).getBusinessname();
				tblData[i][2]=businesses.get(i).getBusinessStar();
				tblData[i][3]=businesses.get(i).getAvgCost();
				tblData[i][4]=businesses.get(i).getAllsell();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmBusinessManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnChange);
		toolBar.add(btnDelete);
		toolBar.add(btnCou);
		toolBar.add(btnSch);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		toolBar.add(btnMore);
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnChange.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnCou.addActionListener(this);
		this.btnSch.addActionListener(this);
		this.btnMore.addActionListener(this);
		this.btnSearch.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			FrmBusinessAdd dlg=new FrmBusinessAdd(this,"添加商家",true);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnChange){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness b=this.businesses.get(i);
			FrmBusinessChange dlg=new FrmBusinessChange(this,"修改商家",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness business=this.businesses.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除"+business.getBusinessid()+"号商家吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				if(JOptionPane.showConfirmDialog(this,"该操作会删除与商家有关的商类、商品、优惠券与满减","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
				try {
					PPutil.BusinessManager.deleteBusiness(business.getBusinessid());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnCou){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness b=this.businesses.get(i);
			FrmCouponManager dlg=new FrmCouponManager(this,"优惠券",true,b.getBusinessid());
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnSch){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness b=this.businesses.get(i);
			FrmSchemeManager dlg=new FrmSchemeManager(this,"满减方案",true,b.getBusinessid());
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnMore){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness b=this.businesses.get(i);
			FrmCouponManager dlg=new FrmCouponManager(this,"满减方案",true,b.getBusinessid());
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		
	}
}
