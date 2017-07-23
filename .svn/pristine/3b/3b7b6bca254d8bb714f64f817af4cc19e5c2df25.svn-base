<%@page import="boardService.IndexTopTenArrayView"%>
<%@page import="boardService.IndexTopTenService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="bean.*"%>
<%@page import="java.io.*" %>

<div id="pattern" class="pattern">
   <div id="pt_wrap">
   <div class="topten_img"></div>
   <div>
      <ul class="topten_list">
      <%
         request.setCharacterEncoding("utf-8");
         IndexTopTenService indexTopTenService = IndexTopTenService.getInstance();
         IndexTopTenArrayView viewData = indexTopTenService.IndexTopTenService();
         pageContext.setAttribute("viewData", viewData);
         BoardBean boardBean = null;
      %>
      <% for(int i=0; i<viewData.getTopTenArray().size();i++){ 
         boardBean = viewData.getTopTenArray().get(i);
      %>
         <li>
            <div class="top_info">
               <p><%=boardBean.getUser_id()%> - <%=boardBean.getHashTags()%></p>
            </div>
         <audio id="music<%=i %>" preload="true"><source src="<%=boardBean.getMusicFilePath()%>"></audio>
         <div id="audioplayer"><button id="pButton<%=i%>" class="play" value="<%=i%>" onclick="play(this.id,this.value)"></button>
         </div>
         </li>
         
      <%} %>
   
      </ul>
   </div>
   </div>
</div>

<script type="text/javascript">

//Play and Pause
function play(id, value) {
   var musicFile = document.getElementById("music" +value);
    // start music
    if (musicFile.paused) {
       musicFile.play();
        // remove play, add pause
       $('#'+id).attr('class','pause');
    } else { // pause music
       musicFile.pause();
        // remove pause, add play
       $('#'+id).attr('class','play');
    }
}
</script>