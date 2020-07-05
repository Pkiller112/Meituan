package edu.cn.pjh.itf;

import java.text.ParseException;
import java.util.List;

import edu.cn.pjh.model.BeanConsumer;
import edu.cn.pjh.model.BeanRider;
import edu.cn.pjh.util.BaseException;

public interface IConsumerManager {
	void createConsumer(String name,String Acc,String Pwd,String Phone,String Sex) throws BaseException;
	List<BeanConsumer> searchConsumers(String State,String content) throws BaseException;
	void deleteConsumer(int csmrid) throws BaseException;
	void changeConsumer(BeanConsumer b) throws BaseException;
	public void BecomeVip(BeanConsumer consumer,int month) throws BaseException, ParseException;
}
