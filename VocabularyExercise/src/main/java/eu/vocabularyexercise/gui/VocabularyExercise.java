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
package eu.vocabularyexercise.gui;

import eu.vocabularyexercise.domain.DefaultVocabularyController;
import eu.vocabularyexercise.domain.DefaultVocabularyExerciseModel;
import eu.vocabularyexercise.domain.interfaces.VocabularyController;
import eu.vocabularyexercise.domain.interfaces.VocabularyExerciseModel;
import eu.vocabularytrainer.vocabulary.DefaultVocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.Direction;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary.UpdateType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
    private VocabularyExerciseModel model = null;
    
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
    private QueryView queryView = null;

    /**
     * 
     */
    public VocabularyExercise() {
        super();
        init();
        model = new DefaultVocabularyExerciseModel();
        setController(new DefaultVocabularyController(model));
        setModel(model);
    }
    
    /**
     * 
     * @param file
     */
    public VocabularyExercise(File file) {
        this();
        Vocabulary voc = DefaultVocabulary.createFromXML(file);
        model.setVocabularyElementPairs(voc.getPairs());
    }
    
    /**
     * 
     * @param file
     * @param model
     * @param controller 
     */
    public VocabularyExercise(File file, VocabularyExerciseModel model, VocabularyController controller) {
        this(file);
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
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        
        JRadioButton button1 = new JRadioButton("ONE-ONE");
        button1.setAlignmentX(CENTER_ALIGNMENT);
        button1.addActionListener(this);
        button1.setActionCommand(Direction.COLUMNONETOONE.toString());
        JRadioButton button2 = new JRadioButton("ONE-TWO");
        button2.setAlignmentX(CENTER_ALIGNMENT);
        button2.addActionListener(this);
        button2.setActionCommand(Direction.COLUMNONETOTWO.toString());
        JRadioButton button3 = new JRadioButton("TWO-ONE");
        button3.setAlignmentX(CENTER_ALIGNMENT);
        button3.addActionListener(this);
        button3.setActionCommand(Direction.COLUMNTWOTOONE.toString());
        JRadioButton button4 = new JRadioButton("TWO-TWO");
        button4.setAlignmentX(CENTER_ALIGNMENT);
        button4.addActionListener(this);
        button4.setActionCommand(Direction.COLUMNTWOTOTWO.toString());
        
        add(button1);
        add(button2);
        add(button3);
        add(button4);
                
        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(button1);
        group.add(button2);
        group.add(button3);
        group.add(button4);
        
        JRadioButton button5 = new JRadioButton("STRING");
        button5.setAlignmentX(CENTER_ALIGNMENT);
        button5.addActionListener(this);
        button5.setActionCommand(Representation.STRING.toString());
        JRadioButton button6 = new JRadioButton("SOUND");
        button6.setAlignmentX(CENTER_ALIGNMENT);
        button6.addActionListener(this);
        button6.setActionCommand(Representation.SOUND.toString());
        JRadioButton button7 = new JRadioButton("IMAGE");
        button7.setAlignmentX(CENTER_ALIGNMENT);
        button7.addActionListener(this);
        button7.setActionCommand(Representation.IMAGE.toString());
        
        JRadioButton button8 = new JRadioButton("STRINGOPTION");
        button8.setAlignmentX(CENTER_ALIGNMENT);
        button8.addActionListener(this);
        button8.setActionCommand(Representation.STRING.toString());
        JRadioButton button9 = new JRadioButton("SOUNDOPTION");
        button9.setAlignmentX(CENTER_ALIGNMENT);
        button9.addActionListener(this);
        button9.setActionCommand(Representation.SOUND.toString());
        JRadioButton button10 = new JRadioButton("IMAGEOPTION");
        button10.setAlignmentX(CENTER_ALIGNMENT);
        button10.addActionListener(this);
        button10.setActionCommand(Representation.IMAGE.toString());
        
        add(button5);
        add(button6);
        add(button7);
        add(button8);
        add(button9);
        add(button10);
                
        //Group the radio buttons.
        ButtonGroup group2 = new ButtonGroup();
        group2.add(button5);
        group2.add(button6);
        group2.add(button7);
        
        //Group the radio buttons.
        ButtonGroup group3 = new ButtonGroup();
        group3.add(button8);
        group3.add(button9);
        group3.add(button10);
        
        
        JButton nextButton = new JButton("next");
        nextButton.addActionListener(this);
        JButton backButton = new JButton("back");
        backButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        add(buttonPanel);
        
        queryView = new QueryView();
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
    public void setModel(VocabularyExerciseModel model) {
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
        if (o != this.model) return;
        if (arg instanceof UpdateType) {
            UpdateType type = (UpdateType) arg;
            switch (type) {
                case DIRECTION:
                case PAIRS:
                    representativesView.setRepresentatives(model.getOptions());
                    queryView.setRepresentative(model.getActiveQuery());
                    break;
                case ACTIVEPAIR:
                    queryView.setRepresentative(model.getActiveQuery());
                case OPTIONSINTERACTIONTYPE:
                    representativesView.setRepresentation(model.getOptionsRepresentation());
                    break;
                case QUERYINTERACTIONTYPE:
                    queryView.setRepresentation(model.getQueryRepresentation());
                    break;
            }
        }
    }
    
    /**
     * 
     */
    private void updateVocabularyEntryPairs() {
        representativesView.setRepresentatives(model.getOptions());
        // updateActivePair();
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
    public VocabularyExerciseModel getModel() {
        return model;
    }
    
    /**
     * 
     * @param vocabulary 
     */
    public void setVocabulary(Vocabulary vocabulary) {
        model.setVocabularyElementPairs(vocabulary.getPairs());
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() instanceof JButton) {
            JButton button = (JButton)ae.getSource();
            if (button.getText().equals("next")) {
                model.shiftToNextPairs();
            } else {
                model.shiftToPreviousPairs();
            }
            return;
        }
        try {
            model.setDirection(Direction.valueOf(ae.getActionCommand()));
            return;
        } catch (IllegalArgumentException ex) {}
        try {
            JRadioButton button = (JRadioButton)ae.getSource();
            if (button.getText().endsWith("OPTION")) {
                model.setOptionsRepresentation(Representation.valueOf(ae.getActionCommand()));
            } else {
                model.setQueryRepresentation(Representation.valueOf(ae.getActionCommand()));
            }
        } catch (IllegalArgumentException ex) {}
    }
}
