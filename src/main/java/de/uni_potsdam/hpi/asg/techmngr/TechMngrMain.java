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

import java.io.File;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.uni_potsdam.hpi.asg.common.iohelper.FileHelper;
import de.uni_potsdam.hpi.asg.common.iohelper.LoggerHelper;
import de.uni_potsdam.hpi.asg.common.technology.TechnologyDirectory;

public class TechMngrMain {

    public static final String techdir      = "$BASEDIR/tech";
    public static final String balsatechdir = "$BASEDIR/tools/balsa/share/tech";

    public static void main(String[] args) {
        LoggerHelper.initLogger(3, null, false, "/techmngr_log4j2.xml");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            return;
        }

        File balsaTechDirFile = FileHelper.getInstance().replaceBasedir(balsatechdir);

        TechnologyDirectory techDir = TechnologyDirectory.create(techdir, balsaTechDirFile);
        if(techDir == null) {
            return;
        }

        TechMngrWindowAdapter adapt = new TechMngrWindowAdapter();
        TechMngrFrame tiframe = new TechMngrFrame(adapt, techDir);
        tiframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tiframe.pack();
        tiframe.setLocationRelativeTo(null); //center
        tiframe.setVisible(true);

        while(!adapt.isClosed()) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
            }
        }
    }
}
