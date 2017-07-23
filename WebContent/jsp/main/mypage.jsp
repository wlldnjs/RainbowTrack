<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">

	function modify(modifyForm){
      var modifyForm = document.modifyForm;
      var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
	  var email = modifyForm.email.value;
      
      if(!modifyForm.pw.value){
         alert("새 비밀번호를 입력하세요");
         return false;
      }
      if(modifyForm.pw.value != modifyForm.pw_ok.value){
    	 alert("비밀번호 확인이 일치하지 않습니다.");
         return false;
      }
      if(!modifyForm.phoneNum.value){
         alert("새 전화번호를 입력하세요");
         return false;
      }
      if(!email.match(regExp)){
			alert("이메일 형식이 맞지않습니다");
			return false;
	  }
      return true;   
   }
</script>
<div id="mypage">
<form action="../../UserController?command=image" enctype="multipart/form-data" method="post">
	<div id="modify_img">
		<div id="user_img">
			<c:if test="${ bean.image != null }" >
				<img id="current_image" src="${bean.image}" />
			</c:if>
			<c:if test="${ bean.image == null }" >
        		<img id="current_image" src="http://210.123.255.149:8080/rt_img/default_image.png" />
        	</c:if>
		</div>
		<div id="img_change">
			<input type="hidden" name="user_id" value="${ bean.id }" />
			<p>프로필 사진 수정<p>
			<div id="input_file" class="input_file_my">
				<input type="text" id="fileName" class="file_input_textbox" readonly="readonly" placeholder="파일 업로드">
				<div class="file_input_div">
				<input type="button" value="search files" class="file_input_button">
				<input type="file" name="upload_image" class="file_input_hidden" style="cursor: pointer;"
						 onchange="javascript:document.getElementById('fileName').value=this.value">
				</div>
			</div>
			<input type="submit" value="수정하기" class="button_css_mypage">
		</div>
	</div>
   <%-- <table>
      <tr>
      	<c:if test="${ bean.image != null }" >
         <td><img id="my_image" src="${bean.image}" /></td>
        </c:if>
        <c:if test="${ bean.image == null }" >
         <td><img id="my_image" src="http://210.123.255.149:8080/rt_img/default_image.png" /></td>
        </c:if>
        <td><input type="hidden" name="user_id" value="${ bean.id }" /></td>
      </tr>
      <tr>
         <td>프로필 사진 수정</td>
         <td><input type="file" name="upload_image"></td>
         <td><input type="submit" value="수정하기"></td>
      </tr>
   </table> --%>
</form>
<form action="../../UserController?command=modify" onsubmit="return modify();" 
   method="post" name="modifyForm">
   <input type="hidden" name="user_id" value="${ bean.id }" />
   <table>
      <thead>
         <tr>
            <td colspan="2">계정설정</td>
         </tr>
      </thead>
      <tbody>
         <tr>
            <td>아이디</td>
            <td>${ bean.id }</td>
         </tr>
         <tr>
            <td>이름</td>
            <td>${ bean.name }</td>
         </tr>
         <tr>
            <td>생년월일</td>
            <td>
            <fmt:parseDate value="${ bean.birth }" pattern="yyyy-MM-dd" var="birthdate"/>
         	<fmt:formatDate value="${birthdate}" dateStyle="medium" />
            </td>
         </tr>
         <tr>
            <td>새 비밀번호</td>
            <td><input type="password" name="pw"></td>
         </tr>
         <tr>
            <td>새 비밀번호 재입력</td>
            <td><input type="password" name="pw_ok"></td>
         </tr>
         <tr>
            <td>새 연락처</td>
            <td><input type="text" name="phoneNum" value="${ bean.phoneNum }"></td>
         </tr>
         <tr>
            <td>새 이메일주소</td>
            <td><input type="text" name="email" value="${ bean.email }"></td>
         </tr>
         <tr>
            <td><input type="submit" value="수정하기" class="button_css_mypage"></td>
         </tr>
      </tbody>
   </table>
</form>
<form action="../../UserController?command=resign" method="post">
   <table>
      <tr>
         <td><input type="submit" value="탈퇴하기" class="button_css_mypage"/></td>
      </tr>
   </table>
</form>
</div>