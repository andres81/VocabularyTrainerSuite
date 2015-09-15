package nl.team51.playingoggfiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andre Schepers <andre@team51.nl>
 */
public class Andre {
  
  public static void main(String[] args) {

    JFrame mainWin = new JFrame();
    JPanel pane = (JPanel)mainWin.getContentPane();
    JButton button = new JButton("Play");
    pane.add(button);
    mainWin.setVisible(true);
    mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        final AudioFilePlayer player = new AudioFilePlayer ();
        player.play("/Users/andres81/something.mp3");
      }
    });
  }
}
