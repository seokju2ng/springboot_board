const LOG_IN_BTN = 'login';
const LOG_OUT_BTN = 'logout';
const JOIN_BTN = 'join';
const FIND_PWD_BTN = 'find-pwd';
const MY_PAGE = 'mypage';
const ADMIN_BTN = 'admin';

$.btnClick = function (btn) {
    let func = null;
    switch (btn.id) {
        case LOG_IN_BTN :
            func = $.login; break;
        case LOG_OUT_BTN :
            func = $.logout; break;
        case JOIN_BTN :
            func = $.join; break;
        case FIND_PWD_BTN :
            func = $.findPwd; break;
        case MY_PAGE :
            func = $.mypage; break;
        case ADMIN_BTN :
            func = $.admin; break;
        default : return;
    }
    func();
};

$.admin = function () {
    // location.href = '/admin';
    Swal.fire({
        input: 'text',
        title: '카테고리 등록',
        text: '등록할 카테고리명을 입력하세요',
        confirmButtonText: '등록',
        confirmButtonColor: '#ff7799',
        showCancelButton: true,
        cancelButtonText: '닫기',
    }).then((data) => {
        if (data.value) {
            $.ajax({
                url: '/admin/add-category',
                type: 'GET',
                data: {category: data.value}
            }).then((data) => {
                if (data.result === SUCCESS) {
                    location.reload(true);
                }
            })
        }
    })
};

$.mypage = function () {
    location.href = '/mypage';
};

$.join = async function () {
    let inputId = await $.inputId();
    if (inputId === undefined) return;
    $.ajax({
        url: '/member/check-duplicate',
        type: 'GET',
        data: {id : inputId},
        success: (data) => $.duplicateResult(data, inputId)
    });
};

$.duplicateResult = async function (data, inputId) {
    if (data.result === DUPLICATE) {
        Swal.fire({
            title: "아이디 중복",
            text: "다른 아이디를 입력해주세요",
            icon: "error",
            confirmButtonColor: '#ff7799'
        }).then($.join);
    } else {
        let inputPwd = await $.inputPwd();
        if (inputPwd === undefined) return;
        let inputEmail = await $.inputEmail();
        if (inputEmail === undefined) return;
        let inputNick = await $.inputNickname();
        if (inputNick === undefined) return;
        $.ajax({
            url: '/member/join',
            type: 'POST',
            data: {id: inputId, pwd: inputPwd, email: inputEmail, nick: inputNick},
            success: $.joinResult
        })
    }
};

$.joinResult = function (data) {
    if (data.result === SUCCESS) {
        Swal.fire({
            title: '회원가입 완료',
            text: '로그인 창으로 이동합니다',
            icon: 'success',
            confirmButtonColor: '#ff7799'
        }).then(()=>$.login());
    } else {
        Swal.fire({
            title: '가입 실패',
            text: '회원가입에 실패하였습니다',
            icon: 'error',
            confirmButtonColor: '#ff7799'
        });
    }
};

$.warningMessage = async function(title, content) {
    await Swal.fire({
        title: title,
        text: content,
        icon: "warning",
        confirmButtonColor: '#ff7799'
    });
};

$.inputId = async function () {
    let inputId = await $.swalInput('text', '회원가입', '사용할 아이디를 입력하세요', '다음 &rarr;');
    if (inputId === undefined) return undefined;
    inputId = defend(inputId);
    let idRegExp = /^[a-zA-Z][0-9a-zA-Z_-]{5,19}$/;
    if (!idRegExp.test(inputId)) {
        await $.warningMessage('아이디 유효조건', '영문으로 시작 / 영문, 숫자, 특문(-,_) 가능 / 6-20자');
        return await $.inputId();
    }
    return inputId;
};

$.inputNickname = async function () {
    let inputNick = await $.swalInput('text', '회원가입', '닉네임을 입력하세요', '회원가입');
    if (inputNick === undefined) return undefined;
    inputNick = defend(inputNick);
    let nickRegExp = /^[^\t\r\n\v\f]{2,15}$/;   // 모든 문자 2-15자
    if (!nickRegExp.test(inputNick)) {
        await $.warningMessage('닉네임 유효조건', '닉네임은 2-15자 입니다.');
        return await $.inputNickname();
    }
    return inputNick;
};

$.inputEmail = async function () {
    let inputEmail = await $.swalInput('email', '회원가입', '이메일 주소를 입력하세요', '다음 &rarr;');
    if (inputEmail === undefined) return undefined;
    inputEmail = defend(inputEmail);
    let emailRegExp = /^[a-z0-9_]{5,20}@[a-z]{2,15}\.(com|net|co.kr|ac.kr|kr)$/;
    if (!emailRegExp.test(inputEmail)) {   // 이메일 유효성 정규 표현식
        await $.warningMessage('이메일 유효조건', '이메일 형식으로 입력해주세요!');
        return await $.inputEmail();
    }
    return inputEmail;
};

