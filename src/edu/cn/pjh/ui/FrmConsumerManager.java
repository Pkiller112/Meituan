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
	private Button btnAdd = new Button("���");
	private Button btnChange = new Button("�޸�");
	private Button btnDelete = new Button("ɾ��");
	private Button btnVip = new Button("��ͨ��Ա");
	private JComboBox cmbState=new JComboBox(new String[]{"","��Ա"});
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblTitle[]={"�û����","����","�Ա�","ע������","�˺�","����","�绰","��Ա����ʱ��"};
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
		//��ȡ��������
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
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
			FrmConsumerAdd dlg=new FrmConsumerAdd(this,"����û�",true);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnChange){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���û�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanConsumer b=this.Consumers.get(i);
			FrmConsumerChange dlg=new FrmConsumerChange(this,"�޸��û�",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "�û�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanConsumer consumer=this.Consumers.get(i);
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ��"+consumer.getConsumerid()+"���û���","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					PPutil.ConsumerManager.deleteConsumer(consumer.getConsumerid());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		else if(e.getSource()==this.btnVip){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���û�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanConsumer b=this.Consumers.get(i);
			FrmConsumerVip dlg=new FrmConsumerVip(this,"��ͨ��Ա",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
	}
}
