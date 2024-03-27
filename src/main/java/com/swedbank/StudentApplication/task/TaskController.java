package com.swedbank.StudentApplication.task;

import com.swedbank.StudentApplication.group.Group;
import com.swedbank.StudentApplication.group.GroupService;
import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    //    @GetMapping(produces = "application/json")
//    ResponseEntity<List<Task>> findAll() {
//        List<Task> list = service.findAll();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    List<Task> findAllAll() {
        return service.findAll();
    }
    @GetMapping("id")
    @ResponseStatus(HttpStatus.OK)
    Task findById(@PathVariable("id") long id)
            throws TaskNotFoundException {
        return service.findById(id);
    }
    @PostMapping()
    ResponseEntity<Task> saveTask(@RequestBody Task task) {
        if (task.getGroupId() != null) {
            Group group = groupService.findById(task.getGroupId());
            task.setGroup(group);
        }
        Task saveTask = service.save(task);
        return new ResponseEntity<>(saveTask, HttpStatus.CREATED);
    }

    @DeleteMapping("id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTask(@PathVariable("id") Long id)
            throws TaskNotFoundException {
        service.delete(id);
    }
}
