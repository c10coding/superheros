package net.dohaw.superheros.superhero;

import net.dohaw.superheros.Wrapper;
import net.dohaw.superheros.superhero.spell.SpellType;

import java.util.HashSet;
import java.util.List;

public abstract class SuperheroWrapper extends Wrapper<SuperheroType> {

    private List<SpellType> spells;

    public SuperheroWrapper(SuperheroType KEY) {
        super(KEY);
        this.spells = compileSpells();
    }

    protected abstract List<SpellType> compileSpells();

    public List<SpellType> getSpells() {
        return spells;
    }

}
