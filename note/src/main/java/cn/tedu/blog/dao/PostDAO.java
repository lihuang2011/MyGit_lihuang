package cn.tedu.blog.dao;

import cn.tedu.blog.entity.Post;

public interface PostDAO {
	Post findPostById(String id);
}
