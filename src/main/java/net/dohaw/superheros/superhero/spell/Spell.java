package net.dohaw.superheros.superhero.spell;

import net.dohaw.superheros.Wrapper;
import net.dohaw.superheros.WrapperHolder;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.thanos.Freeze;
import net.dohaw.superheros.superhero.spell.thanos.Levitate;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Spell extends WrapperHolder {

    public static final NamespacedKey SPELL_ITEM_KEY = NamespacedKey.minecraft("spell-type");

    /* Thanos Spells */
    public static final SpellWrapper FREEZE = new Freeze();
    public static final SpellWrapper LEVITATE = new Levitate();

    public static List<SpellWrapper> getSuperheroSpells(SuperheroType superheroType){
        List<SpellWrapper> spells = new ArrayList<>();
        for(Wrapper wrapper : wrappers.values()){
            SpellWrapper spell = (SpellWrapper) wrapper;
            if(spell.getSuperheroType() == superheroType){
                spells.add(spell);
            }
        }
        return spells;
    }

    public static boolean isSpellItem(ItemStack stack){
        if(stack == null || stack.getItemMeta() == null) return false;
        return stack.getItemMeta().getPersistentDataContainer().has(SPELL_ITEM_KEY, PersistentDataType.STRING);
    }

    public static SpellType getItemSpellType(ItemStack stack){
        String pdcSpellTypeKey = stack.getItemMeta().getPersistentDataContainer().get(SPELL_ITEM_KEY, PersistentDataType.STRING);
        return SpellType.valueOf(pdcSpellTypeKey);
    }

    public static void castItemSpell(Player player, SuperheroType superheroType, SpellType spellType){
        SpellWrapper spell = (SpellWrapper) getByKey(spellType);
        if(spell.getSuperheroType() == superheroType){
            spell.cast(player);
        }
    }

}
