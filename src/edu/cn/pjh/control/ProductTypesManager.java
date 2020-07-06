package edu.cn.pjh.control;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.cn.pjh.itf.IProductTypesManager;
import edu.cn.pjh.model.BeanProduct;
import edu.cn.pjh.model.BeanProductType;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class ProductTypesManager implements IProductTypesManager{
	public List<BeanProductType> searchProductType(int bsnid) throws BaseException{
		List<BeanProductType> result=new ArrayList<BeanProductType>();
		Connection conn=null;
		String sql=null;
		java.sql.PreparedStatement pst=null;
		try {
			conn=DBUtil.getConnection();
			sql="select tpe_id,tpe_name,tpe_num,tpe_content,bsn_id from pp_product_types where bsn_id=?";			
			pst=conn.prepareStatement(sql);
			pst.setInt(1, bsnid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanProductType u=new BeanProductType();
				u.setBusinessid(bsnid);
				u.setTypeid(rs.getInt(1));
				u.setTypename(rs.getString(2));
				u.setProduct_num(rs.getInt(3));
				u.setContent(rs.getString(4));
				u.setBusinessid(rs.getInt(5));
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
	
	public void createProductType(int bsnid,String name,String content) throws BaseException, ParseException{
		if(name==null || "".equals(name)||content==null || "".equals(content)){
			throw new BusinessException("信息不能为空！");
		}
		SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
		Connection conn=null;
		int num;
		try {
			conn=DBUtil.getConnection();
			String sql="insert into pp_product_types (bsn_id,tpe_name,tpe_num,tpe_content) values(?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,bsnid);
			pst.setString(2, name);
			pst.setInt(3, 0);
			pst.setString(4, content);
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
	public void changeProductType(BeanProductType b)throws BaseException{
		if(b.getTypename()==null || "".equals(b.getTypename())){
			throw new BusinessException("类名不能为空！");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();

			String sql="update pp_product_types set tpe_name=?,tpe_content=? where tpe_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getTypename());
			pst.setString(2, b.getContent());
			pst.setInt(3, b.getTypeid());
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
	public void deleteProductType(BeanProductType b)throws BaseException{
		Connection conn=null;
		//删商品类别，其下不能有商品
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_product where tpe_id =?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,b.getTypeid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该类下含有商品！");
			rs.close();
			pst.close();

			sql="delete from pp_product_types where tpe_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, b.getTypeid());
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
