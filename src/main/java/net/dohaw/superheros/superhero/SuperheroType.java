package net.dohaw.superheros.superhero;

import java.util.ArrayList;
import java.util.List;

public enum SuperheroType {

    THANOS(true),
    FLASH(false),
    BATMAN(false),
    SUPERMAN(false),
    LOKI(false),
    BLACK_WIDOW(false),
    SPIDER_MAN(false);

    private boolean isSeekerRole;
    SuperheroType(boolean isSeekerRole){
        this.isSeekerRole = isSeekerRole;
    }

    public static List<SuperheroType> getHiderRoles(){
        List<SuperheroType> superheroTypes = new ArrayList<>();
        for(SuperheroType type : SuperheroType.values()){
            if(!type.isSeekerRole){
                superheroTypes.add(type);
            }
        }
        return superheroTypes;
    }

}
