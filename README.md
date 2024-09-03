# The Rot (Working Title) Testing Grounds

This is a testing Ground for "The Rot" (Working Title) using the Sarxos Webcam Capture library. This mostly works. 

Currently running successfully on MacM1 (Multiple Stations) 

Current Problems:

- OpenCV/JavaCV Driver library access
- V4L4J Driver functionality
- JavaCPP Libraries

To run, follow these steps based on Platform

## Mac Silicon

1) Install JRE or JDK Minimum Version 17. 22 is preferable, but the whole program works in 17 even though it was written in 22. Nothing breaks by downscaling. Have not tested 8, likely will not.
2) Clone this repo
3) Open Terminal
4) `cd {/wherever/this/repo/exists/SarxTest/src}`
5) run `javac -cp "../lib/*":. sarxtest/*.java`
6) run `java -cp "../lib'*":. sarxtest.HandlerTests`
7) Follow the instructions on the Terminal

This runs the program on any Mac Silicon Machine. Pi and Windows is in development. 

Features still working on

- [ ] Fullscreen
- [ ] Pi Functionality
- [ ] JavaCV Functionality
- [ ] JavaCPP Build within Pi Environment
- [ ] Config Handler to run the first time on the machine to choose image files.

ChangeLog: 

9/1 -  Switch to JavaCV Library. Still not fully functional.
