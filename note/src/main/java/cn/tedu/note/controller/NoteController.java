package cn.tedu.note.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.entity.Note;

import cn.tedu.note.service.NoteService;
import cn.tedu.note.web.JsonResult;

@Controller
@RequestMapping("/note")
public class NoteController {
	@Autowired
	private NoteService noteService;
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult<List<Map<String,Object>>> 
		listNotesByNotebookId(String notebookId){
		try {
			List<Map<String,Object>> list = 
					noteService.listNotesByNotebookId(
							notebookId);
			return new JsonResult<List<Map<String,Object>>>(list);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<Map<String,Object>>>(e);
		}
	}
//	@RequestMapping("/description.do")
//	@ResponseBody
	public JsonResult<Map<String,Object>> 
		displayDesciption(String noteId){
		try {
			Map<String,Object> map= 
					noteService.displayDescription(noteId);
//			JsonResult Json = new JsonResult();
//			Json.setState(0);
//			Json.setMessage("");
//			Json.setData(str);
			return new JsonResult<Map<String,Object>>(map);
		} catch (Exception e) {
//			JsonResult Json = new JsonResult();
//			Json.setState(1);
//			Json.setMessage(e.getMessage());
//			e.printStackTrace();
			return new JsonResult<Map<String,Object>>(e);
		}
		
	}
	@RequestMapping("/description.do")
	@ResponseBody
	public JsonResult<Note> loadNote(String noteId){
		try {
			Note note = 
					noteService.loadNote(
							noteId);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			e.printStackTrace();
			return  new JsonResult<Note>(e); 
		}
		
		
	}
	@RequestMapping("/update.do")
	@ResponseBody
	public JsonResult<Note> updateNote(
		String noteId,String title,String body){
		try {
			Note note = noteService.updateNoteBody(
					noteId, title, body);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			
			e.printStackTrace();
			return new JsonResult<Note>(e);
		}
	}
	@RequestMapping("/saveNote.do")
	@ResponseBody
	public JsonResult<Note> 
		saveNote(String userId,
				String notebookId,String title){
		try {
			Note note = 
					noteService.saveNote(
							userId, notebookId, title);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			
			e.printStackTrace();
			return new JsonResult<Note>(e);
		}
	}
	@RequestMapping("/updateNote.do")
	@ResponseBody
	public JsonResult<Note> updateNote(
			String noteId){
		try {
			Note note = 
					noteService.deleteNote(noteId);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			
			e.printStackTrace();
			return new JsonResult<Note>(e);
		}
	}
	@RequestMapping("/moveNote.do")
	@ResponseBody
	public JsonResult<Note> 
		moveNote(String noteId,String notebookId){
		try {
			Note note = 
					noteService.moveNote(
							noteId, notebookId);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Note>(e);
		}
	}
	@RequestMapping("/search.do")
	@ResponseBody
	public JsonResult<List<Map<String,Object>>> searchNotes(
		String key,int page,int pageSize){
		List<Map<String,Object>> list =
				noteService.searchNotes(key, page, pageSize);
		return new JsonResult<List<Map<String,Object>>>(list);
	}
}
