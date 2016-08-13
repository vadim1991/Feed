package com.feed.processor;

import com.feed.model.EntryModel;
import com.feed.model.GlobalConfig;
import com.feed.model.TransferEntry;
import com.feed.service.EntryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

/**
 * Created by Vadym on 14.08.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessorTest {

    private TransferEntry transferEntry;
    private final File validFile = new File(getClass().getResource("/entry.xml").getFile());
    private final File invalidFile = new File(getClass().getResource("/logback.xml").getFile());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final EntryModel entryModel = new EntryModel("Содержимое записи", dateFormat.parse("2014-01-01 00:00:00"));

    @Autowired
    @Qualifier("validationProcessor")
    private Processor validateXmlProcessor;
    @Autowired
    @Qualifier("parseXMLProcessor")
    private Processor parseXMLProcessor;
    @Autowired
    @Qualifier("saveEntryProcessor")
    private SaveValidEntriesProcessor saveEntryProcessor;


    public ProcessorTest() throws ParseException {
    }

    @Before
    public void init() {
        GlobalConfig globalConfig = GlobalConfig.builder().build();
        transferEntry = new TransferEntry(globalConfig);
        transferEntry.getIncomingFileList().addAll(asList(validFile, invalidFile));
        EntryService entryServiceMock = Mockito.mock(EntryService.class);
        Mockito.doNothing().when(entryServiceMock).batchSave(singletonList(entryModel.toEntity()));
        saveEntryProcessor.setEntryService(entryServiceMock);
        entryServiceMock.batchSave(new ArrayList<>());
    }

    @Test
    public void validationProcessorTest() {
        initialTransferEntryTest();
        validateXmlProcessor.process(transferEntry);
        assertEquals(transferEntry.getValidFileList().size(), 1);
        assertEquals(transferEntry.getErrorFileList().size(), 1);
        assertTrue(invalidFile.equals(transferEntry.getErrorFileList().get(0)));
    }

    @Test
    public void parseXMLProcessorTest() {
        initialTransferEntryTest();
        validateXmlProcessor.process(transferEntry);
        parseXMLProcessor.process(transferEntry);
        assertFalse(transferEntry.getValidFileList().isEmpty());
        assertEquals(transferEntry.getIncomingEntries().size(), 1);
        assertTrue(entryModel.equals(transferEntry.getIncomingEntries().get(0)));
    }

    @Test
    public void saveValidEntriesProcessorTest() {
        initialTransferEntryTest();
        validateXmlProcessor.process(transferEntry);
        parseXMLProcessor.process(transferEntry);
        saveEntryProcessor.process(transferEntry);
        assertEquals(transferEntry.getProcessedFileList().size(), 1);
        assertTrue(validFile.equals(transferEntry.getProcessedFileList().get(0)));
    }

    private void initialTransferEntryTest() {
        assertEquals(transferEntry.getIncomingFileList().size(), 2);
        assertTrue(transferEntry.getErrorFileList().isEmpty());
        assertTrue(transferEntry.getProcessedFileList().isEmpty());
        assertTrue(transferEntry.getValidFileList().isEmpty());
        assertTrue(transferEntry.getIncomingEntries().isEmpty());
    }

}
