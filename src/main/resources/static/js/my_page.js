$(document).ready(() => {
    $('span.page-num').eq(0).css('margin-left', '15px');
    $('span.page-num').eq($('span.page-num').length - 1).css('margin-right', '15px');
    $.setImage();

    $('button#write').click($.write);
    $('.content_subtitle.not_selected').click($.changeType);
    $('.shortcuts').click($.showArticle);
    // $('.profile_photo').click($.fileUpload);
});

$.setImage = () => {
    if ($('.profile_photo')[0].id === '') return;
    let id = $('.nick_area').attr('id');
    let middlePath = id.substr(3, id.length);
    let fileName = $('#imgValue').val();
    console.log(middlePath);
    let src = '/member/get-profile?middlePath=' + encodeURI(middlePath) + '&imageFileName=' + encodeURI(fileName);
    console.log(src);
    $('#profile').attr('src', src);
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
