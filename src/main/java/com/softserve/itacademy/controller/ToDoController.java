package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.task.TaskInfoForm;
import com.softserve.itacademy.dto.todo.ToDoCollaboratorForm;
import com.softserve.itacademy.dto.todo.ToDoCreateForm;
import com.softserve.itacademy.dto.todo.ToDoInfoForm;
import com.softserve.itacademy.dto.todo.ToDoUpdateForm;
import com.softserve.itacademy.mapper.TaskMapper;
import com.softserve.itacademy.mapper.ToDoMapper;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/todos")
public class ToDoController {

    private final UserService userService;
    private final TaskService taskService;
    private final ToDoService toDoService;
    private final ToDoMapper toDoMapper;
    private final TaskMapper taskMapper;

    @GetMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") long ownerId,
                         Model model) {

        ToDoCreateForm todo = new ToDoCreateForm();
        todo.setOwnerId(ownerId);
        model.addAttribute("todo", todo);

        return "create-todo";
    }

    @PostMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") long ownerId,
                         @ModelAttribute("todo") @Valid ToDoCreateForm toDoCreateForm,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "create-todo";
        }

        User owner = userService.readById(ownerId);
        ToDo toDo = toDoMapper.convertTodoCreateFormToEntity(toDoCreateForm, owner);
        toDoService.create(toDo);

        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/{id}/tasks")
    public String read(@PathVariable("id") long id,
                       Model model) {

        List<Task> tasks = taskService.getByTodoId(id);

        List<TaskInfoForm> taskList = tasks.stream()
                .map(taskMapper::convertEntityToTaskInfoForm)
                .collect(Collectors.toList());

        model.addAttribute("tasks", taskList);

        ToDo toDo = toDoService.readById(id);
        model.addAttribute("collaborators", toDo.getCollaborators());
        model.addAttribute("todo", toDo);
        model.addAttribute("users", userService.getAll());
        ToDoCollaboratorForm toDoCollaboratorForm = new ToDoCollaboratorForm();
        toDoCollaboratorForm.setTodoId(id);
        model.addAttribute("todoForm", toDoCollaboratorForm);

        return "todo-tasks";
    }

    @GetMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable("todo_id") long todoId,
                         @PathVariable("owner_id") long ownerId,
                         Model model
    ) {

        ToDo toDo = toDoService.readById(todoId);
        ToDoUpdateForm toDoUpdateForm = toDoMapper.convertEntityToTodoUpdateForm(toDo);
        model.addAttribute("todoUpdate", toDoUpdateForm);

        return "update-todo";
    }

    @PostMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@ModelAttribute("todoUpdate") @Valid ToDoUpdateForm toDoUpdateForm,
                         @PathVariable("todo_id") long todoId,
                         @PathVariable("owner_id") long ownerId,
                         BindingResult bindingResult,
                         Model model
    ) {

        if (bindingResult.hasErrors()) {
            ToDo toDo = toDoService.readById(todoId);
            toDoUpdateForm = toDoMapper.convertEntityToTodoUpdateForm(toDo);
            model.addAttribute("todoUpdate", toDoUpdateForm);
            return "update-todo";
        }

        toDoUpdateForm.setId(todoId);
        toDoUpdateForm.setOwnerId(ownerId);

        User owner = userService.readById(ownerId);
        ToDo toDo = toDoMapper.convertTodoUpdateFormToEntity(toDoUpdateForm, owner);
        toDoService.update(toDo);

        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/{todo_id}/delete/users/{owner_id}")
    public String delete(@PathVariable("todo_id") long todoId,
                         @PathVariable("owner_id") long ownerId
    ) {

        toDoService.delete(todoId);

        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/all/users/{user_id}")
    public String getAll(@PathVariable("user_id") long usedId,
                         Model model) {

        User user = userService.readById(usedId);
        model.addAttribute("user", user);

        List<ToDo> toDos = toDoService.getByUserId(usedId);

        List<ToDoInfoForm> toDoList = toDos.stream()
                .map(toDoMapper::convertEntityToTodoInfoForm)
                .collect(Collectors.toList());
        model.addAttribute("todos", toDoList);

        return "todos-user";
    }


    @PostMapping("/{todo_id}/add")
    public String addCollaborator(@PathVariable("todo_id") long todoId,
                                  @ModelAttribute("todoForm") ToDoCollaboratorForm toDoCollaboratorForm,
                                  Model model) {

        long collaboratorId = toDoCollaboratorForm.getCollaboratorId();
        userService.addCollaborator(todoId, collaboratorId);

        return "redirect:/todos/" + todoId + "/tasks";
    }


    @GetMapping("/{todo_id}/remove/{collaborator_id}")
    public String removeCollaborator(@PathVariable("todo_id") long todoId,
                                     @PathVariable("collaborator_id") long collaboratorId) {


        userService.removeCollaborator(todoId, collaboratorId);

        return "redirect:/todos/" + todoId + "/tasks";
    }
}
