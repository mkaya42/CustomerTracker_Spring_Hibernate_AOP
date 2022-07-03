<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.mky.springdemo.util.SortUtils" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Customers</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>

</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			 		
				    <c:url var="sortLinkFirstName" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.FIRST_NAME) %>" />
					</c:url>
				    <c:url var="sortLinkLastName" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.LAST_NAME) %>" />
					</c:url>
					<c:url var="sortLinkEmail" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.EMAIL) %>" />
					</c:url>
			
			<input type="button" value="Add Customer" 
			  onclick="window.location.href='showFormForAdd'; return false;" class="add-button" />
			  <form:form action="search" method="GET">
			  Search Customer : <input type="text" name="theSearchName"/>
			  					<input type="submit" value="Search" class="add-button"/>
			  					
			  </form:form>
			<table>
				<tr>
					<th><a href="${sortLinkFirstName}">First Name</a></th>
					<th><a href="${sortLinkLastName}">Last Name</a></th>
					<th><a href="${sortLinkFirstName}">Email</a></th>
					<th></th>
				</tr>
				<c:forEach var="tempCustomer" items="${customers}">
				  
				 	 <!-- Construct an update link -->
				    <c:url var="updateLink" value="/customer/showFormForUpdate">
				    <c:param name="customerId" value="${tempCustomer.id}"></c:param>
				    </c:url>
				    <!-- Construct a delete link -->
				    <c:url var="deleteLink" value="/customer/delete">
				    <c:param name="customerId" value="${tempCustomer.id}"></c:param>
				    </c:url>
				
					<tr>
						<td>${tempCustomer.firstName}</td>
						<td>${tempCustomer.lastName}</td>
						<td>${tempCustomer.email} </td>
						<td>
						<!-- Display the update link -->
						<a href="${updateLink}">Update</a>
						|
						<!-- Display the delete link -->
						<a href="${deleteLink}" onclick="if(!(confirm('Are you sure want to delete this customer ?'))) return false;">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>