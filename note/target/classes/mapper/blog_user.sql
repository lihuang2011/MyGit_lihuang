create table blog_user(
	u_id varchar(100),
	u_name varchar(100),
	u_password varchar(100),
	primary key(u_id)
);
drop table blog_post;
create table blog_post(
	p_id varchar(100),
	p_body varchar(1000),
	p_user_id varchar(100),
	primary key(p_id)
);
insert into blog_post
	(p_id,p_body,p_user_id)
values('1','今天天气不错哟，哈哈哈','1');

insert into blog_post
	(p_id,p_body,p_user_id)
values('2','确实不错哈','1');

insert into blog_post
	(p_id,p_body,p_user_id)
values('3','你今天吃了么！','2');

drop table blog_comment;
create table blog_comment(
	c_id varchar(100),
	c_body varchar(1000),
	c_post_id varchar(100),
	primary key (c_id)

);
drop table blog_forward;
create table blog_forward(
	f_id varchar(100),
	f_body varchar(1000),
	f_comment_id varchar(100),
	primary key (f_id)
);
insert into blog_forward
	(f_id,f_body,f_comment_id)
values('1','hahaha','1');	
insert into blog_forward
	(f_id,f_body,f_comment_id)
values('2','hehehe','1');
insert into blog_forward
	(f_id,f_body,f_comment_id)
values('3','xixixi','1');


insert into blog_user
	(u_id,u_name,u_password)
values('1','Tom','123');

insert into blog_user
	(u_id,u_name,u_password)
values('2','Jerry','123');

insert into blog_post
	(p_id,p_body,p_user_id)
values('1','今天天气不错哟，哈哈哈','1');

insert into blog_post
	(p_id,p_body,p_user_id)
values('2','确实不错哈','1');

insert into blog_post
	(p_id,p_body,p_user_id)
values('3','你今天吃了么！','2');

insert into blog_post
	(p_id,p_body,p_user_id)
values('4','今天食堂的饭不咋的！','2');	

insert into blog_comment
	(c_id,c_body,c_post_id)
values('1','abc','1');	
insert into blog_comment
	(c_id,c_body,c_post_id)
values('2','edf','1');	
insert into blog_comment
	(c_id,c_body,c_post_id)
values('3','ijk','1');	
