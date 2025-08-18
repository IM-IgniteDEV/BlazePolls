package com.ignitedev.blazePolls.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TextUtility {

  public String color(String input) {
    return ChatColor.translateAlternateColorCodes('&', input);
  }

  public List<String> color(List<String> lines) {
    return lines.stream().map(TextUtility::color).collect(Collectors.toList());
  }

  public Component component(String input) {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(input);
  }

  public List<Component> components(List<String> lines) {
    return lines.stream().map(TextUtility::component).collect(Collectors.toList());
  }
}
