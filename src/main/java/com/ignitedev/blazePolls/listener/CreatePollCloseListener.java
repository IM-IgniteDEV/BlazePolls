package com.ignitedev.blazePolls.listener;

import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.gui.CreatePollGUI;
import com.ignitedev.blazePolls.manager.CreationManager;
import com.ignitedev.blazePolls.util.TextUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

@RequiredArgsConstructor
public class CreatePollCloseListener implements Listener {
  @Autowired private static PluginConfig config;

  private final CreationManager creationManager;

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (!(event.getPlayer() instanceof Player player)) {
      return;
    }
    if (!(event.getInventory().getHolder() instanceof CreatePollGUI)) {
      return;
    }
    InventoryCloseEvent.Reason reason = event.getReason();

    if (reason == InventoryCloseEvent.Reason.PLUGIN
        || reason == InventoryCloseEvent.Reason.OPEN_NEW) {
      return;
    }
    creationManager
        .getSession(player)
        .ifPresent(
            session -> {
              creationManager.endSession(player);
              player.sendMessage(TextUtility.parseMiniMessage(config.getMsgCreationCancelled()));
            });
  }
}
