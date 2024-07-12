package org.example.vegetableshopping.exception;

public class ResourceExitsException extends RuntimeException{
        private String objectName;
        private String fieldName;
        private String fieldValue;

        public ResourceExitsException(String objectName, String fieldName, String fieldValue) {
            super(String.format("%s with %s '%s' already exists", objectName, fieldName, fieldValue));
            this.objectName = objectName;
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

}
