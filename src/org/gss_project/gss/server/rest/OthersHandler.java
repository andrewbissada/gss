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
package org.gss_project.gss.server.rest;

import org.gss_project.gss.common.exceptions.ObjectNotFoundException;
import org.gss_project.gss.common.exceptions.RpcException;
import org.gss_project.gss.server.domain.FileHeader;
import org.gss_project.gss.server.domain.Folder;
import org.gss_project.gss.server.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A class that handles operations on the 'others' namespace.
 *
 * @author past
 */
public class OthersHandler extends RequestHandler {
	/**
	 * The logger.
	 */
	private static Log logger = LogFactory.getLog(OthersHandler.class);

	/**
     * Serve the 'others' namespace for the user.
     *
     * @param req The servlet request we are processing
     * @param resp The servlet response we are processing
     * @throws IOException if an input/output error occurs
	 */
	void serveOthers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	String parentUrl = getContextPath(req, true);
        String path = getInnerPath(req, PATH_OTHERS);
		if (path.equals(""))
			path = "/";

    	User user = getUser(req);
    	User owner = getOwner(req);
    	if (!owner.equals(user)) {
    		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    		return;
    	}
    	if (path.equals("/"))
			try {
	    		// Request to retrieve the other users who have shared resources to this user
	        	JSONArray json = new JSONArray();

    	    	List<User> others = getService().getUsersSharingFoldersForUser(owner.getId());
		    	for (User u: others) {
		    		JSONObject j = new JSONObject();
		    		j.put("username", u.getUsername()).
		    			put("uri", parentUrl + u.getUsername());
		    		json.put(j);
		    	}

            	sendJson(req, resp, json.toString());
    		} catch (ObjectNotFoundException e) {
    			logger.error("User not found", e);
    			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return;
    		} catch (RpcException e) {
    			logger.error("", e);
    			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return;
			} catch (JSONException e) {
				logger.error("", e);
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		else
			try {
	    		// Request to retrieve the files and folders shared by the other user
	    		// Chop any trailing slash
	        	path = path.endsWith("/")? path.substring(0, path.length()-1): path;
	        	// Chop any leading slash
	        	path = path.startsWith("/")? path.substring(1): path;

	        	User other = getService().findUser(path);
	        	JSONObject json = new JSONObject();

				List<JSONObject> subfolders = new ArrayList<JSONObject>();
    	    	List<Folder> folders = getService().getSharedRootFolders(other.getId(), owner.getId());
        		for (Folder f: folders) {
        			JSONObject j = new JSONObject();
        			j.put("name", f.getName()).
        				put("uri", getApiRoot() + f.getURI()).
        				put("shared", f.getShared());
    				subfolders.add(j);
        		}
    			json.put("folders", subfolders);

    	    	List<JSONObject> files = new ArrayList<JSONObject>();
    	    	List<FileHeader> fileHeaders = getService().getSharedFiles(other.getId(), owner.getId());
    	    	for (FileHeader f: fileHeaders) {
    	    		JSONObject j = new JSONObject();
    				j.put("name", f.getName()).
    					put("owner", f.getOwner().getUsername()).
    					put("deleted", f.isDeleted()).
    					put("version", f.getCurrentBody().getVersion()).
    					put("size", f.getCurrentBody().getFileSize()).
    					put("content", f.getCurrentBody().getMimeType()).
    					put("creationDate", f.getAuditInfo().getCreationDate().getTime()).
    					put("modificationDate", f.getAuditInfo().getModificationDate().getTime()).
    					put("path", f.getFolder().getPath()).
    					put("shared", f.getShared()).
    					put("versioned",f.isVersioned()).
        				put("uri", getApiRoot() + f.getURI());
        			files.add(j);
    	    	}
    	    	json.put("files", files);

            	sendJson(req, resp, json.toString());
    		} catch (ObjectNotFoundException e) {
    			logger.error("User not found", e);
    			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return;
    		} catch (RpcException e) {
    			logger.error("", e);
    			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return;
			} catch (JSONException e) {
				logger.error("", e);
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}

   		resp.setHeader("Expires", "-1");
	}

}
