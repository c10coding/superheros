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

public class Invisibility extends SpellWrapper {

    public Invisibility() {
        super(SpellType.INVISIBLITY, SuperheroType.BLACK_WIDOW);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        caster.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10 * 20, 0));
        Spell.playSound(caster, Sound.ENTITY_SQUID_SQUIRT);
        Spell.spawnParticle(caster, Particle.SQUID_INK, 10, 0.5f, 0.5f, 0.5f);
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.GHAST_TEAR;
    }

    @Override
    public String getItemDisplayName() {
        return "&eInvisibility";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7You turn invisible for 10 seconds");
    }

}
