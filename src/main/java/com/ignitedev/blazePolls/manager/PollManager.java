package com.ignitedev.blazePolls.manager;

import com.ignitedev.blazePolls.model.Poll;
import com.twodevsstudio.simplejsonconfig.api.Service;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PollManager {
  private static Service<String, Poll> service = Service.getService(Poll.class);

  public void initialize() {
    service.loadAndGetAll();
  }

  public List<Poll> getActive() {
    long now = System.currentTimeMillis();
    return (List<Poll>) service.getMatching(poll -> !poll.isClosed() && !poll.isExpired(now));
  }

  public Optional<Poll> findById(String id) {
    return Optional.ofNullable(service.getById(id));
  }

  public Poll create(UUID creator, String question, List<String> options, long durationMillis) {
    String id = generateId();
    Poll poll =
        new Poll(id, creator, question, options, System.currentTimeMillis(), durationMillis);
    service.save(poll);
    return poll;
  }

  public boolean close(String id) {
    Poll poll = service.getById(id);

    if (poll == null) {
      return false;
    }
    poll.setClosed(true);
    service.save(poll);
    return true;
  }

  public boolean remove(String id) {
    Poll poll = service.getById(id);

    if (poll == null) {
      return false;
    }
    service.delete(poll);
    return true;
  }

  public boolean vote(Poll poll, Player player, int optionIndex) {
    if (poll.isClosed() || poll.isExpired(System.currentTimeMillis())) {
      return false;
    }
    if (optionIndex < 0 || optionIndex >= poll.getOptions().size()) {
      return false;
    }
    if (poll.hasVoted(player.getUniqueId())) {
      return false;
    }
    poll.recordVote(player.getUniqueId(), optionIndex);
    service.save(poll);
    return true;
  }

  public int getVoteCount(Poll poll, int optionIndex) {
    int count = 0;
    for (Integer vote : poll.getVotes().values()) {
      if (vote != null && vote == optionIndex) {
        count++;
      }
    }
    return count;
  }

  public void checkExpirations() {
    long now = System.currentTimeMillis();
    List<Poll> polls =
        (List<Poll>) service.getMatching(poll -> !poll.isClosed() && poll.isExpired(now));

    for (Poll poll : polls) {
      poll.setClosed(true);
      service.save(poll);
    }
  }

  private String generateId() {
    Set<String> existing =
        service.getMatching(p -> true).stream().map(Poll::getId).collect(Collectors.toSet());
    String id;
    do {
      long rnd = ThreadLocalRandom.current().nextLong(2176782336L); // 36^6
      StringBuilder idBuilder = new StringBuilder(Long.toString(rnd, 36));
      while (idBuilder.length() < 6) {
        idBuilder.insert(0, "0");
      }
      id = idBuilder.toString();
    } while (existing.contains(id));
    return id;
  }
}
