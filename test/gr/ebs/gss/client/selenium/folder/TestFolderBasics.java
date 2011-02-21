/*
 * Copyright 2011 Electronic Business Systems Ltd.
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
package gr.ebs.gss.client.selenium.folder;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestFolderBasics extends FolderUtils{
	
/**
 *  ---------------------------------------Tests ------------------------------------------------
 * 
 */
	
	@Test
	public void testMakeNewFolder() throws InterruptedException{
		
		//Create the folder with the given folderName
		makeNewFolder(userName, folderName);
//		makeNewFolder(userName, folderDestination);
		
		//Necessary delay 
		Thread.sleep(2000);
				
		Assert.assertEquals(folderName, action.getText(By.id(userName + "." + folderName)));	
						
	}
	
	@Test (expected=org.openqa.selenium.NoSuchElementException.class)
	public void deleteFolder() throws InterruptedException{
	
		//Delete the folder
		deleteFolder(userName, folderName);
		
		//the test is successful if that condition fails
		Assert.assertEquals(folderName, action.getText(By.id(userName + "." + folderName)));	
						
	}
    
//	@Test
	public void testMoveToTrash(){
		//Select the desired folder
		selectFolderBelowHome(userName,folderName);

		//Move to Trash 
		moveToTrash(userName, folderName);
		
		/**
		 * TODO: In order to confirm that 
		 * the new folder exists in Trash folder
		 * trash folder should be expanded first 
		 * and then select the element
		 */
		
	}
	
//	@Test
	public void testEmptyTrash() throws InterruptedException{
			
		emptyTrash();
		
		/**
		 * TODO: Confirm that the trash folder is empty 
		 */
		
	}
	
//	/**
//	 * Method that tries to expand the '+' symbol 
//	 * in order to reveal folder's containing folders 
//	 * @throws InterruptedException 
//	 */	
//	@Test
//	public void testExpand() throws InterruptedException{
////		selectTrash();
//		expandTree();		
//		Thread.sleep(3000);
//				
//	}
	
//	@Test
	public void testCutFolder(){
		cutFolder(userName, folderName);
		
		paste(userName, folderDestination);
		
		/**TODO: In order to confirm successful paste of the folderName to folderDestination
		 * we should expand folderDestination and see if the folder name is in it's sub folders
		 */
		
	}
	
//	@Test
	public void testCopyNPasteFolder(){
		copy(userName, folderName);
		
		paste(userName, folderDestination);
		
		/**TODO: In order to confirm successful paste of the folderName to folderDestination
		 * we should expand folderDestination and see if the folder name is in it's sub folders
		 */
		
	}
	
//	@Test
	public void testRenameFolder() throws InterruptedException{
		//select the folder you want to rename and click Properties
		//clickFolderProperties(userName, folderName);
		clickFolderProperties(userName, newFolderName);
		
		//in the input box add the new folderName		
		action.type(By.id("folderPropertiesDialog.textBox.name"), newFolderName);
		
		//click OK
		action.click(By.id("folderPropertiesDialog.button.ok"));
		
		Thread.sleep(2000);
		
		Assert.assertEquals(newFolderName, action.getText(By.id(userName + "." + newFolderName)));
		
	}
	/**
	 * TODO: Make it run!!!!
	 * @throws InterruptedException
	 */
//	@Test
	public void testRightClickOnFolder() throws InterruptedException{
		rightClickOnFolder(userName, folderName);
		
		Thread.sleep(500);
		
		selectFolderContextMenuOption("refresh");
		Thread.sleep(1000);
	}
	

}
