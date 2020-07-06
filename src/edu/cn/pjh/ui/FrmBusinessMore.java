package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.model.BeanProduct;
import edu.cn.pjh.model.BeanProductType;
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;


public class FrmBusinessMore extends JDialog implements ActionListener{
	int nowtype_pro=0;
	int bsnid=0;
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_type=new JMenu("商类管理");
    private JMenu menu_product=new JMenu("商品管理");
    
    private JMenuItem  menuItem_Addtype=new JMenuItem("新建商类");
    private JMenuItem  menuItem_Changetype=new JMenuItem("修改商类");
    private JMenuItem  menuItem_Deletetype=new JMenuItem("删除商类");
    private JMenuItem  menuItem_Addproduct=new JMenuItem("添加商品");
    private JMenuItem  menuItem_Changeproduct=new JMenuItem("修改商品");
    private JMenuItem  menuItem_Deleteproduct=new JMenuItem("删除商品");
 
	
    private Object tblTypeTitle[]={"商类编号","商类名","种类数量","描述"};
	private Object tblTypeData[][];
	DefaultTableModel tabTypeModel=new DefaultTableModel();
	private JTable dataTableType=new JTable(tabTypeModel);
	
	
	private Object tblProductTitle[]={"商品编号","商品名","原价","会员价","数量"};
	private Object tblProductData[][];
	DefaultTableModel tabProductModel=new DefaultTableModel();
	private JTable dataTableProduct=new JTable(tabProductModel);
	

	List<BeanProductType> types=null;
	List<BeanProduct> products=null;
	private void reloadTypeTable(){
		try {
			types=PPutil.ProductTypesManager.searchProductType(this.bsnid);
			tblTypeData =new Object[types.size()][4];
			for(int i=0;i<types.size();i++){
				tblTypeData[i][0]=types.get(i).getTypeid();
				tblTypeData[i][1]=types.get(i).getTypename();
				tblTypeData[i][2]=types.get(i).getProduct_num();
				tblTypeData[i][3]=types.get(i).getContent();
			}
			tabTypeModel.setDataVector(tblTypeData,tblTypeTitle);
			this.dataTableType.validate();
			this.dataTableType.repaint();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	private void reloadTypeproductTabel(int typeIdx){
		if(typeIdx<0) return;
		BeanProductType type=types.get(typeIdx);
		try {
			products=PPutil.ProductManager.searchProduct(type.getTypeid());
			tblProductData =new Object[products.size()][5];
			for(int i=0;i<products.size();i++){
				tblProductData[i][0]=products.get(i).getProductid();
				tblProductData[i][1]=products.get(i).getProductname();
				tblProductData[i][2]=products.get(i).getProduct_price();
				tblProductData[i][3]=products.get(i).getProduct_vipprice();
				tblProductData[i][4]=products.get(i).getNum();
			}
			tabProductModel.setDataVector(tblProductData,tblProductTitle);
			this.dataTableProduct.validate();
			this.dataTableProduct.repaint();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	public FrmBusinessMore(JDialog f, String s, boolean b,int bsnid){
		super(f, s, b);
		this.setTitle("店铺信息");
//		this.setAlwaysOnTop(true);
		this.bsnid=bsnid;
	    //菜单
	    this.menu_type.add(this.menuItem_Addtype); this.menuItem_Addtype.addActionListener(this);
	    this.menu_type.add(this.menuItem_Changetype); this.menuItem_Changetype.addActionListener(this);
	    this.menu_type.add(this.menuItem_Deletetype); this.menuItem_Deletetype.addActionListener(this);
	    this.menu_product.add(this.menuItem_Addproduct); this.menuItem_Addproduct.addActionListener(this);
	    this.menu_product.add(this.menuItem_Changeproduct); this.menuItem_Changeproduct.addActionListener(this);
	    this.menu_product.add(this.menuItem_Deleteproduct); this.menuItem_Deleteproduct.addActionListener(this);

	    menubar.add(menu_type);
	    menubar.add(menu_product);
	    this.setJMenuBar(menubar);
	    
	    this.getContentPane().add(new JScrollPane(this.dataTableType), BorderLayout.WEST);
	    this.dataTableType.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {
				nowtype_pro=dataTableType.getSelectedRow();
				if(nowtype_pro<0) {
					return;
				}
				reloadTypeproductTabel(nowtype_pro);
				
			}
	    	
	    });
		// 屏幕居中显示
		this.setSize(1200, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
	    
	    this.getContentPane().add(new JScrollPane(this.dataTableProduct), BorderLayout.CENTER);
	    this.reloadTypeTable();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.menuItem_Addtype){
			FrmTypeAdd dlg=new FrmTypeAdd(this,"添加商类",true,this.bsnid);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Changetype){
			int i=this.dataTableType.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商类","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanProductType type=this.types.get(i);
			FrmTypeChange dlg=new FrmTypeChange(this,"修改商类",true,type);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Deletetype){
			int i=this.dataTableType.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商类","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanProductType type=this.types.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除"+type.getTypeid()+"号商类吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					PPutil.ProductTypesManager.deleteProductType(type);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		if(e.getSource()==this.menuItem_Addproduct){
			int i=this.dataTableType.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商类","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanProductType type=this.types.get(i);
			FrmProductAdd dlg=new FrmProductAdd(this,"添加商类",true,type.getTypeid());
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Changeproduct){
			int i=this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanProduct product=this.products.get(i);
			FrmProductChange dlg=new FrmProductChange(this,"修改商品",true,product);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Deleteproduct){
			int i=this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanProduct product=this.products.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除"+product.getProductid()+"号商品吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					PPutil.ProductManager.deleteProduct(product);
					
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		this.reloadTypeTable();
		this.reloadTypeproductTabel(nowtype_pro);
	}
}
