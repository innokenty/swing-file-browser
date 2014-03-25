package com.example.filelist;

import org.apache.commons.net.ftp.FTP;
import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * @author innokenty
 */
@Resource.Classpath("swing-file-browser.properties")
@Resource.File("/etc/swing-file-browser.properties")
class FtpConnectionProperties {

    @Property("host")
	private String host;
    
    @Property("port")
	private Integer port;
    
    @Property("ftps")
	private Boolean ftps;
    
    @Property("username")
	private String username;

    @Property("password")
	private String password;
    
    @Property("file.type")
	private String fileType;

	FtpConnectionProperties() {
		PropertyLoader.populate(this);
	}

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port == null ? FTP.DEFAULT_PORT : port;
    }

    public boolean isFtps() {
        return ftps == null ? false : ftps;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFileType() {
        return fileType;
    }
}
