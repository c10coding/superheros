package net.dohaw.superheros.superhero.spell.loki;

import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Shadow extends SpellWrapper {

    public Shadow() {
        super(SpellType.SHADOW, SuperheroType.LOKI);
    }

    @Override
    public boolean cast(Player caster, SuperherosPlugin plugin) {
        EntityPlayer npc = Spell.createNPC(caster);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            npc.setRemoved(Entity.RemovalReason.a);
        }, 20L * 5);
        return true;
    }

    @Override
    public Material getItemMaterial() {
        return Material.PLAYER_HEAD;
    }

    @Override
    public String getItemDisplayName() {
        return "&eShadow";
    }

    @Override
    public List<String> getItemLore() {
        return Arrays.asList("&7Creates a shadow of yourself");
    }

}
