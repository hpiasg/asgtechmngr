package de.uni_potsdam.hpi.asg.techinstall;

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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TechInstallWindowAdapter extends WindowAdapter {

    private boolean closed;

    public TechInstallWindowAdapter() {
        this.closed = false;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        super.windowClosed(e);
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
