<!DOCTYPE html>
<%@page import="bean.PlaylistBean"%>
<%@page import="playlistService.PlaylistArrayView"%>
<%@page import="playlistService.PlaylistShowService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html >
<head>
<script src="../index/js/jquery-1.11.0.min.js"></script>
<script src="../index/js/jquery-ui-1.10.4.custom.js"></script>
  <meta charset="UTF-8">
  <title>Music Player - Rainbow Track</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

  <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css'>
   <style>
   *, *:before, *:after {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

@-webkit-keyframes spin {
  from {
    -webkit-transform: rotate(0deg);
            transform: rotate(0deg);
  }
  to {
    -webkit-transform: rotate(360deg);
            transform: rotate(360deg);
  }
}

@keyframes spin {
  from {
    -webkit-transform: rotate(0deg);
            transform: rotate(0deg);
  }
  to {
    -webkit-transform: rotate(360deg);
            transform: rotate(360deg);
  }
}
body {
  background-color: rgba(34, 34, 34, 0.1);
  font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
  font-weight: 300;
  line-height: 1.5em;
}

.table {
  display: table;
  width: 100%;
  height: 100vh;
}

.table-cell {
  display: table-cell;
  vertical-align: middle;
}

#playbar {
  visibility: hidden;
  height: 0;
  width: 0;
}

#player {
  margin: 0 auto;
  position: relative;
  width: 400px;
  height: 400px;
}
#player #main {
  width: 400px;
  height: 400px;
  overflow: hidden;
  border-radius: 5px;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
  box-shadow: 0 10px 60px rgba(0, 0, 0, 0.6);
  background-image: url("../../images/Main_image.jpg");
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  z-index: 2;
  position: relative;
  -webkit-transition: -webkit-transform 0.4s ease-in-out;
  transition: -webkit-transform 0.4s ease-in-out;
  transition: transform 0.4s ease-in-out;
  transition: transform 0.4s ease-in-out, -webkit-transform 0.4s ease-in-out;
}
#player.show #main {
  -webkit-transform: translateX(-200px);
          transform: translateX(-200px);
}
#player.show ol {
  -webkit-transform: translateX(200px);
          transform: translateX(200px);
  box-shadow: 1px 2px 10px rgba(0, 0, 0, 0.3);
  -webkit-transition: 0s box-shadow 0.05s, -webkit-transform 0.4s ease-in-out;
  transition: 0s box-shadow 0.05s, -webkit-transform 0.4s ease-in-out;
  transition: transform 0.4s ease-in-out, 0s box-shadow 0.05s;
  transition: transform 0.4s ease-in-out, 0s box-shadow 0.05s, -webkit-transform 0.4s ease-in-out;
}
#player .playback_controls {
  opacity: 0;
  color: rgba(0, 0, 0, 0.55);
  font-size: 22px;
  -webkit-transform: translateY(10px);
          transform: translateY(10px);
  -webkit-transition: opacity 0.3s ease-in-out, -webkit-transform 0.3s ease-in-out;
  transition: opacity 0.3s ease-in-out, -webkit-transform 0.3s ease-in-out;
  transition: opacity 0.3s ease-in-out, transform 0.3s ease-in-out;
  transition: opacity 0.3s ease-in-out, transform 0.3s ease-in-out, -webkit-transform 0.3s ease-in-out;
  padding: 20px;
  width: 100%;
  position: absolute;
  left: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.95);
  z-index: 1;
}
#player .playback_controls .buttons {
  text-align: center;
  padding-left: 15px;
}
#player .playback_controls .buttons i {
  display: inline-block;
  padding: 0 15px;
  width: 48px;
  text-align: center;
  cursor: pointer;
}
#player .playback_controls .buttons i#playpause.loading {
  opacity: 0.4;
  position: relative;
}
#player .playback_controls .buttons i#playpause.loading:after {
  position: absolute;
  content: '';
  display: block;
  width: 50px;
  height: 50px;
  top: 50%;
  margin-top: -25px;
  margin-left: -28px;
  left: 50%;
  border: 3px solid #ddd;
  border-radius: 100%;
  border-right: 3px solid #4FB6EC;
  -webkit-animation-duration: 0.5s;
          animation-duration: 0.5s;
  -webkit-animation-name: spin;
          animation-name: spin;
  -webkit-animation-iteration-count: infinite;
          animation-iteration-count: infinite;
  -webkit-animation-timing-function: linear;
          animation-timing-function: linear;
}
#player .playback_controls .menu {
  float: right;
  cursor: pointer;
  opacity: 0.2;
  -webkit-transition: opacity 0.2s;
  transition: opacity 0.2s;
}
#player .playback_controls .menu:hover {
  opacity: 0.6;
}

#main:hover .playback_controls, .show #main .playback_controls {
  opacity: 1;
  -webkit-transform: translateY(0px);
          transform: translateY(0px);
}

h2 {
  font-weight: 300;
  font-size: 16px;
  text-align: center;
  margin: 0 0 10px 0;
}

h3 {
  font-weight: 700;
  font-size: 12px;
  text-align: center;
  margin: 0;
}

