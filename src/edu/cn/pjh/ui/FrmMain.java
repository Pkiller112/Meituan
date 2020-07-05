package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import edu.cn.pjh.control.SystemUserManager;




public class FrmMain extends JFrame implements ActionListener {
	private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_Manager=new JMenu("ϵͳ����");
    private JMenu menu_Order=new JMenu("��������");
    private JMenu menu_Appraise=new JMenu("�û�����");
    private JMenu menu_search=new JMenu("��ѯͳ��");
    
    private JMenuItem  menuItem_SystemUserManager=new JMenuItem("����Ա����");
    private JMenuItem  menuItem_BusinessManager=new JMenuItem("�̼ҹ���");
    private JMenuItem  menuItem_RiderManager=new JMenuItem("���ֹ���");
    private JMenuItem  menuItem_ConsumerManager=new JMenuItem("�û�����");
    
    private JMenuItem  menuItem_beginorder=new JMenuItem("�µ�");
    private JMenuItem  menuItem_cancelorder=new JMenuItem("�˵�");
    
    private JMenuItem  menuItem_appProduct=new JMenuItem("������Ʒ");
    private JMenuItem  menuItem_appRider=new JMenuItem("��������");
    
    private JMenuItem  menuItem_ConsumerSearch=new JMenuItem("�û����������ѯ");
    private JMenuItem  menuItem_RiderSearch=new JMenuItem("�����ܵ������ѯ");
    private JMenuItem  menuItem_ProductSearch=new JMenuItem("��Ʒ��Ϣ��ѯ");
    
    
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("��������ϵͳ");
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
	    //�˵�
	   
	    	menu_Manager.add(menuItem_SystemUserManager);
	    	menuItem_SystemUserManager.addActionListener(this);
	    	menu_Manager.add(menuItem_BusinessManager);
	    	menuItem_BusinessManager.addActionListener(this);
	    	menu_Manager.add(menuItem_RiderManager);
	    	menuItem_RiderManager.addActionListener(this);
	    	menu_Manager.add(menuItem_ConsumerManager);
	    	menuItem_ConsumerManager.addActionListener(this);
	    	menubar.add(menu_Manager);
	    
	    	menu_Order.add(this.menuItem_beginorder);
	    	menuItem_beginorder.addActionListener(this);
	    	menu_Order.add(this.menuItem_cancelorder);
	    	menuItem_cancelorder.addActionListener(this);
	    	menubar.add(menu_Order);
	    	
	    	menu_Appraise.add(this.menuItem_appProduct);
	    	menuItem_appProduct.addActionListener(this);
	    	menu_Appraise.add(this.menuItem_appRider);
	    	menuItem_appRider.addActionListener(this);
	    	menubar.add(menu_Appraise);
	    	
	    	menu_search.add(this.menuItem_ConsumerSearch);
	    	menuItem_ConsumerSearch.addActionListener(this);
	    	menu_search.add(this.menuItem_RiderSearch);
	    	menuItem_RiderSearch.addActionListener(this);
	    	menu_search.add(this.menuItem_ProductSearch);
	    	menuItem_ProductSearch.addActionListener(this);
	    	menubar.add(this.menu_search);
	    	this.setJMenuBar(menubar);
	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("����!"+SystemUserManager.currentUser.getUsername());
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.menuItem_SystemUserManager){
			FrmSystemUserManager dlg=new FrmSystemUserManager(this,"����Ա����",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_BusinessManager){
			FrmBusinessManager dlg=new FrmBusinessManager(this,"�̼ҹ���",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_RiderManager) {
			FrmRiderManager dlg=new FrmRiderManager(this,"���ֹ���",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ConsumerManager) {
			FrmConsumerManager dlg=new FrmConsumerManager(this,"�û�����",true);
			dlg.setVisible(true);
		}
	}
}
