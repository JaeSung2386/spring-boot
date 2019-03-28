<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user/drop">
					<input type="hidden" name="userno" value="${authuser.no }">
					<label class="block-label" for="name">email</label>
					<input id="email" name="email" type="text" value="">
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					<input type="submit" value="탈퇴하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
<c:if test='${param.result == "success" }'>
	<script>
		alert("정상적으로 탈퇴 하였습니다.");
		location.href="${pageContext.servletContext.contextPath }";
	</script>
</c:if>
</html>