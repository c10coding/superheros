package net.dohaw.superheros.superhero.type;

import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import net.dohaw.superheros.superhero.spell.SpellType;

import java.util.Arrays;
import java.util.List;

public class BlackWidow extends SuperheroWrapper {

    public BlackWidow() {
        super(SuperheroType.BLACK_WIDOW);
    }

    @Override
    protected List<SpellType> compileSpells() {
        return Arrays.asList(SpellType.HIGH_JUMP, SpellType.INVISIBLITY);
    }

}
