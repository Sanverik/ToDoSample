package com.softserve.itacademy.mapper;

import com.softserve.itacademy.dto.todo.ToDoCreateForm;
import com.softserve.itacademy.dto.todo.ToDoInfoForm;
import com.softserve.itacademy.dto.todo.ToDoUpdateForm;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.utils.DateFormatterUtil;
import org.springframework.stereotype.Component;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;

@Component
public class ToDoMapper {

    public ToDo convertTodoCreateFormToEntity(ToDoCreateForm toDoCreateForm, User owner) {

        ToDo toDo = new ToDo();
        toDo.setTitle(toDoCreateForm.getTitle());
        toDo.setOwner(owner);

        return toDo;
    }

    public ToDoUpdateForm convertEntityToTodoUpdateForm(ToDo toDo) {

        ToDoUpdateForm toDoUpdateForm = new ToDoUpdateForm();

        toDoUpdateForm.setId(toDo.getId());
        toDoUpdateForm.setTodoTitle(toDo.getTitle());
        User owner = toDo.getOwner();
        toDoUpdateForm.setOwner(owner.getFirstName() + " " + owner.getLastName());
        toDoUpdateForm.setOwnerId(owner.getId());

        return toDoUpdateForm;
    }


    public ToDo convertTodoUpdateFormToEntity(ToDoUpdateForm toDoUpdateForm, User owner) {

        ToDo toDo = new ToDo();

        toDo.setId(toDoUpdateForm.getId());
        toDo.setTitle(toDoUpdateForm.getTodoTitle());
        toDo.setOwner(owner);

        return toDo;
    }

    public ToDoInfoForm convertEntityToTodoInfoForm(ToDo toDo){

        ToDoInfoForm toDoInfoForm = new ToDoInfoForm();

        toDoInfoForm.setId(toDo.getId());
        toDoInfoForm.setTitle(toDo.getTitle());
        toDoInfoForm.setCreatedAt(DateFormatterUtil.format(toDo.getCreatedAt()));

        User owner = toDo.getOwner();
        toDoInfoForm.setOwner(owner.getFirstName() + " " + owner.getLastName());


        return toDoInfoForm;
    }
}
