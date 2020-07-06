package edu.cn.pjh.itf;

import java.util.List;

import edu.cn.pjh.model.BeanAddress;
import edu.cn.pjh.util.BaseException;

public interface IAddressManager {
	void deleteAddress(int addid)throws BaseException;
	void changeAddress(BeanAddress b) throws BaseException;
	void createAddress(int csmrid,String pro,String city,String area,String info,String com,String phone) throws BaseException;
	List<BeanAddress> searchAddress(int csmrid,String content) throws BaseException;
}
