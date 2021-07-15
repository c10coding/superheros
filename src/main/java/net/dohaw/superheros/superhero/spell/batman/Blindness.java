package net.dohaw.superheros.superhero.spell.batman;

import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class  Blindness extends SpellWrapper {

    public Blindness() {
        super(SpellType.BLINDNESS, SuperheroType.BATMAN);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {

        UUID seekerUUID = plugin.getSeekerUUID();
        if(seekerUUID != null){
            Player seeker = Bukkit.getPlayer(seekerUUID);
            if(seeker != null){
                if(seeker.getLocation().distance(caster.getLocation()) > 5){
                    caster.sendMessage(ChatColor.RED + "You aren't close enough to the seeker!");
                    return true;
                }
                Spell.spawnParticle(caster, Particle.CLOUD, 20, 1, 1, 1);
                Spell.playSound(caster, Sound.BLOCK_SAND_PLACE);
                seeker.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20, 0));
                return true;
            }
        }

        return false;
    }

    @Override
    public Material getItemMaterial() {
        return Material.GHAST_TEAR;
    }

    @Override
    public String getItemDisplayName() {
        return "&eBlindness";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Blinds the seeker if they are within", "&75 blocks from you", "&7for 3 seconds");
    }

}
