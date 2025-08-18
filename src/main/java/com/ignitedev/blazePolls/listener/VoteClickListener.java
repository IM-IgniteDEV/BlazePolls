package com.ignitedev.blazePolls.listener;

import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.gui.VoteGUI;
import com.ignitedev.blazePolls.manager.PollManager;
import com.ignitedev.blazePolls.model.Poll;
import com.ignitedev.blazePolls.util.TextUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@RequiredArgsConstructor
public class VoteClickListener implements Listener {
  @Autowired private static PluginConfig config;

  private final PollManager pollManager;

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player player)) {
      return;
    }
    Inventory inventory = event.getInventory();

    if (inventory.getHolder() == null) {
      return;
    }
    if (!(inventory.getHolder() instanceof VoteGUI holder)) {
      return;
    }
    event.setCancelled(true);
    int slot = event.getRawSlot();

    if (slot < 9) {
      return;
    }
    int index = slot - 9;
    Poll poll = holder.getPoll();
    if (index < poll.getOptions().size()) {
      player.sendMessage(
          TextUtility.parseMiniMessage(
              pollManager.vote(poll, player, index)
                  ? config.getMsgVoteRecorded()
                  : config.getMsgCannotVote()));
      new VoteGUI(config, pollManager, poll).open(player);
    }
  }
}
