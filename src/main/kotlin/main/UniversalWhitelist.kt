package main

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.arguments.UUIDArgument
import dev.jorel.commandapi.executors.CommandExecutor
import main.tools.YMLHelper
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*


class UniversalWhitelist : JavaPlugin() {
    private val whitelist = YMLHelper("whitelist", this)

    override fun onEnable() {
        saveDefaultConfig()
        if (server.hasWhitelist() && server.isWhitelistEnforced) {
            val whitelistedPlayers = server.whitelistedPlayers
            for (player in whitelistedPlayers) {
                whitelist.set(player.uniqueId.toString(), player.name)
            }
            server.setWhitelist(false)
        }

        server.pluginManager.registerEvents(LoginListener(whitelist, config), this)
        registerCommands()
    }

    private fun registerCommands() {
        val addUUID = CommandAPICommand("adduuid")
            .withArguments(UUIDArgument("a uuid"))
            .executes(CommandExecutor { sender, args ->
                val uuid = args[0] as UUID

                if (whitelist.getString(uuid.toString()) != null) {
                    sender.sendMessage(
                        ChatColor.YELLOW.toString() + config.getString("messages.uuid_already_added")!!.format(uuid)
                    )
                    return@CommandExecutor
                }

                whitelist.set(uuid.toString(), uuid)
                sender.sendMessage(
                    ChatColor.GREEN.toString() + config.getString("messages.uuid_added")!!.format(uuid)
                )
            })

        val removeUUID = CommandAPICommand("removeuuid")
            .withArguments(UUIDArgument("a uuid"))
            .executes(CommandExecutor { sender, args ->
                val uuid = args[0] as UUID
                if (whitelist.contains(uuid.toString())) return@CommandExecutor CommandAPI.fail("UUID is already on the whitelist")

                whitelist.set(uuid.toString(), null)
                server.getPlayer(uuid)?.run {
                    kick(
                        Component.text(config.getString("messages.kicked_no_longer_whitelisted")!!),
                        PlayerKickEvent.Cause.WHITELIST
                    )
                }

                sender.sendMessage(
                    ChatColor.GREEN.toString() + config.getString("messages.uuid_removed")!!.format(uuid)
                )
            })

        val add = CommandAPICommand("add")
            .withArguments(StringArgument("playerName"))
            .executes(CommandExecutor { sender, args ->
                val playerName = args[0] as String

                for (player in whitelist.getValues(false).values) {
                    if (player == playerName) {
                        return@CommandExecutor sender.sendMessage(
                            ChatColor.YELLOW.toString() + config.getString("messages.player_already_added")!!
                                .format(playerName)
                        )
                    }
                }

                whitelist.set(playerName, playerName)
                sender.sendMessage(
                    ChatColor.GREEN.toString() + config.getString("messages.player_added")!!.format(playerName)
                )
            })

        val remove = CommandAPICommand("remove")
            .withArguments(StringArgument("playerName"))
            .executes(CommandExecutor { sender, args ->
                val playerName = args[0] as String
                var onWhitelist = false
                for (playerKey in whitelist.getKeys(false)) {
                    if (whitelist.getString(playerKey) != playerName) continue

                    onWhitelist = true
                    whitelist.set(playerKey, null)
                    Bukkit.getPlayerExact(playerName)?.run {
                        kick(
                            Component.text(config.getString("messages.kicked_no_longer_whitelisted")!!),
                            PlayerKickEvent.Cause.WHITELIST
                        )
                    }
                    sender.sendMessage(
                        ChatColor.GREEN.toString() + config.getString("messages.player_removed")!!.format(playerName)
                    )
                }

                if (!onWhitelist) CommandAPI.fail("'$playerName' was not on the whitelist")
            })

        val turnOn = CommandAPICommand("on")
            .executes(CommandExecutor { sender, _ ->
                config.set("activated", true)
                sender.sendMessage(
                    ChatColor.GREEN.toString() + config.getString("messages.whitelist_turned_on")
                )
            })

        val turnOff = CommandAPICommand("off")
            .executes(CommandExecutor { sender, _ ->
                config.set("activated", false)
                sender.sendMessage(
                    ChatColor.RED.toString() + config.getString("messages.whitelist_turned_off")
                )
            })

        val status = CommandAPICommand("status")
            .executes(CommandExecutor { sender, _ ->
                val whitelistStatus = config.getBoolean("activated")
                if (whitelistStatus) {
                    sender.sendMessage(
                        ChatColor.GREEN.toString() + config.getString("messages.whitelist_on")
                    )
                } else {
                    sender.sendMessage(
                        ChatColor.YELLOW.toString() + config.getString("messages.whitelist_off")
                    )
                }
            })

        val listPlayers = CommandAPICommand("list")
            .executes(CommandExecutor { sender, _ ->
                val whitelistedPlayers = whitelist.getValues(false).values
                if (whitelistedPlayers.isEmpty()) return@CommandExecutor CommandAPI.fail("The whitelist is empty")

                val listString = whitelistedPlayers.toTypedArray().joinToString(", ")
                sender.sendMessage(
                    ChatColor.YELLOW.toString() + config.getString("messages.players_on_whitelist")!!.format(listString)
                )
            })

        val reload = CommandAPICommand("reload")
            .executes(CommandExecutor { sender, _ ->
                reloadConfig()
                sender.sendMessage(
                    ChatColor.GREEN.toString() + config.getString("messages.config_reloaded")
                )
            })

        CommandAPI.unregister("whitelist", true)
        CommandAPICommand("whitelist")
            .withSubcommand(addUUID)
            .withSubcommand(removeUUID)
            .withSubcommand(add)
            .withSubcommand(remove)
            .withSubcommand(turnOn)
            .withSubcommand(turnOff)
            .withSubcommand(status)
            .withSubcommand(listPlayers)
            .withSubcommand(reload)
            .withPermission("whitelist.manage")
            .register()
    }
}
