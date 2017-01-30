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

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TechMngrMain {

    public static String techdir             = "$BASEDIR/tech";
    public static String balsatechdir        = "$BASEDIR/tools/balsa/share/tech";
    public static String techfileExtension   = ".xml";
    public static String genlibfileExtension = ".lib";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            return;
        }

        TechnologyDirectory techDir = TechnologyDirectory.create(techdir);
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
