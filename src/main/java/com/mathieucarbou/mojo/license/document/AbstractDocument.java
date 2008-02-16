/**
 * Copyright (C) 2008 Mathieu Carbou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this document except in compliance with the License.
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

import java.io.File;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public abstract class AbstractDocument implements Document
{
    private final String file;

    protected AbstractDocument(String file)
    {
        this.file = file;
    }

    public boolean isUnknown()
    {
        return false;
    }

    protected final String file()
    {
        return file;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Document");
        sb.append("{file=").append(file);
        sb.append(",unknown=").append(isUnknown());
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
