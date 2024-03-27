package com.swedbank.StudentApplication.task;

import com.swedbank.StudentApplication.task.exceptiion.TaskExistsException;
import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Task findById(long id) throws TaskNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task save(Task task) throws TaskExistsException {
        return repository.save(task);
    }

    @Override
    public void update(Task task) throws TaskNotFoundException {
        Task exsistingTask = repository.findById(task.getId())
                .orElseThrow(() -> new TaskNotFoundException(task.getId()));
        exsistingTask.setId(task.getId());
        exsistingTask.setShortDesc(task.getShortDesc());
        exsistingTask.setDetails(task.getDetails());
        exsistingTask.setStartDate(task.getStartDate());
        exsistingTask.setEndDate(task.getEndDate());
        repository.save(exsistingTask);
    }

    @Override
    public void delete(long id) throws TaskNotFoundException {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
