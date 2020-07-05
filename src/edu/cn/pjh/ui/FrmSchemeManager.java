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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanCoupon;
import edu.cn.pjh.model.BeanScheme;
import edu.cn.pjh.util.BaseException;

public class FrmSchemeManager extends JDialog implements ActionListener{
	int bsnid=0;
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加");
	private Button btnChange = new Button("修改");
	private Button btnDelete = new Button("删除");
	private Object tblTitle[]={"满减编号","满减金额","优惠金额","优惠券冲突"};
	private Object tblData[][];
	List<BeanScheme> schemes=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			schemes=PPutil.SchemeManager.searchScheme(bsnid);
			tblData =new Object[schemes.size()][4];
			for(int i=0;i<schemes.size();i++){
				tblData[i][0]=schemes.get(i).getSchemeid();
				tblData[i][2]=schemes.get(i).getReduce();
				tblData[i][1]=schemes.get(i).getNeedprice();
				tblData[i][3]=schemes.get(i).getIfcanusecoupon();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmSchemeManager(JDialog f, String s, boolean b,int bsnid) {
		super(f, s, b);
		this.bsnid=bsnid;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnChange);
		toolBar.add(btnDelete);
		
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
			FrmSchemeAdd dlg=new FrmSchemeAdd(this,"添加满减方案",true,this.bsnid);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnChange){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择方案","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanScheme b=this.schemes.get(i);
			FrmSchemeChange dlg=new FrmSchemeChange(this,"修改满减方案",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择方案","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanScheme scheme=this.schemes.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除"+scheme.getSchemeid()+"号方案吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					PPutil.SchemeManager.deleteScheme(scheme);
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}
}
