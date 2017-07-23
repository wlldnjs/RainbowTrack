<%@page import="dao.UserDao"%>
<%@page import="bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form action="boardWrite.bd" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="user_id" value="${ sessionScope.id }" />
		
		<div id="board_write">
			<div class="user_img">
				<c:if test="${ bean.image != null }" >
					<img width="40px" height="40px" src="${ bean.image }">
				</c:if>
				<c:if test="${ bean.image == null }" >
					<img width="40px" height="40px" src="http://210.123.255.149:8080/rt_img/default_image.png" >
				</c:if>
					<p><span>${ sessionScope.id }</span> 님의 메세지 쓰기</p>
			</div>
			<div id="input_text">
				<textarea rows="7px" cols="85px" name="board"  class="placeholder" placeholder="내용을 입력해 주세요."></textarea>
				<input type="text" size="84" name="hashTags" placeholder="#해쉬태그" class="placeholder" id="hashTag"/>
			</div>
			<div id="input_file">
				<input type="text" id="fileName" class="file_input_textbox" readonly="readonly" placeholder="파일 업로드">
				<div class="file_input_div">
				<input type="button" value="search files" class="file_input_button">
				<input type="file" name="upload_file" class="file_input_hidden" style="cursor: pointer;"
						 onchange="javascript:document.getElementById('fileName').value=this.value">
				</div>
			</div>
			<div id="input_btn">
				<input type="submit" value="Write" class="button_css_WR"/>
				<input type="reset" value="Reset" class="button_css"/>
			</div>
		</div>

		<%-- <table cellpadding="5">
			<tr>
				<td>
				<c:if test="${ bean.image != null }" >
					<img width="25px" height="25px" src="${ bean.image }">
				</c:if>
				<c:if test="${ bean.image == null }" >
					<img width="25px" height="25px" src="http://210.123.255.149:8080/rt_img/default_image.png">
				</c:if>
					${ sessionScope.id }님의 메세지 쓰기</td>
			</tr>
			<tr>
				<td><textarea rows="7px" cols="85px" name="board"  class="placeholder"
						placeholder="내용을 입력해 주세요."></textarea></td>
			</tr>
			<tr>
				<td><input type="text" size="84" name="hashTags" placeholder="#해쉬태그" class="placeholder"/></td>
			</tr>
			<tr>
				<td>
					<input type="text" id="fileName" class="file_input_textbox"
							 readonly="readonly" placeholder="파일 업로드">
					<div class="file_input_div">
					<input type="button" value="search files" class="file_input_button">
					<input type="file" name="upload_file" class="file_input_hidden" style="cursor: pointer;"
							 onchange="javascript:document.getElementById('fileName').value=this.value">
					</div>
				</td>
			</tr>
			<tr>
				<td align="right"><input type="submit" value="게시" /> <input
					type="reset" value="다시쓰기" /></td>
			</tr>
		</table> --%>
	</form>
</body>
</html>