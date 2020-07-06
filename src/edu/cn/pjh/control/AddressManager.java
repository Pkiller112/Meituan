package edu.cn.pjh.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.cn.pjh.itf.IAddressManager;
import edu.cn.pjh.model.BeanAddress;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class AddressManager implements IAddressManager {
	public List<BeanAddress> searchAddress(int csmrid,String content) throws BaseException{
		List<BeanAddress> result=new ArrayList<BeanAddress>();
		Connection conn=null;
		String sql=null;
		java.sql.PreparedStatement pst=null;
		try {
			conn=DBUtil.getConnection();
				sql="select adr_id,csmr_id,adr_pro,adr_city,adr_area,adr_moreinfo,communicate,phone from pp_address where csmr_id =?";
				if(content!=null && !"".equals(content)) {
					sql+=" and (adr_pro like ? or adr_city like ? or adr_area like ? or adr_moreinfo like ?)";
				}
					sql+=" order by adr_id";
				pst=conn.prepareStatement(sql);
				pst.setInt(1,csmrid);
				if(content!=null && !"".equals(content)){
					String s="%"+content+"%";
					pst.setString(2, s);
					pst.setString(3, s);
					pst.setString(4, s);
					pst.setString(5, s);
				}				

			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanAddress u=new BeanAddress();
				u.setAddrid(rs.getInt(1));
				u.setConsumerid(rs.getInt(2));
				u.setProvince(rs.getString(3));
				u.setCity(rs.getString(4));
				u.setArea(rs.getString(5));
				u.setContent(rs.getString(6));
				u.setCommuicate(rs.getString(7));
				u.setPhone(rs.getString(8));
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
	public void createAddress(int csmrid,String pro,String city,String area,String info,String com,String phone) throws BaseException{
		if(pro==null || "".equals(pro)|| city==null || "".equals(city)|| info==null || "".equals(info)|| area==null || "".equals(area)|| com==null || "".equals(com)|| phone==null || "".equals(phone)){
			throw new BusinessException("信息不能为空！");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_address where csmr_id=? and adr_pro=? and adr_city=? and adr_area=? and adr_moreinfo=? and communicate=? and phone=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, csmrid);
			pst.setString(2, pro);
			pst.setString(3, city);
			pst.setString(4, area);
			pst.setString(5, info);
			pst.setString(6, com);
			pst.setString(7, phone);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("已存在相同地址！");
			rs.close();
			pst.close();
			sql="insert into pp_address (csmr_id,adr_pro,adr_city,adr_area,adr_moreinfo,communicate,phone) values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, csmrid);
			pst.setString(2, pro);
			pst.setString(3, city);
			pst.setString(4, area);
			pst.setString(5, info);
			pst.setString(6, com);
			pst.setString(7, phone);
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
	public void changeAddress(BeanAddress b) throws BaseException{
		if(b.getProvince()==null || "".equals(b.getProvince())||b.getCity()==null || "".equals(b.getCity())||b.getArea()==null || "".equals(b.getArea())||b.getContent()==null || "".equals(b.getContent())||b.getCommuicate()==null || "".equals(b.getCommuicate())||b.getPhone()==null || "".equals(b.getPhone())){
			throw new BusinessException("信息不能为空！");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_address where csmr_id=? and adr_pro=? and adr_city=? and adr_area=? and adr_moreinfo=? and communicate=? and phone=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, b.getConsumerid());
			pst.setString(2, b.getProvince());
			pst.setString(3,b.getCity());
			pst.setString(4, b.getArea());
			pst.setString(5, b.getContent());
			pst.setString(6, b.getCommuicate());
			pst.setString(7, b.getPhone());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("已存在相同地址！");
			rs.close();
			pst.close();
			

			sql="update pp_address set adr_pro=?,adr_city=?,adr_area=?,adr_moreinfo=?,communicate=?,phone=? where csmr_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getProvince());
			pst.setString(2,b.getCity());
			pst.setString(3, b.getArea());
			pst.setString(4, b.getContent());
			pst.setString(5, b.getCommuicate());
			pst.setString(6, b.getPhone());
			pst.setInt(7, b.getConsumerid());
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
	public void deleteAddress(int addid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_order where csmr_id=? and order_status!= ? and order_status!= ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,addid);
			pst.setString(2,"完成");
			pst.setString(3,"已取消");
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该地址未完成的订单！");
			rs.close();
			pst.close();

			sql="delete from pp_address where adr_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, addid);
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
