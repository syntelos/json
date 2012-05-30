/*
 * Gap Data
 * Copyright (C) 2009 John Pritchard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package json;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Locale;

/**
 * Provides the Open Social date formats RFC 1123 and ISO 8601.
 * 
 */
public abstract class Date {
    
    private static final DateTimeFormatter RFC1123 = DateTimeFormat
        .forPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'")
        .withLocale(Locale.US)
        .withZone(DateTimeZone.UTC);

    private static final DateTimeFormatter ISO8601 = ISODateTimeFormat.dateTime()
        .withZone(DateTimeZone.UTC); 

    private static final DateTimeFormatter SimpleDate = DateTimeFormat
        .forPattern("dd MMM yyyy")
        .withLocale(Locale.US);
 

    public static java.util.Date Parse(String dateStr) {
        try {
            return RFC1123.parseDateTime(dateStr).toDate();
        }
        catch (Exception exc) {
            try {
                return ISO8601.parseDateTime(dateStr).toDate();
            }
            catch (Exception exc2) {
                return null;
            }
        }
    }
    public static java.util.Date ParseRFC1123(String dateStr) {
        try {
            return RFC1123.parseDateTime(dateStr).toDate();
        }
        catch (Exception exc) {
            return null;
        }
    }
    public static java.util.Date ParseISO8601(String dateStr) {
        try {
            return ISO8601.parseDateTime(dateStr).toDate();
        }
        catch (Exception exc) {
            return null;
        }
    }
    public static String FormatRFC1123(java.util.Date date) {
        return FormatRFC1123(date.getTime());
    }
    public static String FormatISO8601(java.util.Date date) {
        return FormatISO8601(date.getTime());
    }
    public static String FormatRFC1123(long timeStamp) {
        return RFC1123.print(timeStamp);
    }
    public static String FormatISO8601(long timeStamp) {
        return ISO8601.print(timeStamp);
    }
    public static String FormatSimpleDate(java.util.Date date) {
        return FormatSimpleDate(date.getTime());
    }
    public static String FormatSimpleDate(long timeStamp) {
        return SimpleDate.print(timeStamp);
    }
}
