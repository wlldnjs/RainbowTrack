<%@page import="bean.FollowBean"%>
<%@page import="userService.FollowListView"%>
<%@page import="userService.FollowListService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="fade">
<%
	request.setCharacterEncoding("utf-8");
	String myId = String.valueOf(session.getAttribute("id"));
%>
	<ul class="tabs">
		<li><a href="#item1">follower</a></li>
		<li><a href="#item2">following</a></li>
	</ul>
<%
	FollowListService followListService = FollowListService.getInstance();
	FollowListView followerData = followListService.FollowerListService(myId);
	pageContext.setAttribute("followerData", followerData);
%>
<div class="items">
<div id="item1">
<% if(followerData == null) {%>
<h5>팔로워가 없습니다</h5>
<%} else {%>
<% for(FollowBean followBean : followerData.getFollowList()) {%>
<figure class="snip1578">
  <a href="search.bd?search=<%=followBean.getFollow()%>"><img src="<%=followBean.getImage() %>" /></a>
  <figcaption>
    <h3><%=followBean.getFollow() %></h3>
    <!-- <div>
    추가입력
    </div> -->
  </figcaption>
</figure>
<%} %>
<%} %>
</div>


<%
	FollowListView followingData = followListService.FollowingListService(myId);
	pageContext.setAttribute("followingData", followingData);
%>
<div id="item2">
<% if(followingData == null) {%>
<h5>팔로잉이 없습니다</h5>
<%} else {%>
<% for(FollowBean followBean : followingData.getFollowList()) {%>
<figure class="snip1578">
  <a href="search.bd?search=<%=followBean.getFollow()%>&follow=true"><img src="<%=followBean.getImage() %>" /></a>
  <figcaption>
    <h3><%=followBean.getFollow() %></h3>
    <!-- <div>
    추가입력
    </div> -->
  </figcaption>
</figure>
<%} %>
<%} %>
</div>
</div>

</div>

<script src="../index/js/jquery-1.11.0.min.js"></script>
<script src="../index/js/jquery.idTabs.js"></script>
<script>
	var fade = function(id,s){
		s.tabs.removeClass(s.selected);
		s.tab(id).addClass(s.selected);
		s.items.fadeOut();
		s.item(id).fadeIn();
		return false;
	};
	$.fn.fadeTabs = $.idTabs.extend(fade);
	$(".fade").fadeTabs();
</script>