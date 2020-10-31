package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.todo.ToDoCreateForm;
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

@Controller
@AllArgsConstructor
@RequestMapping("/todos")
public class ToDoController {

    private final UserService userService;
    private final TaskService taskService;
    private final ToDoService toDoService;
    private final ToDoMapper toDoMapper;

    @GetMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") long ownerId, Model model) {

        ToDoCreateForm todo = new ToDoCreateForm();
        todo.setOwnerId(ownerId);
        model.addAttribute("todo", todo);

        return "create-todo";
    }

    @PostMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") long ownerId, @ModelAttribute("todo") @Valid ToDoCreateForm toDoCreateForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "create-todo";
        }

        User owner = userService.readById(ownerId);
        ToDo toDo = toDoMapper.convertTodoCreateFormToEntity(toDoCreateForm, owner);
        toDoService.create(toDo);

        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/{id}/tasks")
    public String read(@PathVariable("id") long id, Model model) {

        List<Task> tasks = taskService.getByTodoId(id);
        model.addAttribute("tasks", tasks);

        ToDo toDo = toDoService.readById(id);
        model.addAttribute("collaborators", toDo.getCollaborators());
        model.addAttribute("todo", toDo);

        return "todo-tasks";
    }

    //    @GetMapping("/{todo_id}/update/users/{owner_id}")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @PostMapping("/{todo_id}/update/users/{owner_id}")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{todo_id}/delete/users/{owner_id}")
//    public String delete(//add needed parameters) {
//                         // ToDo
//        return " ";
//    }
//
    @GetMapping("/all/users/{user_id}")
    public String getAll(@PathVariable("user_id") long usedId, Model model) {

        User user = userService.readById(usedId);
        model.addAttribute("user", user);

        List<ToDo> allUserToDo = toDoService.getByUserId(usedId);
        model.addAttribute("todos", allUserToDo);

        return "todos-user";
    }


    @GetMapping("/{todo_id}/add/{collaborator_id}")
    public String addCollaborator() {
        //ToDo
        return "";
    }


    @GetMapping("/{todo_id}/remove/{collaborator_id}")
    public String removeCollaborator(@PathVariable("todo_id") long todoId, @PathVariable("collaborator_id") long collaboratorId) {


        return "redirect:/todos/" + todoId + "/tasks";
    }
}
