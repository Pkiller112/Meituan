package edu.cn.pjh.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.cn.pjh.itf.IProductManager;
import edu.cn.pjh.model.BeanProduct;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class ProductManager implements IProductManager{
	public List<BeanProduct> searchProduct(int typeid) throws BaseException{
		List<BeanProduct> result=new ArrayList<BeanProduct>();
		Connection conn=null;
		String sql=null;
		java.sql.PreparedStatement pst=null;
		try {
			conn=DBUtil.getConnection();
			sql="select pro_id,pro_name,pro_price,pro_vipprice,pro_num,tpe_id from pp_product where tpe_id=?";			
			pst=conn.prepareStatement(sql);
			pst.setInt(1, typeid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanProduct u=new BeanProduct();
				u.setTypeid(typeid);
				u.setProductid(rs.getInt(1));
				u.setProductname(rs.getString(2));
				u.setProduct_price(rs.getFloat(3));
				u.setProduct_vipprice(rs.getFloat(4));
				u.setNum(rs.getInt(5));
				u.setTypeid(rs.getInt(6));
				result.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	//创建用户完成
	public void createProduct(int typeid,String name, float price,float vipprice,int num) throws BaseException, ParseException{
		if(name==null || "".equals(name)||"".equals(price)||"".equals(vipprice)||"".equals(num)){
			throw new BusinessException("信息不能为空！");
		}
		SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();

			String sql="insert into pp_product (tpe_id,pro_name,pro_price,pro_vipprice,pro_num) values(?,?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,typeid);
			pst.setString(2, name);
			pst.setFloat(3, price);
			pst.setFloat(4, vipprice);
			pst.setInt(5, num);
			pst.execute();
			
			//创建商品后，要在对应类下更新商品数量
			sql="select count(*) from pp_product where tpe_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, typeid);
			java.sql.ResultSet rs=pst.executeQuery();
			rs.next();
			int tpenum=rs.getInt(1);
			
			sql="update pp_product_types set tpe_num=? where tpe_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, tpenum);
			pst.setInt(2, typeid);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void changeProduct(BeanProduct b)throws BaseException{
		if(b.getProductname()==null || "".equals(b.getProductname())||"".equals(b.getProduct_vipprice()) || "".equals(b.getProduct_price())|| "".equals(b.getNum())){
			throw new BusinessException("信息不能为空！");
		}
		Connection conn=null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			conn=DBUtil.getConnection();

			String sql="update pp_product set pro_name=?,pro_price=?,pro_vipprice=?,pro_num=? where pro_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getProductname());
			pst.setFloat(2, b.getProduct_price());
			pst.setFloat(3, b.getProduct_vipprice());
			pst.setInt(4, b.getNum());
			pst.setInt(5, b.getProductid());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void deleteProduct(BeanProduct b)throws BaseException{
		Connection conn=null;
		//删商品，不能有未完成的订单
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_order,pp_order_moreinfo where pp_order.order_barcade=pp_order_moreinfo.order_barcade and pp_order_moreinfo.pro_id=? and pp_order.order_status!=? and pp_order.order_status!=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,b.getProductid());
			pst.setString(2,"完成");
			pst.setString(3,"已取消");
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("商品存在未完成的订单！");
			rs.close();
			pst.close();

			sql="delete from pp_product where pro_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, b.getProductid());
			pst.execute();
			pst.close();
			
			//删去商品后，要在对应类下更新商品数量
			sql="select count(*) from pp_product where tpe_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, b.getTypeid());
			rs=pst.executeQuery();
			rs.next();
			int tpenum=rs.getInt(1);
			
			sql="update pp_product_types set tpe_num=? where tpe_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, tpenum);
			pst.setInt(2, b.getTypeid());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
