<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="/js/jquery-3.6.0.min.js" ></script>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/member.css">
</head>
<body>

<header>
<tiles:insertAttribute name="header"/>
    <hr>
</header>


<nav>
<tiles:insertAttribute name="menu"/>
    <hr>
</nav>

<div class="wrap" >
<section>
    <tiles:insertAttribute name="content"/>
</section>

<footer>
    <hr>
<tiles:insertAttribute name="footer"/>
</footer>
</div>

</body>
</html>
