package org.example.repository;

import org.example.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositary extends CrudRepository<UserInfo,Long> {
public UserInfo findByUserName(String username);
}
