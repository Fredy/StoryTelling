package com.storytelling.repository;

import com.storytelling.model.StoryFragment;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StoryFragRepository extends PagingAndSortingRepository<StoryFragment, Long> {

  List<StoryFragment> findAll();

  List<StoryFragment> findByStoryProposition_IdOrderByPubDate(Long id);

}
