/*
 * script/log_in.js
 * 登录界面中执行的脚本程序
 */
//console.log("呵呵");
//网页加载以后执行

//移到const.js中
//var SUCCESS = 0;
//var ERROR = 1;
$(function(){
	//为login按钮绑定事件
	$('#login').click(loginAction);
	$('#regist_button').click(registAction);
	$('#code').blur(function(){
		var code = $(this).val();
		var url = baseUrl+"/account/checkCode.do?code="+code;
		$.getJSON(url,function(result){
			if(result.state==SUCCESS){
				$('#code').removeClass('error');
				$('#msg3').html('').removeClass('error2');
				return false;
			}else{
				$('#code').addClass('error');
				$('#msg3').addClass('error2')
				.html(result.message);
			}	
		});
	});
	$('#codeImg').click(function(){
		var url = baseUrl+"/account/code.do?math="+Math.random();
		$(this).attr('src',url);
		
	});
});
function loginAction(){
	//console.log("login click");
	//检查表单数据的正确性
	//将表单数据发送到服务器
	//利用Callback处理返回结果
	//如果成功就跳转到主页
	//如果失败显示错误消息
	var name = $('#count').val();
	var password = $('#password').val();
	//console.log(name+":"+password);
	var reg = /^\w{3,10}$/;
	var pass= true;
	$('#count').removeClass("error");
	$('#msg1').removeClass('error2');
	$('#password').removeClass("error");
	$('#msg2').removeClass('error2');
	if(!reg.test(name)){
		$('#count').addClass('error');
		$('#msg1').addClass('error2');
		pass = false;
	}
	if(!reg.test(password)){
		$('#password').addClass('error');
		$('#msg2').addClass('error2')
		pass = false; 
	}
	if(!pass){
		return false;
	}
	//已经获得了正确的数据
	var data = 
		{'name':name,
			'password':password};
	
	$.ajax({
		"url":"account/login.do",
		"type":"post",
		"data":data,
		"dataType":"JSON",
		"success":function(result){
			console.log(result);
			if(result.state==SUCCESS){
				//通过cookie记录userId
//				SetCookie("userId",result.data.id);
//				SetCookie("userName",result.data.name);
//				SetCookie("userPassword",result.data.password);
//				SetCookie("userToken",result.data.token);
//				SetCookie("userNick",result.data.nick);
				addCookie("userId",result.data.id,0.3);
				window.location="edit.html";
			}else{
				$('#errMsg').html(result.message);
			}
		}
	});
}

function registAction(){
	//console.log("registActionClick");
	var name = $('#regist_username').val();
	var nick = $('#nickname').val();
	var password = $('#regist_password').val();
	var comfire = $('#final_password').val();
	var pass = true;
	var nameReg = /^\w{3,10}$/;
	var nickReg = /^.{3,10}$/;
	$('input').removeClass("error");
	$('.warning').hide();
	if(!nameReg.test(name)){
		pass = false;
		//$('#warning_1').show();
		$('#regist_username').addClass("error");
		$('#warning_1>span')
		.html("3-10个单词字符").parent().show();
	}
	if(!nickReg.test(nick)){
		pass=false;
		$('#nickname').addClass("error");
		$('#warning_nick>span')
		.html("3-10个单词").parent().show();	
	}
	if(!nameReg.test(password)){
		pass = false;
		$('#regist_password').addClass("error")
		.next().show().html('<span>3-10个单词字符</span>');
	}
	if(!nameReg.test(comfire)){
		pass = false;
		$('#final_password').addClass("error")
		.next().show().html('<span>3-10个单词字符</span>');	
	}
	if(password != comfire){
		pass = false;
		$('#final_password').addClass("error")
		.next().show().html('<span>密码不一致</span>');
	}
	if(!pass){
		return false;
	}
	$.ajax({
		url:"account/regist.do",
		type:"post",
		data:{"name":name,"password":password,"nick":nick},
		dataType:"JSON",
		success:function(result){
			if(result.state==SUCCESS){
				console.log(result);
				
				//注册成功  清空信息
				$('input[type=text],input[type=password]').val('');
				//注册完成后将用户名写到登录页面的登录框，
				//空的focus（）可以将光标聚集到密码框
				$('#count').val(result.data.name);
				$('#password').focus();
				//jquery  中空的点击函数  相当与执行了一次点击。 
				$('#back').click();
			}else{
//				$('#back').parent().append('span').html(result.message).css('font-size','5px');
				$('#regist_username').addClass("error");
				$('#warning_1>span')
				.html(result.message).parent().show();
			}
		}
	});
	
	
}

