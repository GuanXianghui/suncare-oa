package com.gxx.oa.exceptions;

/**
 * ajax“Ï≥£
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 19:45
 */
public class AjaxException extends RuntimeException {
    /**
     * ajax“Ï≥£
     * @param errorMessage
     */
    public AjaxException(String errorMessage){
        super(errorMessage);
    }
}
