package cn.tedu.note.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.tedu.note.entity.Notebook;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.web.JsonResult;
@Controller
@RequestMapping("/notebook")
public class NotebookController {
	@Autowired
	private NotebookService notebookService;
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult<List<Map<String,Object>>> 
		list(String userId){
		try {
			List <Map<String,Object>> list =
				 notebookService.listNotebooks(userId);
			return new JsonResult<List<Map<String,Object>>>(list);
		} catch (Exception e) {

			e.printStackTrace();
			return new JsonResult<List<Map<String,Object>>>(e);
		}
		
	}
	@RequestMapping("/saveNotebook.do")
	@ResponseBody
	public JsonResult<Notebook> 
		saveNotebook(String userId,String typeId,String name){
		try {
			Notebook notebook = 
					notebookService.saveNotebook(
							userId, typeId, name);
			return new JsonResult<Notebook>(notebook);
		} catch (Exception e) {
			
			e.printStackTrace();
			return new JsonResult<Notebook>(e);
		}
	}
	
}
