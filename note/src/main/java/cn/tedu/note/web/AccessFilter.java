package cn.tedu.note.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.note.util.Md5;

public class AccessFilter implements Filter {

	public void doFilter(
		ServletRequest req,
		ServletResponse res,
		FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest request = 
				(HttpServletRequest)req;
		HttpServletResponse response =
				(HttpServletResponse)res;
		//除了log_in.html  其他的html都拦截
		StringBuffer url =
				request.getRequestURL();
//		System.out.println(url);
		
		String path = 
				url.toString();
		if(path.endsWith(".html")|| path.endsWith(".do")){
			//放过log_in.html
			if(path.endsWith("log_in.html")){
				chain.doFilter(request, response);
				return;
			}
			//放过/account/*.do;
			if(path.indexOf("/account/")>0){
				chain.doFilter(request, response);
				return;
			}
			//处理没有登录的就转到登录页面
			processAccessControl(
					request,response,chain);
				return;
		}
		chain.doFilter(request, response);
	}
	//处理访问控制
	
	private void processAccessControl(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain) throws IOException, ServletException{
		//检查Cookie中是否有Token，没有就去登录页面
		Cookie[] cookies = 
				request.getCookies();
		Cookie token = null;
		for(Cookie cookie:cookies){
			if("token".equals(
					cookie.getName())){
				token = cookie;
				break;
			}
		}
		if(token==null){
			//如果没有找到，就重定向到登录页面
			String path = 
					request.getContextPath();
			String login = path+"/log_in.html";
			response.sendRedirect(login);
			return;
		}
		//处理token是否合理
		String value = token.getValue();
		String[] data = 
				value.split("\\|");
		String time = data[0];
		String md5 = data[1];
		String userAgent = 
				request.getHeader("User-Agent");
		if(md5.equals(
				Md5.saltMd5(
						userAgent+time))){
			chain.doFilter(request, response);
			return ;
		}else{
		//如果token验证不合理，就重定向到登录页面
			String path = 
					request.getContextPath();
			String login = path+"/log_in.html";
			response.sendRedirect(login);
			return;
		}
		
	}

	public void init(
		FilterConfig arg0) throws ServletException {
	}
	
	public void destroy() {

	}
}
