package com.ignitedev.blazePolls.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@UtilityClass
public class ItemUtility {

  public ItemStack prepareItemstack(Material material, String name, List<String> lore) {
    ItemStack stack = new ItemStack(material);
    ItemMeta meta = stack.getItemMeta();

    if (meta == null) {
      return stack;
    }
    if (name != null) {
      meta.displayName(TextUtility.component(name));
    }
    if (lore != null) {
      meta.lore(TextUtility.components(lore));
    }
    meta.addItemFlags(ItemFlag.values());
    stack.setItemMeta(meta);
    return stack;
  }
}
