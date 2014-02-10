<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登陆</title>
</head>
<body>
	<label>${requestScope.status}</label>
	<form name="form1" method="post" action="Login.do">
		<p>
			<label for="userName">用户名</label> <input type="text" name="userName"
				id="userName">
		</p>
		<p>
			<label for="password">密码</label> <input type="password"
				name="password" id="password">
		</p>
		<p>
			<input type="submit" name="button" id="button" value="登陆">
		</p>
	</form>
</body>
</html>