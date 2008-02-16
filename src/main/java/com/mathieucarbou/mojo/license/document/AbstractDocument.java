/**
 * Copyright (C) 2008 Mathieu Carbou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mathieucarbou.mojo.license.document;

import static com.mathieucarbou.mojo.license.document.DocumentType.*;
import static org.codehaus.plexus.util.FileUtils.*;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public abstract class AbstractDocument implements Document
{
    private final String file;
    private final DocumentType type;

    protected AbstractDocument(String file)
    {
        this.file = file;
        this.type = fromExtension(extension(file));
    }

    public String getFile()
    {
        return file;
    }

    public DocumentType getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Document");
        sb.append("{file=").append(file);
        sb.append(",type=").append(getType());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AbstractDocument that = (AbstractDocument) o;
        return !(file != null ? !file.equals(that.file) : that.file != null);
    }

    @Override
    public int hashCode()
    {
        return (file != null ? file.hashCode() : 0);
    }
}
