import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
/**********************************************************
GUI for a critter simulation.  Impements Runnable to allow
a method to run in the background while the user continues
to click on buttons.

@author Scott Grissom
@version August 2016
 ***********************************************************/
public class CritterGUI extends JFrame implements ActionListener, Runnable{

    /** simulation speed */
    private final int DELAY = 50;

    /** is simulation currently runnning? */
    private boolean isRunning;  

    /** the simulation object that controls everything */
    private Simulation world; 

    /** displays updated statistics */
    JTextArea statsArea;

    // FIX ME: define buttons as neeeded
    JButton ants;
    JButton birds;
    JButton hippos;
    JButton vultures;
    JButton giraffes;
    JButton start;
    JButton stop;

    // FIX ME: define menu items as needed
   /** menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenuItem quitItem;
    private JMenuItem clearItem;

    /************************************************************
    Main method displays the simulation GUI
     ************************************************************/
    public static void main(String arg[]){
        CritterGUI gui = new CritterGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Mark's Critter Simulation");
        gui.setSize(600,600);
        gui.pack();
        gui.setVisible(true);
    }

    /************************************************************
    Create the GUI
     ************************************************************/
    public CritterGUI(){

        // simulation is turned off 
        isRunning = false;

        // create the lay out
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        // Place the simulation on the screen
        position.gridx = 0;
        position.gridy = 1;
        position.gridwidth = 6;           
        world = new Simulation();
        add(world, position);
        
        // Place a label
        position = new GridBagConstraints();
        position.gridx = 6;
        position.gridy = 0;  
        add(new JLabel("Live Stats"),position);

        // Place stats area below the label
        position = new GridBagConstraints();
        statsArea = new JTextArea(7,12);
        statsArea.setBackground(Color.YELLOW);
        position.gridx = 6;
        position.gridy = 1;    
        position.anchor = GridBagConstraints.PAGE_START;
        add(statsArea, position);  
        statsArea.setText(world.getStats());

        //place ants button
        position = new GridBagConstraints();
        ants = new JButton( "Ants" );
        ants.setForeground(Color.RED);
        position.gridx = 0;
        position.gridy = 2;   
        add(ants, position);

        //place birds button
        birds = new JButton( "Birds" );
        position = new GridBagConstraints();
        birds.setForeground(Color.BLUE);
        position.gridx = 1;
        position.gridy = 2;   
        add(birds, position);
        
        //place hippos button
        hippos = new JButton( "Hippos" );
        position = new GridBagConstraints();
        hippos.setForeground(Color.GRAY);
        position.gridx = 3;
        position.gridy = 2;   
        add(hippos, position);
        
        //place vultures button
        vultures = new JButton( "Vultures" );
        position = new GridBagConstraints();
        vultures.setForeground(Color.BLACK);
        position.gridx = 2;
        position.gridy = 2;   
        add(vultures, position);
        
        //place giraffes button
        giraffes = new JButton( "Giraffes" );
        position = new GridBagConstraints();
        giraffes.setForeground(Color.MAGENTA);
        position.gridx = 4;
        position.gridy = 2;   
        add(giraffes, position);
        
        //place start button
        start = new JButton( "Start" );
        position = new GridBagConstraints();
        start.setForeground(Color.BLACK);
        position.gridx = 1;
        position.gridy = 0;   
        add(start, position);
        
        //place stop button
        stop = new JButton( "Stop" );
        position = new GridBagConstraints();
        stop.setForeground(Color.BLACK);
        position.gridx = 2;
        position.gridy = 0;   
        add(stop, position);
        
        //action listeners for each button
        ants.addActionListener(this);
        birds.addActionListener(this);
        hippos.addActionListener(this);
        vultures.addActionListener(this);
        giraffes.addActionListener(this);
        start.addActionListener(this);
        stop.addActionListener(this);

        //set up File Menus
        setupMenus();
        pack();
        
        // Advanced topic! this must be at the end of this method
        // start the simulation in separate thread
        new Thread(this).start();
    }

    /************************************************************
    Respond to button clicks
    @param e action even triggered by user
     ************************************************************/
    public void actionPerformed(ActionEvent e){

        //exit application if QUIT menu item
        if (e.getSource() == quitItem){
            System.exit(1);
        }
        
        //set running variable to true if START button
        if (e.getSource() == start){
            isRunning = true;
        }

        //set running variable to false if STOP button
        if (e.getSource() == stop){
            isRunning = false;
        }

        //reset simulation if CLEAR menu item
        if (e.getSource() == clearItem){
            world.reset();
        }

        //inject 10 ants if ANTS button
        if(e.getSource() == ants){ 
            world.addAnts(10);
        }
        
        //inject 10 birds if BIRDS button
        if(e.getSource() == birds){ 
            world.addBirds(10);
        }
        
        //inject 10 hippos if HIPPOS button
        if(e.getSource() == hippos){ 
            world.addHippos(10);
        }  
        
        //inject 10 vultures if VULTURES button
        if(e.getSource() == vultures){ 
            world.addVultures(10);
        }
        
        //inject 10 giraffes if GIRAFFES button
        if(e.getSource() == giraffes){ 
            world.addGiraffes(10);
        }
        
        // Afterwards, update display and statistics
        world.repaint();
        statsArea.setText(world.getStats());
    }

    /************************************************************
    Once started, this method runs forever in a separate thread
    The simulation only advances and displays if the boolean
    variable is currently true
     ************************************************************/
    public void run(){
        try {

            // run forever
            while(true) {

                // only update simulation if it is running
                if (isRunning) {
                    world.oneStep();
                    statsArea.setText(world.getStats());
                }

                // pause between steps.  Otherwise, the simulation
                // would move too quickly to see
                Thread.sleep(DELAY);
            }
        }
        catch (InterruptedException ex) {
        }
    }    
    
    /*********************************************************************
    Set up the menu items
     *********************************************************************/
    private void setupMenus(){

        // create menu components
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        clearItem = new JMenuItem("Clear");

        // assign action listeners
        quitItem.addActionListener(this);
        clearItem.addActionListener(this);
        
        // display menu components
        fileMenu.add(clearItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();

        menus.add(fileMenu);
        setJMenuBar(menus);
    } 
}