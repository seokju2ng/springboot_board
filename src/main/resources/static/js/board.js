$(document).ready(function(){
    $('span.page-num').eq(0).css('margin-left', '15px');
    $('span.page-num').eq($('span.page-num').length - 1).css('margin-right', '15px');
    $.setImage();

    $('button#write').click($.write);
    $('select#category').change($.reload);
    $('select#list-size').change($.reload);
    $('table.board tr td:nth-child(2)').click($.showBoard);
    $('td.member').click($.showMemberInfo);
});

$.showMemberInfo = function () {
    let mid = $(this).closest('td').attr('id').substr(1);
    location.href = "/mypage?id="+mid;
};

$.showBoard = function (event) {
    let idx = event.target.id;
    location.href = "/board/"+idx;
};

$.setImage = () => {
    let profiles = $('.profile_photo');
    for (let i = 0; i < profiles.length; i++) {
        let value = profiles[i].parentElement.children[1].value.split(':');
        let middlePath = value[0];
        let fileName = value[1];
        let src = '/member/get-profile?middlePath=' + encodeURI(middlePath) + '&imageFileName=' + encodeURI(fileName);
        profiles[i].src = src;
    }
};


$.prev = function (startPage) {
    $.reload(startPage - 1);
};

$.next = function (endPage) {
    $.reload(endPage + 1);
};

$.write = function () {
    if($('div.login button').attr('id') === 'login') {
        $.login('/board/write');
    } else {
        location.href = "/board/write";
    }
};

$.reload = function (page) {
    let requrl = "/board?";
    let category = $('select#category').val();
    let listSize = $('select#list-size').val();

    if (category !== '전체보기') {
        requrl += "category=" + category + "&";
    }
    if (listSize !== '10') {
        requrl += "size=" + listSize + "&";
    }
    if (typeof page !== "object") {
        requrl += "page=" + page;
    }
    location.href = requrl;
};

