package de.uni_potsdam.hpi.asg.techmngr;

/*
 * Copyright (C) 2017 Norman Kluge
 * 
 * This file is part of ASGtechmngr.
 * 
 * ASGtechmngr is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ASGtechmngr is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ASGtechmngr.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.uni_potsdam.hpi.asg.common.gui.PropertiesDialog;
import de.uni_potsdam.hpi.asg.common.gui.PropertiesPanel;
import de.uni_potsdam.hpi.asg.common.iohelper.FileHelper;
import de.uni_potsdam.hpi.asg.common.technology.Technology;
import de.uni_potsdam.hpi.asg.techmngr.Configuration.TextParam;

public class EditTechDialog extends PropertiesDialog {
    private static final long serialVersionUID = 7635453181517878899L;

    private Technology        tech;
    private EditTechDialog    parent;

    public EditTechDialog(Technology tech) {
        this.tech = tech;
        this.parent = this;

        this.setModalityType(ModalityType.APPLICATION_MODAL);

        constructEditPanel(getContentPane());
    }

    private void constructEditPanel(Container root) {
        PropertiesPanel editPanel = new PropertiesPanel(this);
        root.add(editPanel);
        GridBagLayout gbl_editpanel = new GridBagLayout();
        gbl_editpanel.columnWidths = new int[]{150, 300, 0, 0, 40, 0};
        gbl_editpanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_editpanel.rowHeights = new int[]{15, 15, 15, 15, 15, 15, 0};
        gbl_editpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        editPanel.setLayout(gbl_editpanel);

        editPanel.addTextEntry(0, TextParam.name, "Name", "");
        editPanel.addTextEntry(1, TextParam.balsafolder, "Balsa technology folder", "", true, JFileChooser.DIRECTORIES_ONLY, false, true, "Choose the folder which contains the startup.scm");
        editPanel.addTextEntry(2, TextParam.genlibfile, "Genlib file", "", true, JFileChooser.FILES_ONLY, false);
        editPanel.addTextEntry(3, TextParam.searchpath, "Search path", "", false, null, false, true, "While using Design Compiler this value is appended to 'search_path'");
        editPanel.addTextEntry(4, TextParam.libraries, "Libraries", "", false, null, false, true, "While using Design Compiler 'link_library' and 'target_library' are set to this value\n(Thus you can define multiple libraries by seperating them with a space character)");
        addButtons(editPanel);

        getDataFromPanel(editPanel);
    }

    private void addButtons(PropertiesPanel editPanel) {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        GridBagConstraints gbc_btnpanel = new GridBagConstraints();
        gbc_btnpanel.insets = new Insets(15, 0, 5, 0);
        gbc_btnpanel.anchor = GridBagConstraints.LINE_START;
        gbc_btnpanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnpanel.gridx = 0;
        gbc_btnpanel.gridwidth = 5;
        gbc_btnpanel.gridy = 5;
        editPanel.add(btnPanel, gbc_btnpanel);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkInputDataValidity();
            }
        });
        btnPanel.add(saveButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
            }
        });
        btnPanel.add(closeButton);
    }

    private boolean checkInputDataValidity() {
        if(!checkNameValidity()) {
            return false;
        }
        if(!checkBalsaFolderValidity()) {
            return false;
        }
        if(!checkGenlibFileValidity()) {
            return false;
        }
        return true;
    }

    private boolean checkNameValidity() {
        String name = textfields.get(TextParam.name).getText();
        if(name.equals("")) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(tech == null) {
            File f = new File(getTechDir(), name + TechMngrMain.techfileExtension);
            if(f.exists()) {
                JOptionPane.showMessageDialog(this, "Technology " + name + " already exists. Delete it first", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private boolean checkBalsaFolderValidity() {
        String balsafolder = textfields.get(TextParam.balsafolder).getText();
        File f = new File(balsafolder);
        if(!f.exists()) {
            JOptionPane.showMessageDialog(this, "Balsa technology folder does not exists", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!f.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Balsa technology folder should be a directory. It is not.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!Arrays.asList(f.list()).contains("startup.scm")) {
            JOptionPane.showMessageDialog(this, "Balsa technology folder does not contain a startup.scm", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkGenlibFileValidity() {
        String genlibfile = textfields.get(TextParam.genlibfile).getText();
        File f = new File(genlibfile);
        if(!f.exists()) {
            JOptionPane.showMessageDialog(this, "Genlib file does not exists", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private File getTechDir() {
        return FileHelper.getInstance().replaceBasedir(TechMngrMain.techdir);
    }
}
