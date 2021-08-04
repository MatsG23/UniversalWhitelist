<div style="display: flex; flex-direction: column; align-items: center; justify-content: center;">
 
 # UniversalWhitelist
![GitHub License](https://img.shields.io/github/license/MatsG23/UniversalWhitelist)
![Spiget Downloads](https://img.shields.io/spiget/downloads/94686?label=Total%20Spigot%20Downloads)
 
<img width="150px" src="https://github.com/MatsG23/reference-images/raw/main/stop.png"></img>

<img width="20px" src="https://icon-icons.com/icons2/2348/PNG/512/download_arrow_icon_143023.png"></img> [Download](https://github.com/MatsG23/UniversalWhitelist/releases/latest)
</div>

UniversalWhitelist natively runs on **Minecraft 1.17.1**.
I didn't test it on other versions, but you should definitely try it!

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
