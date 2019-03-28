<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css?after" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
#dialog-delete-form p {
	padding: 10px;
	font-weight: bold;
	font-size: 1.0em;
}

#dialog-delete-form input[type="password"]{
	padding: 5px;
	outline: none;
	width: 180px;
	border: 1px solid #888
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
// jquery plug-in
(function(a){
	$.fn.hello = function() {
		console.log( $(this).attr("id") + "----> hello" );
	}
})(jQuery);

var page = 0;
var isEnd = false;

var messageBox = function(title, message){
	$("#dialog-message").attr("title", title);
	$("#dialog-message p").text(message);
	$("#dialog-message").dialog({
		modal: true,
		buttons: {
			"확인": function(){
				$(this).dialog("close");
			}
		}
	});
}

var render = function(vo, mode){
	// 현업게 가면 이렇게 안한다. -> js template libaray를 사용
	// ex) ejs, uderscore, mustache
	var htmls = 
		"<li data-no='" + vo.no + "'>" +
		"<strong>" + vo.name + "</strong>" +
		"<p>" + vo.message + "</p>" +
		"<strong></strong>" +
		"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
		"</li>";
	if( mode == true ){
		$("#list-guestbook").prepend(htmls);		
	} else {
		$("#list-guestbook").append(htmls);

	}
}

var fetchList = function(){
	if(isEnd == true) {
		return;
	}
	++page;
	$.ajax({
		url: "${pageContext.request.contextPath }/guestbook/api/list?page=" + page,
		type: "get",
		dataType: "json",
		data: "",
		success: function(response) {
			if(response.result == "fail") {
				console.warn(response.message);
				return;
			}
			
			// 페이지 끝을 검출
			if(response.data.length < 5) {
				isEnd = true;
				$("#btn-next").prop("disabled", true);
			}
			
			// rendering
			$.each(response.data, function(index, vo) {
				render(vo, false);
			})
		},
		error: function(xhr, status, e) {
			console.error(status + ":" + e);
		}
	});		
}

$(function(){
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function(){
				var no = $( "#hidden-no" ).val();
				var password = $("#password-delete").val();
				console.log("no: " + no);
				console.log("password: " + password);
				$.ajax({
					url: "${pageContext.servletContext.contextPath }/guestbook/api/delete",
					type: "post",
					dataType: "json",
					data:
						"no=" + no +
						"&password=" + password,
					success: function(response) {
						// 비밀번호 다름
						if(response.result == "fail") {
							console.error(response.message);
							return;
						}
						
						// 삭제 실패
						if(response.data === -1) {
							$( "#dialog-delete-form .validateTips" ).hide();
							$( "#dialog-delete-form .validateTips.error" ).show();
							$( "#delete-password" ).val( "" );
							return;
						}
						console.log(response.data);
						$( "#list-guestbook li[data-no='" + response.data + "']" ).remove();
						dialogDelete.dialog( "close" );
					},
					error: function(xhr, status, e) {
						console.error(status + ":" + e);
					}
				})
			},
			"취소": function(){
				dialogDelete.dialog("close");
			}
		},
		close: function(){
			$("#password-delete").val("");
			$( "#hidden-no" ).val( "" );
		}
	});
	
	// live event(삭제)
	$(document).on("click", "#list-guestbook li a", function(){
		event.preventDefault();
		var no = $(this).data("no")
		console.log("clicked:" + no);
		$( "#hidden-no" ).val( no );
		dialogDelete.dialog("open");
	} );
	
	//메세지 등록 폼 submit 이벤트 처리
	$("#add-form").submit(function() {
		// submit의 기본동작(post)
		// 막아야 한다.
		event.preventDefault();
		
		var vo = {};
		// validate form data
		// focus 처리 할 것
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();
		
		if(vo.name == ""){
			messageBox("글 남기기", "이름은 필수 입력 항목입니다.");
			return;
		}
		if(vo.password == ""){
			messageBox("글 남기기", "비밀번호는 필수 입력 항목입니다.");
			return;
		}
		if(vo.message == ""){
			messageBox("글 남기기", "내용은 필수 입력 항목입니다.");
			return;
		}
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/guestbook/api/add",
			type: "post",
			dataType: "json",
			data: JSON.stringify( vo ),
			contentType: 'application/json',
			success: function(response) {
				if(response.result == "fail") {
					console.error(response.message);
					return;
				}
				
				// rendering
				console.log(response.data);
				render(response.data, true);
			},
			error: function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});

		//내용 비우기
		$("#input-name").val("");
		$("#input-password").val("");
		$("#tx-content").val("");
	});

	//스크롤 이벤트
	$(window).scroll( function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		
		//console.log(scrollTop + ":" + windowHeight + ":" + documentHeight);
		if( scrollTop + windowHeight + 10 > documentHeight) {
			fetchList();
		}
	});
	
	$("#btn-next").click(function() {
		$(this).hello();
		fetchList();
	});

	//최초 리스트 가져오기
	fetchList();
	
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<button id="btn-next">다음</button>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
  					<input type="hidden" name=no id="hidden-no" value=""/>
 					<input style="padding:5px; outline:none" type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p style="padding:20px 0"></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>