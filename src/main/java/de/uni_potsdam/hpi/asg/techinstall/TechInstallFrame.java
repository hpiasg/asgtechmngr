package de.uni_potsdam.hpi.asg.techinstall;

import java.awt.BasicStroke;

/*
 * Copyright (C) 2017 Norman Kluge
 * 
 * This file is part of ASGtechinstall.
 * 
 * ASGtechinstall is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ASGtechinstall is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ASGtechinstall.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.util.Enumeration;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.OptionPaneUI;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.uni_potsdam.hpi.asg.common.gui.ParamFrame;
import de.uni_potsdam.hpi.asg.common.gui.ParamFrame.AbstractTextParam;
import de.uni_potsdam.hpi.asg.techinstall.Configuration.TextParam;

public class TechInstallFrame extends ParamFrame {
    private static final long serialVersionUID = -4879956586784429087L;

    private Configuration     config;

    public TechInstallFrame(Configuration config, WindowAdapter adapt) {
        super("ASGconfiggen");
        this.config = config;
        this.config.setFrame(this);
        this.addWindowListener(adapt);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        constructEditPanel(tabbedPane);
    }

    private void constructEditPanel(JTabbedPane tabbedPane) {
        JPanel editPanel = new JPanel();
        tabbedPane.addTab("Edit", null, editPanel, null);
        GridBagLayout gbl_editpanel = new GridBagLayout();
        gbl_editpanel.columnWidths = new int[]{150, 300, 0, 0, 40, 0};
        gbl_editpanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_editpanel.rowHeights = new int[]{45, 15, 15, 15, 15, 15, 15, 15, 0};
        gbl_editpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        editPanel.setLayout(gbl_editpanel);

        constructTextEntry(editPanel, 0, TextParam.name, "Name", "");
        constructTextEntry(editPanel, 1, TextParam.balsafolder, "Balsa technology folder", "", true, JFileChooser.DIRECTORIES_ONLY, false, true, "Choose the folder which contains the startup.scm");
        constructTextEntry(editPanel, 2, TextParam.genlibfile, "Genlib file", "", true, JFileChooser.FILES_ONLY, false);
        constructTextEntry(editPanel, 3, TextParam.searchpath, "Search path", "", false, null, false, true, "While using Design Compiler this value is appended to 'search_path'");
        constructTextEntry(editPanel, 4, TextParam.libraries, "Libraries", "", false, null, false, true, "While using Design Compiler 'link_library' and 'target_library' are set to this value\n(Thus you can define multiple libraries by seperating them with a space character)");
    }
}
