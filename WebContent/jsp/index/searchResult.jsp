<%@page import="dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String type = request.getParameter("type");
		UserDao dao = UserDao.getInstance();
		String email = request.getParameter("email");
		if(type.equals("searchId")){
			String id = dao.searchId(email);
			if(id != null){
			%>
				회원님의 아이디는 <b><%=id %></b>입니다. 
			<%
			}
			else{
			%>
				아이디가 없습니다. 
			<%
			}
		} else if(type.equals("searchPw")){ 
			String id = request.getParameter("id");
			String pw = dao.searchPw(id, email);
			if(pw != null){
			%>
				회원님의 비밀번호는 <b><%=pw %></b>입니다. 
			<%
			}
			else{
			%>
				아이디 또는 이메일을 다시 확인해 주세요.
			<%
			}
		}	
	%>
</body>
</html>