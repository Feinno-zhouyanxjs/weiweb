<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<label>${requestScope.status}</label>
	<form name="form1" method="post" action="manager/Password.do">
		<p>
			<label for="oldpassword">旧密码</label> <input type="password"
				name="oldpassword" id="oldpassword">
		</p>
		<p>
			<label for="newpassword">新密码</label> <input type="password"
				name="newpassword" id="newpassword">
		</p>
		<p>
			<label for="newpassword2">重复输入</label> <input type="password"
				name="newpassword2" id="newpassword2">
		</p>
		<p>
			<input type="submit" name="button" id="button" value="提交">
		</p>
	</form>
</body>
</html>