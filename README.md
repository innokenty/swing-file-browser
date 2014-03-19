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
 1. local files browsing
 1. FTP connection with some settings available
 1. tabs for different file lists
 1. hotkeys support: enter for opening selected item, backspace for going up a tree
 1. zip archive contents browsing without explicit extraction
 1. opening text and image files (opening files inside archive supported)
 1. zooming images
 1. toggling showing hidden files via toolbar or via menu item
