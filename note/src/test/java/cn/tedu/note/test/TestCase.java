package cn.tedu.note.test;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.blog.dao.PostDAO;
import cn.tedu.blog.entity.Post;
import cn.tedu.note.dao.NoteDAO;
import cn.tedu.note.dao.NotebookDAO;
import cn.tedu.note.dao.StudentDAO;
import cn.tedu.note.dao.UserDAO;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.User;
import cn.tedu.note.service.DemoService;
import cn.tedu.note.service.NoteService;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.Md5;

public class TestCase {
	private ApplicationContext ctx;
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext(
				"spring-mybatis.xml",
				"spring-service.xml",
				"spring-web.xml");
	}
	//测试mybatis配置
	@Test
	public void testMapperScanner(){
		
		Object obj = ctx.getBean("mapperScanner");
		
	}
	//测试UserDAO的save方法
	@Test
	public void testSaveDAO(){
		UserDAO dao = 
				ctx.getBean("userDAO",UserDAO.class);
		System.out.println(dao);
		//调用UUID.randomUUID().toString()生成字符串  永远不重复。
		String id = UUID.randomUUID().toString();
		System.out.println(id);
		User user = new User(
				id,"Tom","123","","Tomcat");
		dao.saveUser(user);
	}
	
	@Test
	public void testFindUserById(){
		UserDAO dao = 
				ctx.getBean("userDAO",UserDAO.class);
		User user = dao.findUserById(
				"39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
		System.out.println(user);
		
	}
	@Test
	public void testFindAll(){
		UserDAO dao = 
				ctx.getBean("userDAO",UserDAO.class);
		List<User> users = 
				dao.findAll();
		for(User user:users){
			System.out.println(user);
		}
	}
	@Test
	public void deleteUser(){
		UserDAO dao = 
				ctx.getBean("userDAO",UserDAO.class);
		User user = dao.findUserById(
				"14b1d649-0b7c-4913-8867-3f3006bccda4");
		dao.deleteUser(user);
	}
	@Test
	public void testUpdateUser(){
		UserDAO dao = 
				ctx.getBean("userDAO",UserDAO.class);
		User user = dao.findUserById(
				"7af99b59-21d3-4b67-8440-4d040be23270");
		user.setName("Jack");
		user.setPassword("4578932131");
		user.setNick("maven");
		dao.updateUser(user);
	}
	@Test
	public void findUserByName(){
		UserDAO dao = 
				ctx.getBean("userDAO",UserDAO.class);
		User user = dao.findUserByName("Tom");
		System.out.println(user);
	}
	@Test
	public void testLogin(){
		String name = "Tom";
		String password = "123";
		UserService service =
				ctx.getBean("userService",
							UserService.class);
		User user = 
				service.login(name, password);
		System.out.println(user);
	}
	@Test
	public  void testMsg(){
		
		try {
			//Message:消息  Digest：摘要
			//MessageDigest 封装了复制的消息摘要算法
			MessageDigest md =
					MessageDigest.getInstance("MD5");
			//"MD5" 是算法名称
			//update :提交数据,如果多次调用的话，
			//就是对一堆数据进行摘要
			//md.update(数据);
			/*处理大数据
			md.update(0-99字节);
			md.update(100-199字节);
			md.update(200-299字节);
			byte[] 摘要 = md.digest();
			*/
			//处理小数据  摘要 = md.digest(数据)；
			String password ="123456";
			byte[] data = password.getBytes("utf-8");
			byte[] md5 = md.digest(data);
//			for(byte b:md5){
//				System.out.println(b);
//			}
			//导  commons-codec
			//将一个byte数组换算为16进制数
			String hex = 
					Hex.encodeHexString(md5);
			System.out.println(hex);
			//base64  将2进制转换为文本编码
			String base64 = 
					Base64.encodeBase64String(md5);
			System.out.println(base64);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testSaltMd5(){
		String pwd = "123";
		System.out.println(Md5.saltMd5(pwd));
	}
	@Test
	public void testRegist(){
		UserService userService = 
				ctx.getBean("userService",
						UserService.class);
		User user = 
				userService.regist("Tomca", "123", "1");
		System.out.println(user);
 	}
	@Test
	public void testFindNotebookByUserId(){
		NotebookDAO dao = 
				ctx.getBean(
						"notebookDAO",NotebookDAO.class);
		String id = "39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		List<Map<String,Object>> list =
				dao.findNotebookByUserId(id);
		for(Map<String,Object> map:list){
			System.out.println(map);
		}
	}
	@Test
	public void testNotebook(){
		NotebookService service =
				ctx.getBean(
						"notebookService",
						NotebookService.class);
		List<Map<String,Object>> list =
				service.listNotebooks(
						"39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
		for(Map<String ,Object> map:list){
			System.out.println(map);
		}
	}
	@Test
	public void testFindNotesByNotebookId(){
		NoteDAO dao = 
				ctx.getBean(
						"noteDAO",NoteDAO.class);
		String notebookId ="0cd94778-4d52-486d-a35d-263b3cfe6de9";
		List<Map<String,Object>> list = 
				dao.findNotesByNotebookId(notebookId);
		for(Map<String,Object> map :list){
			System.out.println(map);
		}
	}
	@Test
	public void testListNotesByNotebookId(){
		NoteService service =
				ctx.getBean("noteService",
						NoteService.class);
		String notebookId ="6d763ac9-dca3-42d7-a2a7-a08053095c08";
		List<Map<String,Object>> list = 
				service.listNotesByNotebookId(notebookId);
		for(Map<String,Object> map :list){
			System.out.println(map);
		}
	}
	@Test
	public void testFindBodyByNoteId(){
		NoteDAO dao = 
				ctx.getBean(
						"noteDAO",NoteDAO.class);
		Map<String,Object> map = dao.findNoteBodyByNoteId(
				"668354a9-c7b4-46a0-9a6d-b138cdefc941");
		System.out.println(map);
	}
	@Test 
	public void testDisplayDescription(){
		NoteService service =
				ctx.getBean("noteService",
						NoteService.class);
		Map<String,Object> map=service.displayDescription(
				"668354a9-c7b4-46a0-9a6d-b138cdefc941");
		System.out.println(map);
	}
	@Test
	public void testFindNoteByNoteId(){
		NoteDAO dao = 
				ctx.getBean(
						"noteDAO",NoteDAO.class);
		Note note = dao.findNoteByNoteId(
				"668354a9-c7b4-46a0-9a6d-b138cdefc941");
		System.out.println(note);
	}
	@Test
	public void testAddNote(){
		NoteDAO dao = 
				ctx.getBean("noteDAO",NoteDAO.class);
		Note note1 = new Note(UUID.randomUUID().toString(),
				"1234","12345",null,"2",
				"123456","12345678",System.currentTimeMillis(),
				System.currentTimeMillis());
		dao.addNote(note1);
	}
	@Test
	public void testUpdate(){
		NoteDAO dao = 
				ctx.getBean("noteDAO",NoteDAO.class);
		Note note = dao.findNoteByNoteId(
				"668354a9-c7b4-46a0-9a6d-b138cdefc941");
		System.out.println(note);
		note.setTitle("hahaha");
		dao.updateNote(note);
		note = dao.findNoteByNoteId(
				"668354a9-c7b4-46a0-9a6d-b138cdefc941");
		System.out.println(note);
	}
	public void testRemoveNote(){
		
	}
	@Test
	public void testBatchRegist(){
		DemoService service = 
				ctx.getBean("demoService",DemoService.class);
		List<String> pwdList = 
				service.batchRegist("Haojian","CCTV");
		System.out.println(pwdList);
	}
	@Test 
	public void testUpdateNoteDelType(){
		NoteDAO noteDAO =
				ctx.getBean("noteDAO",NoteDAO.class);
		String[] arr =
				{"ffc2cf21-78ed-4647-adb4-3e545613ef26",
				"fed920a0-573c-46c8-ae4e-368397846efd ",
				"fe6d1e7c-8cf0-449e-8b8a-553eaa9d37c3",
				"f74d03aa-d01d-4989-95e8-4757d6ca8a2a "};
		noteDAO.updateNoteDelType(arr);
	}
	@Test
	public void testCountNormalNote(){
		List<String>list = 
				new ArrayList<String>();
		list.add("f4cce0e5-ba14-4deb-bc04-e396f53c40f3");
		list.add("f4594f33-06d4-47dc-87cf-c3bd20e5a23f ");
		list.add("f3ad58a7-eb14-4766-90b2-25d5fd22113b");
		list.add("08c75cec-6af6-40a9-b73a-a48763a70a9a");
		NoteDAO noteDAO =
				ctx.getBean("noteDAO",NoteDAO.class);
		int i = noteDAO.countNormalNote(list);
		System.out.println(i);
	}
	@Test
	public void testUpdateNotes(){
		NoteDAO noteDAO =
				ctx.getBean("noteDAO",NoteDAO.class);
		Map<String,Object> map =
				new HashMap<String,Object>();
		String[] arr =
			{"ffc2cf21-78ed-4647-adb4-3e545613ef26",
			"fed920a0-573c-46c8-ae4e-368397846efd ",
			"fe6d1e7c-8cf0-449e-8b8a-553eaa9d37c3",
			"f74d03aa-d01d-4989-95e8-4757d6ca8a2a "};
		map.put("typeId", "1");
		map.put("lastModifyTime", System.currentTimeMillis());
		map.put("ids", arr);
		noteDAO.updateNotes(map);
	}
	@Test
	public void testCountNotes(){
		NoteDAO noteDAO =
				ctx.getBean("noteDAO",NoteDAO.class);
		Map<String,Object> map =
				new HashMap<String,Object>();
		String[] arr =
			{"ffc2cf21-78ed-4647-adb4-3e545613ef26",
			"fed920a0-573c-46c8-ae4e-368397846efd ",
			"fe6d1e7c-8cf0-449e-8b8a-553eaa9d37c3",
			"f74d03aa-d01d-4989-95e8-4757d6ca8a2a "};
		map.put("typeId", "2");
//		map.put("lastModifyTime", System.currentTimeMillis());
		map.put("ids", arr);
		Integer n = noteDAO.countNotes(map);
		System.out.println(n);
	}
	@Test
	public void testDeleteNotes(){
		NoteService service =
				ctx.getBean("noteService",NoteService.class);
		String[] arr =
			{"ffc2cf21-78ed-4647-adb4-3e545613ef26",
			"fed920a0-573c-46c8-ae4e-368397846efd ",
			"fe6d1e7c-8cf0-449e-8b8a-553eaa9d37c3",
			"f74d03aa-d01d-4989-95e8-4757d6ca8a2a "};
		service.deleteNotes(arr);
		
	}
	@Test
	public void testDeleteNotes2(){
		NoteDAO noteDAO =
				ctx.getBean("noteDAO",NoteDAO.class);
		noteDAO.deleteNotes("22222222222222222222222222222222222",
							"2013b419-4439-4109-b4db-c2cc1b58a1b9",
							"2228527e-1cad-4318-94ee-3749a4dc25ce");
	}
	@Test
	public void testAddNotes(){
		NoteDAO noteDAO =
				ctx.getBean("noteDAO",NoteDAO.class);
		Note note1 = new Note(UUID.randomUUID().toString(),
							"1234345","12345532",null,"2",
							"dddd","12345678",System.currentTimeMillis(),
							System.currentTimeMillis());
		Note note2 = new Note(UUID.randomUUID().toString(),
				"11112","11113",null,"2",
				"cccc","123412",System.currentTimeMillis(),
				System.currentTimeMillis());
		List<Note> list = 
				new ArrayList<Note>();
		list.add(note1);
		list.add(note2);
		noteDAO.addNotes(list);
	}
	@Test
	public void testFindAllByKeys(){
		Map<String,Object> map =
				new HashMap<String,Object>();
//		map.put("typeId", "1");
//		map.put("title", "%");
//		map.put("body", "%11");
		map.put("start", 5);
		map.put("size",5);
		NoteDAO noteDAO =
				ctx.getBean("noteDAO",NoteDAO.class);
		List<Map<String,Object>> list = 
				noteDAO.findAllByKeys(map);
		for(Map<String,Object> m:list){
			System.out.println(m);
		}
	}
	@Test 
	public void testSaveStudent(){
		StudentDAO dao =
				ctx.getBean("studentDAO",StudentDAO.class);
	}
	
	@Test
	public void testSearchNotes(){
		NoteService service =
				ctx.getBean("noteService",NoteService.class);
		List<Map<String,Object>> list =
				service.searchNotes("",0,10);
		
		for(Map<String,Object> map : list){
			System.out.println(map);
		}
		
	}
	
	@Test
	public void testFindPostById(){
		PostDAO dao =
				ctx.getBean("postDAO",PostDAO.class);
		Post post = dao.findPostById("1");
		System.out.println(post);
	}
	
	
}
