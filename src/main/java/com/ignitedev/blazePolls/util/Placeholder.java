package com.ignitedev.blazePolls.util;

import net.kyori.adventure.text.Component;

public record Placeholder(String key, Component value) {
  public static Placeholder of(String key, String value) {
    return new Placeholder(key, Component.text(value));
  }

  public static Placeholder of(String key, Component value) {
    return new Placeholder(key, value);
  }
}
