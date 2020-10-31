package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;

    @Override
    public User create(User user) {

        return userRepository.save(user);
    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.get();
    }

    @Override
    public User update(User user) {
        User oldUser = readById(user.getId());
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
        userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.isEmpty() ? new ArrayList<>() : allUsers;
    }


    @Override
    public String readPassword(long id) {
        return userRepository.getUserPassword(id);
    }

    @Override
    public void removeCollaborator(long todoId, long collaboratorId) {

        User user = readById(collaboratorId);
        ToDo toDo = toDoRepository.getById(todoId);
        user.getOtherTodos().remove(toDo);
        update(user);
    }

    @Override
    public void addCollaborator(long todoId, long collaboratorId) {

        User user = readById(collaboratorId);
        ToDo toDo = toDoRepository.getById(todoId);

        List<ToDo> userTodos = user.getMyTodos();
        List<ToDo> collaborationsTodos = user.getOtherTodos();

        if (!collaborationsTodos.contains(toDo) && !userTodos.contains(toDo)) {
            collaborationsTodos.add(toDo);
            update(user);
        }

        // generate warn\error maybe
    }
}
