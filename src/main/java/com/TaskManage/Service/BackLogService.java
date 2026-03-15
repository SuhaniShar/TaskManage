package com.TaskManage.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManage.Enum.IssueType;
import com.TaskManage.Enum.SprintState;
import com.TaskManage.Entity.Issue;
import com.TaskManage.Entity.Sprint;
import com.TaskManage.Repository.IssueRepository;
import com.TaskManage.Repository.SprintRepository;

@Service
public class BackLogService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private SprintRepository sprintRepo;

    public List<Issue> getBackLog(Long projectId) {
        return issueRepo.findByProjectIdAndSprintIdIsNullOrderByBackLogPosition(projectId);
    }

    @Transactional
    public void recordBackLog(Long projectId, List<Long> orderedIssueIds) {
        int position = 0;
        for (Long issueId : orderedIssueIds) {
            Issue issue = issueRepo.findById(issueId)
                    .orElseThrow(() -> new RuntimeException("Issue not found"));
            issue.setBackLogPosition(position++);
            issueRepo.save(issue);
        }
    }

    @Transactional
    public Issue addIssueToSprint(Long issueId, Long sprintId) {
        Issue issue = issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        Sprint sprint = sprintRepo.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        SprintState state = sprint.getState();
        if (state != SprintState.PLANNED && state != SprintState.ACTIVE) {
            throw new RuntimeException("Cannot add issue to sprint in state: " + state);
        }

        issue.setSprintId(sprintId);
        issue.setBackLogPosition(null);

        return issueRepo.save(issue);
    }

    public Map<String, Object> getBackLogHierarchy(Long projectId) {
        List<Issue> backLog = getBackLog(projectId);

        Map<Long, Map<String, Object>> epicMap = new HashMap<>();
        Map<Long, Issue> storyMap = new HashMap<>();

        // Organize Epics and Stories
        for (Issue issue : backLog) {
            if (issue.getIssueType() == IssueType.TASKS) {
                Map<String, Object> epicData = new HashMap<>();
                epicData.put("epic", issue);
                epicData.put("stories", new ArrayList<Issue>());
                epicData.put("subtasks", new HashMap<Long, List<Issue>>());
                epicMap.put(issue.getId(), epicData);
            }

            if (issue.getIssueType() == IssueType.STORIES) {
                storyMap.put(issue.getId(), issue);
            }
        }

        // Assign Stories to Epics
        for (Issue issue : backLog) {
            if (issue.getIssueType() == IssueType.STORIES) {
                Long epicId = issue.getEpicId();
                if (epicId != null && epicMap.containsKey(epicId)) {
                    List<Issue> stories = (List<Issue>) epicMap.get(epicId).get("stories");
                    stories.add(issue);
                }
            }
        }

        // Assign Subtasks to Stories within Epics
        for (Issue issue : backLog) {
            if (issue.getIssueType() == IssueType.SUB_TASKS) {
                Long parentId = issue.getParentIssueId();

                if (parentId != null && storyMap.containsKey(parentId)) {
                    for (Map<String, Object> epicData : epicMap.values()) {
                        List<Issue> stories = (List<Issue>) epicData.get("stories");
                        boolean belongsToEpic = stories.stream().anyMatch(s -> s.getId().equals(parentId));

                        if (belongsToEpic) {
                            Map<Long, List<Issue>> subMap = (Map<Long, List<Issue>>) epicData.get("subtasks");
                            subMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(issue);
                            break;
                        }
                    }
                }
            }
        }

        return Collections.singletonMap("epics", epicMap);
    }
}