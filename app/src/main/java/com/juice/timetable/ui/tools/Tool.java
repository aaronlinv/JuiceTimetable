package com.juice.timetable.ui.tools;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/2/18 13:43
 * </pre>
 */
public class Tool {
    private String toolName;
    private int toolImageId;

    public int getToolImageId() {
        return toolImageId;
    }

    public String getToolName() {
        return toolName;
    }

    public Tool(String toolName, int toolImageId) {
        this.toolName = toolName;
        this.toolImageId = toolImageId;
    }

}
