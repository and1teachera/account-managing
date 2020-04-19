package test.zlatenov.accountmanaging.service;

import test.zlatenov.accountmanaging.model.binding.DeleteUserBindingModel;
import test.zlatenov.accountmanaging.model.binding.UserBindingModel;
import test.zlatenov.accountmanaging.model.entity.User;

import java.util.Collection;

/**
 * @author Angel Zlatenov
 */

public interface UserService {
    Collection<User> getUsers();

    User getUserByEmail(String email);

    void deleteUser(DeleteUserBindingModel deleteUserBindingModel);

    User createUser(UserBindingModel user);

    User updateUser(UserBindingModel user);
}
