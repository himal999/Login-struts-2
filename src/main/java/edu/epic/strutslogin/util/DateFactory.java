/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 *
 * @author himal
 */
public class DateFactory {

    public static Date getFormatterDate(String fromatter, Object value) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(fromatter);
        
        return formatter.parse((String)value);
    }
    

}
