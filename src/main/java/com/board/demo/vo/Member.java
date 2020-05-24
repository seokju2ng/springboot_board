package com.board.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicUpdate
@Builder
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private long memberId;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "pwd", nullable = false)
    private String pwd;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "attendance", nullable = false)
    private int attendance;

    @Column(name = "profile_photo")
    private String profilePhoto;

}
