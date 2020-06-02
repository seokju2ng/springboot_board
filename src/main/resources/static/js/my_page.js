$(document).ready(() => {
    $('span.page-num').eq(0).css('margin-left', '15px');
    $('span.page-num').eq($('span.page-num').length - 1).css('margin-right', '15px');

    $('button#write').click($.write);
    $('.content_subtitle.not_selected').click($.changeType);
    $('.shortcuts').click($.showArticle);
    $('.view_profile').click($.showImage);
    $('.btn_default_profile').click($.setDefaultProfile);
});

$.setDefaultProfile = function () {
    Swal.fire({
        title: '프로필 사진',
        text: '프로필 사진을 기본 사진으로 변경하시겠습니까?',
        icon: 'question',
        confirmButtonColor: '#ff7799',
        confirmButtonText: '예',
        showCancelButton: true,
        cancelButtonText: '아니오'
    }).then(data => {
        if (data.value) {
            let id = $('.nick_area').attr('id');
            id = id.substr(3);
            $.ajax({
                url: '/mypage/set-default-profile',
                type: 'GET',
                data: {id: id},
                success: (data) => {
                    switch (data.result) {
                        case SUCCESS:
                            Swal.fire({
                                title: "프로필 변경",
                                text: "프로필 변경에 성공하였습니다!",
                                icon: 'success',
                                confirmButtonColor: '#ff7799'
                            }).then(()=>{
                                location.reload(true);
                            });
                            break;
                        case FAIL:
                            Swal.fire('프로필 변경', '변경에 실패하였습니다.', 'error');
                            break;
                        case INVALID_APPROACH:
                            Swal.fire('프로필 변경', '접근할 수 없음', 'error');
                            break;
                    }
                }
            })
        }
    });
};

$.showImage = function (event) {
    let id = event.target.src.split("=")[1].split('&')[0];
    let url = '/mypage/show-profile?id=' + id;
    window.open(url,"new", "width=500, height=500, top=150, left=150, resizable=no");
};

$.fileUpload = async function () {
    const { value: file } = await Swal.fire({
        title: '프로필 사진',
        input: 'file',
        confirmButtonColor: '#ff7799',
        inputAttributes: {
            'accept': 'image/*',
            'aria-label': 'Upload your profile picture'
        }
    });

    if (file) {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = (e) => {
            Swal.fire({
                title: '이 사진을 등록하시겠습니까?',
                imageUrl: e.target.result,
                imageAlt: 'The uploaded picture',
                confirmButtonColor: '#ff7799',
                confirmButtonText: '등록',
                showCancelButton: true,
                cancelButtonText: '취소'
            }).then(data => {
                if (data.value) {
                    // console.log(reader);
                    // console.log(file);
                    let formData = new FormData();
                    formData.append("file", file);
                    // console.log(formData);
                    $.ajax({
                        url: '/mypage/set-profile',
                        type: 'POST',
                        processData: false,
                        contentType: false,
                        data: formData,
                        enctype: 'multipart/form-data',
                        dataType: 'json',
                        success: uploadSuccess
                    });
                }
            })
        };
    }
};

function uploadSuccess (data) {
    console.log(data);
    switch (data.result) {
        case SUCCESS:
            Swal.fire({
                title: "프로필 변경",
                text: "프로필 변경에 성공하였습니다!",
                icon: 'success',
                confirmButtonColor: '#ff7799'
            }).then(()=>{
                location.reload(true);
            });
            break;
        case FAIL:
            Swal.fire('프로필 변경', '변경에 실패하였습니다.', 'error');
            break;
        case INVALID_APPROACH:
            Swal.fire('프로필 변경', '접근할 수 없음', 'error');
            break;
    }
};

$.home = function () {
    location.href = "/board";
};

$.showArticle = function (event) {
    location.href = '/board/' + event.target.id;
};

$.changeType = function (event) {
    let url = '/mypage?type=' + event.target.id;
    if (!isMypage) {
        let id = $('.nick_area').attr('id');
        id = id.substr(3, id.length);
        url += ('&id=' + id);
    }
    location.href = url;
};

$.write = function () {
    location.href = "/board/write";
};

$.prev = function (startPage) {
    $.reload(startPage - 1);
};

$.next = function (endPage) {
    $.reload(endPage + 1);
};

$.reload = function (page) {
    let type = $('.content_subtitle.selected').attr('id');
    let requrl = "/mypage?type=" + type + "&page=" + page;
    if (!isMypage) {
        let id = $('.nick_area').attr('id');
        id = id.substr(3, id.length);
        requrl += ('&id=' + id);
    }
    location.href = requrl;
};
