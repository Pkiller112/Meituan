package edu.cn.pjh.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.cn.pjh.itf.IBusinessManager;
import edu.cn.pjh.itf.ISystemUserManager;
import edu.cn.pjh.model.BeanBusiness;
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class BusinessManager implements IBusinessManager{
	public List<BeanBusiness> searchBusiness(String content) throws BaseException{
		List<BeanBusiness> result=new ArrayList<BeanBusiness>();
		Connection conn=null;
		String sql=null;
		java.sql.PreparedStatement pst=null;
		try {
			conn=DBUtil.getConnection();
			sql="select bsn_id,bsn_name,bsn_star,bsn_avgcost,bsn_sumsell from pp_business";
			pst=conn.prepareStatement(sql);
			if(content!=null && !"".equals(content)) {
				sql+=" where bsn_id like ? or bsn_name like ? or bsn_star like ?";
					String s="%"+content+"%";
					pst=conn.prepareStatement(sql);
					pst.setString(1, s);
					pst.setString(2, s);
					pst.setString(3, s);
			}
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBusiness u=new BeanBusiness();
				u.setBusinessid(rs.getInt(1));
				u.setBusinessname(rs.getString(2));
				u.setBusinessStar(rs.getInt(3));
				u.setAvgCost(rs.getFloat(4));
				u.setAllsell(rs.getInt(5));
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
	//创建商家完成
	public void createBusiness(String name,int star) throws BaseException, ParseException{
		if(name==null || "".equals(name)){
			throw new BusinessException("信息不能为空！");
		}
		
		SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();

			String sql="insert into pp_business (bsn_name,bsn_star,bsn_avgcost,bsn_sumsell) values(?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setInt(2, star);
			pst.setFloat(3, 0);
			pst.setInt(4,0);
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
	public void changeBusiness(BeanBusiness b)throws BaseException{
		if(b.getBusinessname()==null || "".equals(b.getBusinessname())|| "".equals(b.getBusinessStar())){
			throw new BusinessException("信息不能为空！");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();

			String sql="update pp_business set bsn_name=?,bsn_star=? where bsn_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getBusinessname());
			pst.setInt(2, b.getBusinessStar());
			pst.setInt(3, b.getBusinessid());
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
	
	public void deleteBusiness(int businessid)throws BaseException{
		Connection conn=null;
		try {//不能有未完成的订单
			conn=DBUtil.getConnection();
			String sql="select * from pp_order where bsn_id=? and order_status!= ? and order_status!= ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,businessid);
			pst.setString(2,"完成");
			pst.setString(3,"已取消");
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("商家存在未完成的订单！");
			rs.close();
			pst.close();
			//商家删除了，类、商品、满减方案、促销券都要删除
			sql="delete from pp_business_scheme where bsn_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, businessid);
			pst.execute();
			
			sql="delete from pp_consumer_coupon where bsn_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, businessid);
			pst.execute();
			
			sql="delete from pp_business_coupon where bsn_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, businessid);
			pst.execute();
			
			sql="delete pp_product from pp_product_types left join pp_product on pp_product_types.tpe_id=pp_product.tpe_id where pp_product_types.bsn_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, businessid);
			pst.execute();
			
			sql="delete from pp_product_types where bsn_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, businessid);
			pst.execute();
			
			sql="delete from pp_business where bsn_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, businessid);
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
