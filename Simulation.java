import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/****************************************************
 * Simulates a 2D world of critters that move around
 * and fight if they inhabit the same location.
 * 
 * @Mark Baker
 * @12/5/2016
 ***************************************************/
public class Simulation extends JPanel{

    /** a 2D world of critters */
    private GVcritter[][] theWorld;

    /** a collection of all live critters */
    private ArrayList <GVcritter> allCritters;

    /** control size of the world */
    private final int ROWS=50, COLUMNS=70, SIZE=10;

    /** number of Ants alive in the simulation */
    private int numAnts;
    private int numBirds;
    private int numHippos;
    private int numVultures;
    private int numGiraffes;

    /** number of Simulation steps**/
    private int stepCount;

    /****************************************************
    Constructor instantiates and initializes all 
    instance members.
     ****************************************************/
    public Simulation(){
        theWorld = new GVcritter[ROWS][COLUMNS];
        allCritters = new ArrayList<GVcritter>();

        numAnts=0;
        numBirds=0;
        numHippos=0;
        numVultures=0;
        numGiraffes=0;

        stepCount=0;

        // set the appropriate size of the invisibile drawing area
        setPreferredSize(new Dimension(COLUMNS*SIZE, ROWS*SIZE));
    }

    /****************************************************
    Add the requested number of Ants into the simulation.
    Repeatedly ask for a random location that is free.
    Increment the number of Ants in the simulation.

    @param num number of ants
     ***************************************************/
    public void addAnts(int num){
        numAnts += num;
        for(int i=1;i<=num;i++){
            // create a new Ant at an open location
            Location loc = getOpenLocation();
            Ant c = new Ant(loc);
            placeCritter(c);
        }
    }

     /****************************************************
    Add the requested number of Birds into the simulation.
    Repeatedly ask for a random location that is free.
    Increment the number of Birds in the simulation.

    @param num number of birds
     ***************************************************/
    public void addBirds(int num) {
        numBirds += num;
        for(int i=1;i<=num;i++){
            // create a new Bird at an open location
            Location loc = getOpenLocation();
            Bird c = new Bird(loc);
            placeCritter(c);
        }
    }
    
     /****************************************************
    Add the requested number of Vultures into the simulation.
    Repeatedly ask for a random location that is free.
    Increment the number of Vultures in the simulation.

    @param num number of vultures
     ***************************************************/
    public void addVultures(int num) {
        numVultures += num;
        for(int i=1;i<=num;i++){
            // create a new Bird at an open location
            Location loc = getOpenLocation();
            Vulture c = new Vulture(loc);
            placeCritter(c);
        }
    }
    
     /****************************************************
    Add the requested number of Hippos into the simulation.
    Repeatedly ask for a random location that is free.
    Increment the number of Hippos in the simulation.

    @param num number of hippos
     ***************************************************/
    public void addHippos(int num) {
        numHippos += num;
        for(int i=1;i<=num;i++){
            // create a new Bird at an open location
            Location loc = getOpenLocation();
            Hippo c = new Hippo(loc);
            placeCritter(c);
        }
    }
    
     /****************************************************
    Add the requested number of Giraffes into the simulation.
    Repeatedly ask for a random location that is free.
    Increment the number of Giraffes in the simulation.

    @param num number of giraffes
     ***************************************************/
    public void addGiraffes(int num) {
        numGiraffes += num;
        for(int i=1;i<=num;i++){
            // create a new Bird at an open location
            Location loc = getOpenLocation();
            Giraffe c = new Giraffe(loc);
            placeCritter(c);
        }
    }

    /******************************************************
    Move forward on step of the simulation
     *****************************************************/
    public void oneStep(){

        // shuffle the arraylist of critters for better performance
        Collections.shuffle(allCritters);
        stepCount++;

        // step throgh all critters using traditional for loop
        for(int i=0; i<allCritters.size(); i++){
            GVcritter attacker = allCritters.get(i);

            // what location does critter want to move to?
            GVcritter.Direction dir = attacker.getMoveDirection();
            Location previousLoc = attacker.getLocation();
            Location nextLoc = getRelativeLocation(previousLoc, dir);  

            // who is at the next location?
            GVcritter defender = theWorld[nextLoc.getRow()][nextLoc.getCol()];

            // no critters here so OK for critter 1 to move
            if(defender == null){
                theWorld[nextLoc.getRow()][nextLoc.getCol()] = attacker;
                attacker.setLocation(nextLoc);
                theWorld[previousLoc.getRow()][previousLoc.getCol()] = null;

                // both critters the same species so peacefully bypass 
            }else if(attacker.getSpecies() == defender.getSpecies()){

                // update critter locations
                attacker.setLocation(nextLoc);
                defender.setLocation(previousLoc);

                // update positions in the world
                theWorld[nextLoc.getRow()][nextLoc.getCol()] = attacker;
                theWorld[previousLoc.getRow()][previousLoc.getCol()] = defender;

                //different species so they fight at location of critter 2
            }else if(attacker.getSpecies() != defender.getSpecies()){
                fight(attacker, defender);
            }
        }

        // update drawing of the world
        repaint();
    }

