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
package eu.vocabularyexercise.gui;

import eu.vocabularyexercise.domain.DefaultVocabularyController;
import eu.vocabularyexercise.domain.DefaultVocabularyModel;
import eu.vocabularyexercise.domain.interfaces.VocabularyController;
import eu.vocabularyexercise.domain.interfaces.VocabularyModel;
import eu.vocabularytrainer.vocabulary.DefaultRepresentative;
import eu.vocabularytrainer.vocabulary.DefaultVocabulary;
import eu.vocabularytrainer.vocabulary.DefaultVocabularyElementPair;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author andres81
 */
public class VocabularyExercise extends JPanel implements Observer, RepresentativesViewCallback, ActionListener {
    
    // Logging
    private static final Logger logger = LogManager.getLogger(VocabularyExercise.class);
    
    /**
     * 
     */
    private VocabularyModel model = null;
    
    /**
     * 
     */
    private VocabularyController controller = null;
    
    /**
     * 
     */
    private RepresentativesView representativesView = null;
    
    /**
     * 
     */
    private JLabel queryView = null;
            
    /**
     * 
     */
    public VocabularyExercise() {
        super();
        init();
        model = new DefaultVocabularyModel();
        setController(new DefaultVocabularyController(model));
        setModel(model);
    }
    
    /**
     * 
     * @param model
     * @param controller 
     */
    public VocabularyExercise(VocabularyModel model, VocabularyController controller) {
        this();
        if (model != null) {
            setModel(model);
        }
        if (controller != null) {
            setController(controller);
        }
    }
    
    /**
     * 
     */
    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        queryView = new JLabel();
        queryView.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(50, 50)));
        add(queryView);
        add(Box.createRigidArea(new Dimension(50, 50)));
        representativesView = new RepresentativesView(this);
        representativesView.setAlignmentX(CENTER_ALIGNMENT);
        add(representativesView);
    }
    
    /**
     * 
     * @param model
     */
    public void setModel(VocabularyModel model) {
        if (model == null) {
            throw new NullPointerException("Model can't be null.");
        }
        this.model = model;
        controller.setModel(model);
        model.addObserver(this);
        updateVocabularyEntryPairs();
    }
    
    /**
     * 
     * @param controller 
     */
    public void setController(VocabularyController controller) {
        if (controller == null) {
            throw new NullPointerException("Can't set a null value for the controller!");
        }
        this.controller = controller;
        this.controller.setModel(model);
    }
    
    /**
     * 
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == this.model) {
            updateVocabularyEntryPairs();
        }
    }
    
    /**
     * 
     */
    private void updateVocabularyEntryPairs() {
        representativesView.setRepresentatives(model.getOptions());
        queryView.setText(model.getActiveQuery().getTitle());
    }
            
    /**
     * 
     * @param uuid 
     */
    @Override
    public void representativeClicked(UUID uuid) {
        controller.doGuess(uuid);
    }
    
    /**
     * 
     * @return 
     */
    public VocabularyModel getModel() {
        return model;
    }
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        VocabularyExercise exercise = new VocabularyExercise();
        Vocabulary voc = DefaultVocabulary.createFromXML("src/main/resources/lesson-1.xml");
        System.err.println(voc.getPairs().size());
        exercise.getModel().setVocabularyElementPairs(voc.getPairs());
        JFrame frame = new JFrame();
        JPanel pane = (JPanel) frame.getContentPane();
        pane.add(exercise, BorderLayout.CENTER);
        JPanel options = new JPanel();
        JButton button = new JButton();
        button.setText("Andre");
        button.addActionListener(exercise);
        options.add(button);
        pane.add(options, BorderLayout.PAGE_START);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.show();
    }
    
    /**
     * 
     * @return 
     */
    public static List<VocabularyElementPair> getRepresentatives() {
        List<VocabularyElementPair> pairs = new ArrayList<>();
        
        DefaultVocabularyElementPair pair = new DefaultVocabularyElementPair(
            new DefaultRepresentative("one", null),
            new DefaultRepresentative("un", null));
        pairs.add(pair);
        pair = new DefaultVocabularyElementPair(
            new DefaultRepresentative("two", null),
            new DefaultRepresentative("deux", null));
        pairs.add(pair);
        pair = new DefaultVocabularyElementPair(
            new DefaultRepresentative("three", null),
            new DefaultRepresentative("trois", null));
        pairs.add(pair);
        pair = new DefaultVocabularyElementPair(
            new DefaultRepresentative("four", null),
            new DefaultRepresentative("quatre", null));
        pairs.add(pair);
        pair = new DefaultVocabularyElementPair(
            new DefaultRepresentative("five", null),
            new DefaultRepresentative("cinque", null));
        pairs.add(pair);
        return pairs;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        model.setDirection(Direction.COLUMNTWOTOTWO);
    }
}
