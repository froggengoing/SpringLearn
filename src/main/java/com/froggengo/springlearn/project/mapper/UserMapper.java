package com.froggengo.springlearn.project.mapper;

import com.froggengo.springlearn.project.domain.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUserList();
}
