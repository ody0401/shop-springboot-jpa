<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="content">
    <div class="container">
        <div class="ItemTitle">
            <h4>상품 등록</h4>
            <div class="line"></div>
        </div>

        <form:form modelAttribute="register" class="registrationForm" enctype="multipart/form-data"
                   action="/product/register">
            <div class="mb-3">
                <label for="productName" class="form-label">상품 이름</label>
                <form:input class="form-control" path="productName"/>
                <form:errors cssStyle="color: red" path="productName" />
            </div>
            <div class="mb-3">
                <label for="productPrice" class="form-label">가격(원)</label>
                <form:input min="0" type="number" class="form-control" path="productPrice"/>
                <form:errors cssStyle="color: red" path="productPrice" />
            </div>
            <div class="mb-3">
                <label for="productDescription" class="form-label">상세 내용</label>
                <form:textarea class="form-control" path="productDescription" cssStyle="resize: none"/>
                <form:errors cssStyle="color: red" path="productDescription" />
            </div>
            <div class="mb-3">
                <label for="productStockNumber" class="form-label">재고 수량</label>
                <form:input min="1" value="1" type="number" class="form-control" path="productStockNumber"/>
                <form:errors cssStyle="color: red" path="productStockNumber" />
            </div>
            <div class="mb-3">
                <div style="margin-bottom: 0.5rem">상품 분류</div>
                <form:select class="form-select" path="category" items="${categoryList}" itemValue="category"
                             itemLabel="label"/>
            </div>
            <div class="mb-3">
                <label for="picture" class="form-label">상품 이미지</label>
                <form:input type="file" path="picture" class="form-control" />
            </div>
            <div class="btnDiv">
                <button class="btn btn-dark" type="submit" id="registerBtn">상품등록</button>
                <button class="btn btn-secondary">돌아가기</button>
            </div>
        </form:form>
    </div>
</div>
<script>
    $(document).ready(()=> {

        const registrationForm = $(".registrationForm");

        $("#registerBtn").click(()=> {
            registrationForm.submit();
        })
    });
</script>

