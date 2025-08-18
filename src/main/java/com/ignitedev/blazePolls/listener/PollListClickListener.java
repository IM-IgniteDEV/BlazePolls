package com.ignitedev.blazePolls.listener;

import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.gui.PollListGUI;
import com.ignitedev.blazePolls.gui.VoteGUI;
import com.ignitedev.blazePolls.manager.PollManager;
import com.ignitedev.blazePolls.model.Poll;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

@RequiredArgsConstructor
public class PollListClickListener implements Listener {
  @Autowired
  private static PluginConfig config;

  private final PollManager pollManager;

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player)) {
      return;
    }
    Inventory inventory = event.getInventory();

    if (inventory.getHolder() == null) {
      return;
    }
    if (!(inventory.getHolder() instanceof PollListGUI)) {
      return;
    }
    event.setCancelled(true);
    int slot = event.getRawSlot();

    if (slot < 0) {
      return;
    }
    List<Poll> active = pollManager.getActive();

    if (slot >= active.size()) {
      return;
    }
    new VoteGUI(config, pollManager, active.get(slot)).open((Player) event.getWhoClicked());
  }
}
