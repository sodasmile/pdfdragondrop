pdfdragondrop
=============

A JavaFX window where you can drag .doc files onto to get them converted to pdf

disclaimer
========
If you're afraid of your files, please go away. This program is not thoroughly tested, so I don't take any responsibilities for lost or damaged files. Read through the code to make sure I haven't made a mistake that could destroy your computer.

That said, it worked on my machine... 

prerequisites
=============

* Java 7
* Maven 3
* openoffice or libreoffice

getting started
===============

Make sure you have the prerequisites installed.

$ git clone https://github.com/sodasmile/pdfdragondrop.git

$ cd pdfdragondrop

$ mvn install

$ chmod u+x start.sh

$ ./start.sh

If that does not work, try something like this

$ java -cp "/Library/Java/JavaVirtualMachines/jdk1.7.0_40.jdk/Contents/Home/jre/lib/jfxrt.jar:/Users/YOURUSER/.m2/repository/pdfdragondrop/pdfdragondrop/1.0-SNAPSHOT/pdfdragondrop-1.0-SNAPSHOT.jar" com.sodasmile.dragondrop.DropConvert

Sorry for the insanely useless way of starting the application. I will see if I can make it a little smarter - or just wait for java8 and see if Oracle have fixed it.

usage
=====
Find a .doc file in one of your folders, drop it on the window. Watch the .pdf file appear in the same folder as your original .doc file.

collaborators
=============
Are welcome. Particularly if you know GUI coding... Not that there's a lot needed, but right now it looks kinda ugly.
