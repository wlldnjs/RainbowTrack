<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel="Stylesheet" href="../index/css/ui-lightness/jquery-ui-1.10.4.custom.css"/>
<Script type="text/javascript">
   function ShowPlaylist(){
      var show = window.open("playlist.jsp","","width=800, height=400",true);
   }
</Script>

<div id="main_menu">
   <div id="profile">
      <a href="search.bd?search=${ bean.id }">
      <c:if test="${ bean.image != null }">
      <img id="my_image" src="${bean.image}" />
      </c:if>
       <c:if test="${ bean.image == null }">
      <img id="my_image" src="http://210.123.255.149:8080/rt_img/default_image.png" />
      </c:if>
      </a><br>
      <a href="search.bd?search=${ bean.id }" class="myId"><%= session.getAttribute("id") %></a>
   </div>
   <ul>
      <li><a href="main.jsp?uri=followList.jsp"><img src="../../images/follower.png" class="menulist"></a></li>
      <li><img src="../../images/playlist.png" class="menulist" onclick="ShowPlaylist()" style="cursor: pointer;"></li>
      <li><a href="main.jsp?uri=mypage.jsp"><img src="../../images/mypage.png" class="menulist"></a></li>
      <li><img src="../../images/logout.png" class="menulist" onclick="href:location='../../UserController?command=logout'" style="cursor: pointer;"></li>
   </ul>
</div>