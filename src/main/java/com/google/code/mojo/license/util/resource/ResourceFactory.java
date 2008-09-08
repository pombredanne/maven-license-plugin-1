/**
 * Copyright (C) 2008 Mathieu Carbou
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.mojo.license.util.resource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <b>Date:</b> 26-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class ResourceFactory
{
    private final File basedir;

    private ResourceFactory(File basedir)
    {
        this.basedir = basedir;
    }

    public URL findResource(String resource)
    {
        // first search relatively to the base directory
        URL res = toURL(new File(basedir, resource));
        if(res != null) { return res; }

        // if not found, search for absolute location on file system
        res = toURL(new File(resource));
        if(res != null) { return res; }

        // if not found, try the classpath
        String cpResource = resource.startsWith("/") ? resource.substring(1) : resource;
        res = Thread.currentThread().getContextClassLoader().getResource(cpResource);
        if(res != null) { return res; }

        // otherwise, tries to return a valid URL
        try
        {
            res = new URL(resource);
            res.openStream().close();
        }
        catch(Exception e)
        {
            throw new InvalidResourceException(resource, e);
        }

        return res;
    }

    private URL toURL(File file)
    {
        if(file.exists() && file.canRead())
        {
            try
            {
                return file.toURI().toURL();
            }
            catch(MalformedURLException e)
            {
                throw new InvalidResourceException(file, e);
            }
        }
        return null;
    }

    public static ResourceFactory newResourceFactory(File basedir)
    {
        return new ResourceFactory(basedir);
    }

}
