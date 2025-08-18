package com.ignitedev.blazePolls.gui;

import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.manager.PollManager;
import com.ignitedev.blazePolls.model.Poll;
import com.ignitedev.blazePolls.util.ItemUtility;
import com.ignitedev.blazePolls.util.Placeholder;
import com.ignitedev.blazePolls.util.TextUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class PollListGUI implements InventoryHolder {
  private final PluginConfig config;
  private final PollManager pollManager;
  @Getter private Inventory inventory;

  public void open(Player player) {
    int size = config.getListGuiSize();
    this.inventory = Bukkit.createInventory(this, size, config.getGuiListTitle());
    List<Poll> polls = pollManager.getActive();
    int slot = 0;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(config.getGuiListCreatedDateFormat());

    for (Poll poll : polls) {
      if (slot >= size) {
        break;
      }
      List<Component> lore = new ArrayList<>();
      for (Component line : config.getGuiListItemLore()) {
        lore.add(
            TextUtility.replacePlaceholders(
                line,
                Placeholder.of("{id}", poll.getId()),
                Placeholder.of(
                    "{created}",
                    simpleDateFormat.format(new Date(poll.getCreatedAtEpochMillis())))));
      }
      Component name =
          TextUtility.replacePlaceholders(
              config.getGuiListItemName(), Placeholder.of("{question}", poll.getQuestion()));
      ItemStack item = ItemUtility.prepareItemstack(Material.BOOK, name, lore);
      this.inventory.setItem(slot++, item);
    }
    player.openInventory(this.inventory);
  }
}
