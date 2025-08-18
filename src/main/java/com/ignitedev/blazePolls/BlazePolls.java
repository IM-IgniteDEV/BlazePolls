package com.ignitedev.blazePolls;

import co.aikar.commands.PaperCommandManager;
import com.ignitedev.blazePolls.command.CreatePollCommand;
import com.ignitedev.blazePolls.command.PollCommand;
import com.ignitedev.blazePolls.config.PluginConfig;
import com.ignitedev.blazePolls.listener.CreatePollClickListener;
import com.ignitedev.blazePolls.listener.CreationChatListener;
import com.ignitedev.blazePolls.listener.PollListClickListener;
import com.ignitedev.blazePolls.listener.VoteClickListener;
import com.ignitedev.blazePolls.manager.CreationManager;
import com.ignitedev.blazePolls.manager.PollManager;
import com.twodevsstudio.simplejsonconfig.SimpleJSONConfig;
import com.twodevsstudio.simplejsonconfig.api.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlazePolls extends JavaPlugin {

  private PollManager pollManager;
  private CreationManager creationManager;

  @Override
  public void onEnable() {
    SimpleJSONConfig.INSTANCE.register(this);

    this.pollManager = new PollManager();
    this.creationManager = new CreationManager();

    registerListeners();
    registerCommands();
    initializeTasks();
  }

  private void registerListeners() {
    Bukkit.getPluginManager().registerEvents(new PollListClickListener(pollManager), this);
    Bukkit.getPluginManager().registerEvents(new VoteClickListener(pollManager), this);
    Bukkit.getPluginManager().registerEvents(new CreatePollClickListener(pollManager, creationManager), this);
    Bukkit.getPluginManager().registerEvents(new CreationChatListener(this, pollManager, creationManager), this);
  }

  private void registerCommands() {
    PaperCommandManager acf = new PaperCommandManager(this);

    acf.registerCommand(new CreatePollCommand(pollManager, creationManager));
    acf.registerCommand(new PollCommand(pollManager));
  }

  private void initializeTasks() {
    PluginConfig config = Config.getConfig(PluginConfig.class);

    getServer().getGlobalRegionScheduler().runAtFixedRate(
        this,
        scheduledTask -> pollManager.checkExpirations(),
        20L * config.getCloseCheckIntervalSeconds(),
        20L * config.getCloseCheckIntervalSeconds()
    );
  }

  @Override
  public void onDisable() {}
}
