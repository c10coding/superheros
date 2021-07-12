package net.dohaw.superheros.superhero.spell.thanos;

import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Freeze extends SpellWrapper {

    public Freeze() {
        super(SpellType.FREEZE, SuperheroType.THANOS);
    }

    @Override
    public void cast(Player caster) {
        System.out.println("Casting Freeze");
    }

    @Override
    public ItemStack createItem() {
        return new ItemStack(Material.ICE);
    }

}
