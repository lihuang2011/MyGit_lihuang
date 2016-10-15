package cn.tedu.note.test;

import java.sql.Timestamp;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.note.dao.NotebookDAO;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Notebook;
import cn.tedu.note.service.NoteService;
import cn.tedu.note.service.NotebookService;

public class TestCase2 {
	private NoteService noteService;
	private NotebookDAO notebookDAO ;
	private NotebookService notebookService;
	@Before
	public void init(){
		ApplicationContext ctx =
				new ClassPathXmlApplicationContext(
						"spring-mybatis.xml",
						"spring-service.xml",
						"spring-web.xml");
		notebookDAO = ctx.getBean(
				"notebookDAO",NotebookDAO.class);
		notebookService = ctx.getBean(
				"notebookService",NotebookService.class);
		noteService = ctx.getBean(
				"noteService",NoteService.class);
	}
	@Test
	public void testFindNotebook(){
		Notebook notebook = 
			notebookDAO.findNotebookById(
					"a35eb2d1-7602-4da5-9280-39bee4f7606e");
				System.out.println(notebook);
	}
	@Test
	public void testAddNotebook(){
		String id = 
				UUID.randomUUID().toString();
		Timestamp creatime  =
				new Timestamp(
					System.currentTimeMillis());
		Notebook notebook = 
				new Notebook(id,
						"39295a3d-cc9b-42b4-b206-a2e7fab7e77c",
						"5","李黄","gsahahaeh",creatime);
		notebookDAO.addNotebook(notebook);
		System.out.println(id);
	}
	@Test
	public void testUpdateNotebook(){
		Notebook book = 
				notebookDAO.findNotebookById(
						"a35eb2d1-7602-4da5-9280-39bee4f7606e");
		book.setName("lihuang");
		notebookDAO.updateNotebook(book);
	}
	@Test
	public void testSaveNotebook(){
		Notebook notebook =
			 notebookService.saveNotebook("39295a3d-cc9b-42b4-b206-a2e7fab7e77c", "1", "李黄234");
		System.out.println(notebook);
	}
	@Test
	public void testSaveNote(){
		Note note = 
				noteService.saveNote(
						"39295a3d-cc9b-42b4-b206-a2e7fab7e77c",
						"1a2573cf-247e-4d43-b7c7-02760a48b935", "(*^__^*) 嘻嘻……");
		System.out.println(note.getId());
		System.out.println(note);
	}
	
}
