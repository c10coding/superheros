package net.dohaw.superheros.superhero.spell;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.dohaw.superheros.SuperherosPlugin;
import net.dohaw.superheros.Wrapper;
import net.dohaw.superheros.WrapperHolder;
import net.dohaw.superheros.superhero.SuperheroType;
import net.dohaw.superheros.superhero.spell.batman.Blindness;
import net.dohaw.superheros.superhero.spell.batman.GrapplingHook;
import net.dohaw.superheros.superhero.spell.black_widow.HighJump;
import net.dohaw.superheros.superhero.spell.black_widow.Invisibility;
import net.dohaw.superheros.superhero.spell.flash.SpeedBoost;
import net.dohaw.superheros.superhero.spell.flash.Teleport;
import net.dohaw.superheros.superhero.spell.loki.Dagger;
import net.dohaw.superheros.superhero.spell.loki.Shadow;
import net.dohaw.superheros.superhero.spell.superman.Fly;
import net.dohaw.superheros.superhero.spell.superman.SeeThrough;
import net.dohaw.superheros.superhero.spell.thanos.Freeze;
import net.dohaw.superheros.superhero.spell.thanos.Levitate;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Spell extends WrapperHolder {

    private static List<EntityPlayer> npcs = new ArrayList<>();

    public static final NamespacedKey SPELL_ITEM_KEY = NamespacedKey.minecraft("spell-type");

    /* Thanos Spells */
    public static final Freeze FREEZE = new Freeze();
    public static final SpellWrapper LEVITATE = new Levitate();

    /* Flash Spells */
    public static final SpellWrapper TELEPORT = new Teleport();
    public static final SpellWrapper SPEED_BOOST = new SpeedBoost();

    /* Batman Spells */
    public static final SpellWrapper GRAPPLING_HOOK = new GrapplingHook();
    public static final SpellWrapper BLINDNESS = new Blindness();

    /* Superman Spells */
    public static final SpellWrapper FLY = new Fly();
    public static final SpellWrapper SEE_THROUGH = new SeeThrough();

    /* Loki Spells */
    public static final SpellWrapper SHADOW = new Shadow();
    public static final SpellWrapper DAGGER = new Dagger();

    /* Black Widow Spells */
    public static final SpellWrapper HIGH_JUMP = new HighJump();
    public static final SpellWrapper INVISIBLITY = new Invisibility();

    public static List<SpellWrapper> getSuperheroSpells(SuperheroType superheroType){
        List<SpellWrapper> spells = new ArrayList<>();
        for(Wrapper wrapper : wrappers.values()){
            SpellWrapper spell = (SpellWrapper) wrapper;
            if(spell.getSuperheroType() == superheroType){
                spells.add(spell);
            }
        }
        return spells;
    }

    public static boolean isSpellItem(ItemStack stack){
        if(stack == null || stack.getItemMeta() == null) return false;
        return stack.getItemMeta().getPersistentDataContainer().has(SPELL_ITEM_KEY, PersistentDataType.STRING);
    }

    public static SpellType getItemSpellType(ItemStack stack){
        String pdcSpellTypeKey = stack.getItemMeta().getPersistentDataContainer().get(SPELL_ITEM_KEY, PersistentDataType.STRING);
        return SpellType.valueOf(pdcSpellTypeKey);
    }

    public static boolean castItemSpell(SuperherosPlugin plugin, Player player, SuperheroType superheroType, SpellType spellType){
        SpellWrapper spell = (SpellWrapper) getByKey(spellType);
        if(spell.getSuperheroType() == superheroType){
            return spell.cast(player, plugin);
        }
        return false;
    }

    public static void spawnParticle(Entity entity, Particle particle, int count, float offsetX, float offsetY, float offsetZ){
        spawnParticle(entity.getLocation(), particle, count, offsetX, offsetY, offsetZ);
    }

    public static void spawnParticle(Location location, Particle particle, int count, float offsetX, float offsetY, float offsetZ){
        Objects.requireNonNull(location.getWorld()).spawnParticle(particle, location, count, offsetX, offsetY, offsetZ);
    }

    public static void playSound(Location location, Sound sound){
        location.getWorld().playSound(location, sound, (float) 0.5, 1);
    }

    public static void playSound(Entity entity, Sound sound){
        playSound(entity.getLocation(), sound);
    }

    public static void playSound(Block block, Sound sound){
        playSound(block.getLocation(), sound);
    }

    public static EntityPlayer createNPC(Player player){

        Location playerLocation = player.getLocation();
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName());
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile);
        npc.setLocation(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ(), playerLocation.getYaw(), playerLocation.getPitch());

        String[] skinStuff = getSkin(player);
        gameProfile.getProperties().put("textures", new Property("textures", skinStuff[0], skinStuff[1]));

        addNPCPacket(npc);

        return npc;
    }

    public static String[] getSkin(Player player){
        EntityPlayer p = ((CraftPlayer)player).getHandle();
        GameProfile profile = p.getProfile();
        Property property = profile.getProperties().get("textures").iterator().next();
        String texture = property.getValue();
        String signature = property.getSignature();
        return new String[]{texture, signature};
    }

    public static void addNPCPacket(EntityPlayer npc){
        for(Player player : Bukkit.getOnlinePlayers()){
            PlayerConnection conn = ((CraftPlayer)player).getHandle().b;
            conn.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            conn.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        }
    }

}
