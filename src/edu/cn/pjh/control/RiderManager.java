package edu.cn.pjh.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import edu.cn.pjh.itf.IRiderManager;
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class RiderManager implements IRiderManager{
	public List<BeanRider> searchRiders(String State,String content) throws BaseException{
		List<BeanRider> result=new ArrayList<BeanRider>();
		Connection conn=null;
		String sql=null;
		java.sql.PreparedStatement pst=null;
		try {
			conn=DBUtil.getConnection();
			if(State!=null&&!"".equals(State)) {//状态不为空，搜索指定骑手
				sql="select rider_id,rider_name,register_time,rider_status,rider_born,rider_sex from pp_rider where rider_status =?";
				
				if(content!=null && !"".equals(content))
					sql+=" and (rider_id like ? or rider_name like ? )";
				sql+=" order by rider_id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, State);
				if(content!=null && !"".equals(content)){
					String s="%"+content+"%";
					pst.setString(2, s);
					pst.setString(3, s);
				}				
			}
			//状态为空，搜索全部骑手
			else{
				sql="select rider_id,rider_name,register_time,rider_status,rider_born,rider_sex from pp_rider ";
				if(content!=null && !"".equals(content))
					sql+=" where (rider_id like ? or rider_name like ? )";
				sql+=" order by rider_id";
				pst=conn.prepareStatement(sql);
				if(content!=null && !"".equals(content)){
					String s="%"+content+"%";
					pst.setString(1, s);
					pst.setString(2, s);
				}				
			}

			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanRider u=new BeanRider();
				u.setRiderid(rs.getInt(1));
				u.setRidername(rs.getString(2));
				u.setRegisterTime(rs.getTimestamp(3));
				u.setRiderStatus(rs.getString(4));
				u.setRiderborn(rs.getDate(5));
				u.setRidersex(rs.getString(6));
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
	public void createRider(String name,String status,String born,String sex) throws BaseException, ParseException{
		if(name==null || "".equals(name)|| born==null || "".equals(born)){
			throw new BusinessException("信息不能为空！");
		}
		SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();

			String sql="insert into pp_rider (register_time,rider_name,rider_status,rider_born,rider_sex) values(?,?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(2, name);
			pst.setString(3, status);
			pst.setDate(4, new java.sql.Date(sd.parse(born).getTime()));
			pst.setString(5, sex);
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
	public void changeRider(BeanRider b)throws BaseException{
		if(b.getRidername()==null || "".equals(b.getRidername())||b.getRiderborn()==null || "".equals(b.getRiderborn())){
			throw new BusinessException("信息不能为空！");
		}
		Connection conn=null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			conn=DBUtil.getConnection();

			String sql="update pp_rider set rider_name=?,rider_status=?,rider_born=?,rider_sex=? where rider_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getRidername());
			pst.setString(2, b.getRiderStatus());
			pst.setDate(3, new java.sql.Date(b.getRiderborn().getTime()));
			pst.setString(4, b.getRidersex());
			pst.setInt(5, b.getRiderid());
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
	public void deleteRider(int riderid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_order where rider_id=? and order_status!=? and order_status!=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,riderid);
			pst.setString(2,"完成");
			pst.setString(3,"已取消");
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("骑手存在未完成的订单！");
			rs.close();
			pst.close();

			sql="delete from pp_rider where rider_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, riderid);
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
