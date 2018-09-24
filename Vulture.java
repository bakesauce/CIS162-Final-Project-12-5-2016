import java.awt.*;
import java.util.Random;
/**
 * Write a description of class Vulture here.
 * 
 * @Mark Baker 
 * @12/5/2016
 */
public class Vulture extends GVcritter {
 // instance variables
    private Direction dir;
    private Random randGen = new Random();
    

    /*****************************************************
    Create starting values for this Vulture.
    @param loc given location for this critter
     *****************************************************/  
    public Vulture(Location loc){
        super(loc); 
        setColor(Color.BLACK);
        setSpecies(Species.VULTURE);
        
        //random starting position in the movement loop
        startPos = randGen.nextInt(14) + 1;

        if (1 <= startPos && startPos <= 3)
            dir = Direction.NORTH;
        else if ( 4 <= startPos && startPos <= 7)
            dir = Direction.WEST;
        else if ( 8 <= startPos && startPos <= 10 )
            dir = Direction.SOUTH;
        else if ( 11 <= startPos && startPos <= 14)
            dir = Direction.EAST;
    }

    /*****************************************************
    Vultures always SCRATCH unless opponent is hippo, then they ROAR
    
    @param opponent who is the critter fighting?
    @return attack strategy
     *****************************************************/     
    public Attack getAttack(GVcritter opponent) {
        if (opponent.getSpecies() == Species.HIPPO) 
            return Attack.ROAR;
        else 
            return Attack.SCRATCH;
    }

    /*****************************************************
    Vultures move in a NW or SW circle pattern

    @return desired direction of next step
     *****************************************************/     
    public Direction getMoveDirection(){
        steps++;
        startPos++;
        
        if(startPos % 14 <= 3)
            return Direction.NORTH;
        else if ( 4 <= startPos % 14 && startPos % 14 <= 7)
            return Direction.WEST;
        else if ( 8 <= startPos % 14 && startPos % 14 <= 10 )
            return Direction.SOUTH;
        else 
           return Direction.EAST;

    }
}
