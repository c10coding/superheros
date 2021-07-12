package net.dohaw.superheros.superhero;

import net.dohaw.superheros.Wrapper;
import net.dohaw.superheros.superhero.spell.SpellType;

import java.util.HashSet;

public abstract class SuperheroWrapper extends Wrapper<SuperheroType> {

    private HashSet<SpellType> spells;

    public SuperheroWrapper(SuperheroType KEY) {
        super(KEY);
        this.spells = compileSpells();
    }

    protected abstract HashSet<SpellType> compileSpells();

    public HashSet<SpellType> getSpells() {
        return spells;
    }

}
