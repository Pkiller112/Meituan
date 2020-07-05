package edu.cn.pjh.util;



import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {
	private static final String jdbcUrl="jdbc:mysql://localhost:3306/ppwork?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
	private static final String dbUser="root";
	private static final String dbPwd="123456";
	private static ComboPooledDataSource ds=null;
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ds=new ComboPooledDataSource();
		ds.setMaxPoolSize(15);
		ds.setUser(dbUser);
		ds.setPassword(dbPwd);
		ds.setJdbcUrl(jdbcUrl);
		try {
			ds.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Set<Connection> conns=new HashSet<>();
	public static synchronized Connection getConnection() throws SQLException {
		return ds.getConnection();
}
}
