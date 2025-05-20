package com.coderush.service;

import com.coderush.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO dto);
    UserDTO getUserById(Long id);
}
