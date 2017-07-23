<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	function checkSearch(search_form) {
		var search_form = document.search_form;
		if (!search_form.search.value) {
			alert("검색내용을 입력하세요");
			return false;
		}
		return true;
	}
</script>

<div id="main_header">
	<div id="header_wrap">
		<div id="logo">
			<h2 class="hidden">로고 이미지</h2>
			<a href="boardRead.bd"><img alt=""
				src="../../images/RainbowTrack.png"></a>
		</div>
		<div id="search">
			<form method="post" action="search.bd?pageNum=1"
				onsubmit="return checkSearch();" name="search_form">
				<input type="hidden" name="myid" value="<%=session.getAttribute("id")%>">
				<div class="box">
					<input class="search" name="search" type="text"
						placeholder="Search id or #what you want" /> <input
						class="submit" type="submit" value="⌕" />
				</div>
			</form>
		</div>
		<div id="link">
			<ul>
				<li><a href="https://ko-kr.facebook.com/"><img
						src="../../images/facebook.png" class="link_img"></a></li>
				<li><a href="https://www.instagram.com/"><img
						src="../../images/instagram.png" class="link_img"></a></li>
				<li><a href="https://soundcloud.com/"><img
						src="../../images/soundcloud.png" class="link_img"></a></li>
			</ul>
		</div>
	</div>
</div>