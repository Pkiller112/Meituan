package edu.cn.pjh;

import edu.cn.pjh.itf.*;
import edu.cn.pjh.control.*;

public class PPutil {
	public static ISystemUserManager SystemUserManager=new SystemUserManager();
	public static IBusinessManager BusinessManager=new BusinessManager();
	public static IConsumerManager ConsumerManager=new ConsumerManager();
	public static IRiderManager RiderManager=new RiderManager();
	public static IProductTypesManager ProductTypesManager=new ProductTypesManager();
	public static IProductManager ProductManager=new ProductManager();
	public static ICouponManager CouponManager=new CouponManager();
	public static ISchemeManager SchemeManager=new SchemeManager();
	public static IOrderManager OrderManager=new OrderManager();
	public static IAddressManager AddressManager=new AddressManager();
}
