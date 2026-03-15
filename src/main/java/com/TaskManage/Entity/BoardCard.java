package com.TaskManage.Entity;

import jakarta.persistence.*;

@Entity
@Table(
    name="board_card",
    indexes={@Index(columnList="board_id,column_id,position")}
)
public class BoardCard {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="issue_id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="column_id")
    private BoardColumn column;

    private int position;

    public Long getId() {
        return id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public BoardColumn getColumn() {
        return column;
    }

    public void setColumn(BoardColumn column) {
        this.column = column;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}