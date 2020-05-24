$(document).ready(function () {
    $('#btn-submit').click($.modify);
    $('#btn-back').swalClickCancel("/board/"+$('.form').attr('id'));
});

$.modify = function () {
    let articleId = $('.form').attr('id');
    Swal.fire({
        title: '글 수정',
        text: '이대로 수정하시겠습니까?',
        icon: "question",
        showCancelButton: true,
        cancelButtonText: '닫기',
        confirmButtonText: '등록',
        confirmButtonColor: '#ff7799'
    }).then(result => {
        if (result.value) {
            $.ajax({
                url: "/board/modify",
                type: "POST",
                data: {
                    boardId: articleId,
                    category: document.modify.category.value,
                    title: document.modify.title.value,
                    content: document.modify.content.value
                },
                success: function (data) {
                    switch (data.result) {
                        case SUCCESS :
                            Swal.fire({
                                title: '수정 완료',
                                text: '수정이 완료되었습니다.',
                                icon: 'success'
                            }).then(() => {
                                location.href = "/board/" + articleId;
                            });
                            break;
                        case FAIL :
                            Swal.fire({
                                title: '유효성 에러',
                                text: '유효한 내용이 아닙니다.',
                                icon: 'error'
                            });
                            break;
                        case INVALID_APPROACH :
                            location.replace('/board');
                            break;
                    }
                }
            });
        }
    });
};
