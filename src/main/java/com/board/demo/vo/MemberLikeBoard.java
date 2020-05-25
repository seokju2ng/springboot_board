package com.board.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "member_like_board")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class MemberLikeBoard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mlb_id", nullable = false)
    private long id;

    @Column(name = "member_id", nullable = false)
    private long memberId;

    @Column(name = "board_id", nullable = false)
    private long boardId;
}
