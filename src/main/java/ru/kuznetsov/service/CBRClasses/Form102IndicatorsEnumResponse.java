
package ru.kuznetsov.service.CBRClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Form102IndicatorsEnumResult">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;any/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "form102IndicatorsEnumResult"
})
@XmlRootElement(name = "Form102IndicatorsEnumResponse")
public class Form102IndicatorsEnumResponse {

    @XmlElement(name = "Form102IndicatorsEnumResult", required = true)
    protected Form102IndicatorsEnumResponse.Form102IndicatorsEnumResult form102IndicatorsEnumResult;

    /**
     * Gets the value of the form102IndicatorsEnumResult property.
     * 
     * @return
     *     possible object is
     *     {@link Form102IndicatorsEnumResponse.Form102IndicatorsEnumResult }
     *     
     */
    public Form102IndicatorsEnumResponse.Form102IndicatorsEnumResult getForm102IndicatorsEnumResult() {
        return form102IndicatorsEnumResult;
    }

    /**
     * Sets the value of the form102IndicatorsEnumResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Form102IndicatorsEnumResponse.Form102IndicatorsEnumResult }
     *     
     */
    public void setForm102IndicatorsEnumResult(Form102IndicatorsEnumResponse.Form102IndicatorsEnumResult value) {
        this.form102IndicatorsEnumResult = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;any/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "any"
    })
    public static class Form102IndicatorsEnumResult {

        @XmlAnyElement(lax = true)
        protected Object any;

        /**
         * Gets the value of the any property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getAny() {
            return any;
        }

        /**
         * Sets the value of the any property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setAny(Object value) {
            this.any = value;
        }

    }

}
