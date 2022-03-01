package minecraftsnek;

import org.bukkit.block.Block;

/**
 *
 * @author xPotato
 */
public class SnekNode {
    
    private Block data;
    private SnekNode child;
    
    public SnekNode(){
        this.data = null;
        this.child = null;
    }

    public Block getData() {
        return data;
    }

    public SnekNode getChild() {
        return child;
    }

    public void setData(Block data) {
        this.data = data;
    }

    public void setChild(SnekNode node) {
        this.child = node;
    }
    
}
