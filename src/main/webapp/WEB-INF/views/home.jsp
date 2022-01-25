<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    <c:if test="${not empty register}">
    alert("<c:out value="${register}" />");
    </c:if>
    <c:if test="${not empty success}">
    alert("${success}");
    </c:if>
</script>


<div class="component">
    <div class="banner">
        <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active"
                        aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"
                        aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"
                        aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="/images/pexels-artem-beliaikin-994517.jpg" class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="/images/pexels-los-muertos-crew-8030175.jpg" class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="/images/pexels-rachel-claire-5864245.jpg" class="d-block w-100" alt="...">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
                    data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
                    data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
    <div class="content newItem">
        <div class="container">
            <div class="newBestItem">
                <div class="ItemTitle">
                    <h4>NEW</h4>
                    <div class="line"></div>
                </div>
                <ul>
                    <c:if test="${not empty products}">
                        <c:forEach items="${products}" var="product">
                            <li>
                                <a style="text-decoration: none; color: black" href="/product/detail/${product.id}">
                                    <div class="card" style="width: 18rem;">
                                        <img src="/images/${product.productImg.imgName}" class="card-img-top" alt="...">
                                        <div class="card-body">
                                            <p class="card-text"><strong>${product.productName}</strong>
                                            <div><strong><fmt:formatNumber
                                                value="${product.price}" pattern="#,###,###"/> 원</strong></div>
                                            </p>
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
    <div class="content bestItem">
        <div class="container">
            <div class="bestItem newBestItem">
                <div class="ItemTitle">
                    <h4>BEST</h4>
                    <div class="line"></div>
                </div>
                <ul>
                    <c:if test="${not empty products}">
                        <c:forEach items="${products}" var="product">
                            <li>
                                <a style="text-decoration: none; color: black" href="/product/detail/${product.id}">
                                    <div class="card" style="width: 18rem;">
                                        <img src="/images/${product.productImg.imgName}" class="card-img-top" alt="...">
                                        <div class="card-body">
                                            <p class="card-text"><strong>${product.productName}</strong>
                                            <div><strong><fmt:formatNumber
                                                value="${product.price}" pattern="#,###,###"/> 원</strong></div>
                                            </p>
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
