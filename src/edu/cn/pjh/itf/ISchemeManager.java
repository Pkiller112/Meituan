package edu.cn.pjh.itf;

import java.text.ParseException;
import java.util.List;

import edu.cn.pjh.model.BeanScheme;
import edu.cn.pjh.util.BaseException;

public interface ISchemeManager {
	void deleteScheme(BeanScheme b)throws BaseException;
	void changeScheme(BeanScheme b) throws BaseException;
	void createScheme(int bsnid, float need,float reduce,String ifcpn) throws BaseException, ParseException;
	List<BeanScheme> searchScheme(int bsnid) throws BaseException;
}
