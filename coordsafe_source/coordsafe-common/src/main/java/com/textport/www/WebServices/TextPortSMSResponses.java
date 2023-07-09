/**
 * TextPortSMSResponses.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.textport.www.WebServices;

public class TextPortSMSResponses  implements java.io.Serializable {
    private com.textport.www.WebServices.TextPortSMSResponse[] responses;

    public TextPortSMSResponses() {
    }

    public TextPortSMSResponses(
           com.textport.www.WebServices.TextPortSMSResponse[] responses) {
           this.responses = responses;
    }


    /**
     * Gets the responses value for this TextPortSMSResponses.
     * 
     * @return responses
     */
    public com.textport.www.WebServices.TextPortSMSResponse[] getResponses() {
        return responses;
    }


    /**
     * Sets the responses value for this TextPortSMSResponses.
     * 
     * @param responses
     */
    public void setResponses(com.textport.www.WebServices.TextPortSMSResponse[] responses) {
        this.responses = responses;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TextPortSMSResponses)) return false;
        TextPortSMSResponses other = (TextPortSMSResponses) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responses==null && other.getResponses()==null) || 
             (this.responses!=null &&
              java.util.Arrays.equals(this.responses, other.getResponses())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getResponses() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResponses());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResponses(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TextPortSMSResponses.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.textport.com/WebServices/", "TextPortSMSResponses"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.textport.com/WebServices/", "Responses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.textport.com/WebServices/", "TextPortSMSResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.textport.com/WebServices/", "TextPortSMSResponse"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
