package net.dohaw.superheros.superhero.spell;

import net.dohaw.corelib.StringUtils;
import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.Wrapper;
import net.dohaw.superheros.superhero.SuperheroType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class SpellWrapper extends Wrapper<SpellType> {

    private ItemStack item;
    private SuperheroType superheroType;

    public SpellWrapper(SpellType KEY, SuperheroType superheroType) {
        super(KEY);
        this.superheroType = superheroType;
        this.item = createItem();
        if(item != null){
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(Spell.SPELL_ITEM_KEY, PersistentDataType.STRING, KEY.toString());
            item.setItemMeta(meta);
        }
    }

    public abstract boolean cast(Player caster, SuperherosPlugin plugin);

    public abstract Material getItemMaterial();

    public abstract String getItemDisplayName();

    public abstract List<String> getItemLore();

    public SuperheroType getSuperheroType() {
        return superheroType;
    }

    private ItemStack createItem(){
        ItemStack stack = new ItemStack(getItemMaterial());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(StringUtils.colorString(getItemDisplayName()));
        meta.setLore(StringUtils.colorLore(getItemLore()));
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack getItem() {
        return item;
    }

}
