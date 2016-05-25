package com.usbus.commons.auxiliaryClasses;

/**
 * Created by Lufasoch on 24/05/2016.
 */
public class Image {
    private String filePath;
    private String name;
    private String extension;

    public Image() {
    }

    public Image(String filePath, String name, String extension) {
        this.filePath = filePath;
        this.name = name;
        this.extension = extension;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
