package edu.cn.pjh.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.cn.pjh.itf.IOrderManager;
import edu.cn.pjh.model.BeanConsumer;
import edu.cn.pjh.model.BeanOrder;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class OrderManager implements IOrderManager {
	public List<BeanOrder> searchOrders(String State,String content) throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		Connection conn=null;
		String sql=null;
		java.sql.PreparedStatement pst=null;
		try {
			conn=DBUtil.getConnection();
			if(State!=null&&!"".equals(State)) {//状态不为空，搜索指定对象
				sql="select order_barcade,bsn_id,csmr_id,rider_id,ori_price,fin_price,sch_id,cpn_id,create_time,order_time,adr_id,order_status where ifvip =?";
			//	用户编号","姓名","性别","注册日期","账号","密码","电话","会员到期时间
				if(content!=null && !"".equals(content))
					sql+=" and (csmr_id like ? or csmr_name like ? )";
				sql+=" order by csmr_id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, "是");
				if(content!=null && !"".equals(content)){
					String s="%"+content+"%";
					pst.setString(2, s);
					pst.setString(3, s);
				}				
			}
			//状态为空，搜索全部
			else{
				sql="select csmr_id,csmr_name,csmr_sex,register_time,csmr_account,csmr_pwd,csmr_phone,vipddl from pp_consumer ";
				if(content!=null && !"".equals(content))
					sql+=" where (csmr_id like ? or csmr_name like ? )";
				sql+=" order by csmr_id";
				pst=conn.prepareStatement(sql);
				if(content!=null && !"".equals(content)){
					String s="%"+content+"%";
					pst.setString(1, s);
					pst.setString(2, s);
				}				
			}

			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanConsumer u=new BeanConsumer();
				u.setConsumerid(rs.getInt(1));
				u.setConsumername(rs.getString(2));
				u.setConsumersex(rs.getString(3));
				u.setRegisterTime(rs.getTimestamp(4));
				u.setConsumeraccount(rs.getString(5));
				u.setConsumerpwd(rs.getString(6));
				u.setConsumerphone(rs.getString(7));
				u.setVipDDL(rs.getString(8));
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
	public void createConsumer(String name,String Acc,String Pwd,String Phone,String Sex) throws BaseException{
		if(name==null || "".equals(name)|| Acc==null || "".equals(Acc)|| Pwd==null || "".equals(Pwd)|| Phone==null || "".equals(Phone)){
			throw new BusinessException("信息不能为空！");
		}
		SimpleDateFormat sd=new SimpleDateFormat( "yyyy-MM-dd" ); 
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_consumer where csmr_account = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, Acc);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该账户已存在！");
			rs.close();
			pst.close();
			sql="insert into pp_consumer (register_time,csmr_name,csmr_sex,csmr_account,csmr_pwd,csmr_phone,ifvip) values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(2, name);
			pst.setString(3, Sex);
			pst.setString(4, Acc);
			pst.setString(5, Pwd);
			pst.setString(6, Phone);
			pst.setString(7, "否");
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
	public void changeConsumer(BeanConsumer b) throws BaseException{
		if(b.getConsumername()==null || "".equals(b.getConsumername())||b.getConsumeraccount()==null || "".equals(b.getConsumeraccount())||b.getConsumerpwd()==null || "".equals(b.getConsumerpwd())||b.getConsumerphone()==null || "".equals(b.getConsumerphone())){
			throw new BusinessException("信息不能为空！");
		}
		Connection conn=null;
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_consumer where csmr_account = ? and csmr_id != ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getConsumeraccount());
			pst.setInt(2, b.getConsumerid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该账户已存在！");		
			

			sql="update pp_consumer set csmr_name=?,csmr_sex=?,csmr_account=?,csmr_pwd=?,csmr_phone=? where csmr_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getConsumername());
			pst.setString(2, b.getConsumersex());
			pst.setString(3, b.getConsumeraccount());
			pst.setString(4, b.getConsumerpwd());
			pst.setString(5, b.getConsumerphone());
			pst.setInt(6, b.getConsumerid());
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
	public void deleteConsumer(int csmrid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_order where csmr_id=? and order_status!= ? and order_status!= ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,csmrid);
			pst.setString(2,"完成");
			pst.setString(3,"已取消");
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("用户存在未完成的订单！");
			rs.close();
			pst.close();
			
			//需删除用户信息和优惠券信息
			sql="delete from pp_consumer where csmr_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, csmrid);
			pst.execute();
			
			sql="delete from pp_consumer_coupon where csmr_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, csmrid);
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
	public void BecomeVip(BeanConsumer consumer,int month) throws BaseException, ParseException{
		Connection conn=null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			conn=DBUtil.getConnection();
			//如果截止日期是空的，从现在开始加一个月；如果不空，ddl+一个月
			String sql="update pp_consumer set ifvip=?,vipddl= ? where csmr_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, "是");
			
			if(consumer.getVipDDL()==null) {
				c.add(Calendar.MONTH, month);
				java.util.Date a=c.getTime();
				pst.setString(2, sd.format(a));	
			}
			
			else {
				java.util.Date a=sd.parse(consumer.getVipDDL());
				 c.setTime(a);
			     c.add(Calendar.MONTH, month);
			     a=c.getTime();
			     pst.setString(2, sd.format(a));
			}
				
			pst.setInt(3, consumer.getConsumerid());	
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
	public BeanConsumer loadConsumer(String csmracc)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select csmr_id,register_time,csmr_name,csmr_sex,csmr_account,csmr_pwd,csmr_phone,ifvip,vipddl from pp_consumer where csmr_account=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,csmracc);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不存在");
			
			BeanConsumer u=new BeanConsumer();
			u.setConsumerid(rs.getInt(1));
			u.setRegisterTime(rs.getTimestamp(2));
			u.setConsumername(rs.getString(3));
			u.setConsumersex(rs.getString(4));
			u.setConsumeraccount(rs.getString(5));
			u.setConsumerpwd(rs.getString(6));
			u.setConsumerphone(rs.getString(7));
			u.setIfvip(rs.getString(8));
			u.setVipDDL(rs.getString(9));
			rs.close();
			pst.close();
			return u;
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
