$(document).ready(function () {
    // $('.article_profile').click($.setImage);
    $.setWriterImage();
    $.setCommentImages();
    // $('.article_profile').click($.setCommentImages);
    $(".comment_info_button").click(showReplyWriteForm);
    $('.like_article').click($.clickLikeButton);
});

$.setWriterImage = () => {
    if ($('.article_profile')[0].id === '') return;
    let value = $('#article_value').val().split(':');
    let middlePath = value[0];
    let fileName = value[1];
    let src = '/member/get-profile?middlePath=' + encodeURI(middlePath) + '&imageFileName=' + encodeURI(fileName);
    console.log(src);
    $('#article_writer_profile').attr('src', src);
};

$.setCommentImages = () => {
    let profiles = $('.writer_profile');
    // console.log(profiles);
    for (let i = 1; i < profiles.length; i++) {
        // console.log(profiles[i].id);
        if (profiles[i].id === '') continue;
        let id = profiles[i].id.substr(7, profiles[i].id.length);
        let value = $('#imgValue' + id).val().split(':');
        let middlePath = value[0];
        let fileName = value[1];
        let src = '/member/get-profile?middlePath=' + encodeURI(middlePath) + '&imageFileName=' + encodeURI(fileName);
        $('#profile' + id).attr('src', src);
    }
};

$.clickLikeButton = function () {
    if ($('.comment_writer').length === 0) return;
    $.postLike(function (data) {
        if (data.result === SUCCESS) {
            if ($('.heart').attr('id') === 'on')
                heartChange('off', 'empty', -1);
            else
                heartChange('on', 'full', 1);
        }
    });
};

function heartChange(id, src, num) {
    $('.heart').attr('id', id);
    $('.heart').attr('src', '/static/img/heart_' + src + '.png');
    $('#likes').text($('#likes').text() * 1 + num);
}

$.postLike = function (successFunc) {
    let articleId = $('.article_wrap').attr('id');
    let flag = $('.heart').attr('id') === 'on' ? 'OFF' : 'ON';
    $.ajax({
        url: '/board/like',
        type: 'POST',
        data: { boardId: articleId, flag: flag },
        success: successFunc
    });
};

function showReplyWriteForm(event) {
    $('.recomment_form').remove();
    let targetId = event.target.id.split(' ');
    let parent = targetId[0];
    let from = targetId[1];
    let to = targetId[2];
    let nickname = $('.comment_writer_name').text();
    let recommentForm = ``
        + `<div class="comment_writer recomment_form">`
        + ` <div class="recomment_writer_name">${nickname}</div>`
        + ` <textarea class="recomment_write_input" placeholder="${to}님께 답글쓰기" onkeydown="resize(this)"></textarea>`
        + ` <div class="comment_writer_button">`
        + `  <button class="button2" onclick="removeForm()">취소</button>`
        + `  <button class="button2" onclick="writeReply(${parent}, '${to}')">등록</button>`
        + ` </div>`
        + `</div>`;
    $('#comment_area'+from).append(recommentForm);
    $('.recomment_write_input').focus();
}

function removeForm() {
    $('.recomment_form').remove();
}

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
    let objArray = [
        { name: 'content', value: content },
        { name: 'parent', value: parent},
        { name: 'boardId', value: boardId },
    ];
    $.sendPost('/board/write/reply', objArray);
}

function deleteReply(parent, replyId) {
    let boardId = $('.article_wrap').attr('id');
    Swal.fire({
        title: '댓글 삭제',
        text: '정말로 삭제하시겠습니까?',
        icon: 'question',
        confirmButtonText: "삭제",
        confirmButtonColor: '#ff7799',
        showCancelButton: true,
        cancelButtonText: '취소'
    }).then(data => {
        if (data.value) {
            let objArray = [
                { name: 'replyId', value: replyId },
                { name: 'parent', value: parent},
                { name: 'boardId', value: boardId },
            ];
            $.sendPost('/board/delete/reply', objArray);
        }
    })
}

function modifyArticle() {
    let boardId = $('.article_wrap').attr('id');
    location.href = '/board/modify/'+boardId;
}

function deleteArticle() {
    let boardId = $('.article_wrap').attr('id');
    Swal.fire({
        title: '글 삭제',
        text: '정말로 삭제하시겠습니까?',
        icon: 'warning',
        confirmButtonText: "삭제",
        confirmButtonColor: '#ff7799',
        showCancelButton: true,
        cancelButtonText: '취소'
    }).then(data => {
        if (data.value) {
            let objArray = [{ name: 'boardId', value: boardId }];
            $.sendPost('/board/delete', objArray);
        }
    });
}
