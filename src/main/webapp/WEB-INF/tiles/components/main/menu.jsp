<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div class="component">
    <nav class="navbar navbar-expand-lg navbar-light bg-light menu">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Home</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav justify-content-evenly mb-2 mb-lg-0 ">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/category/top">TOP</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/category/outer">OUTER</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/category/shirts">SHIRTS</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/category/bottom">BOTTOM</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/category/shoes">SHOES</a>
                    </li>
                </ul>
            </div>
            <form class="d-flex" action="/category/search" method="post">
                <input class="form-control me-2" type="search" placeholder="Search" name="keyword" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </nav>
</div>

