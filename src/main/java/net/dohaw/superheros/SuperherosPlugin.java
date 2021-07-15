package net.dohaw.superheros;

import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import net.dohaw.superheros.listener.PlayerWatcher;
import net.dohaw.superheros.listener.SpellWatcher;
import net.dohaw.superheros.superhero.BaseConfig;
import net.dohaw.superheros.superhero.Superhero;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import net.dohaw.superheros.superhero.spell.Spell;
import net.dohaw.superheros.superhero.spell.SpellType;
import net.dohaw.superheros.superhero.spell.SpellWrapper;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SuperherosPlugin extends JavaPlugin {

    private Game game;

    private BaseConfig baseConfig;

    private UUID seekerUUID;
    private Map<UUID, SuperheroType> playerSuperHeros = new HashMap<>();

    @Override
    public void onEnable() {

        CoreLib.setInstance(this);

        JPUtils.validateFiles("config.yml");
        this.baseConfig = new BaseConfig();

        registerSpells();
        registerSuperheros();

        JPUtils.registerEvents(new PlayerWatcher(this), new SpellWatcher());
        JPUtils.registerCommand("superhero", new SuperheroesCommand(this));

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                if(playerSuperHeros.containsKey(player.getUniqueId()) && playerSuperHeros.get(player.getUniqueId()) == SuperheroType.SPIDER_MAN){
                    Block blockAboveHead = player.getLocation().add(0, 2, 0).getBlock();
                    player.setGravity(!blockAboveHead.getType().isSolid());
                }
            }
        }, 0L, 5L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Map<UUID, SuperheroType> getPlayerSuperHeros() {
        return playerSuperHeros;
    }

    private void registerSpells(){

        Spell.registerWrapper(Spell.FREEZE);
        Spell.registerWrapper(Spell.LEVITATE);
        Spell.registerWrapper(Spell.SPEED_BOOST);
        Spell.registerWrapper(Spell.TELEPORT);
        Spell.registerWrapper(Spell.GRAPPLING_HOOK);
        Spell.registerWrapper(Spell.BLINDNESS);
        Spell.registerWrapper(Spell.FLY);
        Spell.registerWrapper(Spell.SEE_THROUGH);
        Spell.registerWrapper(Spell.SHADOW);
        Spell.registerWrapper(Spell.DAGGER);
        Spell.registerWrapper(Spell.HIGH_JUMP);
        Spell.registerWrapper(Spell.INVISIBLITY);

        // Registering spells that are listeners
        JPUtils.registerEvents(Spell.FREEZE);
    }

    private void registerSuperheros(){
        Superhero.registerWrapper(Superhero.THANOS);
        Superhero.registerWrapper(Superhero.BATMAN);
        Superhero.registerWrapper(Superhero.FLASH);
        Superhero.registerWrapper(Superhero.LOKI);
        Superhero.registerWrapper(Superhero.SPIDER_MAN);
        Superhero.registerWrapper(Superhero.SUPER_MAN);
        Superhero.registerWrapper(Superhero.BLACK_WIDOW);
    }

    public void setSuperhero(Player player, SuperheroWrapper superhero){
        removeSuperhero(player);
        playerSuperHeros.put(player.getUniqueId(), superhero.getKEY());
        PlayerInventory inv = player.getInventory();
        List<SpellType> superheroSpells = superhero.getSpells();
        for(SpellType spellType : superheroSpells){
            SpellWrapper wrapper = (SpellWrapper) Spell.getByKey(spellType);
            inv.addItem(wrapper.getItem());
        }
    }

    public void removeSuperhero(Player player){
        PlayerInventory inv = player.getInventory();
        playerSuperHeros.remove(player.getUniqueId());
        for(ItemStack stack : inv.getContents()){
            if(stack != null && Spell.isSpellItem(stack)){
                inv.remove(stack);
            }
        }
    }

    public UUID getSeekerUUID() {
        return seekerUUID;
    }

    public void setSeekerUUID(UUID seekerUUID) {
        this.seekerUUID = seekerUUID;
    }

    public Player getSeeker(){
        return Bukkit.getPlayer(seekerUUID);
    }

    public Game getGame() {
        return game;
    }

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public void startGame(List<Player> hiders){
        this.game = new Game(this, hiders);
        game.startGame();
    }

}
