package com.devops.taskmanagementapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.devops.taskmanagementapi.model.Task;
import com.devops.taskmanagementapi.model.TaskStatus;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskServiceTest {
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void createTaskGeneratesUniqueIdAndStoresTask() {
        Task firstTask = taskService.createTask(new Task(null, "First task", "First description", TaskStatus.TODO));
        Task secondTask = taskService.createTask(new Task(null, "Second task", "Second description", TaskStatus.IN_PROGRESS));

        assertNotEquals(firstTask.getId(), secondTask.getId());
        assertEquals(Optional.of(firstTask), taskService.getTaskById(firstTask.getId()));
        assertEquals(Optional.of(secondTask), taskService.getTaskById(secondTask.getId()));
    }

    @Test
    void getAllTasksReturnsCreatedTasks() {
        Task firstTask = taskService.createTask(new Task(null, "First task", "First description", TaskStatus.TODO));
        Task secondTask = taskService.createTask(new Task(null, "Second task", "Second description", TaskStatus.COMPLETED));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(List.of(firstTask, secondTask), tasks);
    }

    @Test
    void getTaskByIdReturnsExistingTask() {
        Task task = taskService.createTask(new Task(null, "Task", "Description", TaskStatus.TODO));

        Optional<Task> result = taskService.getTaskById(task.getId());

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
    }

    @Test
    void getTaskByIdReturnsEmptyForUnknownId() {
        assertTrue(taskService.getTaskById(999L).isEmpty());
    }

    @Test
    void updateTaskUpdatesExistingTaskAndPreservesId() {
        Task originalTask = taskService.createTask(new Task(null, "Original", "Original description", TaskStatus.TODO));
        Task updatedTask = new Task(null, "Updated", "Updated description", TaskStatus.COMPLETED);

        Optional<Task> result = taskService.updateTask(originalTask.getId(), updatedTask);

        assertTrue(result.isPresent());
        assertEquals(originalTask.getId(), result.get().getId());
        assertEquals("Updated", result.get().getTitle());
        assertEquals("Updated description", result.get().getDescription());
        assertEquals(TaskStatus.COMPLETED, result.get().getStatus());
        assertEquals(Optional.of(updatedTask), taskService.getTaskById(originalTask.getId()));
    }

    @Test
    void updateTaskReturnsEmptyForUnknownId() {
        Task task = new Task(null, "Task", "Description", TaskStatus.TODO);

        assertTrue(taskService.updateTask(999L, task).isEmpty());
    }

    @Test
    void deleteTaskReturnsTrueAndRemovesExistingTask() {
        Task task = taskService.createTask(new Task(null, "Task", "Description", TaskStatus.TODO));

        assertTrue(taskService.deleteTask(task.getId()));
        assertTrue(taskService.getTaskById(task.getId()).isEmpty());
    }

    @Test
    void deleteTaskReturnsFalseForUnknownId() {
        assertFalse(taskService.deleteTask(999L));
    }
}
