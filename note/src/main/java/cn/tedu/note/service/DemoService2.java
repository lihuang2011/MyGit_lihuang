package cn.tedu.note.service;

import java.util.List;



import cn.tedu.note.entity.User;

public interface DemoService2 {

	List<String> batchRegist(String... userName);

	
	User regist(String name, String password, String nick);

	List<String> baRegist(String... userName);

}