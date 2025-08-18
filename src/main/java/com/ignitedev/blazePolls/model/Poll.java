package com.ignitedev.blazePolls.model;

import com.twodevsstudio.simplejsonconfig.data.Identifiable;
import com.twodevsstudio.simplejsonconfig.data.Stored;
import com.twodevsstudio.simplejsonconfig.def.StoreType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Stored(value = "polls", storeType = StoreType.JSON)
@Getter
@Setter
@NoArgsConstructor
public class Poll implements Identifiable<String> {
  private String id;
  private UUID creator;
  private String question;
  private List<String> options = new ArrayList<>();
  private long createdAtEpochMillis;
  private long durationMillis;
  private boolean closed;

  // UUID string -> option index
  private Map<String, Integer> votes = new HashMap<>();

  public Poll(
      String id,
      UUID creator,
      String question,
      List<String> options,
      long createdAtEpochMillis,
      long durationMillis) {
    this.id = id;
    this.creator = creator;
    this.question = question;
    this.options = new ArrayList<>(options);
    this.createdAtEpochMillis = createdAtEpochMillis;
    this.durationMillis = durationMillis;
    this.closed = false;
  }

  public boolean isExpired(long now) {
    if (durationMillis <= 0) {
      return false;
    }
    return now >= createdAtEpochMillis + durationMillis;
  }

  public boolean hasVoted(UUID player) {
    return votes.containsKey(player.toString());
  }

  public void recordVote(UUID player, int optionIndex) {
    votes.put(player.toString(), optionIndex);
  }

  @Override
  public String getId() {
    return id;
  }
}
