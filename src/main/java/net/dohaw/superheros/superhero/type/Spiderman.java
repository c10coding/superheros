package net.dohaw.superheros.superhero.type;

import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import net.dohaw.superheros.superhero.spell.SpellType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Spiderman extends SuperheroWrapper {

    public Spiderman() {
        super(SuperheroType.SPIDER_MAN);
    }

    @Override
    protected List<SpellType> compileSpells() {
        return new ArrayList<>();
    }

}
