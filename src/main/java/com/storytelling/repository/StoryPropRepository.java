package com.storytelling.repository;

import com.storytelling.model.StoryProposition;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StoryPropRepository extends PagingAndSortingRepository<StoryProposition, Long> {

  // TODO: apply paging in this queries
  List<StoryProposition> findByPropTextContaining(String text);

  List<StoryProposition> findByUser_Id(Long id);

  List<StoryProposition> findByUser_Username(String username);

  List<StoryProposition> findByPubDateBetween(Date start, Date end);

  // TODO: ranking query
}
