package com.softserve.itacademy.mapper;

import com.softserve.itacademy.dto.task.TaskCreateForm;
import com.softserve.itacademy.dto.task.TaskInfoForm;
import com.softserve.itacademy.dto.task.TaskUpdateForm;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task convertTaskCreateFormToEntity(TaskCreateForm taskCreateForm, ToDo ownerTodo, State defaultState) {

        Task task = new Task();

        task.setName(taskCreateForm.getName());
        task.setPriority(Priority.valueOf(taskCreateForm.getPriority()));
        task.setState(defaultState);
        task.setTodo(ownerTodo);


        return task;
    }

    public Task convertTaskUpdateFormToEntity(TaskUpdateForm taskUpdateForm, ToDo ownerToDo, State taskState) {

        Task task = new Task();
        task.setId(taskUpdateForm.getId());
        task.setName(taskUpdateForm.getName());
        task.setTodo(ownerToDo);
        task.setPriority(Priority.values()[(int) taskUpdateForm.getPriorityId()]);
        task.setState(taskState);

        return task;
    }

    public TaskUpdateForm convertEntityToTaskUpdateForm(Task task) {

        TaskUpdateForm taskUpdateForm = new TaskUpdateForm();

        taskUpdateForm.setId(task.getId());
        taskUpdateForm.setName(task.getName());
        taskUpdateForm.setPriorityId(task.getPriority().ordinal());
        taskUpdateForm.setStatusId(task.getState().getId());

        return taskUpdateForm;
    }

    public TaskInfoForm convertEntityToTaskInfoForm(Task task) {

        TaskInfoForm taskInfoForm = new TaskInfoForm();

        taskInfoForm.setId(task.getId());
        taskInfoForm.setName(task.getName());
        taskInfoForm.setPriority(task.getPriority().name());
        taskInfoForm.setState(task.getState().getName());

        return taskInfoForm;
    }

}
