<%--
  Created by IntelliJ IDEA.
  User: edgar
  Date: 27.12.18
  Time: 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nutrition Calculator</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <a href="/account">Account</a>
        </div>
        <div class="col">
            <a href="/advice">Advice</a>
        </div>
        <div class="col">
            <a href="/stats">Stats</a>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h3>Your statistics for ${today_nutrition.getDate()}</h3>
            <div>
                <div class="row">Calories: ${today_nutrition.getKal()} of ${user.getNeeds().getKal()}</div>
                <div class="row">Fat:: ${today_nutrition.getFat()} of ${user.getNeeds().getFat()}</div>
                <div class="row">Proteins: ${today_nutrition.getProt()} of ${user.getNeeds().getProt()}</div>
                <div class="row">Carbon Hydroxide: ${today_nutrition.getCarbon()} of ${user.getNeeds().getCarbon()}</div>
                <div class="row">Water: ${today_nutrition.getWater()} of ${user.getNeeds().getWater()}</div>
            </div>
            <div>
                <form action="${pageContext.request.contextPath}/calculator" method="post" id="productform">
                    <select name="product" id="product" form="productform">
                        <c:forEach var="product" items="${products}">
                            <option value="${product.getId()}" name="product">${product.getName()}</option>
                        </c:forEach>
                    </select>
                    <input required type="number" name="amount">Amount <br>
                    <input type="submit" value="Eat!"> <br>
                </form>
            </div>
        </div>
        <div class="col">
            <h3>No your favorite product?</h3><br>
            <h4>Create own!</h4>
            <div>
                <form action="/products" method="post">
                    <div class="row" style="margin-top: 10px">
                        <label for="name">Name: </label>
                        <input required class="input-sm" style="margin-left: 13px" type="text" name="name" id="name">
                    </div>
                    <div class="row" style="margin-top: 10px">
                        <label for="kal">Calories: </label>
                        <input required class="input-sm" style="margin-left: 13px" type="number" name="kal" id="kal">
                    </div>
                    <div class="row" style="margin-top: 10px">
                        <label for="fat">Fat: </label>
                        <input required class="input-sm" style="margin-left: 13px" type="number" name="fat" id="fat">
                    </div>
                    <div class="row" style="margin-top: 10px">
                        <label for="prot">Protein: </label>
                        <input required class="input-sm" style="margin-left: 13px" type="number" name="prot" id="prot">
                    </div>
                    <div class="row" style="margin-top: 10px">
                        <label for="carbon">Carbon Hydroxide: </label>
                        <input required class="input-sm" style="margin-left: 13px" type="number" name="carbon" id="carbon">
                    </div>
                    <div class="row" style="margin-top: 10px">
                        <label for="water">Water: </label>
                        <input required class="input-sm" style="margin-left: 13px" type="number" name="water" id="water">
                    </div>
                    <div class="row" style="margin-top: 10px">
                        <input type="submit" value="Create">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