$.inputPwd = async function () {
    let inputPwd = await $.swalTwoInputs('password', '회원가입', '비밀번호를 입력하세요', '비밀번호', '비밀번호 확인', '다음 &rarr;', '$.enter(this)');
    if (inputPwd === undefined) return undefined;
    if (inputPwd[0] !== inputPwd[1]) {
        await $.warningMessage('비밀번호 불일치', '입력하신 두 비밀번호가 일치하지 않습니다!');
        return await $.inputPwd();
    }
    let pwdRegExp = /^[a-zA-Z0-9~!#$^&*?]{6,20}$/;
    if (inputPwd[0].trim() === "" || !pwdRegExp.test(inputPwd[0])) {
        await $.warningMessage('비밀번호 유효조건','영어, 숫자, 특수문자(~!#$^&*?) 가능 / 6-20자');
        return await $.inputPwd();
    }
    return inputPwd[0];
};

$.findPwd = function () {
    Swal.fire('find');
};

$.enter = function (input) {
    if (window.event.keyCode == 13) {
        switch (input.id) {
            case "swal-input1":
                $('#swal-input2').focus();
                break;
            case "swal-input2":
                $('.swal2-confirm').trigger('click');
                break;
        }
    }
};

$.logout = function () {
    $.ajax({
        url: "/member/logout",
        type: "GET",
        success: function(data) {
            switch (data.result) {
                case SUCCESS:
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: '로그아웃',
                        text: data.nick + '님 안녕히 가세요',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(function (){
                        // location.replace("/board");
                        location.reload(true);
                    });
                    break;
                case INVALID_APPROACH:
                    Swal.fire('잘못된 접근입니다.');
                    break;
            }
        }
    });
};

$.login = async function (where) {
    let join = '<span id="join" class="popup_footer" onclick="$.btnClick(this)">회원가입</span>';
    let findPwd = '<span id="find-pwd" class="popup_footer" onclick="$.btnClick(this)">비밀번호 찾기</span>'

    let id = await $.swalInput('text', '로그인', '아이디를 입력하세요', '다음 &rarr;', join);
    if (id === undefined) return;
    let pwd = await $.swalInput('password', '로그인', '비밀번호를 입력하세요', '로그인', findPwd);
    if (pwd === undefined) return;

    $.ajax({
        url: "/member/login",
        type: "POST",
        data: {id: id, pwd: pwd},
        success: function (data){
            switch (data.result) {
                case FAIL:
                    Swal.fire({
                        title: "로그인 실패",
                        text: "아이디와 비밀번호를 다시 확인해주세요.",
                        icon: "error",
                        confirmButtonColor: '#ff7799'
                    }).then($.login);
                    break;
                case ADMIN:
                    Swal.fire('로그인 성공', '관리자님 안녕하세요.', 'success')
                        .then(function(){
                            location.reload(true);
                        });
                    break;
                case SUCCESS:
                    console.log(where);
                    Swal.fire({
                        title: '로그인 성공',
                        text: data.nick + '님 안녕하세요.',
                        icon: 'success',
                        confirmButtonColor: '#ff7799'
                    }).then(() => {
                        if (where !== undefined)
                            location.href = where;
                        else
                            location.reload(true);
                    });
                    break;
                default :
                    break;
            }
        }
    });
};

$.swalInput = async function (input, title, text, confirmButtonText, footer) {
    const {value : val} = await Swal.fire({
        input: input,
        title: title,
        text: text,
        confirmButtonText: confirmButtonText,
        confirmButtonColor: '#ff7799',
        showCancelButton: true,
        cancelButtonText: '닫기',
        footer: footer,
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
};

$.swalTwoInputs = async function (input, title, text, placeholder1, placeholder2, confirmButtonText, enter) {
    const {value : val} = await Swal.fire({
        title: title,
        html:
            `<div class="swal2-html-container" style="display:block;">${text}</div>` +
            `<input id="swal-input1" onkeypress="${enter}" type="${input}" class="swal2-input" placeholder="${placeholder1}">` +
            `<input id="swal-input2" onkeypress="${enter}" type="${input}" class="swal2-input" placeholder="${placeholder2}">`,
        focusConfirm: false,
        confirmButtonText: confirmButtonText,
        confirmButtonColor: '#ff7799',
        showCancelButton: true,
        cancelButtonText: '닫기',
        preConfirm: () => {
            return [
                $('#swal-input1').val(),
                $('#swal-input2').val()
            ]
        }
    });
    return val;
};