.time-holder {
  padding: 0 40px;
  padding-bottom: 10px;
}
.time-holder .slider {
  width: 100%;
  height: 50px;
  position: relative;
}
.time-holder .slider:before {
  position: absolute;
  top: 50%;
  margin-top: -2px;
  left: 0;
  content: '';
  display: block;
  width: 100%;
  height: 3px;
  background-color: rgba(34, 34, 34, 0.075);
}
.time-holder .slider .fill {
  position: absolute;
  top: 50%;
  width: 100%;
  -webkit-transform-origin: 0% 0%;
          transform-origin: 0% 0%;
  -webkit-transform: scaleX(0);
          transform: scaleX(0);
  margin-top: -2px;
  left: 0;
  content: '';
  display: block;
  height: 3px;
  background-color: #4FB6EC;
  -webkit-transition: -webkit-transform 1s;
  transition: -webkit-transform 1s;
  transition: transform 1s;
  transition: transform 1s, -webkit-transform 1s;
}

a {
  color: rgba(0, 0, 0, 0.55);
  text-decoration: none;
}

.time {
  font-size: 12px;
  font-weight: bold;
  opacity: 0.3;
  float: right;
}

ol#playlist {
  list-style-type: none;
  margin-left: 0;
  width: 400px;
  padding-left: 0;
  position: absolute;
  height: 300px;
  background-color: #fff;
  margin: 0;
  font-size: 16px;
  top: 50px;
  overflow-y: auto;
  -webkit-transition: 0s box-shadow 0.3s, -webkit-transform 0.4s ease-in-out;
  transition: 0s box-shadow 0.3s, -webkit-transform 0.4s ease-in-out;
  transition: transform 0.4s ease-in-out, 0s box-shadow 0.3s;
  transition: transform 0.4s ease-in-out, 0s box-shadow 0.3s, -webkit-transform 0.4s ease-in-out;
  border-radius: 0 5px 5px 0;
}
ol#playlist a {
  display: inline-block;
  width: 300px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

ol#playlist > li {
  counter-increment: customlistcounter;
  padding: 12px 15px;
  height: 50px;
  position: relative;
}
ol#playlist > li:after {
  position: absolute;
  width: 300px;
  left: 9.5%;
  content: '';
  display: block;
  bottom: 0;
  border-bottom: 1px solid rgba(34, 34, 34, 0.1);
}
ol#playlist > li:last-child:after {
  border-color: transparent;
}
ol#playlist > li:hover {
  background-color: rgba(34, 34, 34, 0.02);
}
ol#playlist > li.playing {
  background-color: rgba(79, 182, 236, 0.15);
}
ol#playlist > li.playing:after {
  border-color: transparent;
}
ol#playlist > li.playing:hover {
  background-color: rgba(79, 182, 236, 0.2);
}

ol#playlist > li:before {
  content: counter(customlistcounter) " ";
  float: left;
  text-align: right;
  width: 1em;
  margin-right: 1em;
  font-size: 12px;
  font-weight: bold;
  opacity: 0.3;
  vertical-align: baseline;
  display: inline-block;
}

ol#playlist li:first-child {
  counter-reset: customlistcounter;
}
   
   </style>

<script src="../index/js/jquery-1.11.0.min.js"></script>
</head>

<body>
  <div class="table">
  <div class="table-cell">
    <div id="player">
    <div id="main">
      <div>
        <div class="playback_controls">
          <h2 id="title">Title</h2>
          <h3 id="artist">Rainbow Track</h3>
          <div class="time-holder">
            <div class="slider">
              <div class="fill"></div>
            </div>
          </div>
          <div>
          <i class="fa fa-bars menu"></i>
          <div class="buttons">
            <i class="fa fa-backward" id="back"></i>
            <i class="fa fa-play loading" id="playpause"></i>
            <i class="fa fa-forward" id="next"></i>
          </div>
        </div>
        </div>
        <audio id="playbar" controls></audio>
      </div>
    </div>
    <%
    request.setCharacterEncoding("utf-8");
   String user_id = String.valueOf(session.getAttribute("id"));
   PlaylistShowService playlistShowService = PlaylistShowService.getInstance();
   PlaylistArrayView viewData = playlistShowService.PlaylistShowService(user_id);
   pageContext.setAttribute("viewData", viewData);
   %>
    <ol id="playlist">
    <%if(viewData == null) {%>
플레이리스트가 비어있습니다.
<%} else { %>
<%
	for (int i = 0; i < viewData.getPlaylistArray().size(); i++) {
	PlaylistBean playlistBean = viewData.getPlaylistArray().get(i);
%>
   <li class="playing" id="<%="li"+i%>"><a class="track" href="<%= playlistBean.getMusicFilePath()%>">
   <%=playlistBean.getMusicName()%></a>
   <input type=image id="<%=i%>" align="right" src="../../images/x.png"/>
   <input type=hidden id="boardIdx" value="<%= playlistBean.getBoard_idx()%>">
   <input type=hidden id="id" value="<%= playlistBean.getId()%>" >
   <!--삭제시 플레이리스트 가리는 스크립트-->
   </li>   
<%} %>
	<script>
	// 노래 삭제.	
	$(document).on("click", "input:image", function () {
		var i = $(this);
		var boardIdx = i.next().val();
		var id = i.next().next().val();
		
		$.ajax({
			url : '../../JsonController/deleteSong',
			type : "POST",
			dataType : "JSON",
			data : {
				"id" : id,
				"boardIdx" : boardIdx
			},
			success : function(jsonData) {
				console.log(jsonData);
				if (jsonData.result == "true") {
					i.parent().remove();
				}
			},
			error : function() {
				alert("error");
			}
		});
	});
		
	</script>
	
<%} %>
    </ol>
    <%
    
    %>
    
  </div>
  </div>
