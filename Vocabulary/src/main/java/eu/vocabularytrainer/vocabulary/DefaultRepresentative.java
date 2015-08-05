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
package eu.vocabularytrainer.vocabulary;

import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.util.UUID;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author andres81
 */
public class DefaultRepresentative implements Representative {
    
    // Logging
    private static final Logger logger = LogManager.getLogger(DefaultRepresentative.class);
    
    /**
     * 
     */
    private String title = null;
    
    /**
     * 
     */
    private Image image = null;
    
    /**
     * 
     */
    private UUID uuid = null;
    
    /**
     * 
     */
    @Inject
    public DefaultRepresentative() {
        uuid = UUID.randomUUID();
        image = new BufferedImage(20, 20, TYPE_INT_RGB);
        title = "TITLE";
    }
    
    /**
     * 
     * @param uuid
     * @param title
     * @param image
     */
    public DefaultRepresentative(UUID uuid, String title, Image image) {
        this();
        if (uuid != null) {
            this.uuid = uuid;
        }
        if (title != null) {
            this.title = title;
        }
        if (image != null) {
            this.image = image;
        }
    }
    
    /**
     * 
     */
    @Override
    public Image getImage() {
        return image;
    }
    
    /**
     * 
     * @param image 
     */
    public void setImage(Image image) {
        if (image == null) {
            throw new NullPointerException("Null given for image parameter!");
        }
        this.image = image;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getTitle() {
        return title; 
    }
    
    /**
     * 
     * @param title 
     */
    public void setTitle(String title) {
        if (title == null) {
            throw new NullPointerException("");
        }
        this.title = title;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
