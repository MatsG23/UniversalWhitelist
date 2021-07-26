package main

import main.tools.YMLHelper
import net.kyori.adventure.text.Component
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent


class LoginListener(private val whitelist: YMLHelper, private val pluginConfig: FileConfiguration) : Listener {
    @EventHandler
    private fun onPlayerLogin(event: AsyncPlayerPreLoginEvent) {
        if (pluginConfig.getBoolean("activated") && !getPlayerIsOnWhitelist(event)) {
            event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                Component.text(pluginConfig.getString("messages.not_whitelisted")!!)
            )
        }
    }

    private fun getPlayerIsOnWhitelist(client: AsyncPlayerPreLoginEvent): Boolean {
        val name = client.name
        val uuid = client.uniqueId.toString()

        return when {
            whitelist.contains(name) -> {
                whitelist.set(name, null)
                whitelist.set(uuid, name)
                true
            }
            whitelist.contains(uuid) -> {
                if (whitelist.getString(uuid) != name) whitelist.set(uuid, name)
                true
            }
            else -> false
        }
    }
}
