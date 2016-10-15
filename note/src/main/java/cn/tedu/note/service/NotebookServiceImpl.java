package cn.tedu.note.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.note.dao.NotebookDAO;
import cn.tedu.note.entity.Notebook;
@Service("notebookService")
public class NotebookServiceImpl implements NotebookService ,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private NotebookDAO notebookDAO;
	
	public List<Map<String, Object>> 
		listNotebooks(String userId) {
		if(userId==null || 
				userId.trim().isEmpty()){
			throw new ServiceException(
					"userId不能为空");
		}
		
		return notebookDAO
				.findNotebookByUserId(userId);
	}

	public Notebook saveNotebook(
		String userId, String typeId, String name) {
		if(userId==null || 
			userId.trim().isEmpty()){
			throw new ServiceException(
					"userId不能为空");
		}
		if(name==null || 
			name.trim().isEmpty()){
			throw new ServiceException(
					"name不能为空");
		}
		Notebook notebook = 
				new Notebook();
		notebook.setId(UUID.randomUUID().toString());
		notebook.setUserId(userId);
		notebook.setTypeId(typeId);
		notebook.setName(name);
		notebook.setCreatime(new Timestamp(
				System.currentTimeMillis()));
		notebookDAO.addNotebook(notebook);
		System.out.println(notebook.getId());
		return notebook;
	}

}
