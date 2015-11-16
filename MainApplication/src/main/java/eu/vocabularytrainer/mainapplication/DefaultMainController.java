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
package eu.vocabularytrainer.mainapplication;

import eu.vocabularytrainer.mainapplication.interfaces.MainController;
import eu.vocabularytrainer.mainapplication.interfaces.MainModel;
import eu.vocabularytrainer.vocabulary.VocabularyFromXMLFactory;
import eu.vocabularytrainer.vocabulary.interfaces.Vocabulary;
import java.io.InputStream;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class DefaultMainController implements MainController {
    
    MainModel model = null;
    
    /**
     * 
     * @param model
     */
    public DefaultMainController(MainModel model) {
        this.model = model;
    }

    /**
     * 
     * @param in
     */
    @Override
    public void loadXmlFile(InputStream in) {
        Vocabulary vocabulary = VocabularyFromXMLFactory.createFromXML(in);
        model.setVocabulary(vocabulary);
    }
}
