$(document).ready(function () {
   $(".comment_info_button").click(function (event) {
       $('.recomment_form').remove();
       let targetId = event.target.id.split(' ');
       let parent = targetId[0];
       let from = targetId[1];
       let to = targetId[2];
       let nickname = $('.comment_writer_name').text();
       let recommentForm = ``
           + `<div class="comment_writer recomment_form">`
           + ` <div class="recomment_writer_name">${nickname}</div>`
           + ` <textarea class="recomment_write_input" placeholder="${to}님에게 답글" onkeydown="resize(this)"></textarea>`
           + ` <div class="comment_writer_button">`
           + `  <button class="button2" onclick="removeForm()">취소</button>`
           + `  <button class="button2" onclick="writeReply(${parent}, '${to}')">등록</button>`
           + ` </div>`
           + `</div>`;
       $('#comment_area'+from).append(recommentForm);
   })
});

function removeForm() {
    $('.recomment_form').remove();
}


$.btnClick1 = function (btn) {
    switch (btn.id) {
        case LOG_IN_BTN : $.login(''); break;
        case LOG_OUT_BTN : $.logout(); break;
        case JOIN_BTN : $.join(); break;
        case FIND_PWD_BTN : $.findPwd(); break;
        default : return;
    }
};

function resize(obj) {
    obj.style.height = "0px";
    obj.style.height = (10+obj.scrollHeight)+"px";
}

function viewArticle(articleNo) {
    location.href = "/board/"+articleNo;
}

function viewBoard() {
    location.href = "/board";
}

function writeReply(parent, to) {
    let content = $('.comment_write_input').val();
    let boardId = $('.article_wrap').attr('id');
    if (parent !== 0) {
        content = '<b>'+to+'</b> ' + $('.recomment_write_input').val();
    }
    console.log(boardId, parent, content);

    let form = document.createElement('form');
    form.setAttribute('method', 'post');
    form.setAttribute('action', '/board/write/reply');


    let param1 = document.createElement('input');
    param1.setAttribute('type', 'hidden');
    param1.setAttribute('name', 'boardId');
    param1.setAttribute('value', boardId);
    form.appendChild(param1);

    let param2 = document.createElement('input');
    param2.setAttribute('type', 'hidden');
    param2.setAttribute('name', 'parent');
    param2.setAttribute('value', parent);
    form.appendChild(param2);

    let param3 = document.createElement('input');
    param3.setAttribute('type', 'hidden');
    param3.setAttribute('name', 'content');
    param3.setAttribute('value', content);
    form.appendChild(param3);
    document.body.appendChild(form);
    form.submit();

}
