package me.sendpacket.anticheat.anticheat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Listener implements org.bukkit.event.Listener {

    public static void Setup() {
        onTickUpdate();
        on20TickUpdate();
        addPacketReceivingListener();
        addPacketSendingListener();
    }
    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event){
        for(Check c : CheckManager.CheckList) {
            if(c.enabled) {
                c.onMoveEvent(event);
            }
            if(c.SubCheckList.size() > 0) {
                for (SubCheck sc : c.SubCheckList) {
                    if (sc.enabled) {
                        sc.onMoveEvent(event);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        for(Check c : CheckManager.CheckList) {
            c.onInteract(event);
            if(c.SubCheckList.size() > 0) {
                for (SubCheck sc : c.SubCheckList) {
                    sc.onInteract(event);
                }
            }
        }
    }
    @EventHandler
    public void onEnityDamageByEntity(EntityDamageByEntityEvent event){
        for(Check c : CheckManager.CheckList) {
            c.onEnityDamageByEntity(event);
            if(c.SubCheckList.size() > 0) {
                for (SubCheck sc : c.SubCheckList) {
                    sc.onEnityDamageByEntity(event);
                }
            }
        }
    }
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        for(Check c : CheckManager.CheckList) {
            c.onEntityDamageEvent(event);
            if(c.SubCheckList.size() > 0) {
                for (SubCheck sc : c.SubCheckList) {
                    sc.onEntityDamageEvent(event);
                }
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        for(Check c : CheckManager.CheckList) {
            c.onBlockPlace(event);
            if(c.SubCheckList.size() > 0) {
                for (SubCheck sc : c.SubCheckList) {
                    sc.onBlockPlace(event);
                }
            }
        }
    }
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event){
        for(Check c : CheckManager.CheckList) {
            c.onItemConsume(event);
            if(c.SubCheckList.size() > 0) {
                for (SubCheck sc : c.SubCheckList) {
                    sc.onItemConsume(event);
                }
            }
        }
    }
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event){
        for(Check c : CheckManager.CheckList) {
            c.onEntityShootBow(event);
            if(c.SubCheckList.size() > 0) {
                for (SubCheck sc : c.SubCheckList) {
                    sc.onEntityShootBow(event);
                }
            }
        }
    }
    @EventHandler
    public void onInventoryMove(InventoryClickEvent event){
        for(Check c : CheckManager.CheckList) {
            c.onInventoryMove(event);
            if(c.SubCheckList.size() > 0) {
                for (SubCheck sc : c.SubCheckList) {
                    sc.onInventoryMove(event);
                }
            }
        }
    }

    public static void onTickUpdate()
    {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(AntiCheat.Plugin, new Runnable() {
            public void run() {
                try {
                    for (Check c : CheckManager.CheckList) {
                        c.onTickUpdate();
                        if (c.SubCheckList.size() > 0) {
                            for (SubCheck sc : c.SubCheckList) {
                                sc.onTickUpdate();
                            }
                        }
                    }
                }catch (Exception e){};
            }
        }, 0, 1L);
    }
    public static void on20TickUpdate()
    {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(AntiCheat.Plugin, new Runnable() {
            public void run() {
                try {
                    for (Check c : CheckManager.CheckList) {
                        c.on20TickUpdate();
                        if (c.SubCheckList.size() > 0) {
                            for (SubCheck sc : c.SubCheckList) {
                                sc.on20TickUpdate();
                            }
                        }
                    }
                }catch (Exception e){};
            }
        }, 0, 20L);
    }

    public static void addPacketReceivingListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AntiCheat.Plugin,
                PacketType.Play.Client.USE_ITEM,
                PacketType.Play.Client.USE_ENTITY,
                PacketType.Play.Client.BLOCK_DIG,
                PacketType.Play.Client.FLYING,
                PacketType.Play.Client.POSITION_LOOK,
                PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                try {
                    if (event.getPacket() != null) {
                        for (Check c : CheckManager.CheckList) {
                            c.onPacketReceiving(event);
                            if (c.SubCheckList.size() > 0) {
                                for (SubCheck sc : c.SubCheckList) {
                                    sc.onPacketReceiving(event);
                                }
                            }
                        }
                    }
                }catch(Exception e){}
            }
        });
    }
    public static void addPacketSendingListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AntiCheat.Plugin, PacketType.Play.Server.ANIMATION) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if(event.getPacket() != null) {
                    for (Check c : CheckManager.CheckList) {
                        c.onPacketSending(event);
                        if (c.SubCheckList.size() > 0) {
                            for (SubCheck sc : c.SubCheckList) {
                                sc.onPacketSending(event);
                            }
                        }
                    }
                }
            }
        });
    }
}
