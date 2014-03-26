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
 1. ftp host:port, ftps, file encoding settings
 1. anonymous login support
 1. storing preferred ftp connection settings in a properties file
 1. file and directory icons based on the underlying system ui
 1. list entry go up a tree on the top
 1. tabs for different file lists
 1. support for simultaneous work with different tabs in slow file system environment
 1. toggling showing hidden files via toolbar or via menu item
 1. hotkeys support: enter for opening selected item, backspace for going up a tree
 1. zip archive contents browsing without explicit extraction
 1. opening text and image files in a standalone thread (opening files inside archive supported)
 1. loading files in a separate worker thread
 1. closing file preview on ESC or on 'Meta + W'
 1. zooming images

## FTP connection properties

### Properties list

property name | type                                  | value example     | default value
------------- | ------------------------------------- | ----------------- | -------------
host          | string                                | ftp.example.com   | –
port          | int                                   | 69                | 21
ftps          | boolean                               | true              | false
username      | string                                | innokenty         | -
password      | string                                | P@$5w0r†          | -
file.type     | one of {binary, ASCII, EBCDIC, local} | binary             | ASCII

### Setting properties
There are three options to specify each property listed in the previous section:
 1. via JVM system property
 1. in a classpath resource named ```swing-file-browser.properties```
 1. in ```/etc/swing-file-browser.properties``` file

If any property is specified in multiple sources of the above,
the upper source in the list gets the priority.

### swing-file-browser.properties file example
```
host=ftp.example.com
ftps=true
username=innokenty
password=P@$5w0r† 
file.type=local
```
