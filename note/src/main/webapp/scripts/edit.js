//scripts/edit.js
/*
 *笔记编辑界面的js脚本 
 *
 */
//数据模型
var model = {notebooks:[],//显示全部笔记本，
			currentNotebook:{},//显示当前选中笔记本
			notebookIndex:0,
			notes:[],//显示所有笔记
			currentNote:{},//显示当前笔记
			noteIndex:0
			};

$(function(){
	//页面加载以后，可以获取登录用户的id
	var userId = getCookie("userId");
	//console.log(userId);
	listNotebookAction(userId);
	//绑定点击添加笔记本事件
	$('#add_notebook').click(addNotebookAction);
	//绑定点击添加笔记事件
	$('#add_note').click(addNoteAction);
	//绑定点击保存笔记事件
	$('#save_note').click(saveNoteAction);
});
function addNotebookAction(){
	var url = baseUrl+'/alert/alert_notebook.html';
	$('#can').load(url,
		function(response,status,xhr){
		if(status=="success"){
			$('#can .cancle,#can .close')
				.click(function(){
					$('#can').empty();
			});
			$('#can .sure').click(saveNotebookAction);
		}else{
			//失败
			$('#can').empty();
		}
	});
}
function saveNotebookAction(){
	//打开对话框
	var userId = getCookie("userId");
	var name = $('#input_notebook').val();
	var url = baseUrl+'/notebook/saveNotebook.do';
	if(userId==null||userId==""){
		alert("uesrId不能为空")
		return;
	}
	if(name==null || name==""){
		alert("笔记本名称不能为空")
		return;
	}
	//typeId  暂时写死
	var data = {"userId":userId,
				"typeId":"1",
				"name":name};
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			listNotebookAction(userId)
			paintNotebooks();
			paintNotes();
			paintCurrentNote();
			$('#can').empty();
		}else{
			alert("创建笔记本失败");
		}
	});
}
function addNoteAction(){
	//打开对话框
	var url = baseUrl+'/alert/alert_note.jsp?t='+(new Date()).getTime();
	$('#can').load(url,
		function(response,status,xhr){
		if(status =="success"){
			//加载成功绑定按钮事件
			$('#can .cancle,#can .close')
				.click(function(){
				$('#can > div').hide(200,function(){
					$('#can').empty();
				});
			});
			$('#can .sure').click(saveNewNoteAction);
			$('#can>div').show(200);	
		}else{
			//失败 清空按钮
			$('#can').empty();
		}	
	});
}
function saveNewNoteAction(){
	//ajax
	//请求成功以后，返回Note对象
	//将note对象插入到notes的第一个
	//更改当前笔记对象
	//刷新页面：paintNotes（）
	//        paintCurrentNote（）；
	var title = $('#input_note').val();
	var notebookId = model.currentNotebook.id;
	var userId = getCookie('userId');
	var data = {"userId":userId,
				"notebookId":notebookId,
				"title":title};
	var url = baseUrl+'/note/saveNote.do';
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			var note = result.data;
			model.currentNote = note;
			model.noteIndex = 0;
			var ary = [{id:note.id,title:note.title}];
			model.notes = ary.concat(model.notes);
			$('#can > div').hide(200,function(){
				$('#can').empty();
			});	
			paintNotes();
			paintCurrentNote();
		}else{
			alert(result.message);
		}
	});
		
}



function listNotebookAction(userId){
	var url =baseUrl+"/notebook/list.do?userId="+userId;
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
			var list = result.data;
			for(var i = 0;i<list.length;i++){
				//console.log(list[i]);
			}
			//更新数据模型
			model.notebooks = list;
			//刷新笔记本列表的显示
			paintNotebooks();
			
		}else{
			alert(result.message);
			
		}
		
	});
	
	
//	$.ajax({
//		url:baseUrl+"/notebook/list.do",
//		type:"get",
//		data:{"userId":userId},
//		dataType:"JSON",
//		success:function(result){
//			if(result.state==SUCCESS){
//				var list =result.data;
//				for(var i=0;i<list.length;i++){
//					console.log(list[i]);
//				}
//			}else{
//				alert(result.message);
//			}
//		}
//	});
	return false;
}
/*
 *讲述据模型中的数据更新显示到界面上 
 */

