package com.github.xhexed.leadermobs.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MessageManager implements MessageSender {
    private MessageSender sender;

    public MessageManager(Plugin plugin) {
        try {
            // Adventure may be present on a hybrid server without native Bukkit methods.
            Class<?> component = Class.forName("net.kyori.adventure.text.Component");
            Class<?> title = Class.forName("net.kyori.adventure.title.Title");
            CommandSender.class.getMethod("sendMessage", component);
            Player.class.getMethod("sendActionBar", component);
            Player.class.getMethod("showTitle", title);
            Class.forName("net.kyori.adventure.text.minimessage.MiniMessage").getDeclaredMethod("miniMessage");
            plugin.getLogger().info("Using native Paper message sender");
            sender = new PaperNativeMessageSender();
        } catch (ReflectiveOperationException | LinkageError e) {
            plugin.getLogger().info("Using Bukkit Adventure message sender");
            sender = new LegacyMessageSender(plugin);
        }
    }

    @Override
    public void close() {
        sender.close();
    }

    @Override
    public void sendMessage(Player player, String message) {
        sender.sendMessage(player, message);
    }

    @Override
    public void sendMessage(CommandSender sender, String message) {
        this.sender.sendMessage(sender, message);
    }

    @Override
    public void sendActionbar(Player player, String message) {
        sender.sendActionbar(player, message);
    }

    @Override
    public void sendTitle(Player player, String title, String subTitle, long fadeIn, long stay, long fadeOut) {
        sender.sendTitle(player, title, subTitle, fadeIn, stay, fadeOut);
    }
}
