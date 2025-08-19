# BlazePolls

Create a poll in seconds, let players vote via a clean inventory UI, Includes automatic expiration and admin controls.

- Platform: Paper 1.21+
- Java: 21

## Features
- Create polls with a duration and question in one command.
- In‑game GUIs for creating, listing, and voting.
- Auto close on expiration, manual close/remove by admins.
- One vote per player; configurable option limit.
- JSON persistence; survives restarts.
- Fully configurable messages (MiniMessage components).

## Requirements
- PaperMC 1.21 or newer
- Java 21 runtime (JDK 21 for building)

## Commands & Permissions
- /createpoll <duration> <question...>
  - Permission: `blazepolls.create`
  - Starts the guided creation flow and opens the Create Poll GUI.

- /poll
  - Permission: `blazepolls.use`
  - Opens the list of active polls; click to open a poll and vote.

- /poll close <pollId>
  - Permission: `blazepolls.admin.close`
  - Force-closes a poll by ID.

- /poll remove <pollId>
  - Permission: `blazepolls.admin.remove`
  - Deletes a poll by ID (removes data file).

## Duration format
Durations support unit-suffixed tokens:
- d = days, h = hours, m = minutes, s = seconds
- A bare number without a unit is treated as minutes.

Examples:
- `10m` (10 minutes)
- `1h30m`
- `2d` (2 days)
- `45` (45 minutes, because no unit)

## Typical workflow
1. Run: `/createpoll 15m Which kit should we use next?`
2. In the GUI, add options (e.g., "Warrior", "Archer", "Mage") and press Start.
3. Players run `/poll`, select the poll, and click an option to vote.
4. The poll auto-closes when time is up, or an admin uses `/poll close <id>`.

## Configuration (config.json)
Key options (defaults shown):
- guiListTitle: "Active Polls"
- guiVoteTitle: "Poll"
- guiCreateTitle: "Create Poll"
- maxOptions: 6
- listGuiSize: 54 (use multiples of 9 up to 54)
- closeCheckIntervalSeconds: 60 (how often to scan for expired polls)
- Messages and GUI texts are Adventure Components using MiniMessage; placeholders include:
  - `{id}`, `{question}`, `{created}`, `{duration}`, `{index}`, `{option}`, `{votes}`

Note: You can freely customize colors and wording using MiniMessage tags.

## Data storage
- Each poll is stored as a JSON file under the `polls/` directory in the plugin data folder.
- Storage is powered by SimpleJSONConfig using a typed Service for `Poll` entities.
- Polls persist across restarts; removing a poll deletes its JSON file.