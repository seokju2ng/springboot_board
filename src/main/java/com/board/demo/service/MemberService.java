package com.board.demo.service;

import com.board.demo.vo.Member;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface MemberService {
    List<Member> getList();
    Member login(String id, String pwd) throws NoSuchAlgorithmException;
    boolean isDuplicate(String id);
    Member join(String id, String pwd, String email, String nick) throws NoSuchAlgorithmException;
    boolean setProfilePhoto(long memberId, String profilePath);
}
