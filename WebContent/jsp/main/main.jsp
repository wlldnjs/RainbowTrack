<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 태그라이브러리 사용 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uri" value="boardRead.jsp" />
<c:if test="${ param.uri != null }">
   <c:set var="uri" value="${ param.uri }" />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../../css/IM_main.css" rel="stylesheet" />
<script src="../index/js/jquery-1.11.0.min.js"></script>
<script src="../index/js/jquery-ui-1.10.4.custom.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<!-- 웹폰트 -->
<script src="https://use.fontawesome.com/238ce3eec6.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<%
   response.setHeader("Pragma", "No-cache");
   response.setHeader("Cache-Control", "no-cache");
   response.setHeader("Cache-Control", "no-store"); 
%>
<title>메인화면</title>
<script type="text/javascript">
	function fixMenu(){
		var a = document.body.scrollscrollTobar.style.top = a
	}
	
</script>
<c:if test="${ sessionScope.id == null }">
	<c:redirect url="../index/index.jsp"></c:redirect>
</c:if>
</head>
<body onload="fixMenu()">

<jsp:include page="main_header.jsp"/>

<div id="main_wrap">
<jsp:include page="main_menu.jsp"/>
<jsp:include page="${ uri }"/>
</div>

<!-- 위로가기 버튼 -->
<button type="button" class="ac-sub-go-top">위로</button>
<script>
	$(window).scroll(function (){
		var quickHeight=$(document).scrollTop();
		if(100 <= quickHeight) {
			$('.ac-sub-go-top').css('display', 'block');
		} else {
			$('.ac-sub-go-top').css('display', 'none');
		}
	});
	/* 스크롤 위치가 500 이상이면 나타나기 */
	
	$('.ac-sub-go-top').click(function(){
		$('html, body').animate({
			scrollTop: '0'
		}, 800);
	});
</script>
</body>
</html>