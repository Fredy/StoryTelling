package com.storytelling.repository;

import com.storytelling.model.StoryFragment;
import com.storytelling.model.StoryProposition;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface StoryPropRepository extends PagingAndSortingRepository<StoryProposition, Long> {

  // TODO: apply paging in this queries
  List<StoryProposition> findAll();

  List<StoryProposition> findByPropTextContaining(String text);

  List<StoryProposition> findByUser_Id(Long id);

  List<StoryProposition> findByUser_Username(String username);

  List<StoryProposition> findByPubDateBetween(Date start, Date end);

  @Query("SELECT p.fragmentList FROM StoryProposition p WHERE p.id = :id")
  List<StoryFragment> storyFragsByPropId(@Param("id") Long id);

  // TODO: ranking query
  // TODO: votes by story query

  @Query("SELECT s.id FROM StoryProposition s")
  List<Long> findAllIds();
}
