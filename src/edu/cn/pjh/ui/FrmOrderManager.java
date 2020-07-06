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
import edu.cn.pjh.model.BeanOrder;
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;

public class FrmOrderManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加");
	private Button btnChange = new Button("修改");
	private Button btnDelete = new Button("删除");
	private JComboBox cmbState=new JComboBox(new String[]{"待接单","配送中","完成","已取消"});
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Object tblTitle[]={"订单编号","商家编号","用户编号","骑手编号","原价","最终价","满减编号","优惠券编号","创建时间","顾客要求时间","实际送达时间","地址编号","订单状态"};
	private Object tblData[][];
	List<BeanOrder> orders=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			orders=PPutil.OrderManager.searchOrders(this.cmbState.getSelectedItem().toString(),this.edtKeyword.getText());
			tblData =new Object[orders.size()][13];
			for(int i=0;i<orders.size();i++){
				tblData[i][0]=orders.get(i).getOrderBarcade();
				tblData[i][1]=orders.get(i).getBusinessid();
				tblData[i][2]=orders.get(i).getConsumerid();
				tblData[i][3]=orders.get(i).getRiderid();
				tblData[i][4]=orders.get(i).getOriginPrice();
				tblData[i][5]=orders.get(i).getFinalPrice();
				tblData[i][6]=orders.get(i).getSchemeid();
				tblData[i][7]=orders.get(i).getCouponid();
				tblData[i][8]=orders.get(i).getCreateTime();
				tblData[i][9]=orders.get(i).getAskTime();
				tblData[i][10]=orders.get(i).getFinTime();
				tblData[i][11]=orders.get(i).getAddressid();
				tblData[i][12]=orders.get(i).getOrderstatus();
				
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmOrderManager(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnChange);
		toolBar.add(btnDelete);
		toolBar.add(cmbState);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		
		
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
			FrmRiderAdd dlg=new FrmRiderAdd(this,"添加订单",true);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnChange){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择骑手","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanRider b=this.riders.get(i);
			FrmRiderChange dlg=new FrmRiderChange(this,"修改骑手",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择骑手","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanRider rider=this.riders.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除"+rider.getRiderid()+"号骑手吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					PPutil.RiderManager.deleteRider(rider.getRiderid());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		
	}
}
