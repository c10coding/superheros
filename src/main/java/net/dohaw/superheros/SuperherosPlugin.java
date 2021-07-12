package net.dohaw.superheros;

import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import net.dohaw.superheros.superhero.Superhero;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public final class SuperherosPlugin extends JavaPlugin {

    private Map<UUID, SuperheroType> playerSuperHeros = new HashMap<>();

    @Override
    public void onEnable() {
        CoreLib.setInstance(this);
        registerSpells();
        registerSuperheros();
        JPUtils.registerEvents(new PlayerWatcher(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Map<UUID, SuperheroType> getPlayerSuperHeros() {
        return playerSuperHeros;
    }

    private void registerSpells(){
        Spell.registerWrapper(Spell.FREEZE);
        Spell.registerWrapper(Spell.LEVITATE);
    }

    private void registerSuperheros(){
        Superhero.registerWrapper(Superhero.THANOS);
    }

    public void setSuperhero(Player player, SuperheroWrapper superhero){
        playerSuperHeros.put(player.getUniqueId(), superhero.getKEY());
        PlayerInventory inv = player.getInventory();
        HashSet<SpellType> superheroSpells = superhero.getSpells();
        for(SpellType spellType : superheroSpells){
            SpellWrapper wrapper = (SpellWrapper) Spell.getByKey(spellType);
            inv.addItem(wrapper.getItem());
        }
    }

    public void removeSuperhero(Player player){
        PlayerInventory inv = player.getInventory();
        playerSuperHeros.remove(player.getUniqueId());
        for(ItemStack stack : inv.getContents()){
            if(stack != null && Spell.isSpellItem(stack)){
                inv.remove(stack);
            }
        }
    }

}
