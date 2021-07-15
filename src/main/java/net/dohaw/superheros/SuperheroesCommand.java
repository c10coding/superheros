package net.dohaw.superheros;

import net.dohaw.corelib.StringUtils;
import net.dohaw.superheros.superhero.BaseConfig;
import net.dohaw.superheros.superhero.Superhero;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.SuperheroWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SuperheroesCommand implements CommandExecutor {

    private SuperherosPlugin plugin;

    public SuperheroesCommand(SuperherosPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can use this command!");
            return false;
        }

        Player pSender = (Player) sender;
        if(args.length == 0){
            sendHelpCommand(pSender);
            return false;
        }

        String firstArg = args[0];
        switch(firstArg){
            case "start":
                startCommand(pSender);
                break;
            case "set":
                setCommand(pSender, args);
                break;
            case "hero":
                heroCommand(pSender, args);
                break;
            default:
                sendHelpCommand(pSender);
                break;
        }

        return false;
    }

    private void heroCommand(Player player, String[] args){

        if(args.length == 0){
            player.sendMessage(ChatColor.RED + "You used this command wrong! Try doing /superhero hero <hero>");
            return;
        }

        String heroName = args[1];
        SuperheroType superheroType = getSuperhero(heroName);
        if(superheroType == null){
            player.sendMessage(ChatColor.RED + "This is not a valid superhero!");
            return;
        }

        plugin.setSuperhero(player, (SuperheroWrapper) Superhero.getByKey(superheroType));
        player.sendMessage(ChatColor.GREEN + "You have been given this hero!");

    }

    // /sh set <lobby | spawn>
    private void setCommand(Player player, String[] args){

        if(args.length < 2){
            player.sendMessage(StringUtils.colorString("&cYou have used this command incorrectly! Try using /sh set <lobby | spawn>"));
            return;
        }

        Location playerLocation = player.getLocation();
        BaseConfig baseConfig = plugin.getBaseConfig();
        String secondArg = args[1];
        if(secondArg.equalsIgnoreCase("lobby")){
            baseConfig.setLobbyLocation(playerLocation);
            player.sendMessage(ChatColor.GREEN + "You have set this as the lobby location!");
        }else if(secondArg.equalsIgnoreCase("hider")){
            baseConfig.addHiderSpawn(playerLocation);
            player.sendMessage(ChatColor.GREEN + "You have set this as one of the spawn locations for a hider!");
        }else if(secondArg.equalsIgnoreCase("seeker")){
            baseConfig.setSeekerSpawn(playerLocation);
            player.sendMessage(ChatColor.GREEN + "You have set the seeker spawn location!");
        }

    }

    private void startCommand(Player player){

        BaseConfig baseConfig = plugin.getBaseConfig();
        Location lobbyLocation = baseConfig.getLobbyLocation();
        Location seekerSpawn = baseConfig.getSeekerSpawn();
        List<Location> hiderSpawns = baseConfig.getHiderSpawns();
        if(hiderSpawns.isEmpty() || lobbyLocation == null || seekerSpawn == null){
            player.sendMessage(ChatColor.RED + "You can't start the game yet! The seeker, lobby, or hider spawns have not been set.");
            return;
        }

        List<Player> teleportedPlayers = new ArrayList<>();
        List<Player> participants = new ArrayList<>(Bukkit.getOnlinePlayers());
        for(Player p : Bukkit.getOnlinePlayers()){
            teleportedPlayers.add(p);
            p.teleport(lobbyLocation);
            p.sendMessage("You have teleported to the lobby! Seekers and hiders will be chosen shortly...");
        }

        // Hiders and seekers will be chosen 10 seconds after being telepored.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            ThreadLocalRandom current = ThreadLocalRandom.current();

            // If they aren't the only player in the game (Could be the only player if they are play-testing)
            if(teleportedPlayers.size() > 1){
                int randomIndexPlayer = current.nextInt(teleportedPlayers.size());
                Player seeker = teleportedPlayers.remove(randomIndexPlayer);
                seeker.sendTitle(StringUtils.colorString("&7Your role is "), StringUtils.colorString("&eSeeker"));
                plugin.setSeekerUUID(seeker.getUniqueId());
                plugin.setSuperhero(seeker, Superhero.THANOS);
                seeker.sendMessage(StringUtils.colorString("&7You have been assigned the &eThanos &7superhero!"));
            }

            List<SuperheroType> hiderRoles = SuperheroType.getHiderRoles();
            for(Player p : teleportedPlayers){
                SuperheroType randomSuperHero = hiderRoles.remove(current.nextInt(hiderRoles.size()));
                plugin.setSuperhero(p, (SuperheroWrapper) Superhero.getByKey(randomSuperHero));
                p.sendMessage(StringUtils.colorString("&7You have been assigned the &e" + randomSuperHero.name() + "&7 superhero!"));
                p.sendMessage(StringUtils.colorString("&bThe game will start in 10 seconds!"));
            }

            for(int i = 1; i < 10; i++){
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    for(Player p : participants){
                        p.sendTitle(StringUtils.colorString("&7The game is starting in"), StringUtils.colorString("&e" + finalI + "&7 seconds!"));
                    }
                }, (10 * 20L) - (i * 20L));
            }

            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                List<Player> hiders = new ArrayList<>();
                // Teleports the hiders to their locations
                for(UUID hiderUUID : plugin.getPlayerSuperHeros().keySet()){
                    if(!hiderUUID.equals(plugin.getSeekerUUID())){
                        Player hider = Bukkit.getPlayer(hiderUUID);
                        if(hider != null){
                            int randomIndex = ThreadLocalRandom.current().nextInt(hiderSpawns.size());
                            Location hiderSpawn = hiderSpawns.get(randomIndex);
                            hider.teleport(hiderSpawn);
                            hiders.add(hider);
                        }
                    }
                }

                Player seeker = Bukkit.getPlayer(plugin.getSeekerUUID());
                if(seeker != null){
                    seeker.teleport(seekerSpawn);
                }

                plugin.startGame(hiders);

            }, 10 * 20L);

        }, 20L * 10);

    }

    private void sendHelpCommand(Player player){

    }

    private SuperheroType getSuperhero(String superheroName){
        for(SuperheroType type : SuperheroType.values()){
            if(type.name().toLowerCase().equalsIgnoreCase(superheroName)){
                return type;
            }
        }
        return null;
    }

}
