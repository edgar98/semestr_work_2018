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
    <a class="navbar-brand" href="/">Nutrition Calculator</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/signIn">SignIn</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="/signUp">SignUp</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container" style="margin-top: 30px">
    <h1>New user</h1>

    <div class="registration">
        <form action="${pageContext.request.contextPath}" method="post">

            <div class="row" style="margin-top: 10px">
                <label for="username">User name: </label>
                <input class="input-sm" style="margin-left: 13px" type="text" name="username" id="username" required>
            </div>

            <div class="row" style="margin-top: 10px">
                <label for="password">Password: </label>
                <input class="input-sm" style="margin-left: 20px" type="password" name="password" id="password" required>
            </div>

            <div class="row" style="margin-top: 10px">
                <input type="submit" value="Log in">

            </div>
        </form>
    </div>
</div>

</body>

</html>
