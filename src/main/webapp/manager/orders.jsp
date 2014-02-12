<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pzoom.database.DataTable"%>
<%@ page import="com.pzoom.database.DataRow"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>

	<p>用户统计</p>
	<table width="0" border="0">
		<tr>
			<th scope="col">用户</th>
			<th scope="col">菜单</th>
			<th scope="col">合计</th>
			<th scope="col">日期</th>
			<th scope="col">备注</th>
		</tr>
		<%
			Object uobj = request.getAttribute("userDataTable");
			if (uobj != null) {
				DataTable userDataTable = (DataTable) uobj;
				double sum = 0;
				for (int i = 0; i < userDataTable.getRowCount(); i++) {
					DataRow dr = userDataTable.getRow(i + 1);
					sum += dr.getDouble("Price");
		%>
		<tr>
			<td><%=dr.getString("Alias")%></td>
			<td><%=dr.getString("Menus")%></td>
			<td><%=dr.getString("Price")%></td>
			<td><%=dr.getString("CreatedTime")%></td>
			<td><%=dr.getString("Comment")%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="5" align="right">总和：<%=sum%></td>
		</tr>
		<%
			}
		%>
	</table>

	<p>菜单统计</p>
	<table width="0" border="0">
		<tr>
			<th scope="col">菜单</th>
			<th scope="col">合计</th>
		</tr>
		<%
			Object fobj = request.getAttribute("foodDataTable");
			if (fobj != null) {
				DataTable foodDataTable = (DataTable) fobj;
				double sum = 0;
				for (int i = 0; i < foodDataTable.getRowCount(); i++) {
					DataRow dr = foodDataTable.getRow(i + 1);
					sum += dr.getDouble("Price");
		%>
		<tr>
			<td><%=dr.getString("ItemName")%></td>
			<td><%=dr.getString("Price")%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td colspan="2" align="right">总和：<%=sum%></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>
