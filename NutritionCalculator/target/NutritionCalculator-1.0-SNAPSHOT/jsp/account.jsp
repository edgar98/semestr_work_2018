<%--
  Created by IntelliJ IDEA.
  User: edgar
  Date: 27.12.18
  Time: 3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <a href="${pageContext.request.contextPath}/stats">Stats</a>
</div>
<div>
    <a href="${pageContext.request.contextPath}/calculator">Calc</a>
</div>
<div>
    <a href="${pageContext.request.contextPath}/advice">Advice</a>
</div>
<div>
    <form action="${pageContext.request.contextPath}" method="post">
        <div class="row" style="margin-top: 10px">
            <label for="first_name">First name: </label>
            <input required class="input-sm" style="margin-left: 13px" type="text" name="first_name" id="first_name" value="${user.getFirstname()}">
        </div>
        <div class="row" style="margin-top: 10px">
            <label for="last_name">Last name: </label>
            <input required class="input-sm" style="margin-left: 13px" type="text" name="last_name" id="last_name" value="${user.getLastname()}">
        </div>
        <div class="row" style="margin-top: 10px">
            <label for="weight">Weight: </label>
            <input required class="input-sm" style="margin-left: 13px" type="text" name="weight" id="weight" value="${user.getWeight()}">
        </div>
        <div class="row" style="margin-top: 10px">
            <label for="height">Height: </label>
            <input required class="input-sm" style="margin-left: 13px" type="text" name="height" id="height" value="${user.getHeight()}">
        </div>
        <div class="row" style="margin-top: 10px">
            <label for="age">Age: </label>
            <input required class="input-sm" style="margin-left: 13px" type="text" name="age" id="age" value="${user.getAge()}">
        </div>
        <div class="row" style="margin-top: 10px">
            <label for="sex">Sex: </label>
            <input required name="sex" type="radio" value="male"> Man
            <input required name="sex" type="radio" value="female"> Woman
        </div>
        <div class="row" style="margin-top: 10px">
            <label for="activity">Activity: </label>
            <div>
                <input required name="activity" type="radio" value="1.2"> Minimum <br>
                <input required name="activity" type="radio" value="1.375"> Low <br>
                <input required name="activity" type="radio" value="1.55"> Mid <br>
                <input required name="activity" type="radio" value="1.725"> High <br>
                <input required name="activity" type="radio" value="1.9"> Maximum <br>
            </div>
        </div>
        <div class="row" style="margin-top: 10px">
            <input type="submit" value="Log in">
        </div>
    </form>

    <h3>Your needs</h3>
    <div>
        <div class="row">Calories: ${user.getNeeds().getKal()}</div>
        <div class="row">Fat:: ${user.getNeeds().getFat()}</div>
        <div class="row">Proteins: ${user.getNeeds().getProt()}</div>
        <div class="row">Carbon Dioxide: ${user.getNeeds().getCarbon()}</div>
        <div class="row">Water: ${user.getNeeds().getWater()}</div>
    </div>
</div>
</body>
</html>
