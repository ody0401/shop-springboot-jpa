<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${not empty errorMessage}">
    <script>
        alert("${errorMessage}");
    </script>
</c:if>

<div class="content">
    <div class="container">
        <div class="ItemTitle">
            <h4>회원가입</h4>
            <div class="line"></div>
        </div>
        <form:form modelAttribute="member" class="signupForm" method="post" action="/member/signup">
            <div class="mb-3">
                <label for="userName" class="form-label">이름</label>
                <form:input class="form-control" path="userName" placeholder="name"/>
                <form:errors cssStyle="color: red" path="userName" />
            </div>
            <div class="mb-3">
                <label for="userId" class="form-label">이메일</label>
                <form:input type="email" class="form-control" path="userId" placeholder="email@example.com"/>
                <form:errors cssStyle="color: red" path="userId" />
            </div>
            <div class="mb-3">
                <label for="userPw" class="form-label">비밀번호</label>
                <form:password class="form-control" path="userPw" placeholder="password"/>
                <form:errors cssStyle="color: red" path="userPw" />
            </div>
            <div class="mb-3">
                <label for="userPwCheck" class="form-label">비밀번호 확인</label>
                <form:password class="form-control" path="userPwCheck" placeholder="passwordCheck" />
                <form:errors cssStyle="color: red" path="userPwCheck" />
                <div style="color: red"><c:out value="${notEqual}" /></div>
            </div>
            <div class="btnDiv">
                <button class="btn btn-dark" id="signupBtn">회원가입</button>
                <button class="btn btn-secondary">돌아가기</button>
            </div>
        </form:form>
    </div>
</div>
<script>
    $(document).ready(()=> {

        const signupForm = $(".signupForm");

        $("#signupBtn").click(()=> {
            signupForm.submit();
        })
    });
</script>
