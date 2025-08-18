package com.ignitedev.blazePolls.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class CreationSession {
  private final UUID playerId;
  private final String question;
  private final long durationMillis;
  private final List<String> options = new ArrayList<>();
  @Setter private boolean awaitingChatInput = false;
  @Setter private Integer pendingEditIndex = null;
}
