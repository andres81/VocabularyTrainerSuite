/**
 * VocabularyTrainer Copyright (C) 2015 André Schepers andreschepers81@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.vocabularyexercise.gui;

import eu.vocabularyexercise.domain.AudioFilePlayer;
import eu.vocabularytrainer.vocabulary.DefaultVocabulary;
import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class QueryView extends JLabel implements MouseListener {
    
    /**
     * 
     */
    private Representative representative = null;
    
    /**
     * 
     */
    private Representation representation = null;
    
    /**
     * 
     */
    private ImageIcon audioIcon = null;
    
    /**
     * 
     */
    public QueryView() {
        super();
        setMinimumSize(new Dimension(300, 100));
        setAlignmentX(CENTER_ALIGNMENT);
        representation = Representation.STRING;
        audioIcon = new ImageIcon(DefaultVocabulary.getImageFromUrl(getClass().getResource("/Audio-icon.png")));
        this.addMouseListener(this);
    }
    
    /**
     * 
     * @param representation 
     */
    public void setRepresentation(Representation representation) {
        this.representation = representation;
        updateGui();
    }
    
    /**
     * 
     * @param representative
     */
    public void setRepresentative(Representative representative) {
        this.representative = representative;
        if (representation == Representation.SOUND) {
            playSound();
        }
        updateGui();
    }
    
    /**
     * 
     */
    public void updateGui() {
        setIcon(null);
        setText(null);
        switch (representation) {
            case IMAGE:
                setIcon(new ImageIcon(representative.getImage()));
                break;
            case SOUND:
                setIcon(audioIcon);
                break;
            case STRING:
                setText(representative.getTitle());
                break;
        }
    }

    private void playSound() {
        String audio = representative.getSound();
        if (audio == null || audio.equals("")) return;
        final AudioFilePlayer player = new AudioFilePlayer ();
        player.playAudioFile(audio);
        
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if (representation == Representation.SOUND) {
            playSound();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
}
