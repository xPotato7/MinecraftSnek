package minecraftsnek;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author xPotato
 */
public class CommandBoard implements CommandExecutor{
    
    // default value set to 5
    private int boardHeight = 10;
    private int boardWidth = 10;
    
    // info of the board
    private Block bottomLeft;
    
    // player
    private Player p;
    
    // empty constructor
    public CommandBoard(){
        
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            p = (Player)sender;
            Location l = p.getLocation();
            
            if(args.length == 2){
                boardHeight = Integer.parseInt(args[0]);
                boardWidth = Integer.parseInt(args[1]);
            }
            
            
            bottomLeft = l.getBlock().getRelative(-(boardHeight/2), -1, -(boardWidth/2));

            // platform of board aka oak planks
            Block temp = bottomLeft;

            for(int i=0;i<boardHeight;i++){
                for(int j=0;j<boardWidth;j++){
                    temp.setType(Material.OAK_PLANKS);
                    
                    //making sure what's on the board is just air
                    temp.getRelative(0, 1, 0).setType(Material.AIR);
                    
                    temp = temp.getRelative(0, 0, 1);
                }
                temp = temp.getRelative(1, 0, -boardWidth);
            }

            // borders aka obsidian at the edges
            Block border = bottomLeft.getRelative(-1, 1, -1);
            Block otherEdgeBorder = border.getRelative(0, 0, boardWidth+1);

            for(int i=0;i<boardHeight+2;i++){
                border.setType(Material.OBSIDIAN);
                otherEdgeBorder.setType(Material.OBSIDIAN);
                border = border.getRelative(1, 0, 0);
                otherEdgeBorder = otherEdgeBorder.getRelative(1, 0, 0);
            }

            border = bottomLeft.getRelative(-1, 1, 0);
            otherEdgeBorder = border.getRelative(boardHeight+1, 0, 0);

            for(int i=0;i<boardWidth;i++){
                border.setType(Material.OBSIDIAN);
                otherEdgeBorder.setType(Material.OBSIDIAN);
                border = border.getRelative(0, 0, 1);
                otherEdgeBorder = otherEdgeBorder.getRelative(0, 0, 1);
            }

            p.sendMessage(ChatColor.GREEN+"Board sucessfully created!");
            
        }
        
        return true;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public Block getBottomLeft() {
        return bottomLeft;
    }

    public Player getP() {
        return p;
    }

}
