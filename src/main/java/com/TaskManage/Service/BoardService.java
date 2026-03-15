package com.TaskManage.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManage.Enum.IssueStatus;
import com.TaskManage.Entity.Board;
import com.TaskManage.Entity.BoardCard;
import com.TaskManage.Entity.BoardColumn;
import com.TaskManage.Entity.Issue;
import com.TaskManage.Repository.BoardCardRepository;
import com.TaskManage.Repository.BoardColumnRepository;
import com.TaskManage.Repository.BoardRepository;
import com.TaskManage.Repository.IssueRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private BoardColumnRepository columnRepo;

    @Autowired
    private BoardCardRepository cardRepo;

    @Autowired
    private IssueRepository issueRepo;

    // Create a new board
    public Board createBoard(Board board) {
        return boardRepo.save(board);
    }

    public Optional<Board> getByBoardId(Long id) {
        return boardRepo.findById(id);
    }

    public List<BoardColumn> getColumns(Long boardId) {
        return columnRepo.findByBoardIdOrderByPosition(boardId);
    }

    public List<BoardCard> getCardsForColumn(Long boardId, Long columnId) {
        return cardRepo.findByBoardIdAndColumnIdOrderByPosition(boardId, columnId);
    }

    // Add an issue to a board column
    @Transactional
    public BoardCard addIssueToBoard(Long boardId, Long columnId, Long issueId) {
        Issue issue = issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        // Remove previous card if exists
        cardRepo.findByIssue(issue).ifPresent(cardRepo::delete);

        BoardColumn column = columnRepo.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        Board board = boardRepo.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        // Check WIP limit
        if (column.getWipLimit() != null && column.getWipLimit() > 0) {
            long count = cardRepo.countByBoardIdAndColumnId(boardId, columnId);
            if (count >= column.getWipLimit()) {
                throw new RuntimeException("WIP limit reached for column: " + column.getName());
            }
        }

        // Determine position
        List<BoardCard> existing = cardRepo.findByBoardIdAndColumnIdOrderByPosition(boardId, columnId);
        int position = existing.size();

        // Create new card
        BoardCard card = new BoardCard();
        card.setBoard(board);      // Use object, not ID
        card.setColumn(column);    // Column object
        card.setIssue(issue);      // Issue object
        card.setPosition(position);
        card = cardRepo.save(card);

        // Update issue status based on column status
        updateIssueStatus(issue, column.getStatusKey());

        return card;
    }

    // Move a card to another column / position
    @Transactional
    public void moveCards(Long boardId, Long columnId, Long cardId, int newPosition, String performedBy) {
        BoardCard card = cardRepo.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not available"));

        BoardColumn fromColumn = card.getColumn();
        BoardColumn toColumn = columnRepo.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        // Check WIP limit
        if (toColumn.getWipLimit() != null && toColumn.getWipLimit() > 0) {
            long count = cardRepo.countByBoardIdAndColumnId(boardId, columnId);
            if (!Objects.equals(fromColumn.getId(), toColumn.getId()) && count >= toColumn.getWipLimit()) {
                throw new RuntimeException("WIP limit exceeded for column: " + toColumn.getName());
            }
        }

        // Adjust positions in the source column
        List<BoardCard> fromCards = cardRepo.findByBoardIdAndColumnIdOrderByPosition(boardId, fromColumn.getId());
        for (BoardCard c : fromCards) {
            if (c.getPosition() > card.getPosition()) {
                c.setPosition(c.getPosition() - 1);
                cardRepo.save(c);
            }
        }

        // Adjust positions in the destination column
        List<BoardCard> toCards = cardRepo.findByBoardIdAndColumnIdOrderByPosition(boardId, toColumn.getId());
        for (BoardCard c : toCards) {
            if (c.getPosition() >= newPosition) {
                c.setPosition(c.getPosition() + 1);
                cardRepo.save(c);
            }
        }

        // Update card
        card.setColumn(toColumn);
        card.setPosition(newPosition);
        cardRepo.save(card);

        // Update issue status
        updateIssueStatus(card.getIssue(), toColumn.getStatusKey());
    }

    // Update issue status
    private void updateIssueStatus(Issue issue, IssueStatus issueStatus) {
        if (issueStatus == null) return;
        issue.setIssueStatus(issueStatus);
        issueRepo.save(issue);
    }

    // Record column card order
    @Transactional
    public void recordColumn(Long boardId, Long columnId, List<Long> orderedCardIds) {
        int position = 0;
        for (Long cardId : orderedCardIds) {
            BoardCard card = cardRepo.findById(cardId)
                    .orElseThrow(() -> new RuntimeException("Card not available"));
            card.setPosition(position++);
            cardRepo.save(card);
        }
    }

    // Sprint methods (to implement later)
    @Transactional
    public void startSprint(Long sprintId) {}

    @Transactional
    public void completeSprint(Long sprintId) {}
}