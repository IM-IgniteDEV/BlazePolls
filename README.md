![BlazePolls Banner](https://i.imgur.com/tqubkmM.jpeg)

# BlazePolls
[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21+-blue)](https://www.minecraft.net/)
[![Java Version](https://img.shields.io/badge/Java-21-red)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

Create polls in seconds with a clean inventory UI

![BlazePolls Demo](https://i.imgur.com/HLl6Qjx.gif)

## ✨ Features
- Create polls with a single command, including question and duration
- Simple in-game GUIs for creating, listing, and voting
- Automatic expiration with manual close/remove for admins
- One vote per player, configurable option limits
- Fully configurable messages using MiniMessage
- JSON-based persistence (polls survive restarts)

## 🛠 Commands & Permissions

| Command | Permission | Description |
|---------|------------|-------------|
| `/createpoll <question...>` | `blazepolls.create` | Start guided creation flow & open Create Poll GUI |
| `/poll` | `blazepolls.use` | Open the list of active polls & vote |
| `/poll close <id>` | `blazepolls.admin.close` | Force-close a poll |
| `/poll remove <id>` | `blazepolls.admin.remove` | Delete a poll (removes data file) |

## ⏳ Duration Format
Durations support unit-suffixed tokens:  
`d = days, h = hours, m = minutes, s = seconds`  
Bare numbers are treated as minutes.

**Examples:**
- `10m` = 10 minutes
- `1h30m` = 1 hour 30 minutes
- `2d` = 2 days
- `45` = 45 minutes

## Messages and GUIs use placeholders:  
`{id}`, `{question}`, `{created}`, `{duration}`, `{index}`, `{option}`, `{votes}`

## 💾 Data Storage
- Each poll is saved as JSON in the plugin data folder
- Powered by SimpleJSONConfig service
- Polls persist across restarts
- Removing a poll deletes its file

## ✅ Requirements
- PaperMC 1.21+
- Java 21 runtime (JDK 21 for building)

## Why BlazePolls?
- Easy to use, fully GUI-driven
- Reliable JSON storage system
- Highly customizable messages with MiniMessage
- Perfect for community servers and events

**Get BlazePolls Today!**  
Simple, fast, and powerful polling for your Minecraft server.


