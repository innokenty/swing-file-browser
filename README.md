# swing-file-browser

A simple swing-based file browser with ftp support and files preview.

## Running application

### Option one
 1. ```mvn clean compile```
 1. ```mvn exec:java```

### Option two
 1. ```mvn package```
 1. ```java -jar target/com.example-0.1-SNAPSHOT.jar```

## Features
 1. local & ftp files browsing
 1. ftp host:port, ftps, file encoding and anonymous login settings
 1. file and directory icons based on the underlying system ui
 1. list entry go up a tree on the top
 1. tabs for different file lists
 1. toggling showing hidden files via toolbar or via menu item
 1. hotkeys support: enter for opening selected item, backspace for going up a tree
 1. zip archive contents browsing without explicit extraction
 1. opening text and image files in a standalone thread (opening files inside archive supported)
 1. loading files in a separate worker thread
 1. closing file preview on ESC or on 'Meta + W'
 1. zooming images
