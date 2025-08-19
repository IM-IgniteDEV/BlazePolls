package com.ignitedev.blazePolls.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@UtilityClass
public class ItemUtility {

  public ItemStack prepareItemstack(Material material, Component name, List<Component> lore) {
    ItemStack stack = new ItemStack(material);
    ItemMeta meta = stack.getItemMeta();
    if (meta == null) {
      return stack;
    }
    if (name != null) {
      meta.displayName(name);
    }
    if (lore != null) {
      meta.lore(lore);
    }
    meta.addItemFlags(ItemFlag.values());
    stack.setItemMeta(meta);
    return stack;
  }
}
