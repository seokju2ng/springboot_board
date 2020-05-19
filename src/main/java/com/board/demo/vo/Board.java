package com.board.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
@Builder
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date")
    private String date;

    @Column(name = "likes", nullable = false)
    private long likes;

    @Column(name = "views", nullable = false)
    private long views;

    @Column(name = "writer", nullable = false)
    private Long writer;

    @Column(name = "category", nullable = false)
    private Long category;
}
