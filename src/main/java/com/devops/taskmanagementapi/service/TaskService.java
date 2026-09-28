package com.devops.taskmanagementapi.service;

import com.devops.taskmanagementapi.model.Task;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final Map<Long, Task> tasks = new LinkedHashMap<>();
    private long nextId = 1L;

    public Task createTask(Task task) {
        Objects.requireNonNull(task, "task must not be null");
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> getTaskById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public Optional<Task> updateTask(Long id, Task task) {
        Objects.requireNonNull(task, "task must not be null");
        if (!tasks.containsKey(id)) {
            return Optional.empty();
        }

        task.setId(id);
        tasks.put(id, task);
        return Optional.of(task);
    }

    public boolean deleteTask(Long id) {
        return tasks.remove(id) != null;
    }
}
