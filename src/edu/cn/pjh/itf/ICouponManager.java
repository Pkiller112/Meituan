package edu.cn.pjh.itf;

import java.text.ParseException;
import java.util.List;

import edu.cn.pjh.model.BeanCoupon;
import edu.cn.pjh.model.BeanProduct;
import edu.cn.pjh.util.BaseException;

public interface ICouponManager {

	List<BeanCoupon> searchCoupon(int bsnid) throws BaseException;
	void createCoupon(int bsnid, float reduce,int needtimes,String begin,String dead) throws BaseException, ParseException;
	void changeCoupon(BeanCoupon b) throws BaseException;
	void deleteCoupon(BeanCoupon b)throws BaseException;
	
}
