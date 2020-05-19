$(document).ready(function () {
    $('#btn-submit').click($.write);
    $('#btn-back').click(function () {
        Swal.fire({
            title: '취소',
            text: '정말로 취소하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            cancelButtonText: '아니오',
            confirmButtonText: '예',
            confirmButtonColor: '#ff7799'
        }).then(result => {
            if (result.value) {
                location.href = "/board";
            }
        })
    });
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
                title: document.write.title.value,
                content: document.write.content.value
            },
            success: function (data) {
                switch (data.result) {
                    case SUCCESS :
                        Swal.fire({
                            title: '등록 완료',
                            text: '작성한 글이 등록되었습니다.',
                            icon: 'success'
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
                            Swal.fire({
                                title: '접근 에러',
                                text: '잘못된 접근입니다.',
                                icon: 'error'
                            }).then(() => {
                                location.href = "/board";
                    });
                        break;
                    }
                }
            });
        }
    });
}