    /******************************************************
    Step through the 2D world and paint each location white
    (for no critter) or the critter's color.  The SIZE of 
    each location is constant.

    @param g graphics element used for display
     *****************************************************/      
    public void paintComponent(Graphics g){
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLUMNS; col++){
                GVcritter c = theWorld[row][col];

                // set color to white if no critter here
                if(c == null){
                    g.setColor(Color.WHITE);
                    // set color to critter color   
                }else{    
                    g.setColor(c.getColor());
                }

                // paint the location
                g.fillRect(col*SIZE, row*SIZE, SIZE, SIZE);
            }
        }
    }

     /****************************************************
      return string of stats
     ***************************************************/
    public String getStats () {
        String x;
        x = "" + "Steps: " + stepCount +
        "\nAnts: " + numAnts +
        "\nBirds: " + numBirds +
        "\nHippos: " + numHippos +
        "\nVultures: " + numVultures +
        "\nGiraffes: " + numGiraffes;

        return x;
    }

    /****************************************************
     reset the simulation. All instance variables back to 0. Clear all critters.
     ***************************************************/
    public void reset () {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                theWorld[i][j] = null;
            }
        }

        allCritters.clear();

        numAnts=0;
        numBirds=0;
        numHippos=0;
        numVultures=0;
        numGiraffes=0;
        stepCount=0;

    }

    /****************************************************
    Find an open location
     ***************************************************/
    private Location getOpenLocation () {
        Random randGen = new Random();
        int r, c;
        do {
            r = randGen.nextInt(50);
            c = randGen.nextInt(70);
        } while (theWorld[r][c] != null);

        Location x = new Location(r, c);
        return x;
    }

    /****************************************************
     place a critter in the sim
     ***************************************************/
    private void placeCritter (GVcritter c) {
        allCritters.add(c);
        int row = c.getLocation().getRow();
        int col = c.getLocation().getCol();
        theWorld[row][col] = c;
    }

    /****************************************************
    return next movement location 
     ***************************************************/
    private Location getRelativeLocation (Location loc, GVcritter.Direction d) {
        int rowStart = loc.getRow();
        int colStart = loc.getCol();
        int rowEnd = 0, colEnd = 0;

        if (d == GVcritter.Direction.WEST) {
            if (colStart == 0) {
                colEnd = COLUMNS - 1;
                rowEnd = rowStart;
            } else {
                colEnd = colStart - 1;
                rowEnd = rowStart;
            }
        } else if (d == GVcritter.Direction.EAST) {
            if (colStart == COLUMNS - 1) {
                colEnd = 0;
                rowEnd = rowStart;
            } else {
                colEnd = colStart + 1;
                rowEnd = rowStart;
            }
        } else if (d == GVcritter.Direction.SOUTH) {
            if (rowStart == ROWS - 1) {
                colEnd = colStart;
                rowEnd = 0;
            } else {
                colEnd = colStart;
                rowEnd = rowStart + 1;
            }
        } else if (d == GVcritter.Direction.NORTH) {
            if (rowStart == 0) {
                colEnd = colStart;
                rowEnd = ROWS - 1;
            } else {
                colEnd = colStart;
                rowEnd = rowStart - 1;
            }
        }

        Location x = new Location (rowEnd, colEnd);
        return x;
    }

    /****************************************************
    kill a critter and remove it from the sim
     ***************************************************/
    private void critterDies (GVcritter c) {
        if (c.getSpecies() == GVcritter.Species.ANT) 
            numAnts = numAnts - 1;
        else if (c.getSpecies() == GVcritter.Species.BIRD) 
            numBirds = numBirds - 1;
        else if (c.getSpecies() == GVcritter.Species.VULTURE) 
            numVultures = numVultures - 1;
        else if (c.getSpecies() == GVcritter.Species.HIPPO) 
            numHippos = numHippos - 1;
        else 
            numGiraffes = numGiraffes - 1;

        allCritters.remove(c);
    }

    /****************************************************
    fight method. determins winner of fights
     ***************************************************/
    private void fight (GVcritter attacker, GVcritter defender) {
        boolean attackerWins = false;

        if (attacker.getAttack(defender) == defender.getAttack(attacker)) {
            Random randGen = new Random();
            int x = randGen.nextInt(2);
            if (x == 0) {
                attackerWins = true;
            } else 
                attackerWins = false;
        } else if (attacker.getAttack(defender) == GVcritter.Attack.FORFEIT) {
            attackerWins = false;
        } else if (defender.getAttack(attacker) == GVcritter.Attack.FORFEIT){
            attackerWins = true;
        } else if ((attacker.getAttack(defender) == GVcritter.Attack.POUNCE && 
            defender.getAttack(attacker) == GVcritter.Attack.ROAR) || 
        (attacker.getAttack(defender) == GVcritter.Attack.ROAR && 
            defender.getAttack(attacker) == GVcritter.Attack.SCRATCH) ||
        (attacker.getAttack(defender) == GVcritter.Attack.SCRATCH && 
            defender.getAttack(attacker) == GVcritter.Attack.POUNCE)){
            attackerWins = true;
        } else 
            attackerWins = false;

        if (attackerWins) {
            theWorld[attacker.getLocation().getRow()][attacker.getLocation().getCol()] = null;
            attacker.setLocation(defender.getLocation());

            critterDies(defender);
        } else {
            theWorld[attacker.getLocation().getRow()][attacker.getLocation().getCol()] = null;

            critterDies(attacker);
        }

    }
}
