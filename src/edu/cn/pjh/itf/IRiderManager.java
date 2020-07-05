package edu.cn.pjh.itf;

import java.text.ParseException;
import java.util.List;

import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;

public interface IRiderManager {

	List<BeanRider> searchRiders(String text, String string) throws BaseException;

	public void createRider(String name,String status,String born,String sex) throws BaseException, ParseException;

	void changeRider(BeanRider b) throws BaseException;

	void deleteRider(int riderid) throws BaseException;

}
