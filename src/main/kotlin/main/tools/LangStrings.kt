package main.tools

enum class LangStrings(val key: String) {
    NOT_WHITELISTED("messages.not_whitelisted"),
    NO_LONGER_WHITELISTED("messages.no_longer_whitelisted"),
    WHITELIST_NOW_ON("messages.whitelist_now_on"),
    ADDED("messages.added"),
    ALREADY_ADDED("messages.already_added"),
    REMOVED("messages.removed"),
    WHITELIST_TURNED_ON("messages.whitelist_turned_on"),
    WHITELIST_TURNED_OFF("messages.whitelist_turned_off"),
    WHITELIST_ON("messages.whitelist_on"),
    WHITELIST_OFF("messages.whitelist_off"),
    PLAYERS_ON_WHITELIST("messages.players_on_whitelist"),
    CONFIG_RELOADED("messages.config_reloaded")
}