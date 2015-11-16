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
import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import eu.vocabularytrainer.vocabulary.interfaces.Representative.Representation;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
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
        audioIcon = new ImageIcon(getImageFromUrl(getClass().getResource("/Audio-icon.png")));
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

    /**
     * 
     */
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
    
    /**
     * 
     * @param imageUrlString
     * @return 
     */
    public static Image getImageFromUrlString(String imageUrlString) {
        URL url;
        try {
            url = new URL(imageUrlString);
        } catch (IOException e) {
            System.err.println("Could not form url from given image url.");
            return null;
        }
        return getImageFromUrl(url);
    }
    
    /**
     * 
     * @param imageUrl
     * @return 
     */
    public static Image getImageFromUrl(URL imageUrl) {
        BufferedImage image;
        try {
            image = ImageIO.read(imageUrl);
        } catch (IllegalArgumentException | IOException ex) {
            System.err.println("Could not load image from url: " + imageUrl);
            return null;
        }
        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();

        return resizeImage(image, type);
    }
    
    /**
     * 
     * @param originalImage
     * @param type
     * @return 
     */
   private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        
        double orgHeight = originalImage.getHeight();
        double orgWidth = originalImage.getWidth();
        double ar = orgWidth / orgHeight;
        int width = (int)(100.0 * ar);
        
        BufferedImage resizedImage = new BufferedImage(width, 100, type);
        Graphics2D g = resizedImage.createGraphics();
              g.drawImage(originalImage,
                          // Area to draw on
                          0, 0, width, 100,
                          // part of the original image we take, full of course.
                          0, 0, originalImage.getWidth(), originalImage.getHeight(),
                          // Optional observer that is called when image is fully drawn.
                          null);
        g.dispose();

        return resizedImage;
    }
}
