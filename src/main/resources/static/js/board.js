$(document).ready(function(){
    $('button#write').click(function () {
        if($('div.login button').attr('id') === 'login') {
            $.login(true);
        } else {
            location.href = "/board/write";
        }
    });
    $('select#category').change($.reload);
    $('select#list-size').change($.reload);
});

$.reload = function () {
    let requrl = "/board?";
    let category = $('select#category').val();
    let listSize = $('select#list-size').val();

    if (category !== '전체보기') {
        requrl += "category=" + category + "&";
    }
    if (listSize !== '10') {
        requrl += "size=" + listSize + "&";
    }

    location.href = requrl;
};

