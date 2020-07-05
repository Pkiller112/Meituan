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

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanConsumer;
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;

public class FrmConsumerManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加");
	private Button btnChange = new Button("修改");
	private Button btnDelete = new Button("删除");
	private Button btnVip = new Button("开通会员");
	private JComboBox cmbState=new JComboBox(new String[]{"","会员"});
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Object tblTitle[]={"用户编号","姓名","性别","注册日期","账号","密码","电话","会员到期时间"};
	private Object tblData[][];
	List<BeanConsumer> Consumers=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			Consumers=PPutil.ConsumerManager.searchConsumers(this.cmbState.getSelectedItem().toString(),this.edtKeyword.getText());
			tblData =new Object[Consumers.size()][8];
			for(int i=0;i<Consumers.size();i++){
				tblData[i][0]=Consumers.get(i).getConsumerid();
				tblData[i][1]=Consumers.get(i).getConsumername();
				tblData[i][2]=Consumers.get(i).getConsumersex();
				tblData[i][3]=Consumers.get(i).getRegisterTime();
				tblData[i][4]=Consumers.get(i).getConsumeraccount();
				tblData[i][5]=Consumers.get(i).getConsumerpwd();
				tblData[i][6]=Consumers.get(i).getConsumerphone();
				tblData[i][7]=Consumers.get(i).getVipDDL();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmConsumerManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnChange);
		toolBar.add(btnDelete);
		toolBar.add(cmbState);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		toolBar.add(btnVip);
		
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
		this.btnSearch.addActionListener(this);
		this.btnVip.addActionListener(this);
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
			FrmConsumerAdd dlg=new FrmConsumerAdd(this,"添加用户",true);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnChange){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择用户","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanConsumer b=this.Consumers.get(i);
			FrmConsumerChange dlg=new FrmConsumerChange(this,"修改用户",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "用户","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanConsumer consumer=this.Consumers.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除"+consumer.getConsumerid()+"号用户吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					PPutil.ConsumerManager.deleteConsumer(consumer.getConsumerid());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		else if(e.getSource()==this.btnVip){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择用户","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanConsumer b=this.Consumers.get(i);
			FrmConsumerVip dlg=new FrmConsumerVip(this,"开通会员",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
	}
}
