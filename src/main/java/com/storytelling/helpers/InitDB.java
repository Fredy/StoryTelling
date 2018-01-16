package com.storytelling.helpers;

import com.storytelling.model.StoryFragment;
import com.storytelling.model.StoryProposition;
import com.storytelling.model.User;
import com.storytelling.repository.StoryFragRepository;
import com.storytelling.repository.StoryPropRepository;
import com.storytelling.repository.UserRepository;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.text.TextProducer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDB {

  @Autowired
  UserRepository userRepository;

  @Autowired
  StoryPropRepository propRepository;

  @Autowired
  StoryFragRepository fragRepository;


  Fairy fairy = Fairy.create();

  @PostConstruct
  void insertAll() {
    List<User> users = this.insertUsers();
    this.insertStoriesProp(users);
  }

  List<User> insertUsers() {
    List<User> users = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
      Person person = this.fairy.person();
      User user = new User();

      user.setUsername(String.format("user%d", i));
      user.setFirstName(person.getFirstName());
      user.setLastName(person.getLastName());
      user.setEmail(person.getEmail());
      user.setSignDate(new Date());

      users.add(user);
    }
    this.userRepository.save(users);

    return users;
  }

  void insertStoriesProp(List<User> users) {
    TextProducer text = this.fairy.textProducer();
    for (User user : users) {
      System.out.printf("USER: %d",user.getId());
      for (int i = 0; i < 5; i++) {
        StoryProposition proposition = new StoryProposition();
        proposition.setPropText(text.limitedTo(300).paragraph(190));
        proposition.setPubDate(new Date());
        // TODO: prop votes

        //user.getPropList().add(proposition); // Same as proposition.setUser(user);
        proposition.setUser(user);
        //proposition.setFragmentList(this.insertStoryFrag(proposition, users));

        this.propRepository.save(proposition);
        this.insertStoryFrag(proposition, users);
      }
    }
    System.out.println("DONE!");
  }

  List<StoryFragment> insertStoryFrag(StoryProposition proposition, List<User> users) {
    TextProducer text = this.fairy.textProducer();
    List<StoryFragment> fragments = new ArrayList<>();

    for (int j = 0; j < 5; j++) {
      StoryFragment fragment = new StoryFragment();
      fragment.setFragText(text.limitedTo(300).paragraph(190));
      fragment.setPubDate(new Date());

      int random = ThreadLocalRandom.current().nextInt(0, 10);
      fragment.setUser(users.get(random));
      // TODO: frag votes
      //proposition.getFragmentList().add(fragment);
      fragment.setStoryProposition(proposition);
      fragments.add(fragment);
    }
    this.fragRepository.save(fragments);
    return fragments;

  }


}
