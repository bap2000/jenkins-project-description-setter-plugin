/*
 * The MIT License
 *
 * Copyright (C) 2010-2011 by Anthony Robinson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsCi.plugins.projectDescriptionSetter;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildWrapperDescriptor;
import hudson.util.ListBoxModel;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

import java.nio.charset.Charset;
import java.util.Map;

@Extension
public class DescriptionSetterWrapperDescriptor extends BuildWrapperDescriptor {

    private String charset;
    private String projectDescriptionFilename;

    public DescriptionSetterWrapperDescriptor() {
        super(DescriptionSetterWrapper.class);
        load();
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(final String charset) {
        this.charset = charset;
    }

    public String getProjectDescriptionFilename() {
        return projectDescriptionFilename;
    }

    public void setProjectDescriptionFilename(final String projectDescriptionFilename) {
        this.projectDescriptionFilename = projectDescriptionFilename;
    }

    public boolean configure(final StaplerRequest request, final JSONObject formData) {
        request.bindJSON(this, formData);
        save();
        return true;
    }

    public ListBoxModel doFillCharsetItems() {
        ListBoxModel items = new ListBoxModel();
        for (Map.Entry<String, Charset> entry : Charset.availableCharsets().entrySet()) {
            items.add(entry.getValue().displayName(), entry.getKey());
        }
        return items;
    }

    public String getDefaultCharset() {
        return Charset.defaultCharset().name();
    }

    @Override
    public String getDisplayName() {
        return Messages.wrapper_displayName();
    }

    @Override
    public boolean isApplicable(AbstractProject<?, ?> abstractProject) {
        return true;
    }

}
