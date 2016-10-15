package cn.tedu.note.service;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;


/**
 * @author soft01
 *
 */
public interface NoteService {
	String NORMAL_TYPE = "1";
	String DELETE_TYPE = "2";
	String DISABLED_TYPE = "3";
	
	String NORMAL_STATUS = "1";
	String FAVORITE_STATUS = "2";
	//查询笔记本中的全部笔记
	List<Map<String ,Object>> 
		listNotesByNotebookId(String notebookId);
	Map<String,Object> displayDescription(
				String noteId);
	Note loadNote(String noteId);
	/**
	 * 更新笔记的内容
	 * @param noteId 笔记的ID
	 * @param title 标题
	 * @param body 内容
	 */
	Note updateNoteBody(
			String noteId,String title,String body);
	Note saveNote(String userId,
			String notebookId,String title);
	Note deleteNote(String noteId);
	Note moveNote(String noteId,String notebookId);
	/**
	 * 批量删除的方法，批量移动方法
	 * 指定的笔记
	 * @param ids
	 */
	void deleteNotes(String... ids);
	/**
	 * 
	 * @param key 查找关键字
	 * @param page 页号
	 * @param pageSize 页面大小
	 */
	List<Map<String,Object>>
		searchNotes(String key,int page,int pageSize);
}
