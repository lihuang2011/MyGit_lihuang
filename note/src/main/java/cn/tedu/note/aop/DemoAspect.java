package cn.tedu.note.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//  Aspect 切面--横切面
@Component
@Aspect  //配合<aop:aspectj-autoproxy/> 自动代理 
public class DemoAspect {
	/**
	 * 切入点：cn.tedu.note.controller包中的所有类的所有方法
	 * 
	 * 通知：@Before 在调用方法之前时候执行
	 */
//	@Before("within(cn.tedu.note.controller.NoteController)")
//	@Before("bean(userDAO)")
//	@Before("bean(*DAO)")
	@Before("execution(* "+ 
	        "cn.tedu.note.controller.AccountController.randomCode())")
	
	public void hello(){
		System.out.println("Hello World!");
	}
}
