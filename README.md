# LeaderMobs
 - A plugin inspired by Brian's BossesExpansion which broadcasts top damage count by players. Supports MythicMobs and Boss.
# Notes / Limitations
 - ???

## Minecraft 1.21.1

This branch targets Bukkit/Spigot and Paper 1.21.1 with Java 21.
It is a server plugin: put the built JAR in `plugins/`, not `mods/`.
Hybrid servers such as Youer require an in-server compatibility test.

Install a Minecraft-1.21.1-compatible release of MythicMobs and/or EliteMobs.
The modern MythicMobs hook compiles against 5.6.2 and EliteMobs against 9.1.10;
these compile-time API versions do not certify every provider release on 1.21.1.
PlaceholderAPI remains optional and now loads before LeaderMobs.
Existing `config.yml`, `rewards.yml`, commands and reward rules are preserved.
Back up the LeaderMobs folder before replacing the old JAR and fully restart.

### Build

Use JDK 21, then run `bash gradlew clean build` (Windows: `gradlew.bat clean build`).
The installable file is `build/libs/LeaderMobs-2.2.3-mc1.21.1.jar`.
Root `build` also runs the subproject tests. GitHub Actions uploads the JAR
as the `Artifacts` workflow artifact after a successful build.

### Verify on the server

- Startup: LeaderMobs enables, registers installed mob hooks, and logs the message sender.
- Spawn a configured MythicMobs/EliteMobs mob: check its spawn announcement.
- Attack with two players, melee and projectiles: check damage rankings and percentages.
- Kill the mob: check death chat, titles, actionbar, and configured reward commands.
- Check incoming mob damage rankings and configured minimum damage/player conditions.
- Test `/lm toggle`, `/lm reload`, permissions, and PlaceholderAPI substitutions.
- Restart and verify configuration and player preferences are retained.

Local validation in the editing environment was blocked before compilation:
Gradle 8.10.1 could not be downloaded (`Network is unreachable`), and only
Java 17 was installed. No live Minecraft server test has been performed.

References: [Paper 1.21.1 API](https://jd.papermc.io/paper/1.21.1/)
and [Adventure platform 4.3.4 fixes](https://github.com/PaperMC/adventure-platform/releases/tag/v4.3.4).
