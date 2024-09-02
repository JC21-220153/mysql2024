<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JK3B09</title>
</head>
<% 
	ArrayList<String[]> result =
		(ArrayList<String[]>) request.getAttribute("result");
%>

<body>
<h1>メーカー一覧</h1>
<FORM METHOD="GET" ACTION="./item">
<SELECT NAME="MAKER_CODE">

<% for (String[] ss : result) { %>
		<OPTION VALUE="<%= ss[1] %>">
		<%= ss[0] %>
		</OPTION>
<% } %>


</SELECT>
<INPUT TYPE="SUBMIT" VALUE="絞り込む"/>

<%
	ArrayList<String[]> result2 =
		(ArrayList<String[]>) request.getAttribute("result2");
%>

<table>

<h2>商品一覧</h2>

<% for (String[] ss2 : result2) { %>
	<tr>
		<th><%= ss2[0] %></th>
		<th><%= ss2[1] %></th>
		<th><%= ss2[2] %></th>
	<tr>
<% } %>

</table>

</FORM>
</body>
</html>