package net.dohaw.superheros.superhero.spell.thanos;

import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Levitate extends SpellWrapper {

    public Levitate() {
        super(SpellType.LEVITATE, SuperheroType.THANOS);
    }

    @Override
    public void cast(Player caster) {
        System.out.println("Casting Levitate");
    }

    @Override
    public ItemStack createItem() {
        return new ItemStack(Material.FEATHER);
    }

}
