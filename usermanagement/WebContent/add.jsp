<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User management. Add</title>
    </head>
    <body>
        <form action="<%=request.getContextPath()%>/add" method="post">
        First name <input type="text" name="firstName" value=""><br>
        Last name <input type="text" name="lastName" value=""><br>
        Date of birth <input type="text" name="date" value=""><br>
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