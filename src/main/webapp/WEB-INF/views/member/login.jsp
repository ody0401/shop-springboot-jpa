<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="content">
    <div class="container">
        <div class="ItemTitle">
            <h4>로그인</h4>
            <div class="line"></div>
        </div>
        <form class="loginForm needs-validation" novalidate method="post" action="/member/login">
            <div class="mb-3 row">
                <label for="inputEmail" class="col-sm-2 col-form-label">이메일</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control" id="inputEmail" name="username" required>
                    <div class="invalid-feedback">
                        이메일을 입력해주세요.
                    </div>
                </div>
            </div>
            <div class="mb-3 row">
                <label for="inputPassword" class="col-sm-2 col-form-label">패스워드</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="inputPassword" name="password" required>
                    <div class="invalid-feedback">
                        패스워드를 입력해주세요.
                    </div>
                </div>
            </div>
            <div class="btnLoginForm">
                <button class="btn btn-dark" id="loginBtn">로그인</button>
                <button class="btn btn-secondary">돌아가기</button>
            </div>
        </form>
    </div>
</div>
<script>
    $(document).ready(() => {
        const loginForm = $(".loginForm");

        $("#loginBtn").click(() => {
            loginForm.submit();
        })

    })
</script>
