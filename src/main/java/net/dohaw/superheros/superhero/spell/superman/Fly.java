package net.dohaw.superheros.superhero.spell.superman;

import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Fly extends SpellWrapper {

    public Fly() {
        super(SpellType.FLY, SuperheroType.SUPERMAN);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        caster.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            caster.setAllowFlight(false);
        }, 5 * 20L);
        Spell.playSound(caster, Sound.BLOCK_BEACON_POWER_SELECT);
        Spell.spawnParticle(caster, Particle.END_ROD, 30, 1, 1, 1);
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.AMETHYST_SHARD;
    }

    @Override
    public String getItemDisplayName() {
        return "&eFly";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Lets you fly for 5 seconds");
    }

}
