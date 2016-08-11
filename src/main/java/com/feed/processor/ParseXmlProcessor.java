package com.feed.processor;

import com.feed.model.EntryModel;
import com.feed.model.TransferEntry;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Slf4j
public class ParseXmlProcessor implements Processor {

    @Override
    public void process(TransferEntry transferEntry) {
        for (File file : transferEntry.getValidFileList()) {
            parseXml(file, transferEntry);
        }
    }

    private void parseXml(File file, TransferEntry transferEntry) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EntryModel.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            transferEntry.getIncomingEntries().add((EntryModel) unmarshaller.unmarshal(file));
        } catch (JAXBException e) {
            log.error("File {} cannot be parse or is not valid", file.getName());
            transferEntry.getErrorFileList().add(file);
        }
    }

}
