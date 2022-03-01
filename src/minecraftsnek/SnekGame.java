package minecraftsnek;

import java.util.ArrayList;
import java.util.Random;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author xPotato
 */
public class SnekGame implements Listener{
    
    // snek head
    private SnekNode head;
    
    // apple
    private Block apple;
    
    // apply block type
    private final Material appleType = Material.RED_CONCRETE;
    
    // random
    private Random r = new Random();
    
    // move
    private String movement; 
    
    // info from board
    private Player p;
    private int boardHeight;
    private int boardWidth;
    private Block bottomLeft;
    
    
    public SnekGame(CommandBoard board){
        this.p = board.getP();
        this.boardHeight = board.getBoardHeight();
        this.boardWidth = board.getBoardWidth();
        this.bottomLeft = board.getBottomLeft();
        this.head = new SnekNode();
    }
    
    
    public void begin(){
        
        // random spot for snek head in the board
        Block temp = bottomLeft.getRelative(r.nextInt(boardHeight), 1, r.nextInt(boardWidth));
        
        // snek's head block type is set here
        temp.setType(Material.GREEN_CONCRETE);
        head.setData(temp);
        
        // random spot for apple but must not be same as snek head
        apple = appleSpot();
        
        // movement is empty
        movement = "";
        
        p.sendMessage("Game has begun");
    }
    
    // generate apple in board
    public Block appleSpot(){
        Block temp = bottomLeft;
        int count = 0;
        
        // random generation with count
        while(count < boardWidth*boardHeight){
            temp = bottomLeft.getRelative(r.nextInt(boardHeight), 1, r.nextInt(boardWidth));
            if(temp.getType() == Material.AIR){
                // apple's block type is set here
                temp.setType(appleType);
                break;
            }
            temp = bottomLeft;
            count++;
        }
        
        // if the while loop cannot find a new spot for apple / took too long
        if(temp == bottomLeft){
            ArrayList<Block> list = new ArrayList();
            for(int i=0;i<boardHeight;i++){
                for(int j=0;j<boardWidth;j++){
                    // if not snek's body
                    if(temp.getType() == Material.AIR){
                        list.add(temp);
                    }
                    temp = temp.getRelative(0, 0, 1);
                }
                temp = temp.getRelative(1, 0, -boardWidth);
            }
            
            // check if there is any empty spaces left
            if(list.size() != 0){
                // now list has all the empty spaces left in the board
                // return a random empty block
                temp = list.get(r.nextInt(list.size()));
                temp.setType(appleType);
            }
            
        }
        
        return temp;
    }
    
    
    @EventHandler
    public void madeMovement(PlayerInteractEvent e){
        if(e.getPlayer() == p){
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
                Material itemOnHand = e.getMaterial();
                
                Material forLEFT = Material.IRON_INGOT;
                Material forUP = Material.DIAMOND;
                Material forDOWN = Material.GOLD_INGOT;
                Material forRIGHT = Material.REDSTONE;
                
                if(itemOnHand == forUP){
                    movement = "UP";
                }else if(itemOnHand == forDOWN){
                    movement = "DOWN";
                }else if(itemOnHand == forLEFT){
                    movement = "LEFT";
                }else if(itemOnHand == forRIGHT){
                    movement = "RIGHT";
                }
                
                // testing before piston extend event was implemented
                // plot twist: it works!
//                move();
            }
            
        }
    }
    
    
    public void move(){
            
        Block nextBlock = null;

        switch(movement){
            case "UP":
                nextBlock = head.getData().getRelative(1, 0, 0);
                break;
            case "DOWN":
                nextBlock = head.getData().getRelative(-1, 0, 0);
                break;
            case "LEFT":
                nextBlock = head.getData().getRelative(0, 0, -1);
                break;
            case "RIGHT":
                nextBlock = head.getData().getRelative(0, 0, 1);
                break;
        }

        SnekNode previousNode = head;
        SnekNode newNode = new SnekNode();
        // if next block is not the border or the snek itself
        if(nextBlock.getType() == Material.AIR){

            nextBlock.setType(head.getData().getType());
            newNode.setData(nextBlock);
            newNode.setChild(previousNode);
            head = newNode;

            SnekNode temp = head;
            while(previousNode.getChild() != null){
                previousNode = previousNode.getChild();
                temp = temp.getChild();
            }
            previousNode.getData().setType(Material.AIR);
            temp.setChild(null);

        }
        // if next block is apple
        else if(nextBlock.getType() == apple.getType()){
            // we do the same thing as if the next block was air
            nextBlock.setType(head.getData().getType());
            newNode.setData(nextBlock);
            newNode.setChild(previousNode);
            head = newNode;

            // we did not delete the last block of the snek's body
            // thus the snek ate the apple and gained new block

            // new apple
            apple = appleSpot();

        }else{
            p.sendMessage(ChatColor.RED+"GameOver");
            // game stopper here
        }
            
    }
    
    @EventHandler
    public void pistonTimer(BlockPistonExtendEvent e){
        // timer of this piston event has to be set in game
        if(movement != ""){
            move();
        }
    }
    
    
    // returns the length of the snek
    // I never actually used this. It is here for fun.
    public int snekLength(){
        SnekNode temp = head;
        int count = 1;
        while(temp.getChild() != null){
            count++;
            temp = temp.getChild();
        }
        return count;
    }
    
}
