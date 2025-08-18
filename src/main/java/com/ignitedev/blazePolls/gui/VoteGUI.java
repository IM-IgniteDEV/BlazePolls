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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class VoteGUI implements InventoryHolder {
  private final PluginConfig config;
  private final PollManager pollManager;
  @Getter private final Poll poll;
  @Getter private Inventory inventory;

  public void open(Player player) {
    int options = poll.getOptions().size();
    int rows = Math.min(6, Math.max(1, 1 + (options + 8) / 9));
    int size = rows * 9;
    Component title =
        TextUtility.replacePlaceholders(
            config.getGuiVoteTitleFormat(), Placeholder.of("{id}", poll.getId()));
    this.inventory = Bukkit.createInventory(this, size, title);

    List<Component> questionLore = new ArrayList<>();
    questionLore.add(config.getGuiVoteQuestionLabel());
    questionLore.add(Component.text(poll.getQuestion()));
    questionLore.add(Component.text(" "));
    questionLore.add(
        poll.isClosed() ? config.getGuiVoteStatusClosed() : config.getGuiVoteStatusActive());
    this.inventory.setItem(
        0,
        ItemUtility.prepareItemstack(
            Material.OAK_SIGN, config.getGuiVoteQuestionHeaderName(), questionLore));

    for (int i = 0; i < poll.getOptions().size(); i++) {
      List<Component> lore = new ArrayList<>();

      for (Component line : config.getGuiVoteOptionLore()) {
        lore.add(
            TextUtility.replacePlaceholders(
                line,
                Placeholder.of("{votes}", String.valueOf(pollManager.getVoteCount(poll, i)))));
      }
      ItemStack item =
          ItemUtility.prepareItemstack(
              Material.PAPER,
              TextUtility.replacePlaceholders(
                  config.getGuiVoteOptionName(),
                  Placeholder.of("{index}", String.valueOf(i)),
                  Placeholder.of("{option}", poll.getOptions().get(i))),
              lore);
      this.inventory.setItem(9 + i, item);
    }
    player.openInventory(this.inventory);
  }
}
