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
package eu.vocabularytrainer.mainwindow;

import eu.vocabularytrainer.mainapplication.interfaces.MainController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Andre Schepers andreschepers81@gmail.com
 */
public final class MenuBar extends JMenuBar implements ActionListener {
    
    /**
     * 
     */
    public static final String LOAD_LESSON_XML = "LOAD_LESSON_XML";
    
    private final MainController controller;
    
    /**
     * 
     * @param controller 
     */
    public MenuBar(MainController controller) {
        this.controller = controller;
        add(getVocabularyMenu(controller));
    }
    
    
    /**
     * 
     * @param controller
     * @return 
     */
    public JMenu getVocabularyMenu(MainController controller) {
        JMenu m = new JMenu("Vocabulary");
        JMenuItem menuItem = new JMenuItem("Load a lesson");
        KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(
                KeyEvent.VK_X, 
                KeyEvent.CTRL_DOWN_MASK);
        menuItem.setAccelerator(keyStrokeToOpen);
        menuItem.setActionCommand(MenuBar.LOAD_LESSON_XML);
        menuItem.addActionListener(MenuBar.this);
        m.add(menuItem);
        return m;
    }

    /**
     * 
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(MenuBar.LOAD_LESSON_XML)) {
            loadLessonXml();
        }
    }
    
    /**
     * 
     */
    private void loadLessonXml() {
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.xml", "xml");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(MenuBar.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            controller.loadXmlFile(fc.getSelectedFile());
        }
    }
}
