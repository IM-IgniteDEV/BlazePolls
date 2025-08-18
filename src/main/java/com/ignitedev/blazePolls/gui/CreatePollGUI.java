package com.ignitedev.blazePolls.gui;

import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.manager.CreationManager;
import com.ignitedev.blazePolls.manager.CreationSession;
import com.ignitedev.blazePolls.manager.PollManager;
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
public class CreatePollGUI implements InventoryHolder {
  private final PluginConfig config;
  private final PollManager pollManager;
  private final CreationManager creationManager;
  @Getter private final CreationSession session;
  @Getter private Inventory inventory;

  public void open(Player player) {
    this.inventory = Bukkit.createInventory(this, 27, config.getGuiCreateTitle());

    List<Component> questionLore = new ArrayList<>();

    questionLore.add(config.getGuiCreateQuestionLabel());
    questionLore.add(Component.text(session.getQuestion()));
    this.inventory.setItem(
        0,
        ItemUtility.prepareItemstack(
            Material.OAK_SIGN, config.getGuiCreateQuestionItemName(), questionLore));

    List<Component> durationLore = new ArrayList<>();

    for (Component line : config.getGuiCreateDurationLore()) {
      durationLore.add(
          TextUtility.replacePlaceholders(
              line,
              Placeholder.of(
                  "{duration}",
                  (session.getDurationMillis() <= 0
                      ? config.getGuiCreateDurationNone()
                      : (session.getDurationMillis() / 1000) + "s"))));
    }
    this.inventory.setItem(
        1,
        ItemUtility.prepareItemstack(
            Material.CLOCK, config.getGuiCreateDurationItemName(), durationLore));

    List<Component> addLore = new ArrayList<>();

    for (Component line : config.getGuiCreateAddOptionLore()) {
      addLore.add(
          TextUtility.replacePlaceholders(
              line, Placeholder.of("{max}", String.valueOf(config.getMaxOptions()))));
    }
    this.inventory.setItem(
        3,
        ItemUtility.prepareItemstack(
            Material.ANVIL, config.getGuiCreateAddOptionItemName(), addLore));

    this.inventory.setItem(
        4,
        ItemUtility.prepareItemstack(
            Material.BARRIER,
            config.getGuiCreateRemoveLastItemName(),
            config.getGuiCreateRemoveLastLore()));

    this.inventory.setItem(
        7,
        ItemUtility.prepareItemstack(
            Material.LIME_WOOL,
            config.getGuiCreateStartItemName(),
            config.getGuiCreateStartLore()));

    this.inventory.setItem(
        8,
        ItemUtility.prepareItemstack(
            Material.RED_WOOL,
            config.getGuiCreateCancelItemName(),
            config.getGuiCreateCancelLore()));

    for (int i = 0; i < session.getOptions().size() && i < config.getMaxOptions(); i++) {
      ItemStack item =
          ItemUtility.prepareItemstack(
              Material.PAPER,
              TextUtility.replacePlaceholders(
                  config.getGuiCreateOptionName(),
                  Placeholder.of("{index}", String.valueOf(i)),
                  Placeholder.of("{option}", session.getOptions().get(i))),
              config.getGuiCreateOptionEditHintLore());
      this.inventory.setItem(9 + i, item);
    }
    player.openInventory(this.inventory);
  }
}
