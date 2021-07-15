package net.dohaw.superheros.listener;

import net.dohaw.corelib.StringUtils;
import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.Superhero;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.loki.Dagger;
import net.dohaw.superheros.util.LocationUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.*;

public class PlayerWatcher implements Listener {

    private Map<UUID, List<SpellType>> spellsOnCooldown = new HashMap<>();
    private SuperherosPlugin plugin;

    public PlayerWatcher(SuperherosPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCastSpell(PlayerInteractEvent e){

        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        Action action = e.getAction();
        if(item != null && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)){
            if(Spell.isSpellItem(item)){
                e.setCancelled(true);
                SpellType spellType = Spell.getItemSpellType(item);
                SuperheroType playerSuperhero = plugin.getPlayerSuperHeros().get(player.getUniqueId());
                if(playerSuperhero != null){
                    if(!isOnCooldown(player, spellType)){
                        boolean isSuccessfullyCasted = Spell.castItemSpell(plugin, player, playerSuperhero, spellType);
                        if(isSuccessfullyCasted){
                            putSpellOnCooldown(player, spellType);
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "This spell is on cooldown!");
                    }
                }
            }
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        plugin.setSuperhero(e.getPlayer(), Superhero.SPIDER_MAN);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        plugin.removeSuperhero(e.getPlayer());
    }

    // Doesn't let them drop loki's dagger
    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        ItemStack stack = e.getItemDrop().getItemStack();
        ItemMeta meta = stack.getItemMeta();
        if(meta != null && meta.getPersistentDataContainer().has(Dagger.DAGGER_KEY, PersistentDataType.STRING)){
            e.getPlayer().sendMessage(ChatColor.RED + "You can't drop this dagger!");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if(LocationUtil.hasMoved(e.getTo(), e.getFrom(), true)){
            Block blockInFront = getLocationInFront(player.getLocation()).add(0, 0.2, 0).getBlock();
            Map<UUID, SuperheroType> superheros = plugin.getPlayerSuperHeros();
            if(superheros.containsKey(player.getUniqueId()) && superheros.get(player.getUniqueId()) == SuperheroType.SPIDER_MAN && blockInFront.getType().isSolid()){
                if(player.isSneaking()){
                    Vector vel = player.getVelocity();
                    if(vel.getY() < 0.3){
                        player.setVelocity(vel.add(new Vector(0, 0.1, 0)));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSeekerTouchHider(EntityDamageByEntityEvent e){
        Entity eDamager = e.getDamager();
        Entity eDamaged = e.getEntity();
        if(eDamaged instanceof Player && eDamager instanceof Player){
            Player damaged = (Player) eDamaged;
            if(eDamager.getUniqueId().equals(plugin.getSeekerUUID())){
                damaged.sendMessage(ChatColor.RED + "You have been tagged!");
                damaged.setGameMode(GameMode.SPECTATOR);
                Spell.playSound(damaged, Sound.ENTITY_GENERIC_EXPLODE);
                plugin.getGame().removeHider(damaged);
            }
        }
    }

    @EventHandler
    public void onSeekerDeath(EntityDeathEvent e){
        if(e.getEntity() instanceof Player && e.getEntity().getUniqueId().equals(plugin.getSeekerUUID())){
            plugin.getGame().getHiders().forEach(player -> {
                player.sendTitle("The seekers have won!", "");
                plugin.getGame().endGame();
            });
        }
    }

    private void putSpellOnCooldown(Player caster, SpellType spellType){
        List<SpellType> currentSpellsOnCD = spellsOnCooldown.containsKey(caster.getUniqueId()) ? spellsOnCooldown.get(caster.getUniqueId()) : new ArrayList<>();
        currentSpellsOnCD.add(spellType);
        spellsOnCooldown.put(caster.getUniqueId(), currentSpellsOnCD);
        // 30 second cooldown
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            currentSpellsOnCD.remove(spellType);
            String prettySpellName = org.apache.commons.lang.StringUtils.capitalize(spellType.name().replace("_", " "));
            caster.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(StringUtils.colorString("&b" + prettySpellName + " is not on cooldown anymore!")));
        }, 30 * 20L);
    }

    private boolean isOnCooldown(Player player, SpellType spellType){
        if(!spellsOnCooldown.containsKey(player.getUniqueId())) return false;
        return spellsOnCooldown.get(player.getUniqueId()).contains(spellType);
    }

    private Location getLocationInFront(Location loc){
        Location noPitch = loc.clone();
        noPitch.setPitch(0);
        return noPitch.add(loc.clone().getDirection().multiply(1.5));
    }

}
