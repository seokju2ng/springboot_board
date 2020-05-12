package com.board.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "title", nullable = false)
    private String Title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "likes", nullable = false)
    private Long likes;

    @Column(name = "views", nullable = false)
    private Long views;

    @Column(name = "writer", nullable = false)
    private Long writer;

    @Column(name = "category", nullable = false)
    private Long category;
}
