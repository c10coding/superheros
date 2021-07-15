package net.dohaw.superheros.superhero.spell.black_widow;

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

public class HighJump extends SpellWrapper {

    public HighJump() {
        super(SpellType.HIGH_JUMP, SuperheroType.BLACK_WIDOW);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        caster.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 10, 0));
        Spell.playSound(caster, Sound.ENTITY_SLIME_JUMP);
        Spell.spawnParticle(caster, Particle.SQUID_INK, 30, 0.5f, 0.5f, 0.5f);
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.SLIME_BALL;
    }

    @Override
    public String getItemDisplayName() {
        return "&eHigh Jump";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Allow you to jump higher than normal for 10 seconds");
    }

}
