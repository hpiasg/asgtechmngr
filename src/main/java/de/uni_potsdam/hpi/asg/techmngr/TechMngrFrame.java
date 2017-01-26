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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import de.uni_potsdam.hpi.asg.common.gui.ParamFrame;
import de.uni_potsdam.hpi.asg.common.technology.Technology;
import de.uni_potsdam.hpi.asg.techmngr.Configuration.TextParam;

public class TechMngrFrame extends ParamFrame {
    private static final long serialVersionUID = -4879956586784429087L;

    private Configuration     config;
    private InstalledTechs    instTechs;

    public TechMngrFrame(Configuration config, WindowAdapter adapt, InstalledTechs instTechs) {
        super("ASGtechmngr");
        this.config = config;
        this.config.setFrame(this);
        this.addWindowListener(adapt);
        this.instTechs = instTechs;

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        constructManagePanel(tabbedPane);
        constructEditPanel(tabbedPane);

        tabbedPane.setEnabledAt(1, false);
    }

    private void constructManagePanel(JTabbedPane tabbedPane) {
        JPanel mngPanel = new JPanel();
        tabbedPane.addTab("Manage", null, mngPanel, null);
        GridBagLayout gbl_mngpanel = new GridBagLayout();
        gbl_mngpanel.columnWidths = new int[]{150, 70, 150, 0};
        gbl_mngpanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_mngpanel.rowHeights = new int[]{150, 15, 15, 0};
        gbl_mngpanel.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        mngPanel.setLayout(gbl_mngpanel);

        InstalledTechsTableModel tm = new InstalledTechsTableModel(instTechs.getTechs());
        JTable table = new JTable(tm);
        table.setCellSelectionEnabled(false);
        table.getTableHeader().setUI(null);
        tm.formatTable(table);

        JScrollPane scroll = new JScrollPane(table);
        GridBagConstraints gbc_table = new GridBagConstraints();
        gbc_table.insets = new Insets(0, 0, 5, 5);
        gbc_table.anchor = GridBagConstraints.LINE_START;
        gbc_table.fill = GridBagConstraints.BOTH;
        gbc_table.gridx = 0;
        gbc_table.gridwidth = 3;
        gbc_table.gridy = 0;
        mngPanel.add(scroll, gbc_table);

        JButton newButton = new JButton("New");
        GridBagConstraints gbc_newbtn = new GridBagConstraints();
        gbc_newbtn.insets = new Insets(0, 0, 5, 5);
        gbc_newbtn.anchor = GridBagConstraints.LINE_START;
        gbc_newbtn.fill = GridBagConstraints.HORIZONTAL;
        gbc_newbtn.gridx = 1;
        gbc_newbtn.gridy = 1;
        mngPanel.add(newButton, gbc_newbtn);

        JButton importButton = new JButton("Import");
        GridBagConstraints gbc_importbtn = new GridBagConstraints();
        gbc_importbtn.insets = new Insets(0, 0, 5, 5);
        gbc_importbtn.anchor = GridBagConstraints.LINE_START;
        gbc_importbtn.fill = GridBagConstraints.HORIZONTAL;
        gbc_importbtn.gridx = 1;
        gbc_importbtn.gridy = 2;
        mngPanel.add(importButton, gbc_importbtn);
    }

    private void constructEditPanel(JTabbedPane tabbedPane) {
        JPanel editPanel = new JPanel();
        tabbedPane.addTab("Edit", null, editPanel, null);
        GridBagLayout gbl_editpanel = new GridBagLayout();
        gbl_editpanel.columnWidths = new int[]{150, 300, 0, 0, 40, 0};
        gbl_editpanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_editpanel.rowHeights = new int[]{45, 15, 15, 15, 15, 15, 15, 15, 0};
        gbl_editpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        editPanel.setLayout(gbl_editpanel);

        constructTextEntry(editPanel, 0, TextParam.name, "Name", "");
        constructTextEntry(editPanel, 1, TextParam.balsafolder, "Balsa technology folder", "", true, JFileChooser.DIRECTORIES_ONLY, false, true, "Choose the folder which contains the startup.scm");
        constructTextEntry(editPanel, 2, TextParam.genlibfile, "Genlib file", "", true, JFileChooser.FILES_ONLY, false);
        constructTextEntry(editPanel, 3, TextParam.searchpath, "Search path", "", false, null, false, true, "While using Design Compiler this value is appended to 'search_path'");
        constructTextEntry(editPanel, 4, TextParam.libraries, "Libraries", "", false, null, false, true, "While using Design Compiler 'link_library' and 'target_library' are set to this value\n(Thus you can define multiple libraries by seperating them with a space character)");
    }

    private class InstalledTechsTableModel extends DefaultTableModel {
        private static final long serialVersionUID = -1667760148528261921L;
//        private Set<Technology>   techs;
        private JTable            table;

        private final String[]    buttonCols       = {"Edit", "Export", "Delete"};

        public InstalledTechsTableModel(Set<Technology> techs) {
            super();
//            this.techs = techs;
            this.addColumn("Name");
            for(String str : buttonCols) {
                this.addColumn(str);
            }
            for(Technology t : techs) {
                Vector<Object> data = new Vector<>();
                data.add(t.getName());
                for(String str : buttonCols) {
                    data.add(str);
                }
                this.addRow(data);
            }
        }

        public void formatTable(JTable table) {
            this.table = table;
            TableColumn col = null;
            for(String str : buttonCols) {
                col = table.getColumn(str);
                internalSetColRenderer(col);
            }
        }

        private void internalSetColRenderer(TableColumn col) {
            col.setCellRenderer(new ButtonRenderer());
            col.setCellEditor(new ButtonCellEditor());
            col.setPreferredWidth(40);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            for(String str : buttonCols) {
                if(table.getColumnModel().getColumn(column).getIdentifier().equals(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    private class ButtonRenderer implements TableCellRenderer {
        private JButton button;

        public ButtonRenderer() {
            this.button = new JButton();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            button.setText(value.toString());
            return button;
        }
    }

    private class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
        private static final long serialVersionUID = 1730931555074843347L;

        private JButton           button;
        private Integer           row;
        private Integer           col;

        public ButtonCellEditor() {
            super();
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("test " + row + ", " + col);
                }
            });
            row = null;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText(value.toString());
            this.row = row;
            this.col = column;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}
