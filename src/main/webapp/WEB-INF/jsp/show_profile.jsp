<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${mypage.nickname}님의 프로필 사진</title>
    <style>
        body {  text-align:center;  margin: 0;  }
        img {  max-width: 500px;  }
    </style>
</head>
<body oncontextmenu='return false' onselectstart='return false' ondragstart='return false' onclick="self.close();">
<img src="/member/get-profile?middlePath=${mypage.memberId}&imageFileName=${mypage.profilePhoto}">
</body>
</html>
