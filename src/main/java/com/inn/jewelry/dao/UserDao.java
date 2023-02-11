package com.inn.jewelry.dao;

import com.inn.jewelry.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
}
