package cn.tedu.note.service;

import java.util.List;

public interface DemoService {
	//String... 就是String[];
	//编译插除(替换)为String[] new String[]{userId};
	//称为变长参数
	List<String> batchRegist(String... userName);
}
