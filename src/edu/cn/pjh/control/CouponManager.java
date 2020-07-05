package edu.cn.pjh.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.cn.pjh.itf.ICouponManager;
import edu.cn.pjh.model.BeanConsumerCoupon;
import edu.cn.pjh.model.BeanCoupon;
import edu.cn.pjh.model.BeanProduct;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class CouponManager implements ICouponManager{
	public List<BeanCoupon> searchCoupon(int bsnid) throws BaseException{
		List<BeanCoupon> result=new ArrayList<BeanCoupon>();
		Connection conn=null;
		String sql=null;
		java.sql.PreparedStatement pst=null;
		try {
			conn=DBUtil.getConnection();
			sql="select cpn_id,cpn_reduce,cpn_needtimes,begin_time,dead_time from pp_business_coupon where bsn_id=?";			
			pst=conn.prepareStatement(sql);
			pst.setInt(1, bsnid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanCoupon u=new BeanCoupon();
				u.setCouponid(rs.getInt(1));
				u.setReduce(rs.getFloat(2));
				u.setNeedtime(rs.getInt(3));
				u.setBeginTime(rs.getDate(4));
				u.setDeadTime(rs.getDate(5));
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
	
	public void createCoupon(int bsnid, float reduce,int needtimes,String begin,String dead) throws BaseException, ParseException{
		if(begin==null || "".equals(begin)||dead==null || "".equals(dead)||"".equals(reduce)||"".equals(needtimes)){
			throw new BusinessException("信息不能为空！");
		}
		SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="insert into pp_business_coupon (bsn_id,cpn_reduce,cpn_needtimes,begin_time,dead_time) values(?,?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,bsnid);
			pst.setFloat(2, reduce);
			pst.setInt(3, needtimes);
			pst.setDate(4,new java.sql.Date(sd.parse(begin).getTime()));
			pst.setDate(5,new java.sql.Date(sd.parse(dead).getTime()));
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
	
	public void changeCoupon(BeanCoupon b) throws BaseException{
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		if("".equals(b.getReduce())||"".equals(b.getNeedtime())){
			throw new BusinessException("信息不能为空！");
		}
		Connection conn=null;
		
		try {
			conn=DBUtil.getConnection();

			String sql="update pp_business_coupon set cpn_reduce=?,cpn_needtimes=?,begin_time=?,dead_time=? where cpn_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setFloat(1, b.getReduce());
			pst.setInt(2, b.getNeedtime());
			pst.setDate(3, new java.sql.Date(b.getBeginTime().getTime()));
			pst.setDate(4, new java.sql.Date(b.getDeadTime().getTime()));
			pst.setInt(5, b.getCouponid());
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
	
	public void deleteCoupon(BeanCoupon b)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_order,pp_order_moreinfo where pp_order.order_barcade=pp_order_moreinfo.order_barcade and pp_order_moreinfo.pro_id=? and pp_order.order_status!=? and pp_order.order_status!=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,b.getCouponid());
			pst.setString(2,"完成");
			pst.setString(3,"已取消");
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("优惠券存在未完成的订单！");
			rs.close();
			pst.close();
			//删用户手里的券
			sql="delete from pp_consumer_coupon where cpn_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, b.getCouponid());
			pst.execute();
			pst.close();
			//删商家的券
			sql="delete from pp_business_coupon where cpn_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, b.getCouponid());
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
