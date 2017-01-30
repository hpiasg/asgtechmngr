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

import java.awt.Window;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import de.uni_potsdam.hpi.asg.common.iohelper.FileHelper;
import de.uni_potsdam.hpi.asg.common.technology.Balsa;
import de.uni_potsdam.hpi.asg.common.technology.Genlib;
import de.uni_potsdam.hpi.asg.common.technology.SyncTool;
import de.uni_potsdam.hpi.asg.common.technology.Technology;

public class TechnologyDirectory {

    private File            dir;
    private Set<Technology> techs;

    private TechnologyDirectory(File dir, Set<Technology> techs) {
        this.dir = dir;
        this.techs = techs;
    }

    public static TechnologyDirectory create(String dir) {
        return create(FileHelper.getInstance().replaceBasedir(dir));
    }

    public static TechnologyDirectory create(File dir) {
        if(!dir.exists()) {
            dir.mkdirs();
        }
        if(!dir.isDirectory()) {
            return null;
        }
        Set<Technology> techs = TechnologyDirectory.readTechnologies(dir);

        return new TechnologyDirectory(dir, techs);
    }

    private static Set<Technology> readTechnologies(File dir) {
        Set<Technology> techs = new HashSet<>();
        for(File f : dir.listFiles()) {
            Technology t = Technology.readInSilent(f);
            if(t != null) {
                techs.add(t);
            }
        }
        return techs;
    }

    public Technology createTechnology(Window parent, String name, String balsafolder, String genlibfile, String searchPaths, String libraries) {
        Balsa balsa = new Balsa("resyn", name);
        File sourcedir = new File(balsafolder);
        File targetdir = new File(getBalsaTechDir(), name);
        targetdir.mkdirs();
        try {
            FileUtils.copyDirectory(sourcedir, targetdir);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(parent, "Error while copying balsa technology directory", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Genlib genlib = new Genlib(name + TechMngrMain.genlibfileExtension);
        File sourcefile = new File(genlibfile);
        File targetfile = new File(dir, name + TechMngrMain.genlibfileExtension);
        try {
            FileUtils.copyFile(sourcefile, targetfile);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(parent, "Error while copying genlib file", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        List<String> postCompileCmds = new ArrayList<>(); // aka not yet implemented
        List<String> verilogIncludes = new ArrayList<>(); // aka not yet implemented
        SyncTool synctool = new SyncTool(searchPaths, libraries, postCompileCmds, verilogIncludes);

        Technology tech = new Technology(name, balsa, genlib, synctool);
        if(!Technology.writeOut(tech, new File(dir, name + TechMngrMain.techfileExtension))) {
            JOptionPane.showMessageDialog(parent, "Error while creating technology file", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        this.techs.add(tech);

        return tech;
    }

    public void deleteTechnology(Window parent, Technology t) {
        String name = t.getName();

        File balsadir = new File(getBalsaTechDir(), name);
        try {
            FileUtils.deleteDirectory(balsadir);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(parent, "Failed to remove Balsa technology folder", "Error", JOptionPane.ERROR_MESSAGE);
        }

        File genlibfile = new File(dir, name + TechMngrMain.genlibfileExtension);
        if(!genlibfile.delete()) {
            JOptionPane.showMessageDialog(parent, "Failed to remove Genlib file", "Error", JOptionPane.ERROR_MESSAGE);
        }

        File techfile = new File(dir, name + TechMngrMain.techfileExtension);
        if(!techfile.delete()) {
            JOptionPane.showMessageDialog(parent, "Failed to remove technology file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private File getBalsaTechDir() {
        return FileHelper.getInstance().replaceBasedir(TechMngrMain.balsatechdir);
    }

    public Set<Technology> getTechs() {
        return techs;
    }
}
