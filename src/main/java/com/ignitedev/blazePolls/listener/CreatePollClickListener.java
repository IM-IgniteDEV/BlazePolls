package com.ignitedev.blazePolls.listener;

import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.gui.CreatePollGUI;
import com.ignitedev.blazePolls.manager.CreationManager;
import com.ignitedev.blazePolls.manager.CreationSession;
import com.ignitedev.blazePolls.manager.PollManager;
import com.ignitedev.blazePolls.util.Placeholder;
import com.ignitedev.blazePolls.util.TextUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@RequiredArgsConstructor
public class CreatePollClickListener implements Listener {
  @Autowired private static PluginConfig config;

  private final PollManager pollManager;
  private final CreationManager creationManager;

  @EventHandler
  public void onInvClick(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player player)) {
      return;
    }
    Inventory inventory = event.getInventory();

    if (inventory.getHolder() == null) {
      return;
    }
    if (!(inventory.getHolder() instanceof CreatePollGUI holder)) {
      return;
    }
    event.setCancelled(true);
    CreationSession session = holder.getSession();
    int slot = event.getRawSlot();
    int base = 9;

    if (slot == 3) { // add option via chat
      if (session.getOptions().size() >= config.getMaxOptions()) {
        player.sendMessage(TextUtility.parseMiniMessage(config.getMsgOptionLimit()));
      } else {
        session.setPendingEditIndex(null);
        session.setAwaitingChatInput(true);
        player.closeInventory();
        player.sendMessage(TextUtility.parseMiniMessage(config.getMsgTypeNewOption()));
      }
    } else if (slot == 4) { // remove last
      if (!session.getOptions().isEmpty()) {
        session.getOptions().removeLast();
      }
      holder.open(player);
    } else if (slot == 7) { // start poll
      if (session.getOptions().size() < 2) {
        player.sendMessage(TextUtility.parseMiniMessage(config.getMsgAddAtLeastTwo()));
        return;
      }
      pollManager.create(
          player.getUniqueId(),
          session.getQuestion(),
          session.getOptions(),
          session.getDurationMillis());
      creationManager.endSession(player);
      player.closeInventory();
      player.sendMessage(TextUtility.parseMiniMessage(config.getMsgPollCreated()));
    } else if (slot == 8) { // cancel
      creationManager.endSession(player);
      player.closeInventory();
      player.sendMessage(TextUtility.parseMiniMessage(config.getMsgCreationCancelled()));
    } else if (slot >= base) { // Clicked on an existing option to edit
      int index = slot - base;
      if (index < session.getOptions().size()) {
        session.setPendingEditIndex(index);
        session.setAwaitingChatInput(true);
        player.closeInventory();
        player.sendMessage(
            TextUtility.parseMiniMessage(
                TextUtility.replacePlaceholders(
                    config.getMsgTypeNewOptionAtIndex(),
                    Placeholder.of("{index}", String.valueOf(index)))));
      }
    }
  }
}
