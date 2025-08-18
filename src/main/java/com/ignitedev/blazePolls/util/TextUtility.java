package com.ignitedev.blazePolls.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TextUtility {

  public Component component(String input) {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(input);
  }

  public List<Component> components(List<String> lines) {
    return lines.stream().map(TextUtility::component).collect(Collectors.toList());
  }

  public Component replacePlaceholder(Component component, Placeholder placeholder){
    return component.replaceText(
        TextReplacementConfig.builder()
            .matchLiteral(placeholder.key())
            .replacement(placeholder.value())
            .build());
  }

  public Component replacePlaceholders(Component component, Placeholder... placeholders) {
    for (Placeholder placeholder : placeholders) {
      component = replacePlaceholder(component, placeholder);
    }
    return component;
  }

  @SneakyThrows
  public Component parseMiniMessage(Component toParse) {
    return parseMiniMessage(MiniMessage.miniMessage().serialize(toParse));
  }

  @SneakyThrows
  public Component parseMiniMessage(String toParse) {
    return MiniMessage.miniMessage().deserialize(toParse);
  }

  @SneakyThrows
  public List<Component> parseMiniMessage(List<String> toParse) {
    return toParse.stream().map(TextUtility::parseMiniMessage).collect(Collectors.toList());
  }
}
