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
package eu.vocabularytrainer.vocabulary.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import eu.vocabularytrainer.vocabulary.DefaultRepresentative;
import java.util.UUID;

/**
 *
 * @author andres81
 */
@JsonDeserialize(as=DefaultRepresentative.class)
public interface Representative {

    /**
     * 
     * @return 
     */
    public String getImage();
    
    /**
     * 
     * @return 
     */
    public String getTitle();

    /**
     * 
     * @return 
     */
    public String getSound();
    
    /**
     * 
     * @param sound
     */
    public void setSound(String sound);
    
    /**
     * 
     * @return 
     */
    public UUID getUuid();
    
    /**
     * 
     */
    public enum Representation {
       STRING,
       IMAGE,
       SOUND;
    }
}
