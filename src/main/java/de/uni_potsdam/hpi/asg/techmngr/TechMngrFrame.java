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

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import de.uni_potsdam.hpi.asg.common.gui.PropertiesFrame;
import de.uni_potsdam.hpi.asg.common.technology.Technology;

public class TechMngrFrame extends PropertiesFrame {
    private static final long        serialVersionUID = -4879956586784429087L;

    private TechnologyDirectory      techDir;
    private InstalledTechsTableModel tablemodel;
    private JFrame                   parent;

    public TechMngrFrame(WindowAdapter adapt, TechnologyDirectory techDir) {
        super("ASGtechmngr");
        this.addWindowListener(adapt);
        this.techDir = techDir;
        this.parent = this;

        constructManagePanel(getContentPane());
    }

    private void constructManagePanel(Container root) {
        JPanel mngPanel = new JPanel();
        root.add(mngPanel);
        GridBagLayout gbl_mngpanel = new GridBagLayout();
        gbl_mngpanel.columnWidths = new int[]{150, 70, 150, 0};
        gbl_mngpanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_mngpanel.rowHeights = new int[]{150, 15, 15, 0};
        gbl_mngpanel.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        mngPanel.setLayout(gbl_mngpanel);

        tablemodel = new InstalledTechsTableModel(techDir);
        JTable table = new JTable(tablemodel);
        table.setCellSelectionEnabled(false);
        table.getTableHeader().setUI(null);
        tablemodel.formatTable(table);

        JScrollPane scroll = new JScrollPane(table);
        GridBagConstraints gbc_table = new GridBagConstraints();
        gbc_table.insets = new Insets(5, 5, 5, 5);
        gbc_table.anchor = GridBagConstraints.LINE_START;
        gbc_table.fill = GridBagConstraints.BOTH;
        gbc_table.gridx = 0;
        gbc_table.gridwidth = 3;
        gbc_table.gridy = 0;
        mngPanel.add(scroll, gbc_table);

        JButton newButton = new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewTechDialog editDia = new NewTechDialog(techDir);
                editDia.pack();
                editDia.setLocationRelativeTo(null); //center
                editDia.setVisible(true);
                //wait
                if(editDia.getTech() != null) {
                    tablemodel.addTech(editDia.getTech());
                }
            }
        });
        GridBagConstraints gbc_newbtn = new GridBagConstraints();
        gbc_newbtn.insets = new Insets(0, 0, 5, 0);
        gbc_newbtn.anchor = GridBagConstraints.LINE_START;
        gbc_newbtn.fill = GridBagConstraints.HORIZONTAL;
        gbc_newbtn.gridx = 1;
        gbc_newbtn.gridy = 1;
        mngPanel.add(newButton, gbc_newbtn);

        JButton importButton = new JButton("Import");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(parent);
                if(result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    importTech(selectedFile);
                }
            }
        });
        GridBagConstraints gbc_importbtn = new GridBagConstraints();
        gbc_importbtn.insets = new Insets(0, 0, 5, 0);
        gbc_importbtn.anchor = GridBagConstraints.LINE_START;
        gbc_importbtn.fill = GridBagConstraints.HORIZONTAL;
        gbc_importbtn.gridx = 1;
        gbc_importbtn.gridy = 2;
        mngPanel.add(importButton, gbc_importbtn);
    }

    private void importTech(File file) {
        if(!file.exists()) {
            return;
        }
        if(file.isDirectory()) {
            importTechFromDir(file);
            return;
        }
        importTechFromFile(file);
    }

    private void importTechFromFile(File file) {
        // TODO Auto-generated method stub

    }

    private void importTechFromDir(File file) {

    }

    private class InstalledTechsTableModel extends DefaultTableModel {
        private static final long   serialVersionUID = -1667760148528261921L;
        private JTable              table;

        private final String[]      buttonCols       = {"Export", "Delete"};
        private List<Technology>    rows;
        private TechnologyDirectory techDir;

        public InstalledTechsTableModel(TechnologyDirectory techDir) {
            super();
            this.techDir = techDir;
            this.rows = new ArrayList<>();

            this.addColumn("Name");
            for(String str : buttonCols) {
                this.addColumn(str);
            }
            for(Technology t : techDir.getTechs()) {
                renderTech(t);
            }
        }

        private void renderTech(Technology t) {
            Vector<Object> data = new Vector<>();
            data.add(t.getName());
            for(String str : buttonCols) {
                data.add(str);
            }
            this.addRow(data);
            this.rows.add(t);
        }

        public void execTableButton(int row, int column) {
            int buttoncol = column - 1;
            if(buttoncol < 0 || buttoncol > buttonCols.length) {
                return;
            }
            if(row < 0 || row > rows.size()) {
                return;
            }
            Technology t = rows.get(row);
            switch(buttonCols[buttoncol]) {
                case "Delete":
                    execDelete(t, row);
                    break;
                case "Export":
                    execExport(t);
                    break;
            }
        }

        private void execExport(Technology t) {

        }

        private void execDelete(Technology t, int row) {
            String name = t.getName();
            int answer = JOptionPane.showConfirmDialog(parent, "Do you really want to permanently delete all files of technology '" + name + "'?", "", JOptionPane.YES_NO_OPTION);
            switch(answer) {
                case JOptionPane.YES_OPTION:
                    break;
                case JOptionPane.NO_OPTION:
                default:
                    return;
            }

            techDir.deleteTechnology(parent, t.getName());
            rows.remove(row);
            removeRow(row);
        }

        public void addTech(Technology t) {
            renderTech(t);
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
            col.setCellEditor(new ButtonCellEditor(this));
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

        public ButtonCellEditor(final InstalledTechsTableModel tm) {
            super();
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tm.execTableButton(row, col);
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
