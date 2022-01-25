<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:if test="${not empty error}">
    <script>
        alert("${error}");
        self.location = "/";
    </script>
</c:if>

<div class="content">
    <div class="container detailArea" style="text-align: center; height: 100%; padding:50px">
        <div style="float: left; overflow: hidden; width: 50%">
            <div class="thumbnail">
                <img style="width: 400px; height: 500px" src="/images/${product.productImgUrl}">
            </div>
        </div>
        <div style="float: right; width: 50%; padding-right: 50px">
            <table class="table">
                <thead>
                <tr>
                    <th style="width: 30%">${product.productName}</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">판매가</th>
                    <td><fmt:formatNumber value="${product.price}" pattern="##,###"/> 원</td>
                </tr>
                <tr>
                    <th scope="row">배송방법</th>
                    <td>택배</td>
                </tr>
                <tr>
                    <th scope="row">배송비</th>
                    <td>2,500 원</td>
                </tr>
                <tr>
                    <th scope="row">상품 설명</th>
                    <td>${product.productDetail}</td>
                </tr>
                <tr>
                    <th scope="row">상품 주문</th>
                    <c:choose>
                        <c:when test="${'SELL' eq product.status}">
                            <td>가능</td>
                        </c:when>
                        <c:otherwise>
                            <td>품절</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <th scope="row">수량</th>
                    <td><input min="1" value="1" style="width: 30%; height: 28px" type="number"
                               class="form-control productCount"/></td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="2">총 상품금액: <input
                            id="totalPrice"
                            style="width: 20%;text-align: right; border: none; outline: none; font-weight: bold"
                            value=""
                            readonly>원
                    </th>
                </tr>
                </tfoot>
            </table>
            <div>
                <button class="btn btn-dark" onclick="buyNowBtn(${product.productId})">바로구매</button>
                <button class="btn btn-secondary" id="addCart">장바구니</button>
            </div>
        </div>
    </div>
</div>

<script>

    const buyNowBtn = (id) => {
        const count = $(".productCount").val();
        const data = {id, count}
        $.ajax({
            method: "POST",
            contentType: "application/json",
            url: "/order/test",
            data: JSON.stringify(data),
        })
            .done((res) => {
                alert("등록이 완료되었습니다.");
                if(confirm("주문 내역을 확인하시겠습니까?")){
                    location.href="/order"
                }
            })
            .fail((request, status, error) => {
                if(request.status === 401) {
                    alert("권한이 없습니다.");
                } else if(request.status === 400) {
                    alert("제품 수량이 부족합니다. 수량을 줄여주세요.");
                }
            })

    }


    $(document).ready(() => {

        const countId = $(".productCount");
        const totalPrice = $("#totalPrice");
        const countVal = countId.val();
        const total = countVal * ${product.price} + 2500;
        totalPrice.val(total.toLocaleString());

        countId.click(() => {
            const countVal = countId.val();

            const total = countVal * ${product.price} + 2500;

            totalPrice.val(total.toLocaleString());

        })

        $("#addCart").click(() => {
            const count = countId.val();
            const productId = ${product.productId};
            const data = {
                count, productId
            }

            if (count.length === 0 || Number(count) < 1) {
                alert("수량을 선택해주세요.");
            } else {
                <sec:authorize access="isAuthenticated()">
                if(window.confirm("장바구니에 등록하시겠습니까?")) {
                    $.ajax({
                        method: "POST",
                        contentType: "application/json",
                        url: "/cart/addCart",
                        data: JSON.stringify(data),
                    })
                        .done((res) => {
                            alert("등록이 완료되었습니다.");
                            if(confirm("장바구니로 이동하시겠습니까?")){
                                location.href="/cart";
                            }
                        })
                        .fail((request, status, error) => {
                            if(request.status === 401) {
                                alert("권한이 없습니다.");
                            } else if(request.status === 400) {
                                alert("수량이 부족하여 장바구니에 추가할 수 없습니다.");
                            }
                        })
                }
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                alert("로그인 후 가능합니다.");
                </sec:authorize>
            }
        })
    })
</script>
