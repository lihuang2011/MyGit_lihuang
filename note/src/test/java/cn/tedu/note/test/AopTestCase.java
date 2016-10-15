package cn.tedu.note.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.note.aop.DemoBean;

public class AopTestCase {
	private ApplicationContext ctx;
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext(
				"spring-aop.xml");
	}
	@Test
	public void test(){
		DemoBean bean = 
				ctx.getBean("demoBean",DemoBean.class);
		bean.test("123");
		bean.test(null);
	}
	@Test
	public void testAround(){
		DemoBean bean = 
				ctx.getBean("demoBean",DemoBean.class);
		bean.demo("1234");
//		bean.demo(null);
	
	}
}
