package com.epam.jwd.core_final.exception;

public class UnknownEntityException extends RuntimeException {

    private final String entityName;
    private final Object[] args;

    public UnknownEntityException(String entityName) {
        super();
        this.entityName = entityName;
        this.args = null;
    }

    public UnknownEntityException(String entityName, Object[] args) {
        super();
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        // you should use entityName, args (if necessary)
        StringBuilder exceptionMessage = new StringBuilder("This entity: \n{" + entityName +", with args: {");
        if(args != null) {
            for(Object arg: args){
                exceptionMessage.append(arg.toString()).append(", ");
            }
            exceptionMessage.append("} - is unknown");
        }
        return exceptionMessage.toString();
    }
}
