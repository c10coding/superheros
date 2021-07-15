package net.dohaw.superheros.superhero.spell.superman;

import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

public class SeeThrough extends SpellWrapper {

    public SeeThrough() {
        super(SpellType.SEE_THROUGH, SuperheroType.SUPERMAN);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                if(!caster.getUniqueId().equals(player.getUniqueId()) && caster.canSee(player)){
                    PacketPlayOutEntityEffect eff = new PacketPlayOutEntityEffect(player.getEntityId(), new MobEffect(MobEffectList.fromId(24), 1000, 1, true, true));
                    ((CraftPlayer)player).getHandle().b.sendPacket(eff);
                }
            }
        }, 0L, 20L);
        Spell.playSound(caster, Sound.ENTITY_SLIME_JUMP);
        Spell.spawnParticle(caster, Particle.END_ROD, 20, 0.5f, 0.5f, 0.5f);
        Bukkit.getScheduler().runTaskLater(plugin, task::cancel, 20L * 5);
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.ENDER_EYE;
    }

    @Override
    public String getItemDisplayName() {
        return "&eSee Through";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Players start glowing through walls.");
    }



}
