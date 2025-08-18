package com.ignitedev.blazePolls.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class DurationUtility {

  public long parseToMillis(String text) {
    if (text == null || text.isEmpty()) {
      return 0L;
    }
    long total = 0L;
    StringBuilder num = new StringBuilder();

    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (Character.isDigit(c)) {
        num.append(c);
        continue;
      }
      long n = num.isEmpty() ? 0 : Long.parseLong(num.toString());
      num.setLength(0);
      switch (Character.toLowerCase(c)) {
        case 'd':
          total += TimeUnit.DAYS.toMillis(n);
          break;
        case 'h':
          total += TimeUnit.HOURS.toMillis(n);
          break;
        case 'm':
          total += TimeUnit.MINUTES.toMillis(n);
          break;
        case 's':
          total += TimeUnit.SECONDS.toMillis(n);
          break;
        default:
          break;
      }
    }
    if (!num.isEmpty()) {
      total += TimeUnit.MINUTES.toMillis(Long.parseLong(num.toString()));
    }
    return total;
  }
}
