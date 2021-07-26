<div align="center">
 
 # UniversalWhitelist
![GitHub License](https://img.shields.io/github/license/JorelAli/CommandAPI)
![Spiget Download Size](https://img.shields.io/spiget/download-size/62353)
![Spiget Downloads](https://img.shields.io/spiget/downloads/62353?label=Total%20Spigot%20Downloads)

<br>
<img width="20px" style="vertical-align: middle" src="https://icon-icons.com/icons2/2348/PNG/512/download_arrow_icon_143023.png"></img>
[Download (Spigot)](https://google.de)
</div>

UniversalWhitelist runs on **Minecraft 1.13.x - 1.17.1**

-----

## Purpose
The plugin provides an improved version of the in-built whitelist system:
 
- it works for Floodgate (Geyser) users
- allows you to customize the whitelist messages and more
- is maintained
-----

## Commands

The plugin overrides the existing `/whitelist` command and uses it as a plugin interface.
That means that all commands must be added as a suffix. All commands will be suggested
in-game, and you can see if you need specific arguments 
(big thanks to [CommandAPI](https://github.com/JorelAli/CommandAPI)).
**You need the permission _whitelist.manage_ (or OP rights) to use the commands in-game!
If you add a Floodgate user, make sure to add the username prefix (in my case ".")**

### `adduuid`
add an uuid to the whitelist

### `removeuuid`
removes an uuid from the whitelist

### `add`
add a username to the whitelist

### `remove`
remove a username from the whitelist

### `on`
turn the whitelist on

### `off` 
turn the whitelist off

### `status`
see the status of the whitelist (on or off)

### `list`
see the whitelisted players

### `reload`
reload the plugin's config (config.yml)

