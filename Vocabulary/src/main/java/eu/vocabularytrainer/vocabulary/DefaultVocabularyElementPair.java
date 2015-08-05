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
import eu.vocabularytrainer.vocabulary.interfaces.VocabularyElementPair;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author andres81
 */
public class DefaultVocabularyElementPair implements VocabularyElementPair {
    
    // Logging
    private static final Logger logger = LogManager.getLogger(DefaultVocabularyElementPair.class);
    
    /**
     * 
     */
    private final Representative entryOne;
    
    /**
     * 
     */
    private final Representative entryTwo;
    
    /**
     * 
     */
    private final UUID uuid;
    
    /**
     * 
     * @param first
     * @param second
     */
    public DefaultVocabularyElementPair(Representative first, Representative second) {
        if (first == null ||
            second == null) {
            throw new NullPointerException();
        }
        this.uuid = UUID.randomUUID();
        entryOne = first;
        entryTwo = second;
    }
    
    /**
     * 
     * @param pair 
     */
    public DefaultVocabularyElementPair(VocabularyElementPair pair) {
        if (pair.getUuid() == null ||
            pair.getFirst() == null ||
            pair.getSecond() == null) {
            throw new NullPointerException();
        }
        this.uuid = pair.getUuid();
        entryOne = pair.getFirst();
        entryTwo = pair.getSecond();
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Representative getFirst() {
        return entryOne;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Representative getSecond() {
        return entryTwo;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DefaultVocabularyElementPair) {
            DefaultVocabularyElementPair pair = (DefaultVocabularyElementPair) obj;
            return uuid == pair.getUuid();
        }
        return false;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.uuid);
        return hash;
    }
}
