package com.juice.timetable.data.parse.dto;

public class VersionDTO {
    private String latestVersion = "";
    private String downloadUrl = "";

    public VersionDTO(String latestVersion, String downloadUrl) {
        this.latestVersion = latestVersion;
        this.downloadUrl = downloadUrl;
    }

    public VersionDTO() {
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    @Override
    public String toString() {
        return "VersionDTO{" +
                "latestVersion='" + latestVersion + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
