package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;

public interface NoteDAO {
	List<Map<String,Object>> 
		findNotesByNotebookId(String notebookId);
	Map<String,Object> 
		findNoteBodyByNoteId(String noteId);
	Note findNoteByNoteId(String noteId);
	void addNote(Note note);
	void updateNote(Note note);
	void removeNote(Note note);
	
	/**
	 * 数组作为参数的更新方法
	 * 
	 */
	void updateNoteDelType(String... ids);
	/**
	 * List作为动态查询参数
	 * 
	 */
	Integer countNormalNote(List<String> ids);
	/**
	 * 使用Map作为参数
	 * 批量更新Note
	 * map中可以保存多个属性
	 * map = {typeId:'2',lastModifyTime:13546847616,ids:['1','2','3']}
	 */
	void updateNotes(Map<String,Object> map);
	/**
	 * 可以传递参数
	 * map={typeId:'1',lastModifyTime:1234343252,ids:['1','2','3']...};
	 */
	Integer countNotes(Map<String,Object> map);
	
	void deleteNotes(String... ids);
	
	void addNotes(List<Note> list);
	
	List<Map<String,Object>> findAllByKeys(Map<String ,Object> map);
	
}
