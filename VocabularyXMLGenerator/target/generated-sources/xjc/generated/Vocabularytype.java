//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.06 at 10:06:16 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vocabularytype complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vocabularytype"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="instructions" type="{}instructionstype"/&gt;
 *         &lt;element name="pairs" type="{}pairstype"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vocabularytype", propOrder = {

})
public class Vocabularytype {

    @XmlElement(required = true)
    protected Instructionstype instructions;
    @XmlElement(required = true)
    protected Pairstype pairs;

    /**
     * Gets the value of the instructions property.
     * 
     * @return
     *     possible object is
     *     {@link Instructionstype }
     *     
     */
    public Instructionstype getInstructions() {
        return instructions;
    }

    /**
     * Sets the value of the instructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Instructionstype }
     *     
     */
    public void setInstructions(Instructionstype value) {
        this.instructions = value;
    }

    /**
     * Gets the value of the pairs property.
     * 
     * @return
     *     possible object is
     *     {@link Pairstype }
     *     
     */
    public Pairstype getPairs() {
        return pairs;
    }

    /**
     * Sets the value of the pairs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pairstype }
     *     
     */
    public void setPairs(Pairstype value) {
        this.pairs = value;
    }

}
