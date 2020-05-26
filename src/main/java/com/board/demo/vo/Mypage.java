package com.board.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Mypage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private long memberId;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int attendance;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "board_num", nullable = false)
    private int boardNum;

    @Column(name = "reply_num", nullable = false)
    private int replyNum;
}
