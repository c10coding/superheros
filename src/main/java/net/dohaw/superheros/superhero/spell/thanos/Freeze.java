package net.dohaw.superheros.superhero.spell.thanos;

import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import net.dohaw.superheros.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class Freeze extends SpellWrapper implements Listener {

    private HashSet<UUID> frozenPlayers = new HashSet<>();

    public Freeze() {
        super(SpellType.FREEZE, SuperheroType.THANOS);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(LocationUtil.hasMoved(e.getTo(), e.getFrom(), false) && frozenPlayers.contains(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
        }
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        for(UUID uuid : plugin.getPlayerSuperHeros().keySet()){
            if(!uuid.equals(plugin.getSeekerUUID())){
                Player player = Bukkit.getPlayer(uuid);
                if(player != null && !frozenPlayers.contains(player.getUniqueId())){
                    frozenPlayers.add(player.getUniqueId());
                    Spell.playSound(player, Sound.BLOCK_CHAIN_PLACE);
                    Spell.spawnParticle(player, Particle.VILLAGER_ANGRY, 30, 0.5f, 0.5f,0.5f);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                       frozenPlayers.remove(player.getUniqueId());
                    }, 20L * 10);
                }
            }
        }
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.ICE;
    }

    @Override
    public String getItemDisplayName() {
        return "&eFreeze";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Freezes your enemies in their tracks");
    }

}
