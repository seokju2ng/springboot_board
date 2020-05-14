$(document).ready(function(){
   $('button.login').clickLogin();
});

$.fn.clickLogin = function() {
    $(this).click(async function () {
        let id = await $.swalLogin('text', '아이디를 입력하세요', '다음 &rarr;', '회원가입');
        if (id) {
            let pw = await $.swalLogin('password', '비밀번호를 입력하세요', '로그인', '비밀번호 찾기');
            if (pw) {
                console.log(id, pw);
                // login
            }
        }
    })
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
