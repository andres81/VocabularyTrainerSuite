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

import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import static eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation.IMAGE;
import static eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation.STRING;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author andres81
 */
public class RepresentativesView extends JPanel implements ActionListener {
    
    // Logging
    private static final Logger logger = LogManager.getLogger(RepresentativesView.class);
    
    /**
     * 
     */
    private List<? extends Representative> representatives = null;
    
    /**
     * 
     */
    private Representation representation = null;
    
    /**
     * 
     */
    private Map<JButton, UUID> buttonMapper = null;
    
    /**
     * 
     */
    private RepresentativesViewCallback callback = null;
    
    /**
     * 
     * @param callback 
     */
    public RepresentativesView(RepresentativesViewCallback callback) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        representation = Representative.Representation.STRING;
        this.callback = callback;
    }
    
    /**
     * 
     * @param callback 
     */
    public void setRepresentativesCallback(RepresentativesViewCallback callback) {
        this.callback = callback;
    }
    
    /**
     * 
     * @param representatives 
     */
    public void setRepresentatives(List<Representative> representatives) {
        this.representatives = representatives;
        initGui();
    }
    
    /**
     * 
     */
    private void initGui() {
        removeAll();
        if (buttonMapper == null) {
            buttonMapper = new HashMap<>();
        }
        if (representatives == null) return;
        for (Representative rep : representatives) {
            JButton button = new JButton();
            button.setAlignmentX(CENTER_ALIGNMENT);
            switch (representation) {
                case IMAGE:
                    button.setIcon(new ImageIcon(rep.getImage()));
                    break;
                case STRING:
                    button.setText(rep.getTitle());
                    break;
                default:
                    break;
            }
            add(button);
            add(Box.createRigidArea(new Dimension(10, 10)));
            buttonMapper.put(button,rep.getUuid());
            button.addActionListener(this);
        }
        revalidate();
    }
    
    /**
     * 
     * @param representation 
     */
    public void setRepresentation(Representation representation) {
        this.representation = representation;
        initGui();
    }

    /**
     * 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (callback == null) {
            logger.info("No callback set so not calling callback function for button press.");
            return;
        }
        JButton buttonClicked;
        try {
            buttonClicked = (JButton) e.getSource();
        } catch(ClassCastException ex) {
            logger.error("Could not cast event source to JButton.", ex);
            return;
        }
        UUID uuid = buttonMapper.get(buttonClicked);
        if (uuid == null) {
            logger.error("Could not find uuid for button with title: " + buttonClicked.getText());
            return;
        }
        callback.representativeClicked(uuid);
    }
}
