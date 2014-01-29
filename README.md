#Codename One Mirah Netbeans Plugin

A Netbeans plugin for writing Codename One applications in Mirah.

##Requirements

* Netbeans 7.4
* The Netbeans Codename One plugin.
* mirah

##Installation

1. Download the ca-weblite-codename1-mirah.nbm module to your computer.
2. Open NetBeans
3. Select "Plugins" from the "Tools" menu.
4. Click on the "Downloaded" tab.
5. Click the "Add Plugins..." button.
6. Select the ca-weblite-codename1-mirah.nbm file in the file chooser and follow prompts to install.

##Usage

1. Open an existing Codename One Application project or create a new Codename One Application project in Netbeans.
2. Right click on the project icon in the Project explorer, and select "Generate Mirah Project".  This will create a folder inside your project directory named "mirah" containing the following:
	* build.xml - An ANT script for compiling your .mirah source files.
	* src/ - A directory for all of your mirah source files.
	* README.md - A readme file with some instructions on how to use this project.
3. Create your mirah source files inside the src directory of the mirah project that was created.  
4. To compile your mirah source files, open a terminal window and navigate to the mirah directory.  Then type `ant` to perform the build.  This will compile the source files and include them in your Codename One Application so that you can use them just like java classes.