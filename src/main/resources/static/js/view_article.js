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