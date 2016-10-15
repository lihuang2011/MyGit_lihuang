package cn.tedu.note.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.note.dao.UserDAO;
import cn.tedu.note.entity.User;
import cn.tedu.note.util.Md5;
@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final long serialVersionUID = 1L;
	@Autowired//或者Resource
	private UserDAO userDAO;

	public User login(
		String name,
		String password)
		throws NameOrPasswordException {
		//入口参数检查
		if(name==null || 
			name.trim().isEmpty()){
			throw new NameOrPasswordException(
								"用户名不能为空");
		}
		if(password == null || 
				password.trim().isEmpty()){
			throw new NameOrPasswordException(
						"密码不能为空");
		}
		//从业务层查询用户信息
		User user = 
				userDAO.findUserByName(name);
		if(user==null){
			throw new NameOrPasswordException(
					"用户名或密码错误");
		}if(user.getPassword().equals(
				Md5.md5(password)) ||
			user.getPassword().equals(
						Md5.saltMd5(password))){
			return user;//登录成功
		}
		 throw new  NameOrPasswordException(
				 	"用户名或密码错误");
	}
	//UserServiceImpl实现类

	public User regist(
		String name,
		String pwd,
		String nick)
		throws UserExistException {
		String rule = "^\\w{3,10}$";
		
		if(name==null || 
				name.trim().isEmpty()){
			throw new ServiceException("用户不能为空！");	
		}
		if(!name.matches(rule)){
			throw new ServiceException("用户名不合格！");
		}
		
		if(pwd==null || 
				pwd.trim().isEmpty()){
			throw new ServiceException("密码不能为空！");
		}
		if(!pwd.matches(rule)){
			throw new ServiceException("密码不合格");
		}
		if(nick==null || 
				nick.trim().isEmpty()){
			throw new ServiceException("昵称不能为空！");
		}
		rule = "^.{3,10}$";
		if(!nick.matches(rule)){
			throw new ServiceException("昵称不合格！");
		}
		User user = 
				userDAO.findUserByName(name);
		if(user!=null){
			throw new UserExistException(
					"用户已经存在！");
		}
		String id = 
				UUID.randomUUID().toString();
		pwd = Md5.saltMd5(pwd);
		
		user = new User(id,name,pwd,"",nick);
		userDAO.saveUser(user);
		//还有很多实际业务功能逻辑
		return user;
	}
	

}
