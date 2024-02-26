package com.juice.timetable.data.parse;


import static com.juice.timetable.app.Constant.URI_GITHUB_RELEASE_API;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.juice.timetable.data.parse.dto.VersionDTO;

import org.junit.Test;

public class ParseVersionTest {
    public static final String TEST_URL = URI_GITHUB_RELEASE_API;

    @Test
    public void getSource() throws Exception {
        String source = ParseVersion.getSource(TEST_URL);
        assertThat(source).isNotEmpty();
    }

    @Test
    public void getVersion() throws Exception {
        String source = ParseVersion.getSource(TEST_URL);
        VersionDTO version = ParseVersion.getVersion(source);
        String latestVersion = version.getLatestVersion();
        assertThat(latestVersion).isNotEmpty();
        assertThat(latestVersion).startsWith(".");

        String downloadUrl = version.getDownloadUrl();
        assertThat(downloadUrl).startsWith("https://").contains(latestVersion);
    }
}