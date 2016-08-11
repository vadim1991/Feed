package com.feed.processor;

import static java.util.Arrays.asList;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
public class FeedStrategy extends DefaultStrategy {

    public FeedStrategy() {
        super(asList(
                new ValidateXmlProcessor(),
                new ParseXmlProcessor(),
                new SaveValidEntriesProcessor(),
                new MoveFilesProcessor()
        ));
    }
}
