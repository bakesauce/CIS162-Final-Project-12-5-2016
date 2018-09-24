import java.awt.*;
import java.util.Random;
/**
 * Write a description of class Bird here.
 * 
 * @Mark Baker 
 * @12/5/2016
 */
public class Bird extends GVcritter {

   //instance variables
    private Direction dir;
    private Random randGen = new Random();
    
    /*****************************************************
    Create starting values for this Bird.
    @param loc given location for this critter
     *****************************************************/ 
    public Bird (Location loc) {
        super(loc); 
        setColor(Color.BLUE);
        setSpecies(Species.BIRD);
        
        //random starting position in movement pattern
        startPos = randGen.nextInt(14) + 1;

        if (1 <= startPos && startPos <= 3)
            dir = Direction.NORTH;
        else if ( 4 <= startPos && startPos <= 7)
            dir = Direction.EAST;
        else if ( 8 <= startPos && startPos <= 10 )
            dir = Direction.SOUTH;
        else if ( 11 <= startPos && startPos <= 14)
            dir = Direction.WEST;
    }
    
    /*****************************************************
   Birds always ROAR
    
    @param opponent who is the critter fighting?
    @return attack strategy
     *****************************************************/    
    public Attack getAttack(GVcritter opponent) {
        return Attack.ROAR;
    }
    
    /*****************************************************
    Birds move in a NE or SE circle pattern

    @return desired direction of next step
     *****************************************************/ 
    public Direction getMoveDirection () {
        steps++;
        startPos++;
        
        if(startPos % 14 <= 3)
            return Direction.NORTH;
        else if ( 4 <= startPos % 14 && startPos % 14 <= 7)
            return Direction.EAST;
        else if ( 8 <= startPos % 14 && startPos % 14 <= 10 )
            return Direction.SOUTH;
        else 
           return Direction.WEST;

    }
}
