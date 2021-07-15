package net.dohaw.superheros;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private boolean isGameRunning;

    private List<Player> aliveHiders;
    private List<Player> hiders;
    private SuperherosPlugin plugin;

    private BukkitTask timer;

    public Game(SuperherosPlugin plugin, List<Player> hiders){
        this.plugin = plugin;
        this.hiders = hiders;
        this.aliveHiders = new ArrayList<>(hiders);
    }

    public void startGame(){
        this.isGameRunning = true;
        new BukkitRunnable(){
            @Override
            public void run() {
                if(aliveHiders.size() == 0){
                    Player seeker = plugin.getSeeker();
                    seeker.sendTitle("You have won the game!", "");
                    cancel();
                    endGame();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
        // 10 minute timer
        this.timer = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(isGameRunning){
                for(Player hider : hiders){
                    hider.sendTitle("You have won the game!", "");
                }
                Player seeker = plugin.getSeeker();
                seeker.sendTitle("Time has ran out", "You lost...");
                endGame();
            }
        }, 20 * 600);
    }

    public void removeHider(Player player){
        aliveHiders.removeIf(p -> p.getUniqueId().equals(player.getUniqueId()));
    }

    public void endGame(){
        this.isGameRunning = false;
        for(Player player : Bukkit.getOnlinePlayers()){
            player.teleport(plugin.getBaseConfig().getLobbyLocation());
        }
        timer.cancel();
    }

    public List<Player> getHiders() {
        return hiders;
    }

}
