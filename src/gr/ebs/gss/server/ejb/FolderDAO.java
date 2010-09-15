/*
 * Copyright 2010 Electronic Business Systems Ltd.
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
package gr.ebs.gss.server.ejb;

import gr.ebs.gss.client.exceptions.ObjectNotFoundException;
import gr.ebs.gss.server.domain.Folder;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.DAO;
import com.google.code.morphia.Datastore;
import com.google.inject.Inject;


/**
 * The Data Access Object for Folder objects.
 *
 * @author past
 */
public class FolderDAO extends DAO<Folder, Long> {
	/**
	 * The Morphia datastore instance.
	 */
	private final Datastore ds;

	/**
	 * Construct a FolderDAO object with the provided datastore.
	 */
	@Inject
	public FolderDAO(Datastore aStore) {
		super(aStore);
		ds = aStore;
	}

	/**
	 * Retrieve the folder with the supplied name that is contained
	 * in a folder with the specified ID.
	 *
	 * @param parentId the ID of the parent folder
	 * @param name the name of the folder
	 * @return the folder found
	 * @throws ObjectNotFoundException if the folder or parent was not found,
	 * 			with the exception message mentioning the precise problem
	 */
	public Folder getFolder(Long parentId, String name) throws ObjectNotFoundException {
		if (parentId == null)
			throw new ObjectNotFoundException("No parent folder specified");
		if (StringUtils.isEmpty(name))
			throw new IllegalArgumentException("No folder name specified");

		Folder result = ds.find(Folder.class).filter("name", name).filter("parent.id", parentId).get();
		if (result == null) throw new ObjectNotFoundException("Folder not found");
		return result;
	}
}
