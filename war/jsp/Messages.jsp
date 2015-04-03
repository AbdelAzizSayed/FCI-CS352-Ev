<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>SocialNet</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main">
  <div class="main_resize">
    <div class="header">
      <div class="logo">
        <h1><a href="#"><span>FCI</span>SocialNet<small>SWIIProject</small></a></h1>

      </div>
	  <div class="logout">
		<form action="#" method="post">		
			<input type="image" name="imageField" id="imageField" src="/images/out.png" />		
		</form>
      </div>
	  
    <div class="content">
      <div class="content_bg">
        <div class="mainbar">
          <div class="article">
            <h2><span>Send Message </span></h2>
            <div class="clr"></div>
			<br>
			<form action="/social/sendMesg" method="post">
				 Message :<br> <textarea rows="5" cols="21" name = "message"></textarea><br>
				 Email :<br><input type="text" name = "recEmail"></input><br><br>
			     <input type = "submit" value="Send">
			</form>
            <div class="clr"></div>
          </div>
        </div>
        <div class="sidebar">
          <div class="gadget">
            <h2 class="star"> Menu</h2>
            <div class="clr"></div>
            <ul class="sb_menu">              
              <li><a href="/social/notifications/">Notifications</a></li>
              <li><a href="/social/addFriend/">Add Friend</a></li>
              <li><a href="/social/join/">Join Group</a></li>
              <li><a href="/social/group/">Create Group</a></li>
              <li><a href="/social/friendList/">My Friends</a></li>
			  <li><a href="/social/sendMessage/">Send Message</a></li>
			  <li><a href="#">Send Group Message</a></li>
            </ul>
          </div>
        </div>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>		
        <div class="clr"></div>
      </div>
    </div>
  </div>
  <div class="fbg">
    <div class="fbg_resize">
      <div class="col c1">
        <h2><span>Share Your Ideas</span></h2>
        <a href="#"><img src="/images/pic_1.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="/images/pic_2.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="/images/pic_3.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="/images/pic_4.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="/images/pic_5.jpg" width="58" height="58" alt="" /></a> <a href="#"><img src="/images/pic_6.jpg" width="58" height="58" alt="" /></a> </div>
      <div class="col c2">
        <h2><span>To our users:</span></h2>
        <p>We are always interested in your feedbacks, so don't hesitate to send us your feedbacks and suggestions. </p>
      </div>
      <div class="col c3">
        <h2><span>About</span></h2>
        <p>We are SWII project Team:
		<br>AbdelAziz Sayed
		<br>Abdelrahman Mostafa
		<br>Essam Mohammed Omar</p>
      </div>
      <div class="clr"></div>
    </div>
  </div>
</div>
<div class="footer">
  <div class="footer_resize">
    <p class="lf">&copy; Copyright <a href="#">FCISocialNet</a>.</p>
    <p class="rf">Layout by Rocket <a href="http://www.rocketwebsitetemplates.com/">Website Templates</a></p>
    <div class="clr"></div>
  </div>
</div>
<div align=center>This template  downloaded form <a href='http://all-free-download.com/free-website-templates/'>free website templates</a></div></body>
</html>