package de.tzimon.ffa.managers;

import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

    public static void createScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");

        objective.setDisplayName("§6§lXIVAR");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int counter = 0;
        objective.getScore("§6Xivar.de").setScore(counter++);
        objective.getScore("§0§7-----------").setScore(counter++);
        objective.getScore("§7> §bKillStreak: ").setScore(counter++);
        objective.getScore("").setScore(counter++);
        objective.getScore("§7> §bOnline: ").setScore(counter++);
        objective.getScore(" ").setScore(counter++);
        objective.getScore("§7> §bRank: ").setScore(counter++);
        objective.getScore("  ").setScore(counter++);
        objective.getScore("§7> §bLobby").setScore(counter++);
        objective.getScore("§1§7-----------").setScore(counter++);

        Team killStreakTeam = scoreboard.registerNewTeam("killStreak");
        killStreakTeam.addEntry("§7> §bKillStreak: ");
        Team playersOnlineTeam = scoreboard.registerNewTeam("playersOnline");
        playersOnlineTeam.addEntry("§7> §bOnline: ");
        Team rankTeam = scoreboard.registerNewTeam("rank");
        rankTeam.addEntry("§7> §bRank: ");

        player.setScoreboard(scoreboard);
        updateAll();
    }

    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        scoreboard.getTeam("killStreak").setSuffix("§a" + CustomPlayer.get(player).getKillStreak());
        scoreboard.getTeam("playersOnline").setSuffix("§a" + Bukkit.getOnlinePlayers().size());
        scoreboard.getTeam("rank").setSuffix(player.isOp() ? "§cOP" : "§aPlayer");
    }

    public static void updateAll() {
        Bukkit.getOnlinePlayers().forEach(ScoreboardManager::updateScoreboard);
    }

}
