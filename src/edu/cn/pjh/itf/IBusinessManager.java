package edu.cn.pjh.itf;

import java.text.ParseException;
import java.util.List;

import edu.cn.pjh.model.BeanBusiness;
import edu.cn.pjh.util.BaseException;

public interface IBusinessManager {
	void deleteBusiness(int businessid)throws BaseException;
	void changeBusiness(BeanBusiness b)throws BaseException;
	void createBusiness(String name,int star) throws BaseException, ParseException;
	List<BeanBusiness> searchBusiness(String text) throws BaseException;
	
}