function paintNotebooks(){
	var list = model.notebooks;
	var ul = $('#notebooks');
	ul.empty();
	for(var i=0;i<list.length;i++){
		var notebook = list[i];
		var li = '<li class="online">'
				+'<a><i class="fa fa-book" title="online" ref="tooltip-bottom">'
				+'</i>'+notebook.name+'</a></li>';
		
//		ul.append('<li class="online">'
//				+'<a><i class="fa fa-book" title="online" ref="tooltip-bottom">'
//				+'</i>'+notebook.name+'</a></li>');
		li =$(li).data("index",i);
		li.click(function(){
			$(this).parent().find('a').removeClass('checked');
			$(this).children('a').addClass('checked');
			var index = $(this).data("index");
//			console.log(index);
			var notebook=model.notebooks[index];
			model.currentNotebook = notebook;
//			console.log(notebook);
			model.notebookIndex = index;
			listCurrentNotesAction();
			
		});
		ul.append(li);
	}
}
/*
	<li class="online">
	<a  class='checked'>
	<i class="fa fa-book" title="online" rel="tooltip-bottom">
	</i> 默认笔记本</a></li>
*/
//控制器方法：获取数据，更新模型
function listCurrentNotesAction(){
	var nid = model.currentNotebook.id;
	//发起异步请求获取notes
	//异步请求成功以后，更新model.notes
	//调用paintNotes（）;
	var url = baseUrl+"/note/list.do?notebookId="+nid;
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
		var list = result.data;
//		for(var i=0;i<list.length;i++){
//			console.log(list[i]);
//		}
		model.notes = list;
		paintNotes();
		}else{
			alert(result.message);
		}
	});
}
//将数据模型中的notes显示到界面
function paintNotes(){
//	console.log(12);
	var list = model.notes;
	var ul = $('#notes')
	ul.empty();
	
	
	for(var i=0;i<list.length;i++){
		var note = list[i];
//		console.log(title);
		var li = 
			'<li class="online">'+
				'<a>'+
					'<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+note.title+'<button disabled="disabled" type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>'
				+'</a>'+
			'<div class="note_menu" tabindex="-1">'+
			'<dl>'+
				'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'+
				'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'+
				'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'+
			'</dl>'+
			'</div>'+
		'</li>'
		
		li = $(li).data("index",i);
		ul.append(li);
		
		li.click(function(){
			$(this).parent().find('a').removeClass();
			$(this).parent().find('li>a>button')
			.attr('disabled','disabled');
			$(this).children('a').addClass('checked');
			$(this).find('a>button')
			.removeAttr('disabled');
			$(this).parent().parent()
			.find('.note_menu').slideUp(20);
			var index = $(this).data("index");
//			console.log(index);
			var note = model.notes[index];
			model.noteIndex = index;
			var noteId = note.id;
//			model.currentNote = note;
			//从服务器端获取笔记的详细信息
//			displayDescriptionAction();
			loadCurrentNoteAction(noteId);
		});
		//绑定笔记菜单事件
		li.children('a').children('button')
			.mouseover(function(){
//				console.log("HI")
				$(this).parent('a').parent('li')
				.children('.note_menu').slideToggle(200);
				
				return false;
			});
		li.find('.btn_delete').click(deleteNoteAction);
		li.find('.btn_move').click(moveNoteAction);
//		.click(function(){
//			var index = model.noteIndex;
//			var note = model.currentNote;
//			var val = confirm('删除：'+note.title);
//			if(val==1){}
//			deleteNoteAction();
//		});
		if(model.currentNote.id){
			if(note.id == model.currentNote.id){
				li.children('a').addClass('checked');
			}
		}
	}
}

