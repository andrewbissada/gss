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
package org.gss_project.gss.admin.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author kman
 *
 */
public class OperationsPanel extends Composite {

	private static UserPanelUiBinder uiBinder = GWT
			.create(UserPanelUiBinder.class);

	interface UserPanelUiBinder extends UiBinder<Widget, OperationsPanel> {
	}



	
	//@UiField Button fixButton;
	
	public OperationsPanel() {
		

		initWidget(uiBinder.createAndBindUi(this));
		

	}

	/*@UiHandler("fixButton")
	void handleClick(@SuppressWarnings("unused") ClickEvent e){
		DeferredCommand.addCommand(new Command() {

			@Override
			public void execute() {
				TwoAdmin.get().showLoadingBox();
				TwoAdmin.get().getAdminService().fixSharedFlagForAllFoldersAndFiles( new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
								TwoAdmin.get().hideLoadingBox();
					}

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Error Fixing Permissions", caught);
						TwoAdmin.get().hideLoadingBox();
						TwoAdmin.get().showErrorBox("Error Fixing Permissions");

					}
				});

			}
		});
	}*/



	

}
