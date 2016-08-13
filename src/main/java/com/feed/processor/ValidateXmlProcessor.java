package com.feed.processor;

import com.feed.model.TransferEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static org.apache.commons.io.FilenameUtils.getExtension;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Component(value = "validationProcessor")
@Slf4j
public class ValidateXmlProcessor implements Processor {

    public static final String XML = "xml";
    public static final String ENTRY_XSD_PATH = "/entry.xsd";
    private Validator validator;

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

    @PostConstruct
    public void init() throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(getClass().getResourceAsStream(ENTRY_XSD_PATH)));
        validator = schema.newValidator();
    }

    private boolean validateXml(File file) {
        try(InputStream inputStream = new FileInputStream(file)) {
            validator.validate(new StreamSource(inputStream));
            return true;
        } catch (IOException | SAXException e) {
            log.error("File {} is invalid by XSD schema due: {}", file.getName(), e.getMessage());
            return false;
        } finally {
            validator.reset();
        }
    }
}
