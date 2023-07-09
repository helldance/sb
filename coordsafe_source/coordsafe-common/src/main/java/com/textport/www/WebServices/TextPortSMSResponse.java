/**
 * TextPortSMSResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.textport.www.WebServices;

public class TextPortSMSResponse implements java.io.Serializable {
	private int itemNumber;

	private java.lang.String mobileNumber;

	private java.lang.String result;

	private int messageID;

	private java.lang.String processingMessage;

	private java.lang.String errorMessage;

	public TextPortSMSResponse() {
	}

	public TextPortSMSResponse(int itemNumber, java.lang.String mobileNumber,
			java.lang.String result, int messageID,
			java.lang.String processingMessage, java.lang.String errorMessage) {
		
		this.itemNumber = itemNumber;
		this.mobileNumber = mobileNumber;
		this.result = result;
		this.messageID = messageID;
		this.processingMessage = processingMessage;
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the itemNumber value for this TextPortSMSResponse.
	 * 
	 * @return itemNumber
	 */
	public int getItemNumber() {
		return itemNumber;
	}

	/**
	 * Sets the itemNumber value for this TextPortSMSResponse.
	 * 
	 * @param itemNumber
	 */
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * Gets the mobileNumber value for this TextPortSMSResponse.
	 * 
	 * @return mobileNumber
	 */
	public java.lang.String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Sets the mobileNumber value for this TextPortSMSResponse.
	 * 
	 * @param mobileNumber
	 */
	public void setMobileNumber(java.lang.String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * Gets the result value for this TextPortSMSResponse.
	 * 
	 * @return result
	 */
	public java.lang.String getResult() {
		return result;
	}

	/**
	 * Sets the result value for this TextPortSMSResponse.
	 * 
	 * @param result
	 */
	public void setResult(java.lang.String result) {
		this.result = result;
	}

	/**
	 * Gets the messageID value for this TextPortSMSResponse.
	 * 
	 * @return messageID
	 */
	public int getMessageID() {
		return messageID;
	}

	/**
	 * Sets the messageID value for this TextPortSMSResponse.
	 * 
	 * @param messageID
	 */
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	/**
	 * Gets the processingMessage value for this TextPortSMSResponse.
	 * 
	 * @return processingMessage
	 */
	public java.lang.String getProcessingMessage() {
		return processingMessage;
	}

	/**
	 * Sets the processingMessage value for this TextPortSMSResponse.
	 * 
	 * @param processingMessage
	 */
	public void setProcessingMessage(java.lang.String processingMessage) {
		this.processingMessage = processingMessage;
	}

	/**
	 * Gets the errorMessage value for this TextPortSMSResponse.
	 * 
	 * @return errorMessage
	 */
	public java.lang.String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the errorMessage value for this TextPortSMSResponse.
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(java.lang.String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof TextPortSMSResponse))
			return false;
		TextPortSMSResponse other = (TextPortSMSResponse) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& this.itemNumber == other.getItemNumber()
				&& ((this.mobileNumber == null && other.getMobileNumber() == null) || (this.mobileNumber != null && this.mobileNumber
						.equals(other.getMobileNumber())))
				&& ((this.result == null && other.getResult() == null) || (this.result != null && this.result
						.equals(other.getResult())))
				&& this.messageID == other.getMessageID()
				&& ((this.processingMessage == null && other
						.getProcessingMessage() == null) || (this.processingMessage != null && this.processingMessage
						.equals(other.getProcessingMessage())))
				&& ((this.errorMessage == null && other.getErrorMessage() == null) || (this.errorMessage != null && this.errorMessage
						.equals(other.getErrorMessage())));
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
		_hashCode += getItemNumber();
		if (getMobileNumber() != null) {
			_hashCode += getMobileNumber().hashCode();
		}
		if (getResult() != null) {
			_hashCode += getResult().hashCode();
		}
		_hashCode += getMessageID();
		if (getProcessingMessage() != null) {
			_hashCode += getProcessingMessage().hashCode();
		}
		if (getErrorMessage() != null) {
			_hashCode += getErrorMessage().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			TextPortSMSResponse.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://www.textport.com/WebServices/", "TextPortSMSResponse"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("itemNumber");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://www.textport.com/WebServices/", "ItemNumber"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("mobileNumber");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://www.textport.com/WebServices/", "MobileNumber"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("result");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://www.textport.com/WebServices/", "Result"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("messageID");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://www.textport.com/WebServices/", "MessageID"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("processingMessage");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://www.textport.com/WebServices/", "ProcessingMessage"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("errorMessage");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://www.textport.com/WebServices/", "ErrorMessage"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
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
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
				_xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
				_xmlType, typeDesc);
	}

}
