$.btnClick = function (btn) {
    if (btn.className === 'login') {
        $.login();
    } else {
        $.logout();
    }
}

$.logout = function () {
    $.ajax({
        url: "/member/logout",
        type: "GET",
        success: function(data) {
            if (data.result === 'SUCCESS') {
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    text: data.nick + '님 안녕히 가세요',
                    showConfirmButton: false,
                    timer: 1500
                }).then(function (){
                    location.href = "";
                })
            } else {
                Swal.fire('잘못된 접근입니다.');
            }
        }
    });
}

$.login = async function () {
    let id = await $.swalLogin('text', '아이디를 입력하세요', '다음 &rarr;', '회원가입');
    if (id) {
        let pwd = await $.swalLogin('password', '비밀번호를 입력하세요', '로그인', '비밀번호 찾기');
        if (pwd) {
            console.log(id, pwd);
            // login
            $.ajax({
                url: "/member/login",
                type: "POST",
                data: {id: id, pwd: pwd}
            }).then(function (data, status) {
                if (status === "success") {
                    switch (data.result) {
                        case "FAIL":
                            Swal.fire("로그인 실패", "아이디와 비밀번호를 다시 확인해주세요.", "error")
                                .then(function(){
                                    $.login();
                                });
                            break;
                        case "ADMIN":
                            Swal.fire('로그인 성공', '관리자님 안녕하세요.', 'success')
                                .then(function(){
                                    // 관리자 로그인시
                                });
                            break;
                        case "SUCCESS":
                            Swal.fire('로그인 성공', data.nick + '님 안녕하세요.', 'success')
                                .then(function(){
                                    $('button#login').attr('class', 'logout');
                                    $('button#login').text("로그아웃");
                                });
                            break;
                        default :
                            break;
                    }
                } else { // 요청 실패
                    Swal.fire("요청 실패");
                }
            })
        }
    }
}



$.swalLogin = async function (input, text, confirmButtonText, footer) {
    const {value: val} = await Swal.fire({
        title: '로그인',
        text: text,
        input: input,
        confirmButtonText: confirmButtonText,
        confirmButtonColor: '#ff7799',
        showCancelButton: true,
        cancelButtonText: '닫기',
        footer: '<a href>'+footer+'</a>',
        inputValidator: (value) => {
        return new Promise((resolve) => {
            if (value.trim() === '') {
        resolve('빈칸을 입력할 수 없습니다.');
    } else {
        resolve();
    }
});
}
});
    return val;
}
