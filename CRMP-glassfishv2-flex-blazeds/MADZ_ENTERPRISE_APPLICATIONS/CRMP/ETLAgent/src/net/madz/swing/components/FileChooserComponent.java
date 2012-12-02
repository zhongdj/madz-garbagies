package net.madz.swing.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import net.madz.swing.resources.ResourceFactory;
import net.madz.swing.view.util.GraphicFactory;

import org.apache.log4j.Logger;

public class FileChooserComponent {
    private static Logger log = Logger.getLogger(FileChooserComponent.class);
    
    private JTextField locationText;

    private JButton button;

    private FileFilter filter;
    
    private JFileChooser fileChooser;
    
    public static String selectFilePath = null;

    public String getSelectFilePath() {
        return selectFilePath;
    }

    public static void setSelectFilePath(String aSelectFilePath) {
        selectFilePath = aSelectFilePath;
    }

    protected String lDomain = "WizardResource";

    private File path;

    public FileChooserComponent(final Component frame, final String type) {
        locationText = GraphicFactory.createTextField(true);
        // new JTextField();
        locationText.setEditable(false);
        locationText.setColumns(30);

        button = GraphicFactory.createTextButton(ResourceFactory.getInstance().getString(lDomain, "BROWSE_LABEL"));
        // new JButton();
        // button.setText("Browse");
        button.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                if (selectFilePath != null) {
                    File selectedFile = new File(selectFilePath);
                    if (selectedFile != null && selectedFile.exists()) {
                        if (selectedFile.isDirectory()) {
                            fileChooser.setCurrentDirectory(selectedFile);
                        } else {
                            fileChooser.setSelectedFile(selectedFile);
                        }
                        
                    }
                }
                
                if (filter != null) {
                    fileChooser.setFileFilter(filter);
                }
                if (null != path) {
                    fileChooser.setCurrentDirectory(path);
                }
                if (type.equalsIgnoreCase("dir")) {
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                   
                } else if (type.equalsIgnoreCase("file")) {
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                } else {
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                }
                int rVal = fileChooser.showOpenDialog(frame);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    File fileName = fileChooser.getSelectedFile();
                    locationText.setText(fileName.toString());
                    //Barry FIX ATP ISSUE 10
                    try {
                        setSelectFilePath(fileName.getCanonicalPath());
                    } catch (IOException e1) {
                        log.error(e1);
                        //ignore this exception
                    }
                }
            }

        });
    }

    public void setFilter(FileFilter filter) {
        this.filter = filter;
    }
    
    public void setFilePath(File path) {
        this.path = path;
    }

    public JTextField getLocationText() {
        return locationText;
    }

    public JButton getButton() {
        return button;
    }
    
  /* public void setSelectedFile(String selectedFilePath) {
       File file = new File(selectedFilePath);
       if (file != null && file.exists()) {
           fileChooser.setSelectedFile(file);
       }
   }*/
}
