Authers: Abdul Binrasheed, Craig Gardner, John St. John


This project was started for Software Methodologies at the University of Oregon in Winter term 2009. It provides an interface for the fonolo web service specifically for the Android smart phone OS. This code is written specifically to comply with the fonolo API version 1.1 build 5. It is likely this code will have to be modified when their new builds come out.



*****Development Instructions******  

Instructions on how to set up the development environment.




**Introduction**


For anyone who is interested in installing and running this program, please follow these instructions in setting up the environment.



**Details**


First install Java, eclipse, the android SDK, and the Eclipse plug-in for Android. Instructions for configuring eclipse to work with android may be found at the android developer site on the installation page.

Next do an SVN checkout of the source code on this site.

In the folder you just checked out, make a new folder named bin.

In the new folder navigate to src->com->android->fonolo and copy private_constants-TEMP into a new file called private_constants.java within that folder. Edit private_constants.java and copy your own fonolo developer ID into the AUTH_KEY field.

Finally open eclipse, select new->project from the menu and select Android project.

Check the box that says from existing source and navigate to the source folder that you checked out via SVN.

Right click on the new project within eclipse and select build path-> configure build path

In this menu you need to add a new jar file which is found in the lib directory of the project.

This should get you up and running!