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
	<form name="form1" method="post" action="SetMenu.do">
		<p>
			<label for="menu">午餐菜单</label>
		</p>
		<p>
			<textarea name="menu" id="menu" cols="85" rows="32">${requestScope.menu }</textarea>
		</p>
		<p>
			<input type="submit" name="button" id="button" value="更新">
		</p>
	</form>
</body>
</html>