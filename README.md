<div align="center">
 
 # UniversalWhitelist
![GitHub License](https://img.shields.io/github/license/MatsG23/UniversalWhitelist)
![Spiget Downloads](https://img.shields.io/spiget/downloads/94686?label=Total%20Spigot%20Downloads)
 
<img width="150px" src="https://raw.githubusercontent.com/MatsG23/UniversalWhitelist/master/stop-sign.png"></img>

[Download](https://github.com/MatsG23/UniversalWhitelist/releases/latest)
</div>

**Since August 2021, this plugin has not received any update. I have stopped maintaining it in favor of the [new whitelist command in Floodgate 2.0](https://wiki.geysermc.org/floodgate/features/#whitelist-command).**

-----

UniversalWhitelist runs natively on **Minecraft 1.17.1**.

-----

## Description
This plugin improves the in-built whitelist system.

New/improved features:
- can block Bedrock (Geyser) players
- all messages are fully customizable and translatable

-----

## Commands
The default `/whitelist` command is overridden and extended.
**For all commands you need the permission _whitelist.manage_.
Also, if you add a Floodgate user, make sure to add the username prefix as well (by default ".").**

I tried my best to make the command names self-explanatory. Nonetheless,
here you see which command does what:

### `/whitelist adduuid <uuid>`
add an uuid to the whitelist

### `/whitelist removeuuid <uuid>`
removes an uuid from the whitelist

### `/whitelist add <name>`
add a username to the whitelist

### `/whitelist remove <name>`
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
