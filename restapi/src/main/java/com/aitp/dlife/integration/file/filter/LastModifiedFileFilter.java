package com.aitp.dlife.integration.file.filter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.file.filters.AbstractFileListFilter;

public class LastModifiedFileFilter extends AbstractFileListFilter<File> {
    private final Map<String, Long> files = new HashMap<>();
    private final Object monitor = new Object();

    @Override
    public boolean accept(File file) {
        synchronized (this.monitor) {
            Long previousModifiedTime = files.put(file.getName(), file.lastModified());

            //return previousModifiedTime == null || previousModifiedTime != file.lastModified();
            return previousModifiedTime == null;
        }
    }
}