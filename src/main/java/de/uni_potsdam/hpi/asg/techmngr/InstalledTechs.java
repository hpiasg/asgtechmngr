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
import java.util.HashSet;
import java.util.Set;

import de.uni_potsdam.hpi.asg.common.iohelper.FileHelper;
import de.uni_potsdam.hpi.asg.common.technology.Technology;

public class InstalledTechs {

    private Set<Technology> techs;
    private File            dir;

    public InstalledTechs(String dir) {
        this.techs = new HashSet<>();
        this.dir = FileHelper.getInstance().replaceBasedir(dir);
    }

    public boolean readIn() {
        if(!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        techs.clear();
        for(File f : dir.listFiles()) {
            Technology t = Technology.readInSilent(f);
            if(t != null) {
                techs.add(t);
            }
        }
        return true;
    }

    public Set<Technology> getTechs() {
        return techs;
    }
}
