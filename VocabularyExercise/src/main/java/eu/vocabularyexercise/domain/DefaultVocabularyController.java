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
package eu.vocabularyexercise.domain;

import eu.vocabularyexercise.domain.interfaces.VocabularyController;
import eu.vocabularyexercise.domain.interfaces.VocabularyExerciseModel;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author andres81
 */
public class DefaultVocabularyController implements VocabularyController {

    // Logging
    private static final Logger logger = LogManager.getLogger(DefaultVocabularyController.class);
    
    /**
     * 
     */
    private VocabularyExerciseModel model = null;
    
    /**
     * 
     * @param model
     */
    public DefaultVocabularyController(VocabularyExerciseModel model) {
        if (model == null) {
            throw new NullPointerException();
        }
        this.model = model;
    }
    
    /**
     * 
     * @param uuid 
     */
    @Override
    public void doGuess(UUID uuid) {
        if (model != null) {
            if (model.getActiveQueryOption().getUuid() == uuid) {
                model.setRandomActiveQueryPair();
            }
        }
    }

    /**
     * 
     * @param model 
     */
    @Override
    public void setModel(VocabularyExerciseModel model) {
        this.model = model;
    }
}
