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
    private JMenu menu_Manager=new JMenu("系统管理");
    private JMenu menu_Order=new JMenu("订单管理");
    private JMenu menu_Appraise=new JMenu("用户评价");
    private JMenu menu_search=new JMenu("查询统计");
    
    private JMenuItem  menuItem_SystemUserManager=new JMenuItem("管理员管理");
    private JMenuItem  menuItem_BusinessManager=new JMenuItem("商家管理");
    private JMenuItem  menuItem_RiderManager=new JMenuItem("骑手管理");
    private JMenuItem  menuItem_ConsumerManager=new JMenuItem("用户管理");
    
    private JMenuItem  menuItem_beginorder=new JMenuItem("下单");
    private JMenuItem  menuItem_cancelorder=new JMenuItem("退单");
    
    private JMenuItem  menuItem_appProduct=new JMenuItem("评价商品");
    private JMenuItem  menuItem_appRider=new JMenuItem("评价骑手");
    
    private JMenuItem  menuItem_ConsumerSearch=new JMenuItem("用户消费情况查询");
    private JMenuItem  menuItem_RiderSearch=new JMenuItem("骑手跑单情况查询");
    private JMenuItem  menuItem_ProductSearch=new JMenuItem("商品信息查询");
    
    
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("外卖管理系统");
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
	    //菜单
	   
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
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+SystemUserManager.currentUser.getUsername());
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
			FrmSystemUserManager dlg=new FrmSystemUserManager(this,"管理员管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_BusinessManager){
			FrmBusinessManager dlg=new FrmBusinessManager(this,"商家管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_RiderManager) {
			FrmRiderManager dlg=new FrmRiderManager(this,"骑手管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ConsumerManager) {
			FrmConsumerManager dlg=new FrmConsumerManager(this,"用户管理",true);
			dlg.setVisible(true);
		}
	}
}
