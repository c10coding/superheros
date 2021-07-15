package net.dohaw.superheros.superhero.type;

import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import net.dohaw.superheros.superhero.spell.SpellType;

import java.util.Arrays;
import java.util.List;

public class Batman extends SuperheroWrapper {

    public Batman() {
        super(SuperheroType.BATMAN);
    }

    @Override
    protected List<SpellType> compileSpells() {
        return Arrays.asList(SpellType.GRAPPLING_HOOK, SpellType.BLINDNESS);
    }

}
