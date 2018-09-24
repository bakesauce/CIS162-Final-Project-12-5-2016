import java.awt.*;
import java.util.Random;
/**
 * Write a description of class Bird here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Giraffe extends GVcritter {

    // instance variables 
    private Direction dir;
    private Random randGen = new Random();

    
    /*****************************************************
    Create starting values for this Giraffe.
    @param loc given location for this critter
     *****************************************************/  
    public Giraffe (Location loc) {
        super(loc); 
        setColor(Color.MAGENTA);
        setSpecies(Species.GIRAFFE);

        if(Math.random() < 0.5)
            dir = Direction.SOUTH;
        else
            dir = Direction.NORTH;
    }

    /*****************************************************
    Giraffes always POUNCE
    
    @param opponent who is the critter fighting?
    @return attack strategy
     *****************************************************/   
    public Attack getAttack(GVcritter opponent) {
        return Attack.POUNCE;
    }

    /*****************************************************
    Girrafes move in a NW/SW lateral pattern, switching N and S every 10 steps.

    @return desired direction of next step
     *****************************************************/     
    public Direction getMoveDirection () {
        steps++;

        if (steps % 10 == 0) {
            if (dir == Direction.NORTH) {
                return dir = Direction.SOUTH;
            } else if (dir == Direction.SOUTH) {
                return dir = Direction.NORTH;
            } else
                return dir;
        }
        if(steps%2 == 0)
                return dir;
            else    
                return Direction.WEST;

    }
}