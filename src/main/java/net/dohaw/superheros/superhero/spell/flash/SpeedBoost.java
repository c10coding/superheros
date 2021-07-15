package net.dohaw.superheros.superhero.spell.flash;

import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class SpeedBoost extends SpellWrapper {

    public SpeedBoost() {
        super(SpellType.SPEED_BOOST, SuperheroType.FLASH);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        Spell.playSound(caster, Sound.ENTITY_GENERIC_EXPLODE);
        Spell.spawnParticle(caster, Particle.FLASH, 30, 1, 1, 1);
        caster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20, 0));
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.FEATHER;
    }

    @Override
    public String getItemDisplayName() {
        return "&7Speed Boost";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Gives you a short speed boost for 10 seconds");
    }

}
