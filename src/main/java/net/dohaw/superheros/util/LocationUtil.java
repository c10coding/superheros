package net.dohaw.superheros.util;

import org.bukkit.Location;

public class LocationUtil {

    public static boolean hasMoved(Location to, Location from, boolean checkY){
        if(to != null){
            boolean hasMovedHorizontally = from.getX() != to.getX() || from.getZ() != to.getZ();
            if(!hasMovedHorizontally && checkY){
                return from.getY() != to.getY();
            }
            return hasMovedHorizontally;
        }
        return false;
    }


}
