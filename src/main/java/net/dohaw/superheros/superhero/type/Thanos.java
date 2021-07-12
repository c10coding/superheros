package net.dohaw.superheros.superhero.type;

import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import net.dohaw.superheros.superhero.spell.SpellType;

import java.util.HashSet;

public class Thanos extends SuperheroWrapper {

    public Thanos() {
        super(SuperheroType.THANOS);
    }

    @Override
    protected HashSet<SpellType> compileSpells() {
        return new HashSet<SpellType>(){{
            add(SpellType.FREEZE);
            add(SpellType.LEVITATE);
        }};
    }

}
