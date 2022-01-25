<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="content">
    <div class="container">
        <div class="ItemTitle">
            <h4>OUTER</h4>
            <div class="line"></div>
        </div>
        <div>
            <div class="productMenu">
                <c:choose>
                    <c:when test="${empty totalProducts}">
                        <span><strong>Total: 0 개</strong></span>
                    </c:when>
                    <c:otherwise>
                        <span><strong>Total: ${totalProducts} 개</strong></span>
                    </c:otherwise>
                </c:choose>
                <ul class="nav justify-content-center">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="?list=new"/>">신상품</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="?list=name"/>">상품명</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="?list=lowPrice"/>">낮은가격</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="?list=highPrice"/>">높은가격</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="productList">
            <ul>
                <c:choose>
                    <c:when test="${not empty productError}">
                        <div><strong><c:out value="${productError}"/></strong></div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${products.dtoList}" var="product">
                            <li>
                                <a href="/product/detail/${product.productId}"
                                   style="color: black; text-decoration: none;">
                                    <div class="card">
                                        <img src="/images/${product.productImgUrl}" class="card-img-top" alt="...">
                                        <div class="card-body">
                                            <p class="card-text">${product.productName}</p>
                                            <div style="font-size: 0.9rem">
                                                <div style="font-size: 0.9rem"><strong>가격: <fmt:formatNumber
                                                        value="${product.price}" pattern="#,###,###"/>원</strong></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:if test="${products.prev}">
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach items="${products.pageList}" var="pageList">
                    <li class="page-item"><a class="page-link"href="<c:url value="/category/outer?page=${pageList}"/>">${pageList}</a></li>
                </c:forEach>
                <c:if test="${products.next}">
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

