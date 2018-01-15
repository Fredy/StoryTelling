package com.storytelling.repository;

import com.storytelling.model.User;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  List<User> findAll();

  User findById(Long id);

  User findByUsername(String username);

}
