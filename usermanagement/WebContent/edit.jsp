<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" class="kn_14_6.berezovskyi.User" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User management. Edit</title>
    </head>
    <body>
        <form action="<%=request.getContextPath()%>/edit" method="post">
        <input type="hidden" name="id" value="${user.id}">
        First name <input type="text" name="firstName" value="${user.firstName}"><br>
        Last name <input type="text" name="lastName" value="${user.lastName}"><br>
        <fmt:parseDate value="${user.dateOfBirthd}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
        Date of birth <input type="text" name="date" value="<fmt:formatDate value="${parsedDate}" type="date" pattern="dd.MM.yyyy" dateStyle="medium"/>"><br>
        <input type="submit" name="okButton" value = "Ok">
        <input type="submit" name="cancelButton" value = "Cancel">
        </form>
        <c:if test="${requestScope.error != null}">
            <script>
            alert('${requestScope.error}');
            </script>
        </c:if>
    </body>
</html>