<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 29.10.2021
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="w3.css">

<head>
    <title>Title</title>
</head>
<body>
Welcome to the Fatty Acids! <br>

<div>
    <div>
        <form id="login" method="get" action="/login" >
            <input type="hidden" name="command" value="form"/>
            <button form="login" type="submit">Log in</button>
        </form>
    </div>

    <div>
        <form id="register" method="get" action="/register" >
            <input type="hidden" name="command" value="form"/>
            <button form="register" type="submit">Register</button>
        </form>
    </div>

</div>

<p>
    Please fill out the form below to add your product: <br>
</p>


<form class="w3-container w3-light-grey" method="post" action="<c:url value='/'/>">
    <input type="hidden" name="command" value="add"/>

    <label>Product Name:</label>
    <input class="w3-input w3-border-0" type="text" class="register-input" name="productName" required>

    <label>Omega-3 content, mg:</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaThree" min="0" step="0.1" required>

    <label>Omega-6 content, mg:</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaSix" min="0" step="0.1" required>

    <label>Number of portions (1pt = 28g):</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="portions" min="1" required>

    <input type="submit" value="Add" name="Add">

</form>


<table class="w3-table w3-striped">
    <thead>
    <tr>
        <th>Product name</th>
        <th>Omega-3 content, mg</th>
        <th>Omega-6 content, mg</th>
        <th>Number of portions (1pt = 28g)</th>
        <th>Delete</th>
        <th>Edit</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsAttribute}">

    <tr>
        <td><c:out value="${product.productName}"/></td>
        <td><c:out value="${product.omegaThree}"/></td>
        <td><c:out value="${product.omegaSix}"/></td>
        <td><c:out value="${product.portion}"/></td>

                    <td>
                        <form method="get" action="<c:url value='/'/>">
                            <input type="hidden" name="productId" value="${product.productId}" />
                            <input type="hidden" name="command" value="delete" />
                            <input type="submit" name="Delete" value="Delete"/>
                        </form>
                    </td>

                    <td>
                            <form method="get" action="/update">
                            <input type="number" hidden name="productId" value="${product.productId}" />
                            <input type="hidden" name="command" value="form" />
                            <input type="submit" name="Edit" value="Edit"/>
                        </form>
                    </td>

    </tr>
    </c:forEach>
    </tbody>
</table>

<p><c:out value="Your proportion: ${proportion}" default=""/></p>
Optimum range is 2-5.
<p><c:out value="${message}${name}" default="" /></p>

</body>
</html>
