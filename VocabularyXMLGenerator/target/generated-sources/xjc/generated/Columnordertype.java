//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.06 at 10:06:16 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for columnordertype.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="columnordertype"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="first-first"/&gt;
 *     &lt;enumeration value="first-second"/&gt;
 *     &lt;enumeration value="second-second"/&gt;
 *     &lt;enumeration value="second-first"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "columnordertype")
@XmlEnum
public enum Columnordertype {

    @XmlEnumValue("first-first")
    FIRST_FIRST("first-first"),
    @XmlEnumValue("first-second")
    FIRST_SECOND("first-second"),
    @XmlEnumValue("second-second")
    SECOND_SECOND("second-second"),
    @XmlEnumValue("second-first")
    SECOND_FIRST("second-first");
    private final String value;

    Columnordertype(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Columnordertype fromValue(String v) {
        for (Columnordertype c: Columnordertype.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
