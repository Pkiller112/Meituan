package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanAddress;
import edu.cn.pjh.model.BeanConsumer;
import edu.cn.pjh.model.BeanScheme;
import edu.cn.pjh.util.BaseException;

public class FrmAddressManager extends JDialog implements ActionListener{
	BeanConsumer consumer=null;
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("���");
	private Button btnChange = new Button("�޸�");
	private Button btnDelete = new Button("ɾ��");
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblTitle[]={"��ַ���","ʡ","��","��","��ϸ��ַ","��ϵ��","��ϵ�绰"};
	private Object tblData[][];
	List<BeanAddress> addresses=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			addresses=PPutil.AddressManager.searchAddress(consumer.getConsumerid(),this.edtKeyword.getText());
			tblData =new Object[addresses.size()][7];
			for(int i=0;i<addresses.size();i++){
				tblData[i][0]=addresses.get(i).getAddrid();
				tblData[i][1]=addresses.get(i).getProvince();
				tblData[i][2]=addresses.get(i).getCity();
				tblData[i][3]=addresses.get(i).getArea();
				tblData[i][4]=addresses.get(i).getContent();
				tblData[i][5]=addresses.get(i).getCommuicate();
				tblData[i][6]=addresses.get(i).getPhone();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmAddressManager(JDialog f, String s, boolean b,BeanConsumer consumer) {
		super(f, s, b);
		this.consumer=consumer;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnChange);
		toolBar.add(btnDelete);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
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
			FrmAddressAdd dlg=new FrmAddressAdd(this,"��ӵ�ַ",true,this.consumer);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnChange){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���û�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanAddress b=this.addresses.get(i);
			FrmAddressChange dlg=new FrmAddressChange(this,"�޸ĵ�ַ",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���û�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanAddress address=this.addresses.get(i);
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ��"+address.getAddrid()+"�ŵ�ַ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					PPutil.AddressManager.deleteAddress(address.getAddrid());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
	}
}
