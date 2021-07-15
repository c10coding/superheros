package net.dohaw.superheros.superhero.spell.flash;

import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Teleport extends SpellWrapper {

    public Teleport() {
        super(SpellType.TELEPORT, SuperheroType.FLASH);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {

        Location currentLocation = caster.getLocation().clone();
        currentLocation.setPitch(0);
        Location teleportLocation = currentLocation.clone().add(currentLocation.clone().getDirection().multiply(5));
        caster.teleport(teleportLocation);

        Spell.playSound(caster, Sound.ITEM_CHORUS_FRUIT_TELEPORT);
        Spell.spawnParticle(caster, Particle.END_ROD, 20, 0.5f, 0.5f, 0.5f);

        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.ENDER_PEARL;
    }

    @Override
    public String getItemDisplayName() {
        return "&eTeleport";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Teleports you forward 5 blocks");
    }

}
