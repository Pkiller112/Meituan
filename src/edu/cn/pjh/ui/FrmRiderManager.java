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
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.model.BeanUser;
import edu.cn.pjh.util.BaseException;

public class FrmRiderManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("���");
	private Button btnChange = new Button("�޸�");
	private Button btnDelete = new Button("ɾ��");
	private JComboBox cmbState=new JComboBox(new String[]{"","����","Ա��","����"});
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblTitle[]={"���ֱ��","����","��������","�Ա�","��ְ����","״̬"};
	private Object tblData[][];
	List<BeanRider> riders=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			riders=PPutil.RiderManager.searchRiders(this.cmbState.getSelectedItem().toString(),this.edtKeyword.getText());
			tblData =new Object[riders.size()][6];
			for(int i=0;i<riders.size();i++){
				tblData[i][0]=riders.get(i).getRiderid();
				tblData[i][1]=riders.get(i).getRidername();
				tblData[i][2]=riders.get(i).getRiderborn();
				tblData[i][3]=riders.get(i).getRidersex();
				tblData[i][4]=riders.get(i).getRegisterTime();
				tblData[i][5]=riders.get(i).getRiderStatus();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmRiderManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnChange);
		toolBar.add(btnDelete);
		toolBar.add(cmbState);
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
			FrmRiderAdd dlg=new FrmRiderAdd(this,"�������",true);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnChange){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanRider b=this.riders.get(i);
			FrmRiderChange dlg=new FrmRiderChange(this,"�޸�����",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanRider rider=this.riders.get(i);
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ��"+rider.getRiderid()+"��������","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					PPutil.RiderManager.deleteRider(rider.getRiderid());
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
