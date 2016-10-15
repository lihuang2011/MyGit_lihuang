package cn.tedu.note.dao;

import java.util.List;

import cn.tedu.note.entity.User;

public interface UserDAO {
	void saveUser(User user);
	List<User> findAll();
	User findUserById(String id);
	void deleteUser(User user);
	void updateUser(User user);
	User findUserByName(String name);
	
}
