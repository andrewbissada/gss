/*
 * Copyright 2008, 2009 Electronic Business Systems Ltd.
 *
 * This file is part of GSS.
 *
 * GSS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GSS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GSS.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gss_project.gss.mbeans;

import org.jboss.system.ServiceMBean;


/**
 * @author chstath
 *
 */
public interface SolrMBean extends ServiceMBean {
    /**
     * Removes the existing index and rebuilds the database from scratch
     */
    public String rebuildIndex();

    /**
     * Adds missing files to the index without deleting the index first
     */
    public String refreshIndex();

    /*
     * Adds the specified file to the index
     */
    public String indexFile(Long id);
}
