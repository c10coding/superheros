package net.dohaw.superheros;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Player> aliveHiders;
    private List<Player> hiders;
    private SuperherosPlugin plugin;

    public Game(SuperherosPlugin plugin, List<Player> hiders){
        this.plugin = plugin;
        this.hiders = hiders;
        this.aliveHiders = new ArrayList<>(hiders);
    }

    public void startGame(){
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
    }

    public void removeHider(Player player){
        aliveHiders.removeIf(p -> p.getUniqueId().equals(player.getUniqueId()));
    }

    public void endGame(){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.teleport(plugin.getBaseConfig().getLobbyLocation());
        }
    }

    public List<Player> getHiders() {
        return hiders;
    }

}
