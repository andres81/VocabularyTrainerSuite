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
package eu.vocabularytrainer.vocabulary;

import eu.vocabularytrainer.vocabulary.interfaces.Representative;
import java.util.UUID;
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
    private String image = null;
    
    /**
     * 
     */
    private String sound = null;
    
    /**
     * 
     */
    private UUID uuid = null;
    
    /**
     * 
     */
    public DefaultRepresentative() {
        uuid = UUID.randomUUID();
        image = "";
        sound = "";
        title = "TITLE";
    }
    
    /**
     * 
     * @param title
     * @param image 
     * @param sound 
     */
    public DefaultRepresentative(String title, String image, String sound) {
        this();
        this.title = title == null ? "title" : title;
        this.image = image;
        this.sound = sound;
    }
    
    /**
     * 
     */
    @Override
    public String getImage() {
        return image;
    }
    
    /**
     * 
     * @param image 
     */
    public void setImage(String image) {
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
        this.title = title == null ? "title" : title;
    }

    /**
     * 
     */
    @Override
    public String getSound() {
        return sound;
    }
    
    /**
     * 
     * @param sound 
     */
    @Override
    public void setSound(String sound) {
        this.sound = sound;
    }
    
    @Override
    public UUID getUuid() {
        return uuid;
    }
}
