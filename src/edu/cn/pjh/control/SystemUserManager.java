package edu.cn.pjh.control;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.itf.ISystemUserManager;
import edu.cn.pjh.model.BeanUser;
import edu.cn.pjh.util.BaseException;
import edu.cn.pjh.util.BusinessException;
import edu.cn.pjh.util.DBUtil;
import edu.cn.pjh.util.DbException;

public class SystemUserManager implements ISystemUserManager{
	public static BeanUser currentUser=null;
	
	public List<BeanUser> loadAllUsers() throws BaseException{
		List<BeanUser> result=new ArrayList<BeanUser>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_id,user_name,user_type,register_time,user_barcade from pp_user";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanUser u=new BeanUser();
				u.setUserid(rs.getString(1));
				u.setUsername(rs.getString(2));
				u.setType(rs.getString(3));
				u.setRegisterTime(rs.getTimestamp(4));
				u.setUserbarcade(rs.getInt(5));
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
	//�����û����
	public void createUser(String userid,String name,String pwd,String pwd2) throws BaseException{
		if(userid==null || "".equals(userid)){
			throw new BusinessException("�˺Ų���Ϊ�գ�");
		}
		if(name==null || "".equals(name)){
			throw new BusinessException("�û�������Ϊ�գ�");
		}
		if(pwd==null || "".equals(pwd)||pwd2==null || "".equals(pwd2)){
			throw new BusinessException("���벻��Ϊ�գ�");
		}
		if(!pwd.equals(pwd2)) {
			throw new BusinessException("��������������һ�£�");
		}
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_user where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��½�˺��Ѿ�����!");
			rs.close();
			pst.close();
			sql="insert into pp_user(user_id,user_name,user_pwd,register_time) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, name);
			pst.setString(3, pwd);
			pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
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
	public void changeUserPwd(String userid,String newPwd,String newPwd2) throws BaseException{
		if(! ("��������Ա".equals(SystemUserManager.currentUser.getType())) )
			throw new BusinessException("���޸�Ȩ�ޣ�");
		if(newPwd==null || "".equals(newPwd)||newPwd2==null || "".equals(newPwd2)){
				throw new BusinessException("���벻��Ϊ�գ�");
			}
		if(!newPwd.equals(newPwd2)) {
				throw new BusinessException("��������������һ�£�");
			}
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="update pp_user set user_pwd= ? where user_id= ?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(2,userid);
				pst.setString(1,newPwd);
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
	public void deleteUser(String userid)throws BaseException{
		if(!("��������Ա".equals(SystemUserManager.currentUser.getType())))
			throw new BusinessException("���޸�Ȩ�ޣ�");
		
		if(userid.equals(SystemUserManager.currentUser.getUserid()))
			throw new BusinessException("��ɾ���Լ���");

		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pp_user where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�˺Ų�����");
			rs.close();
			pst.close();

			sql="delete from pp_user where user_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
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
	//loadUser�޸����
	public BeanUser loadUser(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_id,user_name,user_pwd,register_time,user_type,user_barcade from pp_user where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų�����");
			
			BeanUser u=new BeanUser();
			u.setUserid(rs.getString(1));
			u.setUsername(rs.getString(2));
			u.setUserpwd(rs.getString(3));
			u.setRegisterTime(rs.getTimestamp(4));
			u.setType(rs.getString(5));
			u.setUserbarcade(rs.getInt(6));
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
