package fr.bebedlastreat.estacker.listeners;

import fr.bebedlastreat.estacker.Main;
import fr.bebedlastreat.estacker.utils.StackersManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {
    private Main main;
    public BlockListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (player.isSneaking()) return;
        Block block = e.getBlock();
        Block against = e.getBlockAgainst();
        if (main.getStackingBlocks().contains(block.getType()) && block.getType() == against.getType()) {
            block.setType(Material.AIR);
            if (StackersManager.exist(against.getLocation())) {
                StackersManager.setStackNumber(against.getLocation(), StackersManager.getStackNumber(against.getLocation()) + 1);
            } else {
                StackersManager.createStacker(against.getLocation());
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        if (StackersManager.exist(block.getLocation())) {
            e.setCancelled(true);
            block.getWorld().dropItem(block.getLocation().clone().add(0, 1, 0), new ItemStack(block.getType()));
            if (StackersManager.getStackNumber(block.getLocation()) == 2) {
                StackersManager.removeStacker(block.getLocation());
            } else {
                StackersManager.setStackNumber(block.getLocation(), StackersManager.getStackNumber(block.getLocation()) - 1);
            }
        }
    }
}
