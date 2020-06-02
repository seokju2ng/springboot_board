$(document).ready(function () {
    $('#btn-submit').click($.write);
    $('#btn-back').swalClickCancel("/board");
});

$.write = function () {
    Swal.fire({
        title: '글 작성',
        text: '이대로 작성하시겠습니까?',
        icon: "question",
        showCancelButton: true,
        cancelButtonText: '닫기',
        confirmButtonText: '등록',
        confirmButtonColor: '#ff7799'
    }).then(result => {
        if (result.value) {
        $.ajax({
            url: "/board/write",
            type: "POST",
            data: {
                category: document.write.category.value,
                title: defend(document.write.title.value),
                content: defend(document.write.content.value)
            },
            success: function (data) {
                switch (data.result) {
                    case SUCCESS :
                        Swal.fire({
                            title: '등록 완료',
                            text: '작성한 글이 등록되었습니다.',
                            icon: 'success',
                            confirmButtonColor: '#ff7799'
                        }).then(() => {
                            location.href = "/board";
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
}
