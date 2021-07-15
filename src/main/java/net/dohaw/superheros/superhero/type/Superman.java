package net.dohaw.superheros.superhero.type;

import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import net.dohaw.superheros.superhero.spell.SpellType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Superman extends SuperheroWrapper {

    public Superman() {
        super(SuperheroType.SUPERMAN);
    }

    @Override
    protected List<SpellType> compileSpells() {
        return Arrays.asList(SpellType.FLY, SpellType.SEE_THROUGH);
    }
}
