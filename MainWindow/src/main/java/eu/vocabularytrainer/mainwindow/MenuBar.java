/**
 * VocabularyTrainer Copyright (C) 2015 André Schepers
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
package eu.vocabularytrainer.mainwindow;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public class MenuBar {
    
    /**
     * 
     */
    public enum ActionCommands {
        
        LOAD_LESSON_XML ("LOAD_LESSON_XML");
        
        private final String name;
        
        private ActionCommands(final String s) {
            name = s;
        }
        
        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    /**
     * 
     * @param al
     * @return 
     */
    public static JMenuBar makeMenuBar(ActionListener al) {
        JMenuBar mb = new JMenuBar();
        mb.add(makeVocabularyMenu(al));
        return mb;
    }
    
    /**
     * 
     * @param al
     * @return 
     */
    public static JMenu makeVocabularyMenu(ActionListener al) {
        JMenu m = new JMenu("Vocabulary");
        JMenuItem menuItem = new JMenuItem("Load a lesson");
        menuItem.setActionCommand(ActionCommands.LOAD_LESSON_XML.toString());
        menuItem.addActionListener(al);
        m.add(menuItem);
        return m;
    }
}
