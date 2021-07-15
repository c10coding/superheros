package net.dohaw.superheros.superhero.spell.loki;

import net.dohaw.corelib.StringUtils;
import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Dagger extends SpellWrapper {

    private final ItemStack DAGGER;
    public static final NamespacedKey DAGGER_KEY = NamespacedKey.minecraft("dagger-marker");

    public Dagger() {
        super(SpellType.DAGGER, SuperheroType.LOKI);
        this.DAGGER = dagger();
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        caster.getInventory().addItem(DAGGER);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
           removeDagger(caster);
        }, 10 * 20L);
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.STICK;
    }

    @Override
    public String getItemDisplayName() {
        return "&eDagger";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Gives you a dagger for 5 seconds.");
    }

    private ItemStack dagger(){
        ItemStack dagger = new ItemStack(Material.IRON_SWORD);
        ItemMeta daggerMeta = dagger.getItemMeta();
        daggerMeta.setDisplayName(StringUtils.colorString("&7&lDagger"));
        daggerMeta.getPersistentDataContainer().set(DAGGER_KEY, PersistentDataType.STRING, "marker");
        dagger.setItemMeta(daggerMeta);
        return dagger;
    }

    private void removeDagger(Player player){
        PlayerInventory inv = player.getInventory();
        ItemStack[] contents = inv.getContents();
        for (ItemStack stack : contents) {
            if (stack != null && stack.getItemMeta() != null) {
                if (stack.getItemMeta().getPersistentDataContainer().has(DAGGER_KEY, PersistentDataType.STRING)) {
                    inv.remove(stack);
                }
            }
        }
    }

}
