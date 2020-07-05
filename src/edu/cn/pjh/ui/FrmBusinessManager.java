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
	private Button btnAdd = new Button("���");
	private Button btnChange = new Button("�޸�");
	private Button btnDelete = new Button("ɾ��");
	private Button btnMore = new Button("�����̼�");
	private Button btnCou = new Button("�鿴�Ż�ȯ");
	private Button btnSch = new Button("�鿴����");
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblTitle[]={"�̼ұ��","�̼���","�̼��Ǽ�","ƽ������","������"};
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
			FrmBusinessAdd dlg=new FrmBusinessAdd(this,"����̼�",true);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnChange){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���̼�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness b=this.businesses.get(i);
			FrmBusinessChange dlg=new FrmBusinessChange(this,"�޸��̼�",true,b);
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���̼�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness business=this.businesses.get(i);
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ��"+business.getBusinessid()+"���̼���","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				if(JOptionPane.showConfirmDialog(this,"�ò�����ɾ�����̼��йص����ࡢ��Ʒ���Ż�ȯ������","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
				try {
					PPutil.BusinessManager.deleteBusiness(business.getBusinessid());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnCou){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���̼�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness b=this.businesses.get(i);
			FrmCouponManager dlg=new FrmCouponManager(this,"�Ż�ȯ",true,b.getBusinessid());
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnSch){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���̼�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness b=this.businesses.get(i);
			FrmSchemeManager dlg=new FrmSchemeManager(this,"��������",true,b.getBusinessid());
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnMore){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���̼�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBusiness b=this.businesses.get(i);
			FrmCouponManager dlg=new FrmCouponManager(this,"��������",true,b.getBusinessid());
			dlg.setVisible(true);
				this.reloadTable();
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		
	}
}
