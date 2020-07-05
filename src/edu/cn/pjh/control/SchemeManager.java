package edu.cn.pjh.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.cn.pjh.itf.ISchemeManager;
import edu.cn.pjh.model.BeanCoupon;
import edu.cn.pjh.model.BeanScheme;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class SchemeManager implements ISchemeManager {
	public List<BeanScheme> searchScheme(int bsnid) throws BaseException{
		List<BeanScheme> result=new ArrayList<BeanScheme>();
		Connection conn=null;
		String sql=null;
		java.sql.PreparedStatement pst=null;
		try {
			conn=DBUtil.getConnection();
			sql="select bsn_id,sch_id,sch_need,sch_reduce,ifcanusecpn from pp_business_scheme where bsn_id=?";			
			pst=conn.prepareStatement(sql);
			pst.setInt(1, bsnid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanScheme u=new BeanScheme();
				u.setBusinessid(rs.getInt(1));
				u.setSchemeid(rs.getInt(2));
				u.setNeedprice(rs.getFloat(3));
				u.setReduce(rs.getFloat(4));
				u.setIfcanusecoupon(rs.getString(5));
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
	
	public void createScheme(int bsnid, float need,float reduce,String ifcpn) throws BaseException, ParseException{
		if(ifcpn==null || "".equals(ifcpn)||"".equals(need)||"".equals(reduce)){
			throw new BusinessException("信息不能为空！");
		}
		SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_business_scheme where bsn_id=? and sch_need=?";			
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, bsnid);
			pst.setFloat(2, need);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("存在类似满减！"); 
			
			sql="insert into pp_business_scheme (bsn_id,sch_need,sch_reduce,ifcanusecpn) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,bsnid);
			pst.setFloat(2, need);
			pst.setFloat(3, reduce);
			pst.setString(4, ifcpn);
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
	
	public void changeScheme(BeanScheme b) throws BaseException{
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		if("".equals(b.getReduce())||"".equals(b.getNeedprice())){
			throw new BusinessException("信息不能为空！");
		}
		Connection conn=null;
		
		try {
			conn=DBUtil.getConnection();

			String sql="update pp_business_scheme set sch_need=?,sch_reduce=?,ifcanusecpn=? where bsn_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setFloat(1, b.getNeedprice());
			pst.setFloat(2, b.getReduce());
			pst.setString(3, b.getIfcanusecoupon());
			pst.setInt(4, b.getBusinessid());
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
	
	public void deleteScheme(BeanScheme b)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_order,pp_order_moreinfo where pp_order.order_barcade=pp_order_moreinfo.order_barcade and pp_order_moreinfo.pro_id=? and pp_order.order_status!=? and pp_order.order_status!=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,b.getSchemeid());
			pst.setString(2,"完成");
			pst.setString(3,"已取消");
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("满减方案存在未完成的订单！");
			rs.close();
			pst.close();
			//删商家的券
			sql="delete from pp_business_scheme where sch_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, b.getSchemeid());
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