function moveNoteAction(){
	//显示移动窗口
	var url =baseUrl+'/alert/alert_move.html';
	$('#can').load(url,function(){
		var note = model.currentNote;
		$('#can h4').html('移动'+note.title);
		var notebooks = model.notebooks;
		var list = $('#can #moveSelect').empty();
		for(var i=0;i<notebooks.length;i++){
			var notebook = notebooks[i]; 
			list.append(
					$('<option></option>')
					.val(notebook.id)
					.text(notebook.name));
		}
		$('#can .sure').click(moveNoteAjaxAction);
		$('#can .close,#can .cancle').click(function(){
			$('#can>div').hide(200,function(){
				$('#can').empty();
			});
		});
		$('#can>div').show();
		
	});
	return false;
	
	
}
function moveNoteAjaxAction(){
	var noteId = model.currentNote.id;
	var notebookId = $('#moveSelect').val();
	var data = {"noteId":noteId,
				"notebookId":notebookId}
	var url = baseUrl+"/note/moveNote.do";			
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			var note = result.data;
			var index = model.noteIndex;
			var next = index;
			if(index+1==model.notes.length){
				next--;
			}
			if(model.notes.length==1){
				next == null;
			}
			var notes = model.notes;
			for(var i=index;i<notes.length;i++){
				notes[i]=notes[i+1];
			}
			notes.pop();
			model.noteIndex = next;
			if(next==null){
				model.currentNote={};
				$('#input_note_title').val('');
				um.setContent('');
			}else{
				model.currentNote=notes[next];
				loadCurrentNoteAction(notes[next].id);
			}
			var ary = {'id':note.id,'title':note.title};
			model.notes = ary.concat(model.notes);
			paintNotebooks();
			paintNotes();
			$('#can > div').hide(200,function(){
				$('#can').empty();
			});	
		}else{
			alert(result.message);
		}
	});
}

function deleteNoteAction(){
	var index = model.noteIndex;
	var note = model.currentNote;
	var url = baseUrl+"/note/updateNote.do?noteId="+note.id;
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
			//更改笔记列表，将当前笔记删除
			var index = model.noteIndex;
			var note = model.currentNote;;
			//下一个笔记的位置
			var next = index;//默认
			if(index+1==model.notes.length){
				next--;
			}
			if(model.notes.length==1){
				next = null;
			}
			var notes = model.notes;
			for(var i=index;i<notes.length-1;i++){
				notes[i]=notes[i+1];
			}
			notes.pop();//删除数组最后一个元素
			model.noteIndex = next;
			if(next==null){
				model.currentNote = {};
				$('#input_note_title').val('');
				um.setContent("");
			}else{
				model.currentNote = notes[next];
				loadCurrentNoteAction(notes[next].id);
			}
			paintNotes();
		}else{
			alert(result.message);
		}
	});
	return false;
}

function loadCurrentNoteAction(noteId){
//	var noteId = model.currentNote.id;
//	console.log(noteId);
	var url = baseUrl+"/note/description.do?noteId="+noteId;
	$('#input_note_title').val('笔记加载中...')
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
			model.currentNote = result.data;
			paintCurrentNote();
//			$('#input_note_title').val(result.data.title);
//			$('#myEditor').children('p').html(result.data.body);
		}else{
			alert(result.message);
		}
	});	
}
function paintCurrentNote(){
	var note = model.currentNote;
	$('#input_note_title').val(note.title);
//	//um 就是笔记本编辑区: ueditor 
//	//setContent 可以改变笔记编辑区的内容
	um.setContent(note.body);
	
}

function saveNoteAction(){
	
	var title = $('#input_note_title').val();
	var body = um.getContent();
	console.log(title);
	console.log(body);
	if(body==model.currentNote.body && 
			title==model.currentNote.title){
		console.log("未执行");
		
		//如果笔记内容没有被编辑，就不向服务器发送请求
		return;
	}
	var data = {"noteId":model.currentNote.id,
				"title":title,"body":body};
	//将笔记信息发送到服务器
	//ajax
	var url = baseUrl+"/note/update.do"
	$('#save_note').html("保存中...")
		.attr('disabled','disabled');//按钮变灰 不能点
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			console.log("执行");
			$('#save_note').html('保存笔记')
				.removeAttr('disabled');//removeAttr 按钮恢复正常
			var note = result.data;
			model.currentNote = note;
			model.notes[model.noteIndex].title = note.title;
			paintNotes();
			paintCurrentNote();
			
		}else{
			console(result.message);
		}
	})
}



