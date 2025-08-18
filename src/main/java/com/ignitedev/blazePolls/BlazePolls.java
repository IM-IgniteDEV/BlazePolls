package com.ignitedev.blazePolls;

import co.aikar.commands.PaperCommandManager;
import com.twodevsstudio.simplejsonconfig.SimpleJSONConfig;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.ignitedev.blazePolls.command.CreatePollCommand;
import com.ignitedev.blazePolls.command.PollCommand;
import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.listener.PollListeners;
import com.ignitedev.blazePolls.manager.CreationManager;
import com.ignitedev.blazePolls.manager.PollManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlazePolls extends JavaPlugin {

  private PluginConfig pluginConfig;
  private PollManager pollManager;
  private CreationManager creationManager;

  @Override
  public void onEnable() {
    SimpleJSONConfig.INSTANCE.register(this);

    this.pluginConfig = Config.getConfig(PluginConfig.class);

    this.pollManager = new PollManager();
    this.pollManager.initialize();
    this.creationManager = new CreationManager();

    registerListeners();
    registerCommands();
    runTasks();
  }

  private void registerListeners() {
    Bukkit.getPluginManager()
        .registerEvents(new PollListeners(this, pluginConfig, pollManager, creationManager), this);
  }

  private void registerCommands() {
    PaperCommandManager acf = new PaperCommandManager(this);
    acf.registerCommand(new CreatePollCommand(pluginConfig, pollManager, creationManager));
    acf.registerCommand(new PollCommand(pluginConfig, pollManager));
  }

  private void runTasks() {
    long interval = Math.max(1, pluginConfig.getCloseCheckIntervalSeconds());
    Bukkit.getScheduler()
        .runTaskTimer(this, pollManager::checkExpirations, 20L * interval, 20L * interval);
  }

  @Override
  public void onDisable() {}
}
