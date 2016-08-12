package com.feed.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Builder
@Getter
@ToString
public final class GlobalConfig {

    private String incomingPath;
    private String errorPath;
    private String processingPath;
    private String processedPath;
    private long schedulePeriod;

}
