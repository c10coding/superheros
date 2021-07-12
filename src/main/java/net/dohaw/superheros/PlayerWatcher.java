package net.dohaw.superheros;

import net.dohaw.corelib.StringUtils;
import net.dohaw.superheros.superhero.Superhero;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

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
                SpellType spellType = Spell.getItemSpellType(item);
                SuperheroType playerSuperhero = plugin.getPlayerSuperHeros().get(player.getUniqueId());
                if(playerSuperhero != null && !isOnCooldown(player, spellType)){
                    Spell.castItemSpell(player, playerSuperhero, spellType);
                    putSpellOnCooldown(player, spellType);
                }
            }
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        plugin.setSuperhero(e.getPlayer(), Superhero.THANOS);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        plugin.removeSuperhero(e.getPlayer());
    }

    private void putSpellOnCooldown(Player caster, SpellType spellType){
        List<SpellType> currentSpellsOnCD = spellsOnCooldown.containsKey(caster.getUniqueId()) ? spellsOnCooldown.get(caster.getUniqueId()) : new ArrayList<>();
        currentSpellsOnCD.add(spellType);
        spellsOnCooldown.put(caster.getUniqueId(), currentSpellsOnCD);
        // 30 second cooldown
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            currentSpellsOnCD.remove(spellType);
            caster.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(StringUtils.colorString("&b" + spellType.name() + " is not on cooldown anymore!")));
        }, 30 * 20L);
    }

    private boolean isOnCooldown(Player player, SpellType spellType){
        if(!spellsOnCooldown.containsKey(player.getUniqueId())) return false;
        return spellsOnCooldown.get(player.getUniqueId()).contains(spellType);
    }

}
