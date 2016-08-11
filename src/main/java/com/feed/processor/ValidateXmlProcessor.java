package com.feed.processor;

import com.feed.model.TransferEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FilenameUtils.*;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Slf4j
public class ValidateXmlProcessor implements Processor {

    public static final String XML = "xml";

    @Override
    public void process(TransferEntry transferEntry) {
        for (File file : transferEntry.getIncomingFileList()) {
            if (getExtension(file.getName()).equals(XML) && validateXml(file)) {
                transferEntry.getValidFileList().add(file);
            } else {
                transferEntry.getErrorFileList().add(file);
            }
        }
    }

    private boolean validateXml(File file) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/main/resources/entry.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
            System.out.println("valid file " + file.getName());
            return true;
        } catch (IOException | SAXException e) {
            log.error("File {} is invalid due {}", file.getName(), e.getMessage());
            return false;
        }
    }

}
