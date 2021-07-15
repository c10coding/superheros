package net.dohaw.superheros.superhero.spell.batman;

import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class GrapplingHook extends SpellWrapper {

    public GrapplingHook() {
        super(SpellType.GRAPPLING_HOOK, SuperheroType.BATMAN);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        Trident trident = (Trident) caster.getWorld().spawnEntity(caster.getEyeLocation(), EntityType.TRIDENT);
        trident.setShooter(caster);
        trident.getPersistentDataContainer().set(NamespacedKey.minecraft("spell-item-proj"), PersistentDataType.STRING, "marker");
        trident.setVelocity(caster.getLocation().getDirection().multiply(2));
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.TRIPWIRE_HOOK;
    }

    @Override
    public String getItemDisplayName() {
        return "&eGrappling Hook";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Do I need to explain how awesome this item is?");
    }

}
