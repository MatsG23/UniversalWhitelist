package main

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandAPIConfig
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.arguments.UUIDArgument
import dev.jorel.commandapi.executors.CommandExecutor
import main.tools.LangHelper
import main.tools.LangStrings
import main.tools.YMLHelper
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*


class UniversalWhitelist : JavaPlugin() {
    private val whitelist = YMLHelper("whitelist", this)
    private val langHelper = LangHelper(config)

    companion object {
        var whitelistActivated = true
    }

    override fun onLoad() = CommandAPI.onLoad(CommandAPIConfig())

    override fun onEnable() {
        CommandAPI.onEnable(this)
        saveDefaultConfig()
        if (server.hasWhitelist() && server.isWhitelistEnforced) {
            val whitelistedPlayers = server.whitelistedPlayers
            for (player in whitelistedPlayers) {
                whitelist.set(player.uniqueId.toString(), player.name)
            }
            server.setWhitelist(false)
        }

        server.pluginManager.registerEvents(LoginListener(whitelist, langHelper), this)
        registerCommands()
    }

    private fun registerCommands() {
        val addUUID = CommandAPICommand("adduuid")
            .withArguments(UUIDArgument("a uuid"))
            .executes(CommandExecutor { sender, args ->
                val uuid = args[0] as UUID

                if (whitelist.getString(uuid.toString()) != null) {
                    sender.sendMessage(
                        "${ChatColor.YELLOW}${langHelper.get(LangStrings.ALREADY_ADDED).format(uuid)}"
                    )
                    return@CommandExecutor
                }

                whitelist.set(uuid.toString(), uuid)
                sender.sendMessage(
                    "${ChatColor.GREEN}${langHelper.get(LangStrings.ADDED).format(uuid)}"
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
                        Component.text(langHelper.get(LangStrings.NO_LONGER_WHITELISTED)),
                        PlayerKickEvent.Cause.WHITELIST
                    )
                }

                sender.sendMessage(
                    "${ChatColor.GREEN}${langHelper.get(LangStrings.REMOVED).format(uuid)}"
                )
            })

        val add = CommandAPICommand("add")
            .withArguments(StringArgument("playerName"))
            .executes(CommandExecutor { sender, args ->
                val playerName = args[0] as String

                for (player in whitelist.getValues(false).values) {
                    if (player == playerName) {
                        sender.sendMessage(
                            "${ChatColor.YELLOW}${langHelper.get(LangStrings.ALREADY_ADDED).format(playerName)}"
                        )
                        return@CommandExecutor
                    }
                }

                whitelist.set(playerName, playerName)
                sender.sendMessage(
                    "${ChatColor.GREEN}${langHelper.get(LangStrings.ADDED).format(playerName)}"
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
                            Component.text(langHelper.get(LangStrings.NO_LONGER_WHITELISTED)),
                            PlayerKickEvent.Cause.WHITELIST
                        )
                    }

                    sender.sendMessage(
                        "${ChatColor.GREEN}${langHelper.get(LangStrings.REMOVED).format(playerName)}"
                    )
                }

                if (!onWhitelist) CommandAPI.fail("'$playerName' was not on the whitelist")
            })

        val turnOn = CommandAPICommand("on")
            .executes(CommandExecutor { sender, _ ->
                whitelistActivated = true

                server.onlinePlayers.forEach { player ->
                    val uuid = player.uniqueId
                    if (whitelist.get(uuid.toString()) == null) {
                        player.kick(Component.text(langHelper.get(LangStrings.WHITELIST_NOW_ON)))
                    }
                }

                sender.sendMessage(
                    "${ChatColor.GREEN}${langHelper.get(LangStrings.WHITELIST_TURNED_ON)}"
                )
            })

        val turnOff = CommandAPICommand("off")
            .executes(CommandExecutor { sender, _ ->
                whitelistActivated = false
                sender.sendMessage(
                    "${ChatColor.RED}${langHelper.get(LangStrings.WHITELIST_TURNED_OFF)}"
                )
            })

        val status = CommandAPICommand("status")
            .executes(CommandExecutor { sender, _ ->
                if (whitelistActivated) {
                    sender.sendMessage(
                        "${ChatColor.GREEN}${langHelper.get(LangStrings.WHITELIST_ON)}"
                    )
                } else {
                    sender.sendMessage(
                        "${ChatColor.YELLOW}${langHelper.get(LangStrings.WHITELIST_OFF)}"
                    )
                }
            })

        val listPlayers = CommandAPICommand("list")
            .executes(CommandExecutor { sender, _ ->
                val whitelistedPlayers = whitelist.getValues(false).values
                if (whitelistedPlayers.isEmpty()) {
                    CommandAPI.fail("The whitelist is empty")
                    return@CommandExecutor
                }

                val listString = whitelistedPlayers.toTypedArray().joinToString(", ")
                sender.sendMessage(
                    "${ChatColor.YELLOW}${langHelper.get(LangStrings.PLAYERS_ON_WHITELIST).format(listString)}"
                )
            })

        val reload = CommandAPICommand("reload")
            .executes(CommandExecutor { sender, _ ->
                reloadConfig()
                sender.sendMessage(
                    "${ChatColor.GREEN}${langHelper.get(LangStrings.CONFIG_RELOADED)}"
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
