package com.swedbank.StudentApplication.task;

import com.swedbank.StudentApplication.group.Group;
import com.swedbank.StudentApplication.group.GroupService;
import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import com.swedbank.StudentApplication.group.exception.GroupNotFoundException;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/task")
public class TaskController {

    private final TaskService service;
    private final GroupService groupService;

    @Autowired
    public TaskController(TaskService service, GroupService groupService) {

        this.service = service;
        this.groupService = groupService;

    }

    @GetMapping(produces = "application/json")
    ResponseEntity<List<Task>> findAll() {
        List<Task> list = service.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("id")
    ResponseEntity<Task> findById(@PathVariable("id") long id)
            throws TaskNotFoundException {
        Task task = service.findById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<Task> saveTask(@RequestBody Task task) {
        if (task.getGroupId() != null) {
            Group group = groupService.findById(task.getGroupId());
            task.setGroup(group);
        }
        Task saveTask = service.save(task);
        return new ResponseEntity<>(saveTask, HttpStatus.OK);
    }

    @DeleteMapping("id")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id)
            throws TaskNotFoundException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
