<%@page import="com.douzone.mysite.vo.PageVo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	PageVo pagevo = (PageVo)request.getAttribute("pagevo");
	pageContext.setAttribute("pagevo", pagevo);
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>

$( function() {
	$("#menu").click(function(){
        alert("이름 클릭");
	});
    //$( "#menu" ).menu();
  } );
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search" action="${pageContext.servletContext.contextPath }/board" method="get">
					<select name="search" style="height:30px">
					    <option value="title">제목만 검색</option>
					    <option value="contents">내용만 검색</option>
					    <option value="all">제목과 내용 둘 다 포함</option>
					</select>
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>삭제</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var="count" value="${fn:length(list) }"/>
					<c:if test="${count eq '0' }">
						<tr>
							<td colspan="7">
								<a href="${pageContext.servletContext.contextPath }/board">
									클릭하면 게시판 목록으로 돌아갑니다.
								</a>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${list }" var="vo" varStatus="status">
					<tr>
						<td>[ ${pagevo.totalCount - (status.index + ((pagevo.pageNo - 1) * 10 )) } ]</td>
						<c:choose>
							<c:when test="${vo.o_no eq '1' }">
								<td style="text-align: left;"><a href="${pageContext.servletContext.contextPath }/board/view/${vo.no}/${authuser.no}">${vo.title }</a></td>
							</c:when>
							<c:when test="${vo.o_no != '1' }">
								<td style="text-align: left; padding:0px 0px 0px ${vo.depth * 20}px;"><img src ="${pageContext.servletContext.contextPath }/assets/images/reply.png" width=15 height=15 /><a href="${pageContext.servletContext.contextPath }/board/view/${vo.no}/${authuser.no}">[답글]${vo.title }</a></td>
							</c:when>
						</c:choose>
						<td id="menu">
							${vo.name }
						</td>
						<td>${vo.hit }</td>
						<td>${vo.write_date }</td>
						<td>
							<!-- 로그인하면 삭제 버튼 표시함 -->
							<c:if test="${!empty authuser and authuser.no eq vo.user_no}">
								<a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no}">
								<img src ="${pageContext.servletContext.contextPath }/assets/images/delete.jpg" width=20 height=20/>
								</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test ="${pagevo.pageNo > 5 }">	
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${pagevo.firstPageNo}&search=${pagevo.search }&kwd=${pagevo.kwd }">◀◀</a></li>
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${pagevo.prevPageNo}&search=${pagevo.search }&kwd=${pagevo.kwd }">◀</a></li>
						</c:if>
							<c:forEach var="i" begin="${pagevo.startPageNo }" end="${pagevo.endPageNo }" step="1">
								<c:if test="${i ne 0 }">
									<c:choose>
										<c:when test="${i eq pagevo.pageNo }">
											<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?page=${i}&search=${pagevo.search }&kwd=${pagevo.kwd }">${i}</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageContext.servletContext.contextPath }/board?page=${i}&search=${pagevo.search }&kwd=${pagevo.kwd }">${i}</a></li>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						<c:if test ="${pagevo.pageNo > 5 }">
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${pagevo.nextPageNo}&search=${pagevo.search }&kwd=${pagevo.kwd }">▶</a></li>
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${pagevo.finalPageNo}&search=${pagevo.search }&kwd=${pagevo.kwd }">▶▶</a></li>
						</c:if>
					</ul>
				</div>	
				<c:if test="${!empty authuser }">
					<div class="bottom">
						<!-- 글 쓰기 현재 로그인하고 있는 유저의 no를 넘겨준다. -->
						<a  href="${pageContext.servletContext.contextPath }/board/write" id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>