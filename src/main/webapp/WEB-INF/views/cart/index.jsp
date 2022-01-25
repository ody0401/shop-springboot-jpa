<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
    <div class="container">
        <div class="ItemTitle">
            <h4>장바구니</h4>
            <div class="line"></div>
        </div>
        <table class="table cartTable">
            <thead>
            <tr>
                <th scope="col"><input class="form-check-input" type="checkbox" value="" disabled></th>
                <th scope="col">이미지</th>
                <th scope="col">상품명</th>
                <th scope="col">가격</th>
                <th scope="col">수량</th>
                <th scope="col">삭제</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
            <c:when test="${not empty carts}">
            <c:forEach items="${carts}" var="cart">
            <tr>
                <th scope="row"><input class="form-check-input" type="checkbox" name="checkId"
                                       id = ${cart.id}
                                       value="${cart.product.price * cart.count}">
                </th>
                <td><a><img src="/images/${cart.productImg}"></a></td>
                <td>${cart.product.productName}</td>
                <td>${cart.product.price * cart.count}원</td>
                <td>${cart.count}개</td>
                <td>
                    <input type="button" class="btn btn-dark delBtn" onclick="deleteBtn(${cart.id})"
                           value="삭제하기"/>
                </td>
            </tr>
            </c:forEach>
            <tfoot style="text-align: right">
            <td colspan="6">상품구매금액 <input
                    id="buyPrice"
                    style="width : 10%;text-align: right; border: none; outline: none; font-weight: bold"
                    value="0"
                    readonly> 원+
                배송비 2,500 = 합계: <input
                        id="buyTotalPrice"
                        style="width : 10%;text-align: right; border: none; outline: none; font-weight: bold"
                        value="0"
                        readonly>원
            </td>
            </tfoot>
            </c:when>
            <c:otherwise>
                <tr>
                    <th scope="row" colspan="6">장바구니가 비어있습니다.</th>
                </tr>
            </c:otherwise>
            </c:choose>
            </tbody>
        </table>
        <div class="btnCart">
            <button type="button" onclick="selectBuyBtn()" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop">선택구매
            </button>
            <button type="button" class="btn btn-secondary" onclick="allBuyBtn()" data-bs-toggle="modal"
                    data-bs-target="#staticBackdrop">전부구매
            </button>
            <button type="button" class="btn btn-light" onclick="self.location='/'">둘러보기</button>
        </div>

        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">상품 구매 정보</h5>
                    </div>
                    <div class="modal-body">
                        총 가격: <input
                            id="buyModal"
                            style="width : 30%;text-align: right; border: none; outline: none; font-weight: bold"
                            value="0"
                            readonly>원
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="buyNow" value="0">바로구매</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="buyCancel()">취소하기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    const allBuyBtn = () => {
        let totalPrice = 0;
        $("input[name=checkId]").each(function () {
            console.log($(this).val());
            totalPrice += parseInt($(this).val());
        })
        $("#buyModal").val((totalPrice + 2500).toLocaleString());
        $("#buyNow").val(1);
    }

    const buyCancel = () => {
        $("#buyModal").val(0);
    }

    const selectBuyBtn = () => {
        $("#buyModal").val($("#buyTotalPrice").val());
        $("#buyNow").val(2);
    }

    $(() => {

        $("input[type=checkbox]").change(function () {

            let totalPrice = 0;

            $("input[name = checkId]:checked").each(function () {
                console.log($(this).val());
                totalPrice += parseInt($(this).val());
            })

            if (totalPrice === 0) {
                $("#buyPrice").val(totalPrice);
                $("#buyTotalPrice").val(0);
                $("#buyModal").val(0);
            } else {
                $("#buyPrice").val(totalPrice.toLocaleString());
                $("#buyTotalPrice").val((totalPrice + 2500).toLocaleString());
                $("#buyModal").val((totalPrice + 2500).toLocaleString());
            }
        })

        $("#buyNow").click((e) => {
            console.log( $("#buyModal").val());
            console.log($(e.currentTarget).val());
            console.log(typeof $(e.currentTarget).val());
            const option = $(e.currentTarget).val();

            if (option === "1") {

                $.ajax({
                    method: "POST",
                    contentType: "application/json",
                    url: "/order/all",
                })
                    .done((res) => {
                        console.log("ok");
                        location.href="/order"
                    })
                    .fail((request, status, error) => {
                        if (request.status === 401) {
                            alert("권한이 없습니다.");
                        }
                    })

            } else if (option === "2") {

                let data = {
                    "id": []
                }

                $("input[name = checkId]:checked").each(function () {
                    console.log($(this).attr('id'));
                    const cart = $(this).attr('id');
                    const id = {"id": cart}
                    data["id"].push(id);
                })

                console.log("배열 : " + data);
                console.log("배열 stringify : " + JSON.stringify(data));

                $.ajax({
                    method: "POST",
                    contentType: "application/json",
                    url: "/order/some",
                    data: JSON.stringify(data),
                })
                    .done((res) => {
                        console.log("ok");
                        location.href="/order"
                    })
                    .fail((request, status, error) => {
                        if (request.status === 401) {
                            alert("권한이 없습니다.");
                        }
                    })

            }

        })
    })

    const deleteBtn = (id) => {
        const data = {
            id
        }

        $.ajax({
            method: "DELETE",
            contentType: "application/json",
            url: "/cart/delete",
            data: JSON.stringify(data),
        })
            .done((res) => {
                console.log("ok");
                location.reload();
            })
            .fail((request, status, error) => {
                if (request.status === 401) {
                    alert("권한이 없습니다.");
                }
            })
    }
</script>

