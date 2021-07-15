package net.dohaw.superheros.superhero;

import net.dohaw.corelib.Config;
import net.dohaw.corelib.serializers.LocationSerializer;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class BaseConfig extends Config {

    public BaseConfig() {
        super("config.yml");
    }

    public void setLobbyLocation(Location location){
        LocationSerializer ls = new LocationSerializer();
        config.set("Lobby Location", ls.toString(location));
        saveConfig();
    }

    public Location getLobbyLocation(){
        return config.isSet("Lobby Location") ? new LocationSerializer().toLocation(config.getString("Lobby Location")) : null;
    }

    public Location getSeekerSpawn(){
        LocationSerializer ls = new LocationSerializer();
        return config.isSet("Seeker Spawn Location") ? ls.toLocation(config.getString("Seeker Spawn Location")) : null;
    }

    public List<Location> getHiderSpawns(){
        LocationSerializer ls = new LocationSerializer();
        List<String> spawnLocationsStr = config.getStringList("Hider Spawn Locations");
        List<Location> spawnLocations = new ArrayList<>();
        for(String locStr : spawnLocationsStr){
            spawnLocations.add(ls.toLocation(locStr));
        }
        return spawnLocations;
    }

    public void addHiderSpawn(Location location){
        LocationSerializer ls = new LocationSerializer();
        List<String> spawnLocationsStr = config.getStringList("Hider Spawn Locations");
        spawnLocationsStr.add(ls.toString(location));
        config.set("Hider Spawn Locations", spawnLocationsStr);
        saveConfig();
    }

    public void setSeekerSpawn(Location location){
        LocationSerializer ls = new LocationSerializer();
        config.set("Seeker Spawn Location", ls.toString(location));
        saveConfig();
    }

}
