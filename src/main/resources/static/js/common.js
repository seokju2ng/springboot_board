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
    })

    document.body.appendChild(form);
    form.submit();
};
