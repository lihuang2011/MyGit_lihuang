package cn.tedu.note.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
 * 相当与AOP切面，是扩展功能的类
 * @author soft01
 *
 */
public class DemoAspectBean 
	implements InvocationHandler{
	DemoBeanIf bean;
	
	public DemoAspectBean(
		DemoBeanIf bean){
		this.bean = bean;
		
		
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 *相当与@Around
	 */
	public Object invoke(
		Object proxy, 
		Method method,
		Object[] args) throws Throwable {
		//proxy  是代理接口
		//method 是代理方法
		//args  是方法调用参数
		System.out.println("开始");
		//执行目标方法
		Object obj = method.invoke(bean, args);
		System.out.println("结束");
		return obj;
	}
	
}
