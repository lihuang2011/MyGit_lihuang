package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Notebook;

public interface NotebookDAO {
	List<Map<String,Object>> 
		findNotebookByUserId(String id);
	Notebook findNotebookById(
			String notebookId);
	void addNotebook(Notebook notebook);
	void updateNotebook(Notebook notebook);
	void removeNotebook(Notebook notebook);
}
