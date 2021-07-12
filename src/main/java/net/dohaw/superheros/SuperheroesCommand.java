package net.dohaw.superheros;

import net.dohaw.corelib.StringUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.print.DocFlavor;

public class SuperheroesCommand implements CommandExecutor {

    public SuperheroesCommand(){}

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

            case "sign":
            default:
                sendHelpCommand(pSender);
                break;
        }

        return false;
    }

    // /sh set <lobby | spawn>
    private void setCommand(Player player, String[] args){

        if(args.length < 2){
            player.sendMessage(StringUtils.colorString("&cYou have used this command incorrectly! Try using /sh set <lobby | spawn>"));
            return;
        }

        Location playerLocation = player.getLocation();
        String secondArg = args[1];
        if(secondArg.equalsIgnoreCase("lobby")){

        }else if(secondArg.equalsIgnoreCase("spawn")){

        }

    }

    private void startCommand(Player player){



    }

    private void sendHelpCommand(Player player){}

}
