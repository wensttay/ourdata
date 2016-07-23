/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifpb.simba.ourdata.reader;

/**
 * Enum of print colors
 *
 * @author wensttay
 */
public enum TextColor{
    ANSI_RESET("\u001B[0m"),
    ANSI_BLACK("\u001B[30m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_CYAN("\u001B[36m"),
    ANSI_WHITE("\u001B[37m");

    private String code;

    private TextColor( String code ){
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode(){
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode( String code ){
        this.code = code;
    }
    
    
}
