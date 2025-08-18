package com.ignitedev.blazePolls.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.gui.PollListGUI;
import com.ignitedev.blazePolls.manager.PollManager;
import com.ignitedev.blazePolls.model.Poll;
import com.ignitedev.blazePolls.util.Placeholder;
import com.ignitedev.blazePolls.util.TextUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@CommandAlias("poll")
@RequiredArgsConstructor
@CommandPermission("blazepolls.use")
public class PollCommand extends BaseCommand {
  @Autowired
  private static PluginConfig config;

  private final PollManager pollManager;

  @Default
  @Description("Open the active polls GUI")
  @CommandPermission("blazepolls.use")
  public void openList(Player player) {
    new PollListGUI(config, pollManager).open(player);
  }

  @Subcommand("close")
  @Syntax("<pollId>")
  @CommandPermission("blazepolls.admin.close")
  public void close(CommandSender sender, String pollId) {
    if (pollId == null || pollId.isEmpty()) {
      sender.sendMessage(TextUtility.parseMiniMessage(config.getMsgUsagePollClose()));
      return;
    }
    Optional<Poll> optionalPoll = pollManager.findById(pollId);

    if (optionalPoll.isEmpty()) {
      sender.sendMessage(TextUtility.parseMiniMessage(config.getMsgPollNotFound()));
      return;
    }
    Poll poll = optionalPoll.get();
    poll.setClosed(true);
    pollManager.save(poll);
    sender.sendMessage(
        TextUtility.parseMiniMessage(
            TextUtility.replacePlaceholders(
                config.getMsgClosedPoll(), Placeholder.of("{pollId}", poll.getId()))));
  }

  @Subcommand("remove")
  @Syntax("<pollId>")
  @CommandPermission("blazepolls.admin.remove")
  public void remove(CommandSender sender, String pollId) {
    if (pollId == null || pollId.isEmpty()) {
      sender.sendMessage(TextUtility.parseMiniMessage(config.getMsgUsagePollRemove()));
      return;
    }
    sender.sendMessage(
        TextUtility.parseMiniMessage(
            pollManager.remove(pollId) ? config.getMsgRemovedPoll() : config.getMsgPollNotFound()));
  }
}
