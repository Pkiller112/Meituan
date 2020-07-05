package edu.cn.pjh.itf;

import java.text.ParseException;
import java.util.List;

import edu.cn.pjh.model.BeanProductType;
import edu.cn.pjh.util.BaseException;

public interface IProductTypesManager {
	void deleteProductType(BeanProductType b)throws BaseException;
	void changeProductType(BeanProductType b)throws BaseException;
	void createProductType(int bsnid,String name,String content) throws BaseException, ParseException;
	List<BeanProductType> searchProductType(int bsnid) throws BaseException;
}
