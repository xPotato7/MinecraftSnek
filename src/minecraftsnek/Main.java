package minecraftsnek;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
    
    private CommandBoard board;
    private SnekGame game;
    
    @Override
    public void onEnable(){
        // detects play join event
        getServer().getPluginManager().registerEvents(this, this);
        
        // command for creating the board
        board = new CommandBoard();
        this.getCommand("board").setExecutor(board);
        
    }
    
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(ChatColor.GOLD+"Welcome "+ChatColor.GREEN+e.getPlayer().getDisplayName());
    }
    
    // command for play and reset
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // initialize game, snek and apple
        if(sender instanceof Player){
            Player p = (Player) sender;
            
            if(label.equalsIgnoreCase("play")){
                
                // reset the previous board's game
                if(game != null){
                    //stop registering the piston timer event
                    BlockPistonExtendEvent.getHandlerList().unregister(game);
                }
                
                game = new SnekGame(board);

                // register events in SnekGame i.e., piston timer
                game.begin();
                getServer().getPluginManager().registerEvents(game, this);
                
                // give items used to move the snek to player
                // I am lazy so I will do only one item check
                if(!p.getInventory().contains(Material.IRON_INGOT)){
                    p.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                    p.getInventory().addItem(new ItemStack(Material.DIAMOND));
                    p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                    p.getInventory().addItem(new ItemStack(Material.REDSTONE));
                }
            }
            
            if(label.equalsIgnoreCase("reset")){
                
                //stop registering the piston timer event
                BlockPistonExtendEvent.getHandlerList().unregister(game);
                
                Block temp = board.getBottomLeft();

                for(int i=0;i<board.getBoardHeight();i++){
                    for(int j=0;j<board.getBoardWidth();j++){

                        // changing everything on board to air
                        temp.getRelative(0, 1, 0).setType(Material.AIR);

                        temp = temp.getRelative(0, 0, 1);
                    }
                    temp = temp.getRelative(1, 0, -(board.getBoardWidth()));
                }
                
                
            }
            
        }
        
        return true;
    }
}
