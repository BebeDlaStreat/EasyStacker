package fr.bebedlastreat.estacker.listeners;

import fr.bebedlastreat.estacker.Main;
import fr.bebedlastreat.estacker.utils.Stacker;
import fr.bebedlastreat.estacker.utils.StackersManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class BlockListener implements Listener {
    private Main main;
    public BlockListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Block against = e.getBlockAgainst();
        if (main.getStackingBlocks().contains(block.getType()) && block.getType() == against.getType()) {
            if (player.isSneaking()) {
                int number = main.getSneakingPlace();
                if (number == 0) return;
                block.setType(Material.AIR);
                int inHand = player.getItemInHand().getAmount();
                player.getInventory().clear(player.getInventory().getHeldItemSlot());
                if (StackersManager.exist(against.getLocation())) {
                    Stacker stacker = StackersManager.getStacker(against.getLocation());
                    stacker.setValue(stacker.getValue() + inHand);
                    stacker.updateHologram();
                } else {
                    Stacker stacker = new Stacker(against.getLocation(), 1 + inHand, against.getType());
                    StackersManager.createStacker(stacker);
                    stacker.createHologram();
                }

                return;
            }
            block.setType(Material.AIR);
            if (StackersManager.exist(against.getLocation())) {
                Stacker stacker = StackersManager.getStacker(against.getLocation());
                stacker.setValue(stacker.getValue() + 1);
                stacker.updateHologram();
            } else {
                Stacker stacker = new Stacker(against.getLocation(), 2, against.getType());
                StackersManager.createStacker(stacker);
                stacker.createHologram();
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        if (StackersManager.exist(block.getLocation())) {
            Stacker stacker = StackersManager.getStacker(block.getLocation());
            if (player.isSneaking()) {
                int number = main.getSneakingBreak();
                if (number == 0) {
                    e.setCancelled(true);
                    return;
                }
                if (number == -1) {
                    Collection<ItemStack> drops = block.getDrops(player.getItemInHand());
                    for (int i = 0; i < stacker.getValue() - 1; i++) {
                        for (ItemStack drop : drops) {
                            block.getWorld().dropItem(block.getLocation().clone().add(0, 1, 0), drop);
                        }
                    }
                    StackersManager.delete(stacker);
                    stacker.deleteHologram();
                    return;
                }
                if (number > 0) {
                    if (number >= stacker.getValue()) {
                        Collection<ItemStack> drops = block.getDrops(player.getItemInHand());
                        for (int i = 0; i < stacker.getValue(); i++) {
                            for (ItemStack drop : drops) {
                                block.getWorld().dropItem(block.getLocation().clone().add(0, 1, 0), drop);
                            }
                        }
                        StackersManager.delete(stacker);
                        stacker.deleteHologram();
                        return;
                    } else {
                        Collection<ItemStack> drops = block.getDrops(player.getItemInHand());
                        for (int i = 0; i < number; i++) {
                            for (ItemStack drop : drops) {
                                block.getWorld().dropItem(block.getLocation().clone().add(0, 1, 0), drop);
                            }
                        }
                        if (stacker.getValue() == 1) {
                            StackersManager.delete(stacker);
                            stacker.deleteHologram();
                        } else {
                            stacker.setValue(stacker.getValue() - number);
                            stacker.updateHologram();
                            Bukkit.getScheduler().runTaskLater(main, () -> {
                                block.setType(stacker.getMaterial());
                            }, 1);
                        }
                        return;
                    }
                }

                return;
            }
            /*Collection<ItemStack> drops = block.getDrops(player.getItemInHand());
            if (drops.size() > 0) {
                for (ItemStack drop : drops) {
                    block.getWorld().dropItem(block.getLocation().clone().add(0, 1, 0), drop);
                }
            }*/
            if (stacker.getValue() == 1) {
                StackersManager.delete(stacker);
                stacker.deleteHologram();
            } else {
                stacker.setValue(stacker.getValue() - 1);
                stacker.updateHologram();
                Bukkit.getScheduler().runTaskLater(main, () -> {
                    block.setType(stacker.getMaterial());
                }, 1);
            }
        }
    }
}
