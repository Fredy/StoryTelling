package com.storytelling.repository;

import com.storytelling.model.StoryProposition;
import com.storytelling.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  List<User> findAll();

  User findByUsername(String username);

  // TODO: apply paging in this queries
  @Query("SELECT u.propList from User u WHERE u.id = :id")
  List<StoryProposition> storyPropsByUserId(@Param("id") Long id);

}
