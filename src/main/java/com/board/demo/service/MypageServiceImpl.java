package com.board.demo.service;

import com.board.demo.repository.MypageRepository;
import com.board.demo.vo.Mypage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MypageServiceImpl implements MypageService {
    @Autowired
    private MypageRepository mypageRepository;

    @Override
    public Mypage getMypageInfo(long memberId) {
        return mypageRepository.findById(memberId).orElse(null);
    }
}
