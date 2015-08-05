<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
	<title>Login Page</title>
	<style>
		.error {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #a94442;
			background-color: #f2dede;
			border-color: #ebccd1;
		}

		.msg {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #31708f;
			background-color: #d9edf7;
			border-color: #bce8f1;
		}

		body {
			background-color:#fff;
			-webkit-font-smoothing: antialiased;
			font: normal 14px Roboto,arial,sans-serif;
		}

		.container {
			padding: 25px;
			position: fixed;
		}

		.form-login {
			margin-top:100px;
			background-color: #EDEDED;
			padding-top: 10px;
			padding-bottom: 20px;
			padding-left: 20px;
			padding-right: 20px;
			border-radius: 15px;
			border-color:#d2d2d2;
			border-width: 5px;
			box-shadow:0 1px 0 #cfcfcf;
		}

		h4 {
			border:0 solid #fff;
			border-bottom-width:1px;
			padding-bottom:10px;
			text-align: center;
		}

		.form-control {
			border-radius: 10px;
		}

		.wrapper {
			text-align: center;
		}
	</style>

	<script src="../../resources/assets/lib/jquery.js"></script>


	<!-- Bootstrap Core JavaScript -->
	<script src="../../resources/assets/lib/bootstrap.min.js"></script>
	<link href="../../resources/assets/css/bootstrap.min.css" rel="stylesheet" />
	<link href="../../resources/assets/css/font-awesome.min.css" rel="stylesheet" />
</head>
<body onload='document.loginForm.username.focus();'>






<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">

	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
			<span class="sr-only">Toggle navigation</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand pull-left" style="color:azure" href="#">Vindhya eInfomedia</a>





	</div>
	<!-- /.container -->
</nav>

<form name='loginForm'
	  action="<c:url value='/j_spring_security_check' />" method='POST'>
	<div class="container">
		<div class="row">
			<div class="col-md-offset-5 col-md-3">
				<div class="form-login">
					<h4>Welcome back.</h4>
					<input type="text" name="username" class="form-control input-sm chat-input" placeholder="username" />
					</br>
					<input type="password" name="password" class="form-control input-sm chat-input" placeholder="password" />
					</br>
					<c:if test="${not empty error}">
						<div class="error">${error}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="msg">${msg}</div>
					</c:if>
					<div class="wrapper">
            <span class="group-btn">
                <input name="submit" type="submit" value="submit"
					   class="btn btn-primary btn-md"/>
            </span>
					</div>
				</div>

			</div>
		</div>
	</div>


	<input type="hidden" name="${_csrf.parameterName}"
		   value="${_csrf.token}" />

</form>


</body>
</html>