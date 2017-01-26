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
import java.awt.GridBagLayout;

import javax.swing.JFileChooser;

import de.uni_potsdam.hpi.asg.common.gui.PropertiesDialog;
import de.uni_potsdam.hpi.asg.common.gui.PropertiesPanel;
import de.uni_potsdam.hpi.asg.techmngr.Configuration.TextParam;

public class EditTechDialog extends PropertiesDialog {
    private static final long serialVersionUID = 7635453181517878899L;

    public EditTechDialog() {
        this.setModalityType(ModalityType.APPLICATION_MODAL);

        constructEditPanel(getContentPane());
    }

    private void constructEditPanel(Container root) {
        PropertiesPanel editPanel = new PropertiesPanel(this);
        root.add(editPanel);
        GridBagLayout gbl_editpanel = new GridBagLayout();
        gbl_editpanel.columnWidths = new int[]{150, 300, 0, 0, 40, 0};
        gbl_editpanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_editpanel.rowHeights = new int[]{45, 15, 15, 15, 15, 15, 15, 15, 0};
        gbl_editpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        editPanel.setLayout(gbl_editpanel);

        editPanel.addTextEntry(0, TextParam.name, "Name", "");
        editPanel.addTextEntry(1, TextParam.balsafolder, "Balsa technology folder", "", true, JFileChooser.DIRECTORIES_ONLY, false, true, "Choose the folder which contains the startup.scm");
        editPanel.addTextEntry(2, TextParam.genlibfile, "Genlib file", "", true, JFileChooser.FILES_ONLY, false);
        editPanel.addTextEntry(3, TextParam.searchpath, "Search path", "", false, null, false, true, "While using Design Compiler this value is appended to 'search_path'");
        editPanel.addTextEntry(4, TextParam.libraries, "Libraries", "", false, null, false, true, "While using Design Compiler 'link_library' and 'target_library' are set to this value\n(Thus you can define multiple libraries by seperating them with a space character)");
    }

}
