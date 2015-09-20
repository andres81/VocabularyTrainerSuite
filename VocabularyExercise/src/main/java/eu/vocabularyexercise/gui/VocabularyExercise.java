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

import eu.vocabularyexercise.domain.AudioFilePlayer;
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
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
        initController(new DefaultVocabularyController(model));
        initModel(model);
    }
    
    /**
     * 
     * @param in 
     */
    public VocabularyExercise(InputStream in) {
        this();
        Vocabulary voc = DefaultVocabulary.createFromXML(in);
        model.setVocabulary(voc);
    }
    
    /**
     * 
     * @param in
     * @param model
     * @param controller 
     */
    public VocabularyExercise(InputStream in, VocabularyExerciseModel model, VocabularyController controller) {
        this(in);
        initModel(model);
        initController(controller);
    }
    
    /**
     * 
     */
    private void init() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        
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
        initModel(model);
    }
    
    /**
     * 
     * @param model 
     */
    private void initModel(VocabularyExerciseModel model) {
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
        initController(controller);
    }
    
    /**
     * 
     * @param controller 
     */
    private void initController(VocabularyController controller) {
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
                    representativesView.setRepresentation(model.getOptionsRepresentation());
                    queryView.setRepresentation(model.getQueryRepresentation());
                    break;
                case ACTIVEPAIR:
                    queryView.setRepresentative(model.getActiveQuery());
                    if (model.getQueryRepresentation() == Representation.SOUND) playSound();
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
        model.setVocabulary(vocabulary);
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
    
    /**
     * 
     */
    private void playSound() {
        String audio = model.getActiveQuery().getSound();
        if (audio == null || audio.equals("")) return;
        final AudioFilePlayer player = new AudioFilePlayer();
        player.playAudioFile(audio);
        
    }
}
