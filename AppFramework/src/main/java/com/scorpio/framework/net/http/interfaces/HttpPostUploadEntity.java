package com.scorpio.framework.net.http.interfaces;

import java.io.InputStream;

public interface HttpPostUploadEntity {
	public String getParamName();
	public String getPostFileName();
	public String getContentType();
	public InputStream getInputStream();
	public long getSize();
}
