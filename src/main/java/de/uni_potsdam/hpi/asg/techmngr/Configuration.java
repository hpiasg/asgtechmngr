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

import de.uni_potsdam.hpi.asg.common.gui.PropertiesPanel.AbstractBooleanParam;
import de.uni_potsdam.hpi.asg.common.gui.PropertiesPanel.AbstractTextParam;

public class Configuration {
    public static String notapplicableStr = "N/A";

    //@formatter:off
    public enum TextParam implements AbstractTextParam {
        /*edit*/ name, balsafolder, genlibfile, searchpath, libraries
    }

    public enum BooleanParam implements AbstractBooleanParam {
    }
    //@formatter:on

    private TechMngrFrame frame;

    public void setFrame(TechMngrFrame frame) {
        this.frame = frame;
    }

    public String getTextValue(TextParam param) {
        String str = frame.getTextValue(param);
//        if(param == TextParam.OutDir) {
//            str = replaceBasedir(str);
//        }
        return str;
    }

    public boolean getBooleanValue(BooleanParam param) {
        return frame.getBooleanValue(param);
    }

    private String replaceBasedir(String str) {
        String basedir = System.getProperty("basedir");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")) {
            basedir = basedir.replaceAll("\\\\", "/");
        }
        return str.replaceAll("\\$BASEDIR", basedir);
    }
}
