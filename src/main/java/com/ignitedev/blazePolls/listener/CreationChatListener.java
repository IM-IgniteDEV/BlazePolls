package com.ignitedev.blazePolls.listener;

import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.gui.CreatePollGUI;
import com.ignitedev.blazePolls.manager.CreationManager;
import com.ignitedev.blazePolls.manager.CreationSession;
import com.ignitedev.blazePolls.manager.PollManager;
import com.ignitedev.blazePolls.util.Placeholder;
import com.ignitedev.blazePolls.util.TextUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

@RequiredArgsConstructor
public class CreationChatListener implements Listener {
  @Autowired private static PluginConfig config;

  private final JavaPlugin plugin;
  private final PollManager pollManager;
  private final CreationManager creationManager;

  @EventHandler
  public void onChat(AsyncChatEvent event) {
    Player player = event.getPlayer();
    Optional<CreationSession> optionalCreationSession = creationManager.getSession(player);

    if (optionalCreationSession.isEmpty()) {
      return;
    }
    CreationSession session = optionalCreationSession.get();

    if (!session.isAwaitingChatInput()) {
      return;
    }
    event.setCancelled(true);
    String message = PlainTextComponentSerializer.plainText().serialize(event.message());
    int max = config.getMaxOptions();
    Integer index = session.getPendingEditIndex();

    if (index == null) {
      if (session.getOptions().size() >= max) {
        player.sendMessage(TextUtility.parseMiniMessage(config.getMsgOptionLimit()));
      } else {
        session.getOptions().add(message);
        player.sendMessage(
            TextUtility.parseMiniMessage(
                TextUtility.replacePlaceholders(
                    config.getMsgAddedOption(), Placeholder.of("{text}", message))));
      }
    } else {
      if (index >= 0 && index < session.getOptions().size()) {
        session.getOptions().set(index, message);
        player.sendMessage(
            TextUtility.parseMiniMessage(
                TextUtility.replacePlaceholders(
                    config.getMsgUpdatedOptionAtIndex(),
                    Placeholder.of("{index}", String.valueOf(index)),
                    Placeholder.of("{text}", message))));
      }
    }
    session.setPendingEditIndex(null);
    session.setAwaitingChatInput(false);
    plugin
        .getServer()
        .getScheduler()
        .runTask(
            plugin,
            () -> new CreatePollGUI(config, pollManager, creationManager, session).open(player));
  }
}
