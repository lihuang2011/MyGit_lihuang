package cn.tedu.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.UserDAO;
import cn.tedu.note.entity.User;
import cn.tedu.note.util.Md5;

@Service("demoService")

public class DemoServiceImpl 
	implements DemoService, DemoService2{
	@Resource
	private UserDAO userDAO;
	
	public void update(String... userId) {
		//修改多个用户的更新时间	
		long now = System.currentTimeMillis();
		for(String id:userId){
			User user = 
					userDAO.findUserById(id);
			user.setToken(Long.toString(now));
			userDAO.updateUser(user);
		}
	}
	/* (non-Javadoc)
	 * @see cn.tedu.note.service.DemoService2#batchRegist(java.lang.String)
	 */
	@Transactional
	public List<String> batchRegist(String... userName){
		List<String> list = 
				new ArrayList<String>();
 		for(String name:userName){
			User user = 
					userDAO.findUserByName(name);
			if(user!=null){
				throw new ServiceException("用户已经存在");
			}
			String uuid = UUID.randomUUID().toString();
			String password = 
					uuid.substring(uuid.length()-6, uuid.length());
			list.add(password);
			String md5=Md5.saltMd5(password);
			user = 
				new User(uuid,name,md5,"",name);
			userDAO.saveUser(user);
			
		}
 		//Spring的事务只绑定了当前线程
 		//业务层开启的子线程中的数据操作不参与
 		//当前事务
 		//需要单独处理子线程的事务
 		new Thread(){
 			//这个操作不参与业务层事务
 			//子线程中调用有事务管理的方法
 			public void run(){
// 				userDAO.saveUser(...);
 				somePpt();
 			};
 		}.start();
		return list;
	}
	@Transactional
	protected void somePpt() {
		
//		userDAO.saveUser();
		
	}
	
	
	//事务传播
	/* (non-Javadoc)
	 * @see cn.tedu.note.service.DemoService2#regist(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public User regist(
		String name,String password,String nick){
		if(name==null || name.trim().isEmpty()){
			throw new ServiceException("名字不能为空");
		}
		if(password==null || password.trim().isEmpty()){
			throw new ServiceException("密码不能为空");
		}
		if(nick==null||nick.trim().isEmpty()){
			nick=name;
		}
		User user = 
				userDAO.findUserByName(name);
		if(user!=null){
			throw new ServiceException(name+"已经注册");
		}
		String id = UUID.randomUUID().toString();
		String pwd = Md5.saltMd5(password);
		user = new User(id,name,pwd,"",nick);
		userDAO.saveUser(user);
		user = userDAO.findUserById(id);
		if(user==null){
			throw new ServiceException("注册失败");
		}
		return user;
	}
	/* (non-Javadoc)
	 * @see cn.tedu.note.service.DemoService2#baRegist(java.lang.String)
	 */
	@Transactional
	public List<String>
		baRegist(String... userName){
		List<String> list =
				new ArrayList<String>();
		for(String name :userName){
			
			String pwd = randomPassword(6);
			regist(name,pwd,name);
			list.add(pwd);
		}
		return list;
	}
	private String randomPassword(int n) {
		char[] pwd = new char[n];
		String str = 
				"1234567890abcdefghijklmnABCFDASGJREPHJREHN";
		for(int i=0;i<pwd.length;i++){
			int index = (int)(Math.random()*str.length());
			pwd[i] = str.charAt(index);	
		}
		//更多的业务方法....事务都会传播，
		//所有的方法参与一个事务，只要其中有异常，
		//事务都会回滚
		return new String(pwd);
	}

}
