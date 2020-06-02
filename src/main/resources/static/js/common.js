const DUPLICATE = -3;
const INVALID_APPROACH = -2;
const FAIL = -1;
const ADMIN = 0;
const SUCCESS = 1;

$.sendPost = function (action, objArray)
{
    let form = document.createElement('form');
    form.setAttribute('method', 'post');
    form.setAttribute('action', action);

    objArray.forEach(obj => {
        let param = document.createElement('input');
        param.setAttribute('type', 'hidden');
        param.setAttribute('name', obj.name);
        param.setAttribute('value', obj.value);
        form.appendChild(param);
    });

    document.body.appendChild(form);
    form.submit();
};

$.fn.swalClickCancel = function (backUrl) {
    $(this).click(function () {
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
                location.replace(backUrl);
            }
        });
    });
};

function defend(value) {
    value = value+"";
    value = value.trim();
    // value = value.replace(/</gi, "&lt;").replace(/>/gi, "&gt;");
    // value = value.replace(/\\(/gi, "& #40;").replace(/\\)/gi, "& #41;");
    value = value.replace(/'/gi, "&#39;");
    value = value.replace(/eval\\((.*)\\)/gi, "");
    value = value.replace(/[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']/gi, "\"\"");
    value = value.replace(/<script/gi, "");
    value = value.replace(/<\/script/gi, "");
    return value;
}
