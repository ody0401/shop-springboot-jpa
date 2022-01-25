<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="header" class="component">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <sec:authorize access="isAuthenticated()">
                        <a class="nav-link" href="/member/logout">Logout</a>
                        <a class="nav-link" href="/product/register">Register</a>
                        <a class="nav-link" href="/cart">Cart</a>
                        <a class="nav-link" href="/order">Order</a>
                        <a class="nav-link" style="cursor: pointer" onclick="getDummies()">Dummies</a>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        <a class="nav-link" href="/member/login">Login</a>
                        <a class="nav-link" href="/member/signup">Signup</a>
                        <a class="nav-link" style="cursor: pointer" onclick="getDummies()">Dummies</a>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </nav>
</div>

<script>
    const getDummies = () => {
        $.ajax({
            url:"/dummies",
            method:"GET"
            })
            .done((res) => {
                console.log("OK");
            })
            .fail((request, status, error) => {
                console.log("실패");
            });
    }
</script>

