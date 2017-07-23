<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="Stylesheet" href="css/ui-lightness/jquery-ui-1.10.4.custom.css"/>

<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript">
   function checkLogin(loginForm){
      var loginForm = document.loginForm;
      if(!loginForm.id.value){
         alert("아이디를 입력하세요");
         return false;
      }
      if(!loginForm.pw.value){
         alert("비밀번호를 입력하세요");
         return false;
      }
      return true;   
   }
</script>

<script>
   $(function(){
      $(".login-page").dialog({
         autoOpen:false, //자동으로 열리지않게
            //position:[100,200], //x,y  값을 지정
            //"center", "left", "right", "top", "bottom"
            resizable:false, //크기 조절 못하게
         modal:true,
            
         show:{
            effect:"blind",
            duration: 350
         },
         hide:{
            effect:"blind",
            duration: 350
         },
         
         buttons: {
              close: function() {
                $( this ).dialog( "close" );
              }
            }
      });
      
      $("#login_btn").on("click", function(){
         $("#login_dialog").dialog("open");
      });
      
      $("#join_btn").on("click", function(){
         $("#join_dialog").dialog("open");
      });
   });
</script>
<script>
   $(function (){
      $("#calendar").datepicker({
         changeMonth: true,
         changeYear: true,
         showButtonPanel: true,
         showAnimation: 'blind',
         showOtherMonths: true,
         selectOtherMonths: true,
         dateFormat: "yy-mm-dd",
         yearRange: '-100: +1'
      });
   });
</script>

<div id="index_header">
   <h1 class="hidden">상단 이미지</h1>
   <div id="membership">
      <h2 class="hidden">멤버쉽네비게이션</h2>
      <ul>
         <!-- <li><a href="index.jsp?uri=loginForm.jsp">LOGIN</a></li> -->
         <li><button id="login_btn" class="index_btn">Sign in</button></li>
         <li>&nbsp;&nbsp;&nbsp;</li>
         <li><button id="join_btn" class="index_btn">Create account</button></li>
      </ul>
   </div>
</div>

<!-- 로그인 폼 -->
<div class="login-page" id="login_dialog" >
  <div class="form">
    <form name="loginForm" class="login-form">
      <input id="loginId" type="text" placeholder="id" name="id" />
      <input id="loginPw" type="password" placeholder="password" name="pw" />
      <input id="loginBt" type="button" value="로그인" class="button"/>
    </form>
    <a href="#" onclick="window.open('searchId.jsp', '_blank', 'width=250 height=120, left=820 top=400')"
    style="font-size: 16px;color:#777"> ID찾기 </a>
    &nbsp/&nbsp
    <a href="#" onclick="window.open('searchPw.jsp', '_blank', 'width=280 height=120, left=820 top=400')"
    style="font-size: 16px;color:#777"> PW찾기 </a>
  </div>
</div>

<!-- 회원가입 폼 -->
<div class="login-page" id="join_dialog">
  <div class="form">
    <form action="../../UserController?command=regist" method="post" name="joinForm" accept-charset="UTF-8"
      onsubmit="return checkJoin();" class="register-form">
     
      <input type="text" placeholder="id" name="id" id="id"/>
      <p id="id_check" class="checkP">&nbsp;</p>
            <!-- <button type="button" onclick="idCheck()">id중복</button> -->
      <input type="password" placeholder="password" id='pw' name="pw"/>
      <p id="pw_check" class="checkP">&nbsp;</p>
      <input type="text" placeholder="name" name="name" id='name' />
      <p id="name_check" class="checkP">&nbsp;</p>
      <input type="text" placeholder="phone number" name="phoneNum" />
      <input type="text" placeholder="email address" name="email"/>
      <input type="text" placeholder="date of birth" name="birth" id="calendar" readonly="readonly"/>
      <input type="submit" value="가입하기" class="button"/>
    </form>
  </div>
</div>

<script>

   
   $("#loginBt").click(function () {
      var formData = $("form[name=loginForm]").serialize();
      console.log(formData);
      
      $.ajax({
         url : "../../JsonController/login",
         type : "POST",
         dataType : "JSON",
         data : formData,
         success : function(jsonData) {
            console.log(jsonData);
            if (jsonData.result == "true") {
               location.href = "../main/boardRead.bd";
            } else if (jsonData.result == "already") {
               alert("이미 접속 중인 사용자입니다.");
            } else if (jsonData.result == "idFail") {
               alert("일치하는 아이디가 없습니다.");
            } else {
               alert("비밀번호가 일치하지 않습니다.");
            }
         },
         error : function() {
            alert("error");
         }
      });
      
   });
   
   var check1 = false;
   var check2 = false;
   var check3 = false;
   var blank_pattern = /[\s]/g;
   
   $("#name").blur(function() {
      var name = $("#name").val();
      if(blank_pattern.test(name) == true){
         $("#name_check").html("공백은 사용하실 수 없습니다.");
         check3 = false;
      } else {
         $("#name_check").html("&nbsp;  ");
         check3 = true;
      }
   });
   
   
   $("#pw").blur(function() {
      var pw = document.getElementById('pw').value;
      if(blank_pattern.test(pw) == true){
         $("#pw_check").html("공백은 사용하실 수 없습니다.");
         check2 = false;
      } else {
         $("#pw_check").html(" &nbsp; ");
         check2 = true;
      }
   });
   
   
   $("#id").blur(function () {
      var id = $("#id").val();
      var blank_pattern = /[\s]/g;   //공백 확인용 패턴
      console.log(id);
      
      if(blank_pattern.test(id) == true){
         $("#id_check").html("공백은 사용하실 수 없습니다.");
         check1 = false;
         return false;
      }
      
      $.ajax({
         url : "../../JsonController/checkId",
         type : "POST",
         dataType : "JSON",
         data : {
            "id" : id
         },
         success : function(jsonData, textStatus, jqXHR) {
            console.log(jsonData);
            
            if (jsonData.result == "true") {
               check1 = true;
               $("#id_check").html("사용 가능한 ID입니다.");
            } else {
               check = false;
               $("#id_check").html("사용할 수 없는 ID입니다.");
            }
         },
         error : function(xhr, status, error) {
            alert("error");
         }
      });
      
   });

   function checkJoin(joinForm) {
      console.log(check1);   
      
      var joinForm = document.joinForm;
      /* RegExp는 정규식 검색을 위한 패턴을 정의한다. 자바스크립트 교재 385p. */
      var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
      var email = joinForm.email.value;
      var blank_pattern = /[\s]/g;   //공백 확인용 패턴
      
      if (!joinForm.id.value) {
         alert("아이디를 입력하세요");
         return false;
      }else if (!joinForm.pw.value) {
         alert("비밀번호를 입력하세요");
         return false;
      }else if (!joinForm.name.value) {
         alert("이름을 입력하세요");
         return false;
      }else if(!email.match(regExp)){
         alert("이메일 형식이 맞지않습니다");
         return false;
      }else if (!check1) {
         alert("ID를 확인해주세요");
         return false;
      } else if (!check2) {
         alert("비밀번호를 확인해주세요");
         return false;
      } else if (!check3) {
         alert("이름을 확인해주세요");
         return false;
      } else {
         return true;
      }
      
   }   

</script>