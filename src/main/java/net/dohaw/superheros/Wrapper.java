package net.dohaw.superheros;

public abstract class Wrapper<K extends Enum<K>> {

    protected final K KEY;

    public Wrapper(final K KEY){
        this.KEY = KEY;
    }

    public K getKEY() {
        return KEY;
    }

}
