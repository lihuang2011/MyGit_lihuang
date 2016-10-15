 package cn.tedu.note.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.NoteDAO;
import cn.tedu.note.dao.NotebookDAO;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Notebook;
@Service("noteService")
public class NoteServiceImpl implements NoteService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private NoteDAO noteDAO;
	@Autowired
	private NotebookDAO notebookDAO;
	@Transactional(readOnly=true)
	public List<Map<String, Object>> 
		listNotesByNotebookId(String notebookId) {
		if(notebookId==null || 
				notebookId.trim().isEmpty()){
			throw new ServiceException(
					"notebookId不能为空");
		}
		return noteDAO.findNotesByNotebookId(
					notebookId);
	}
	public Map<String,Object> displayDescription(String noteId) {
		if(noteId==null || 
				noteId.trim().isEmpty()){
			throw new ServiceException(
					"noteId不能为空");
		}
		return noteDAO.findNoteBodyByNoteId(
				noteId);
	}
	public Note loadNote(String noteId) {
		if(noteId==null ||
				noteId.trim().isEmpty()){
			throw new ServiceException(
					"noteId不能为空");
		}
		return noteDAO.findNoteByNoteId(
				noteId);
	}
	public Note updateNoteBody(
		String noteId, String title, String body) {
		if(noteId==null || 
				noteId.trim().isEmpty()){
			throw new ServiceException("ID不能为空");
		}
		if(title==null){
			title = "";
		}
		if(body==null){
			body="";
		}
		Note note = 
				noteDAO.findNoteByNoteId(noteId);
		if(note==null){
			throw new ServiceException("没有笔记信息");
		}
		if(!title.trim().isEmpty()){
			note.setTitle(title);
		}
		note.setBody(body);
		long now = System.currentTimeMillis();
		note.setLastModifyTime(now);
		noteDAO.updateNote(note);
		return note;
	}
	public Note saveNote(
		String userId, String notebookId, String title) {
		if(userId==null||
				userId.trim().isEmpty()){
			throw new ServiceException(
					"userId不能为空");
		}
		if(notebookId==null||
				notebookId.trim().isEmpty()){
			throw new ServiceException(
					"notebookId不能为空");
		}
		if(title==null||
				title.trim().isEmpty()){
			title = "未命名笔记";
					
		}
		Note note = 
				new Note(UUID.randomUUID().toString(),
						notebookId,userId,NORMAL_STATUS,
						NORMAL_TYPE,title,"",
						System.currentTimeMillis(),
						System.currentTimeMillis());
		noteDAO.addNote(note);
		return note;
	}
	public Note deleteNote(String noteId) {
		if(noteId==null ||noteId.trim().isEmpty()){
			throw new ServiceException(
					"笔记ID不能为空");
		}
		Note note = 
				noteDAO.findNoteByNoteId(noteId);
		if(note == null){
			throw new ServiceException(
					"ID不存在");
		}
		if(NORMAL_TYPE.equals(
				note.getTypeId())){
			note.setTypeId(DELETE_TYPE);
			long now =
					System.currentTimeMillis();
			note.setLastModifyTime(now);
			noteDAO.updateNote(note);
			return note;	
		}
		throw new ServiceException(
				"只能删除正常笔记");
	}
	public Note moveNote(
		String noteId, String notebookId) {
		if(noteId==null||
				noteId.trim().isEmpty()){
			throw new ServiceException(
					"笔记ID不能为空");
		}
		if(notebookId==null||
				notebookId.trim().isEmpty()){
			throw new ServiceException(
					"笔记本ID不能为空");
		}
		Note note = 
				noteDAO.findNoteByNoteId(noteId);
		if(note==null){
			throw new ServiceException(
					"笔记ID不存在");
		}
		Notebook notebook = 
				notebookDAO.findNotebookById(
						notebookId);
		if(notebook==null){
			throw new ServiceException(
					"笔记本ID不存在");
		}
		if(NORMAL_TYPE.equals(
				note.getTypeId())){
			note.setNotebookId(notebookId);
			note.setLastModifyTime(
					System.currentTimeMillis());
			noteDAO.updateNote(note);
			return note;
		}
		throw  new ServiceException(
				"只能移除正常笔记");
	}
	@Transactional
	public void deleteNotes(String... ids) {
		//检查别更新的数据是否存在
		//拼接查询条件
		Map<String,Object> map =
				new HashMap<String,Object>();
		map.put("typeId",NORMAL_TYPE);
		map.put("ids", ids);
		//检查正常笔记数量
		Integer n =
				noteDAO.countNotes(map);
		if(n!=ids.length){
			throw new ServiceException("被删除的笔记不存在");
			
		}
		map.put("typeId", DELETE_TYPE);
		noteDAO.updateNotes(map);
	}
	public List<Map<String, Object>> searchNotes(
		String key, int page, int pageSize) {
		if(page<0){
			throw new ServiceException("页面不存在");
		}
		int start = page*pageSize;
		Map<String ,Object> parameter =
				new HashMap<String,Object>();
		parameter.put("start", start);
		parameter.put("size", pageSize);
		if(key!=null){
			key = key.trim();
			if(!key.isEmpty()){
				key = "%"+key+"%";
				parameter.put("title", key);
				parameter.put("body", key);
			}
		}
		List<Map<String,Object>> list =
				noteDAO.findAllByKeys(parameter);
		return list;
	}
	
}
