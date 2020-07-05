package edu.cn.pjh.itf;

import java.text.ParseException;
import java.util.List;

import edu.cn.pjh.model.BeanProduct;
import edu.cn.pjh.util.BaseException;

public interface IProductManager {
	void deleteProduct(BeanProduct b)throws BaseException;
	void changeProduct(BeanProduct b)throws BaseException;
	void createProduct(int typeid,String name, float price,float vipprice,int num) throws BaseException, ParseException;
	List<BeanProduct> searchProduct(int typeid) throws BaseException;
}
