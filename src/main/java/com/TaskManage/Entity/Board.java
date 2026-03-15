package com.TaskManage.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.TaskManage.Enum.BoardType;

import lombok.*;

@Entity
@Table(name="boards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String boardName;
    private String projectKey;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy="board")
    @OrderBy("position")
    private List<BoardColumn> columns = new ArrayList<>();
}