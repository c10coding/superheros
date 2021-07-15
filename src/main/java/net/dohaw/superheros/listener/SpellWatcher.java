package net.dohaw.superheros.listener;

import net.dohaw.superheros.superhero.spell.Spell;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;

public class SpellWatcher implements Listener {

    @EventHandler
    public void onTridentHit(ProjectileHitEvent e){
        Projectile proj = e.getEntity();
        if(proj instanceof Trident && proj.getPersistentDataContainer().has(NamespacedKey.minecraft("spell-item-proj"), PersistentDataType.STRING)){
            Player player = (Player) proj.getShooter();
            player.teleport(proj.getLocation());
            proj.remove();
            Spell.playSound(player, Sound.ITEM_CHORUS_FRUIT_TELEPORT);
        }
    }

}
