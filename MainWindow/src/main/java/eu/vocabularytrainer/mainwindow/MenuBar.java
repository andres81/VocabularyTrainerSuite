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
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
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
    public static final String LOAD_LOCAL_LESSON_XML = "LOAD_LOCAL_LESSON_XML";
    
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
        m.add(getLoadLesson());
        m.add(getLoadRusLessons());
        return m;
    }
    
    /**
     * 
     * @return 
     */
    private JMenuItem getLoadLesson() {
        JMenuItem menuItem = new JMenuItem("Load a lesson");
        KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(
                KeyEvent.VK_X, 
                KeyEvent.CTRL_DOWN_MASK);
        menuItem.setAccelerator(keyStrokeToOpen);
        menuItem.setActionCommand(LOAD_LESSON_XML);
        menuItem.addActionListener(this);
        return menuItem;
    }
    
    /**
     * 
     * @return 
     */
    private JMenu getLoadRusLessons() {
        JMenuItem menuItem1 = new JMenuItem("rus-lesson1-alfabet");
        menuItem1.setActionCommand(LOAD_LOCAL_LESSON_XML);
        menuItem1.addActionListener(this);
        
        JMenuItem menuItem2 = new JMenuItem("rus-lesson2-alfabet");
        menuItem2.setActionCommand(LOAD_LOCAL_LESSON_XML);
        menuItem2.addActionListener(this);
        
        JMenuItem menuItem3 = new JMenuItem("rus-lesson3-alfabet");
        menuItem3.setActionCommand(LOAD_LOCAL_LESSON_XML);
        menuItem3.addActionListener(this);
        
        JMenuItem menuItem4 = new JMenuItem("rus-lesson4-alfabet");
        menuItem4.setActionCommand(LOAD_LOCAL_LESSON_XML);
        menuItem4.addActionListener(this);
        
        JMenuItem menuItem5 = new JMenuItem("rus-lesson5-alfabet");
        menuItem5.setActionCommand(LOAD_LOCAL_LESSON_XML);
        menuItem5.addActionListener(this);

        JMenu subMenu = new JMenu("Russian lessons");
        subMenu.add(menuItem1);
        subMenu.add(menuItem2);
        subMenu.add(menuItem3);
        subMenu.add(menuItem4);
        subMenu.add(menuItem5);
        return subMenu;
    }
    
    /**
     * 
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case LOAD_LESSON_XML:
                loadLessonXml();
                break;
            case LOAD_LOCAL_LESSON_XML:
                {
                    JMenuItem item = (JMenuItem) ae.getSource();
                    String fileName = item.getText();
                    URL url;
                    url = getClass().getResource("/" + fileName + ".xml");
                    File file;
                    try {
                        file = new File(url.toURI());
                        loadLessonXml(file);
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
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
            loadLessonXml(fc.getSelectedFile());
        }
    }
    
        /**
     * 
     */
    private void loadLessonXml(File xmlFile) {
        controller.loadXmlFile(xmlFile);
    }

}
