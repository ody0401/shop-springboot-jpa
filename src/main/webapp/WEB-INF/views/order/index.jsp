<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
    <div class="container">
        <div class="ItemTitle">
            <h4>주문내역</h4>
            <div class="line"></div>
        </div>
        <form>
            <table class="table cartTable">
                <thead>
                <tr>
                    <th scope="col">이미지</th>
                    <th scope="col">상품명</th>
                    <th scope="col">가격</th>
                    <th scope="col">수량</th>
                    <th scope="col">주문상태</th>
                    <th scope="col">주문취소</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty orders}">
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <td><a><img src="/images/${order.product.productImg.imgName}"></a></td>
                                <td>${order.product.productName}</td>
                                <td>${order.orderPrice}원</td>
                                <td>${order.count} 개</td>
                                <c:choose>
                                    <c:when test="${order.orderStatus eq 'ORDER'}">
                                        <td>상품준비중</td>
                                        <td>
                                            <button type="button" class="btn btn-dark" onclick="orderCancelBtn(${order.id})">취소</button>
                                        </td>
                                    </c:when>
                                    <c:when test="${order.orderStatus eq 'SHIP'}">
                                        <td>배송중</td>
                                        <td>
                                        </td>
                                    </c:when>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <th scope="row" colspan="6">주문 상품이 없습니다.</th>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div class="btnCart" style="text-align: center">
                <button class="btn btn-dark" onclick="self.location='/'">돌아가기</button>
            </div>
        </form>
    </div>
</div>

<script>

    const orderCancelBtn = (id) => {

        const data = { id };

        if(window.confirm("주문을 취소하시겠습니까?")) {
            $.ajax({
                method: "DELETE",
                contentType: "application/json",
                url: "/order/cancel",
                data: JSON.stringify(data),
            })
                .done((res) => {
                    console.log("ok");
                    location.reload();
                })
                .fail((request, status, error) => {
                    if (request.status === 400) {
                        console.log("실패");
                    }
                })
        }
    }


    $(() => {
        setTimeout(() => {
            console.log("첫 번째 메시지");
            $.ajax({
                method: "GET",
                contentType: "application/json",
                url: "/order/ship",
            })
                .done((res) => {
                    console.log("ok");
                })
                .fail((request, status, error) => {
                    if (request.status === 400) {
                        console.log("실패");
                    }
                })
        }, 5000);
    })
</script>
