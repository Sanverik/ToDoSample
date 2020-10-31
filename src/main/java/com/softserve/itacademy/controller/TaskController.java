package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.task.TaskCreateForm;
import com.softserve.itacademy.dto.task.TaskUpdateForm;
import com.softserve.itacademy.mapper.TaskMapper;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final ToDoService toDoService;
    private final TaskService taskService;
    private final StateService stateService;
    private final TaskMapper taskMapper;

    @GetMapping("/create/todos/{todo_id}")
    public String create(
            @PathVariable("todo_id") long todoId,
            Model model) {

        model.addAttribute("todoId", todoId);
        model.addAttribute("task", new TaskCreateForm());
        model.addAttribute("priorities", Priority.values());

        return "create-task";
    }

    @PostMapping("/create/todos/{todo_id}")
    public String create(
            Model model, @PathVariable("todo_id") long todoId,
            @ModelAttribute("task") @Valid TaskCreateForm taskCreateForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("priorities", Priority.values());
            return "create-task";
        }

        // new state
        State state = stateService.readById(5L);
        ToDo toDo = toDoService.readById(todoId);
        Task task = taskMapper.convertTaskCreateFormToEntity(taskCreateForm, toDo, state);

        taskService.create(task);
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String update(
            @PathVariable("task_id") long taskId,
            @PathVariable("todo_id") long todoId,
            Model model) {

        TaskUpdateForm task = taskMapper.convertEntityToTaskUpdateForm(taskService.readById(taskId));
        List<State> allStates = stateService.getAll();
        model.addAttribute("task", task);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("states", allStates);

        return "update-task";
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String update(
            @PathVariable("task_id") long taskId,
            @PathVariable("todo_id") long todoId,
            @ModelAttribute("task") @Valid TaskUpdateForm taskUpdateForm,
            BindingResult bindingResult,
            Model model) {

        List<State> allStates = stateService.getAll();

        if (bindingResult.hasErrors()) {
            model.addAttribute("task", taskUpdateForm);
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("states", allStates);
            return "update-task";
        }

        State state = stateService.readById(taskUpdateForm.getStatusId());
        ToDo toDo = toDoService.readById(todoId);
        taskUpdateForm.setId(taskId);
        taskService.update(taskMapper.convertTaskUpdateFormToEntity(taskUpdateForm, toDo, state));

        return "redirect:/todos/" + todoId + "/tasks";

    }

    @GetMapping("/{task_id}/delete/todos/{todo_id}")
    public String delete(@PathVariable("task_id") long taskId,
                         @PathVariable("todo_id") long todoId) {

        taskService.delete(taskId);

        return "redirect:/todos/" + todoId + "/tasks";
    }
}
