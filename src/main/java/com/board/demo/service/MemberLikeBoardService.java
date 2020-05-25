package com.board.demo.service;

public interface MemberLikeBoardService {
    boolean isLike(long boardId, long memberId);

    boolean like(long memberId, long boardId);

    void dislike(long memberId, long boardId);
}
