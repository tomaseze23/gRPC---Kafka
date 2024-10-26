// src/main/java/com/example/demo/util/DateConverter.java
package com.example.demo.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class DateConverter {

    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime localDateTime) {
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDateTime.atZone(ZoneId.systemDefault()));
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Error al convertir LocalDateTime a XMLGregorianCalendar", e);
        }
    }

    public static LocalDateTime fromXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }
}
