package com.aitp.dlife.integration.file.transformer;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.integration.file.transformer.AbstractFilePayloadTransformer;
import org.springframework.util.Assert;

import com.aitp.dlife.integration.util.ExcelReadHelper;

public class ExcelFileToListTransformer extends AbstractFilePayloadTransformer<List<List<Map<String, String>>>> {

    private volatile Charset charset = Charset.defaultCharset();

    public ExcelFileToListTransformer() {
    }

    public void setCharset(String charset) {
        Assert.notNull(charset, "charset must not be null");
        Assert.isTrue(Charset.isSupported(charset), "Charset '" + charset + "' is not supported.");
        this.charset = Charset.forName(charset);
    }

    @Override
    protected List<List<Map<String, String>>> transformFile(File file) throws Exception {
        return ExcelReadHelper.readExcelWithTitle(file.getPath());
    }
}
