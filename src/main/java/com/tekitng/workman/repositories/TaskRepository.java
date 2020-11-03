package com.tekitng.workman.repositories;

import com.tekitng.workman.entities.Task;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.List;

/**
 * @author Amusuo Paschal C.
 * @since 10/28/2020 2:32 PM
 */

@RequestScoped
public class TaskRepository implements PanacheRepository<Task> {

    public void saveTask(Task task) {
        persist(task);
    }

    public void updateTask(Task task) {
        update("title = :title, isCompleted = :completed, dateLastUpdated = :lastUpdated where id = :id",
                Parameters.with("title", task.getTitle())
                        .and("completed", task.isCompleted())
                        .and("lastUpdated", task.getDateLastUpdated())
                        .and("id", task.getId())
        );
    }

    public void deleteTask(Task task) {
        delete("id", task.getId());
    }

    public Task getTaskById(long id) {
        return findById(id);
    }

    public List<Task> getAllTasksForOwner(String email) {
        return findAll().list();
    }
}
