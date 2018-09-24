import java.awt.*;
import java.util.Random;
/**
 * Write a description of class Hippo here.
 * 
 * @Mark Baker
 * @12/5/2016
 */
public class Hippo extends GVcritter
{
   //instance variables
    private Direction dir;
    private int age;
    
    private Random randGen = new Random();

    /*****************************************************
    Create starting values for this Hippo.
    @param loc given location for this critter
     *****************************************************/  
    public Hippo(Location loc){
        super(loc); 
        setColor(Color.GRAY);
        setSpecies(Species.HIPPO);
        
        age = randGen.nextInt(201) + 300;

        //random starting location
        startPos = randGen.nextInt(4);
            if(startPos == 0)
               dir = Direction.NORTH;
            else if ( startPos == 1)
                dir = Direction.WEST;
            else if ( startPos == 2 )
                dir = Direction.SOUTH;
            else 
                dir = Direction.EAST;
    }

    /*****************************************************
    Hippos always POUNCE unless too old, then they FORFEIT.

    @param opponent who is the critter fighting?
    @return attack strategy
     *****************************************************/     
    public Attack getAttack(GVcritter opponent) {
        if (steps < age) 
            return Attack.POUNCE;
        else 
            return Attack.FORFEIT;
    }

    /*****************************************************
    Hippos move in a random direction for 5 steps, then change direction randomly

    @return desired direction of next step
     *****************************************************/     
    public Direction getMoveDirection(){
        steps++;
        
        if (steps % 5 == 0) {
           int x = randGen.nextInt(4);
            if(x == 0) {
                dir = Direction.NORTH;
                return dir;
            } else if ( x == 1) {
                dir = Direction.WEST;
                return dir;
            } else if ( x == 2 ) {
                dir = Direction.SOUTH;
                return dir;
            } else {
                dir = Direction.EAST;
                return dir;
            }
        } else 
            return dir;
    }
}
