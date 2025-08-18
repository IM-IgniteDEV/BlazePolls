package com.ignitedev.blazePolls.manager;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CreationManager {
  private final Map<UUID, CreationSession> sessions = new ConcurrentHashMap<>();

  public CreationSession startSession(Player player, String question, long durationMillis) {
    UUID id = player.getUniqueId();
    CreationSession session = new CreationSession(id, question, durationMillis);
    sessions.put(id, session);
    return session;
  }

  public Optional<CreationSession> getSession(Player player) {
    return Optional.ofNullable(sessions.get(player.getUniqueId()));
  }

  public boolean hasSession(Player player) {
    return sessions.containsKey(player.getUniqueId());
  }

  public void endSession(Player player) {
    sessions.remove(player.getUniqueId());
  }
}
