package cn.tedu.blog.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String body;
	//发帖人
	private User user;
	
	private List<Comment> comments =
				new ArrayList<Comment>();
	private List<Forward> forwards = 
				new ArrayList<Forward>();
	
	public Post() {
		
	}
	
	

	public Post(String id, String body, User user, List<Comment> comments, List<Forward> forwards) {
		super();
		this.id = id;
		this.body = body;
		this.user = user;
		this.comments = comments;
		this.forwards = forwards;
	}



	public List<Forward> getForwards() {
		return forwards;
	}



	public void setForwards(List<Forward> forwards) {
		this.forwards = forwards;
	}



	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

	@Override
	public String toString() {
		return "Post [id=" + id + ", body=" + body + ", user=" + user + ", comments=" + comments + ", forwards="
				+ forwards + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
