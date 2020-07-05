package edu.cn.pjh.itf;

import java.util.List;

import edu.cn.pjh.model.BeanUser;
import edu.cn.pjh.util.BaseException;

public interface ISystemUserManager {

	void changeUserPwd(String string, String string2, String string3) throws BaseException;

	void createUser(String userid, String username, String pwd1, String pwd2) throws BaseException;

	List<BeanUser> loadAllUsers() throws BaseException;

	void deleteUser(String userid) throws BaseException;

}
