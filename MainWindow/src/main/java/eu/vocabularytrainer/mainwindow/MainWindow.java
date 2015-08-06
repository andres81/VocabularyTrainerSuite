/**
 * VocabularyTrainer  Copyright (C) 2015  André Schepers
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.vocabularytrainer.mainwindow;

import eu.vocabularytrainer.mainwindow.MenuBar.ActionCommands;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class MainWindow extends JFrame implements ActionListener {
    
    /**
     * 
     */
    public MainWindow() {
        setJMenuBar(MenuBar.makeMenuBar(MainWindow.this));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        show();
    }
    
    /**
     * 
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(ActionCommands.LOAD_LESSON_XML.toString())) {
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(MainWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
//                File file = fc.getSelectedFile();
                System.out.println("Opening.");
            } else {
                System.out.println("Open command cancelled by user.");
            }
        }
    }
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
       MainWindow mw = new MainWindow(); 
    }
}
