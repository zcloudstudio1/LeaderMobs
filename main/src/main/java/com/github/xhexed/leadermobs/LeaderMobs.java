package com.github.xhexed.leadermobs;

import com.github.xhexed.leadermobs.command.CommandManager;
import com.github.xhexed.leadermobs.config.ConfigManager;
import com.github.xhexed.leadermobs.data.PlayerDataManager;
import com.github.xhexed.leadermobs.reward.RewardManager;
import com.github.xhexed.leadermobs.util.MessageManager;
import com.github.xhexed.leadermobs.util.TextMessageParser;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@Getter
public class LeaderMobs extends JavaPlugin {
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private TextMessageParser messageParser;
    private MessageManager messageManager;
    private RewardManager rewardManager;
    public boolean papi;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        playerDataManager = new PlayerDataManager(this);
        messageParser = new TextMessageParser(this);
        messageManager = new MessageManager(this);
        rewardManager = new RewardManager(this);

        PluginCommand command = Objects.requireNonNull(getCommand("lm"));
        CommandManager commandManager = new CommandManager(this);
        command.setExecutor(commandManager);
        command.setTabCompleter(commandManager);

        PluginManager manager = getServer().getPluginManager();
        registerHooks();
        if (manager.isPluginEnabled("PlaceholderAPI")) {
            getLogger().info("Found PlaceholderAPI");
            papi = true;
        }

        reloadPlugin();
    }

    private void registerHooks() {
        PluginManager manager = getServer().getPluginManager();
        boolean found = false;
        if (manager.isPluginEnabled("MythicMobs")) {
            ClassLoader hookLoader = manager.getPlugin("MythicMobs").getClass().getClassLoader();
            try {
                Class.forName("io.lumine.mythic.bukkit.MythicBukkit", false, hookLoader);
                getLogger().info("Found MythicMobs");
                found |= registerPluginHook("com.github.xhexed.leadermobs.listener.MythicMobsListener");
            } catch (ClassNotFoundException e) {
                getLogger().info("Found legacy MythicMobs API");
                found |= registerPluginHook("com.github.xhexed.leadermobs.listener.LegacyMythicMobsListener");
            }
        }
        if (manager.isPluginEnabled("EliteMobs")) {
            getLogger().info("Found EliteMobs");
            found |= registerPluginHook("com.github.xhexed.leadermobs.listener.EliteMobsListener");
        }
        if (!found) {
            getLogger().warning("Couldn't find any custom mobs plugin...");
        }
    }

    private boolean registerPluginHook(String className) {
        try {
            Class.forName(className).getDeclaredConstructor(getClass()).newInstance(this);
            return true;
        } catch (ReflectiveOperationException | LinkageError e) {
            getLogger().log(java.util.logging.Level.SEVERE, "Failed to register mob hook " + className, e);
            return false;
        }
    }


    @Override
    public void onDisable() {
        if (messageManager != null) messageManager.close();
    }

    public void reloadPlugin() {
        configManager.reloadConfig();
        playerDataManager.reloadData();
        rewardManager.reloadData();
    }
}
