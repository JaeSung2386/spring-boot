<%@page import="com.douzone.mysite.vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newline", "\n");
	BoardVo vo = (BoardVo)request.getAttribute("vo");
	pageContext.setAttribute("vo", vo);
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(vo.contents, newline, "<br>") }
							</div>
						</td>
					</tr>
				</table>
				<table class="tbl-ex">
				<c:set var="count" value="${fn:length(list) }"/>
				<c:forEach items="${list }" var="commentvo" varStatus="status">
					<tr>
						<c:choose>
							<c:when test="${commentvo.o_no eq '1' }">
								<td>
									${commentvo.user_name }
								</td>
							</c:when>
							<c:when test="${commentvo.o_no != '1' }">
								<td style="text-align: left; padding:0px 0px 0px ${commentvo.depth * 15}px;">
								<img src ="${pageContext.servletContext.contextPath }/assets/images/reply.png" width=15 height=15 />
									${commentvo.user_name }
								</td>
							</c:when>
						</c:choose>
						<td>
							${commentvo.write_date }
						</td>
						<!-- 회원만 답글, 수정, 삭제 가능 -->
						<c:if test="${authuser.no != null}">
							<td>
								<a href="${pageContext.servletContext.contextPath }/board/comment/reply/${commentvo.no}">답글</a>
							</td>
							<!-- 댓글을 작성한 유저만 수정 삭제 가능  -->
							<c:if test="${!empty authuser and authuser.no eq commentvo.user_no}">
							<td>
								<a href="${pageContext.servletContext.contextPath }/board/comment/modify/${commentvo.no}">수정</a>
							</td>
							<td>
								<a href="${pageContext.servletContext.contextPath }/board/comment/delete/${commentvo.no}">삭제</a>
							</td>
							</c:if>
						</c:if>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td colspan="4">
							<div class="view-content">${commentvo.contents }</div>
						</td>
					</tr>
					<tr><td></td></tr>
				</c:forEach>
				</table>
				<!-- 로그인 되어 있는 유저만 댓글 작성 가능 -->
				<c:if test="${authuser.no != null}">
					<div id="content">
						<div id="board">
							<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/comment/write">
								<input type="hidden" name="userno" value="${authuser.no }">
								<input type="hidden" name="board_no" value="${vo.no }">
								<table class="tbl-ex">
									<tr>
										<td>
											<textarea id="contents" name="contents" style="width:460px;"></textarea>
										</td>
										<td class ="bottom">
											<input type="submit" value="등록">
										</td>
									</tr>
								</table>
							</form>				
						</div>
					</div>
				</c:if>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board">글목록</a>
					<!-- 현재 로그인 되어있고 로그인하고 있는 유저가 작성한 글만 수정 가능 -->
					<c:if test="${authuser.no eq vo.user_no}">
						<a href="${pageContext.servletContext.contextPath }/board/modify/${vo.no}">글수정</a>
					</c:if>
					<!-- 로그인중이고 글 작성자가 아니면 답글 달기 가능 -->
					<c:if test="${!empty authuser and authuser.no ne vo.user_no}">
						<a href="${pageContext.servletContext.contextPath }/board/reply/${vo.no}">답글달기</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>