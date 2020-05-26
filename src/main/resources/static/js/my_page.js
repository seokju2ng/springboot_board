$(document).ready(() => {
    $('span.page-num').eq(0).css('margin-left', '15px');
    $('span.page-num').eq($('span.page-num').length - 1).css('margin-right', '15px');

    $('button#write').click($.write);
    $('.content_subtitle.not_selected').click($.changeType);
    $('.shortcuts').click($.showArticle);
});

$.home = function () {
    location.href = "/board";
}


$.showArticle = function (event) {
    location.href = '/board/' + event.target.id;
};

$.changeType = function (event) {
    location.href = '/mypage?type=' + event.target.id;
};

$.write = function () {
    if($('div.login button').attr('id') === 'login') {
        $.login('/board/write');
    } else {
        location.href = "/board/write";
    }
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
    location.href = requrl;
};
