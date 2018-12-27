<%--
  Created by IntelliJ IDEA.
  User: edgar
  Date: 26.12.18
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>NutritionCalc</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <a class="navbar-brand" href="#">Nutrition Calculator</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/signIn">SignIn</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/signUp">SignUp</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container" style="margin-top: 30px">
    <h1 class="">Welcome to our calculator</h1>

    <p class="">Here you can control your nutrition and also
        <br class="">get advices for right-eatin'.</p>
    <!-- /container -->
</div>
<div class="container" style="margin-top: 30px">
    <div class="row">
        <div class="col-auto">
            <form action="${pageContext.request.contextPath}/" method="post">
                <div class="row" style="margin-top: 10px">
                    <label for="weight">weight: </label>
                    <input required class="input-sm" style="margin-left: 13px" type="text" name="weight" id="weight">
                </div>
                <div class="row" style="margin-top: 10px">
                    <label for="height">Height: </label>
                    <input required class="input-sm" style="margin-left: 13px" type="text" name="height" id="height">
                </div>
                <div class="row" style="margin-top: 10px">
                    <label for="age">Age: </label>
                    <input required class="input-sm" style="margin-left: 13px" type="text" name="age" id="age">
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
                    <input type="submit" value="Get your needs">
                </div>
            </form>
        </div>
        <div class="col-auto">
            <h3>Your needs</h3>
            <div>
                <div class="row">Calories: ${calc.getKal()}</div>
                <div class="row">Fat:: ${calc.getFat()}</div>
                <div class="row">Proteins: ${calc.getProt()}</div>
                <div class="row">Carbon Dioxide: ${calc.getCarbon()}</div>
                <div class="row">Water: ${calc.getWater()}</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
