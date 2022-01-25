<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="component">
    <div class="content">
        <div class="container">
            <div class="ItemTitle">
                <h4>상품 검색</h4>
                <div class="line"></div>
            </div>
            <div>
                <div class="productMenu">
                    <span style="line-height: 5px"><c:choose>
                        <c:when test="${not empty size}"><strong>Total: ${size} 개</strong></c:when>
                        <c:otherwise><strong>Total: 0 개</strong></c:otherwise>
                    </c:choose></span>
                </div>
            </div>
            <div class="productList" style="margin-top: 15px;">
                <ul>
                    <c:choose>
                        <c:when test="${not empty products}">
                            <c:forEach var="product" items="${products}">
                                <li>
                                    <div class="card" style="width: 18rem;">
                                        <img src="/images/${product.productImg.imgName}" class="card-img-top" alt="...">
                                        <div class="card-body">
                                            <p class="card-text"><strong>${product.productName}
                                            <div><fmt:formatNumber
                                                value="${product.price}" pattern="#,###,###"/> 원</div></strong></p>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
</div>

