package com.ignitedev.blazePolls.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.gui.CreatePollGUI;
import com.ignitedev.blazePolls.manager.CreationManager;
import com.ignitedev.blazePolls.manager.CreationSession;
import com.ignitedev.blazePolls.manager.PollManager;
import com.ignitedev.blazePolls.util.DurationUtility;
import com.ignitedev.blazePolls.util.TextUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@CommandPermission("blazepolls.create")
@CommandAlias("createpoll")
@RequiredArgsConstructor
public class CreatePollCommand extends BaseCommand {
  @Autowired private static PluginConfig config;

  private final PollManager pollManager;
  private final CreationManager creationManager;

  @Default
  @Description("Start poll creation flow")
  @Syntax("<duration> <question...>")
  public void onCreate(Player player, String duration, String[] questionParts) {
    if (duration == null || questionParts == null || questionParts.length == 0) {
      player.sendMessage(TextUtility.parseMiniMessage(config.getMsgUsageCreatePoll()));
      return;
    }
    long durationMillis = DurationUtility.parseToMillis(duration);
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < questionParts.length; i++) {
      if (i > 0) stringBuilder.append(' ');
      stringBuilder.append(questionParts[i]);
    }
    String question = stringBuilder.toString();
    CreationSession session = creationManager.startSession(player, question, durationMillis);
    new CreatePollGUI(config, pollManager, creationManager, session).open(player);
  }
}
