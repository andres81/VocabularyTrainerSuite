/**
 * VocabularyTrainer Copyright (C) 2015 André Schepers andreschepers81@gmail.com
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

import eu.vocabularyexercise.gui.VocabularyExercise;
import eu.vocabularytrainer.mainapplication.DefaultMainController;
import eu.vocabularytrainer.mainapplication.DefaultMainModel;
import eu.vocabularytrainer.mainapplication.interfaces.MainModel;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class MainWindow extends JFrame implements Observer {
    
    /**
     * 
     */
    private VocabularyExercise exercise = null;
    
    /**
     * 
     */
    private MainModel model;
    
    /**
     * 
     */
    public MainWindow() {
        model = new DefaultMainModel();
        model.addObserver(this);
        setJMenuBar(new MenuBar(new DefaultMainController(model)));
        getContentPane().add(getVocabularyExercise(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        show();
    }
    
    /**
     * 
     */
    private VocabularyExercise getVocabularyExercise() {
       if (exercise == null) {
           exercise = new VocabularyExercise();
       }
       return exercise;
    }
    
    //--------------------------------------------------------------------------
    // Main Function
    //--------------------------------------------------------------------------
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
       MainWindow mw = new MainWindow(); 
    }

    /**
     * 
     * @param o
     * @param o1 
     */
    @Override
    public void update(Observable o, Object o1) {
        if (o == model &&
            o1 instanceof MainModel.Changes) {
            switch ((MainModel.Changes) o1) {
                case NEW_VOCABULARY:
                    Vocabulary vocabulary = model.getVocabulary();
                    getVocabularyExercise().setVocabulary(vocabulary);
                    break;
            }
            revalidate();
        }
    }
}
