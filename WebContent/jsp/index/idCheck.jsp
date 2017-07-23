<%@page import="dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String id = request.getParameter("id");
	UserDao dao = new UserDao();
	boolean check = dao.idCheck(id);
	if(check){%>
	<h4>사용가능한 아이디 입니다.</h4>
	<%
	} else {
	%>
	<h4>중복된 아이디 입니다.</h4>
	<%
	}
	
%>