</div>
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/gsap/1.18.0/TweenMax.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/fastclick/1.0.6/fastclick.min.js'></script>


    <script>
    var tracks = []
    var audio;
    var audioPreload;
    var preloaded = false;

    function playTrack(e) {
      e.preventDefault();
      var track = e.target.href;
      audio.src = track;
      $('#playlist li').removeClass('playing');
      $(e.target).parent().addClass('playing');
      $(audio).on('canplay', function() {
        play();
      })
      preloaded = false;
    }

    function queueAudio() {
      audioPreload = document.createElement('audio');
      audioPreload.controls = true;
      var track = tracks.indexOf(audio.src) + 1;
      if (tracks.length >= track) {
        audioPreload.src = tracks[track];
      }
      audioPreload.id = 'playbar';
    }
    function newSong() {
      if (preloaded) {
        var parentEl = audio.parentNode;
        var newTrack = tracks.indexOf(audioPreload.src);
        $('#playlist li').removeClass('playing');
        $($('#playlist').children()[newTrack]).addClass('playing');
        parentEl.replaceChild(audioPreload, audio);
        audio = audioPreload;
        play();
        audio.addEventListener('timeupdate', audioUpdate, false);
        audio.addEventListener('ended', newSong, false);
        preloaded = false;
      } else {
        var track = tracks.indexOf(audio.src) + 1;
        $('#playlist li').removeClass('playing');
        $($('#playlist').children()[track]).addClass('playing');
        audio.src = tracks[track];
      }
    }

    function audioUpdate() {
      var duration = parseInt(audio.duration),
        currentTime = parseInt(audio.currentTime),
        timeLeft = duration - currentTime;
        progress = (audio.currentTime + 1) / duration;
      if (timeLeft <= 10 && !preloaded) {
        preloaded = true;
        queueAudio();
      }
      TweenMax.set($('.fill'), {
        scaleX: progress
      })
    }

    // ------- ON LOAD ---------
    $(function() {
      FastClick.attach(document.body);
      audio = document.getElementById('playbar');
      audio.addEventListener('timeupdate', audioUpdate, false);
      audio.addEventListener('ended', newSong, false);
      var trackElements = document.getElementsByClassName('track');
      var i;
      for (i = 0; i < trackElements.length; i++) {
        trackElements[i].addEventListener('click', function(e) {
          playTrack(e);
        }, false);
        tracks.push(trackElements[i].href);
      }

      audio.src = tracks[0];
      $(audio).on('canplay', function() {
        play();
        //$($('#playlist').children()[0]).addClass('playing');
      });
    });
    var pause = function() {
      audio.pause();
      $('#playpause').addClass('fa-play').removeClass('fa-pause');
    }
    var play = function() {
      audio.play();
      $('#playpause').removeClass('fa-play').addClass('fa-pause').removeClass('loading');
      var index = tracks.indexOf(audio.src);
      $('#title').text($($('#playlist a')[index]).text());
    }
    $('#playpause').click(function() {
      if (audio.src) {
        if (audio.paused) {
          play();
        } else {
          pause();
        }
      } else {
        audio.src = tracks[0];
        $(audio).on('canplay', function() {
          play();
        });
      }
    })
    $('#next').click(function() {
      var track = tracks.indexOf(audio.src);
      if (track == -1) {
        // WAT
      } else if (track >= tracks.length) {
        audio.src = '';
      } else {
        pause();
        $('#playpause').addClass('loading');
        audio.src = tracks[track + 1];
        $('#playlist li').removeClass('playing');
        $($('#playlist').children()[track + 1]).addClass('playing');
        $(audio).on('canplay', function() {
          play();
        });
      }
    })
    $('#back').click(function() {
      var track = tracks.indexOf(audio.src);
      if (track == -1) {
        // WAT
      } else if (track <= 0 || audio.currentTime > 2) {
        audio.currentTime = 0;
      } else {
        pause();
        $('#playpause').addClass('loading');
        audio.src = tracks[track - 1];
        $('#playlist li').removeClass('playing');
        $($('#playlist').children()[track - 1]).addClass('playing');
        $(audio).on('canplay', function() {
          play();
        });
      }
    })
    $('.menu').click(function() {
      $('#player').toggleClass('show');
    });
    </script>

</body>
</html>