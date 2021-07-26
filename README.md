<div align="center">
 
 # UniversalWhitelist
![GitHub License](https://img.shields.io/github/license/MatsG23/UniversalWhitelist)
![Spiget Downloads](https://img.shields.io/spiget/downloads/94686?label=Total%20Spigot%20Downloads)

<br>
 
<img width="20px" src="https://icon-icons.com/icons2/2348/PNG/512/download_arrow_icon_143023.png"></img> [Download](https://github.com/MatsG23/UniversalWhitelist/releases/latest)
</div>

UniversalWhitelist natively runs on **Minecraft 1.17.1**.
I haven't tested it on earlier versions yet, you can definitely give it a try if you run a earlier version of Minecraft.

-----

## Purpose
The plugin provides an improved version of the in-built whitelist system:
 
- it works for Floodgate (Geyser) users
- allows you to customize the whitelist messages and more
- is maintained
-----

## Commands
The plugin overrides the existing `/whitelist` command. All commands start with this command. They will be suggested
in-game, and you can see if you need specific arguments (big thanks to [CommandAPI](https://github.com/JorelAli/CommandAPI)).
**You need the permission _whitelist.manage_ (or OP rights) to use the commands in-game!
If you add a Floodgate user, make sure to add the username prefix (in my case ".")**

### `/whitelist adduuid`
add an uuid to the whitelist

### `/whitelist removeuuid`
removes an uuid from the whitelist

### `/whitelist add`
add a username to the whitelist

### `/whitelist remove`
remove a username from the whitelist

### `/whitelist on`
turn the whitelist on

### `/whitelist off` 
turn the whitelist off

### `/whitelist status`
see the status of the whitelist (on or off)

### `/whitelist list`
see the whitelisted players

### `/whitelist reload`
reload the plugin's config (config.yml)
