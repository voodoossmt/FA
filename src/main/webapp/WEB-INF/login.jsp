<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 20.11.2021
  Time: 1:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>


<body>

    <div>
        <div>
            <h2>Log in</h2>
        </div>

        <form method="post" action="<c:url value='/'/>">
            <input type="hidden" name="command" value="login"/>

        <label>Name:
                <input type="text" name="name" maxlength="30" required><br />
            </label>
            <label>Password:
                <input type="password" name="pass" maxlength="30" required><br />
            </label>
            <button type="submit">Submit</button>
        </form>
    </div>


</body>

<div>
<button onclick="location.href='..'">Back to main </button>
</div>

<p><c:out value="${message}" default="" /></p>

</html>
