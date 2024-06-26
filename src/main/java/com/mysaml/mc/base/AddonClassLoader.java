package com.mysaml.mc.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class AddonClassLoader extends URLClassLoader {
    public AddonClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
    @Override
    public URL getResource(String name) {
        URL resourceUrl = findResource(name);
        if (resourceUrl != null) return resourceUrl;
        return super.getResource(name);
    }
    @Override
    public InputStream getResourceAsStream(String name) {
        URL resourceUrl = findResource(name);
        try {
            return resourceUrl != null
            ? resourceUrl.openStream()
            : super.getResourceAsStream(name);
        } catch (IOException e) {
            return null;
        }
    }
}
