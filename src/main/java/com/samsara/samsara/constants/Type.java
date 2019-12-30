/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.constants;

/**
 *
 * @author eldee
 */
public enum Type {
    
buyer("مشترى"),seller("بائع"),moager("مؤجر"),mostager("مستأجر");
        private String type;
        private Type(String type){
            this.type=type;
        }
        
       static public boolean contain(String type){
            for(Type t:Type.values())
                if(type.equals(t.type))
                    return true;
            return false;
        }
    }
    
