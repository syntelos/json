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

import static json.Primitive.* ;

import java.lang.reflect.Method;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Random;
import java.util.StringTokenizer;


/**
 * String conversions for {@link Primitive} types.
 * 
 * @author jdp
 */
public abstract class Strings {

    public final static <T> T FromString(Primitive type, java.lang.String string){
        if (null == string)
            return null;
        else {
            switch(type){
            case String:
                return (T)string;
            case Boolean:
                return (T)BooleanFromString(string);
            case Byte:
                return (T)ByteFromString(string);
            case Character:
                return (T)CharacterFromString(string);
            case Short:
                return (T)ShortFromString(string);
            case Integer:
                return (T)IntegerFromString(string);
            case Long:
                return (T)LongFromString(string);
            case Float:
                return (T)FloatFromString(string);
            case Double:
                return (T)DoubleFromString(string);
            case Date:
                return (T)DateFromString(string);
            case BigInteger:
                return (T)BigIntegerFromString(string);
            case BigDecimal:
                return (T)BigDecimalFromString(string);
            default:
                throw new java.lang.IllegalStateException("Unrecognized type "+type.name());
            }
        }
    }
    public final static java.lang.String ToString(Primitive type, java.lang.Object instance){
        if (null == instance)
            return null;
        else {
            switch(type){
            case String:
                return (java.lang.String)instance;
            case Boolean:
                return BooleanToString((java.lang.Boolean)instance);
            case Byte:
                return ByteToString((java.lang.Byte)instance);
            case Character:
                return CharacterToString((java.lang.Character)instance);
            case Short:
                return ShortToString((java.lang.Short)instance);
            case Integer:
                return IntegerToString((java.lang.Integer)instance);
            case Long:
                return LongToString((java.lang.Long)instance);
            case Float:
                return FloatToString((java.lang.Float)instance);
            case Double:
                return DoubleToString((java.lang.Double)instance);
            case Date:
                return DateToString((java.util.Date)instance);
            case Enum:
                return EnumToString((java.lang.Enum)instance);
            case BigInteger:
                return BigIntegerToString((java.math.BigInteger)instance);
            case BigDecimal:
                return BigDecimalToString((java.math.BigDecimal)instance);
            default:
                throw new java.lang.IllegalStateException("Unrecognized type "+type.name());
            }
        }
    }

    public final static java.lang.String StringFromString(java.lang.String string){
        if (null != string)
            return string;
        else
            return null;
    }
    public final static java.lang.String StringToString(java.lang.String instance){
        if (null != instance)
            return (java.lang.String)instance;
        else
            return null;
    }
    public final static java.lang.Boolean BooleanFromString(java.lang.String string){
        if (null != string)
            return new Boolean(string);
        else
            return null;
    }
    public final static java.lang.String BooleanToString(java.lang.Boolean instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }

    public final static java.lang.Byte ByteFromString(java.lang.String string){
        if (null != string)
            return java.lang.Byte.decode(string);
        else
            return null;
    }
    public final static java.lang.String ByteToString(java.lang.Byte instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Character CharacterFromString(java.lang.String string){
        if (null != string && 0 < string.length())
            return new java.lang.Character(string.charAt(0));
        else
            return null;
    }
    public final static java.lang.String CharacterToString(java.lang.Character instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Short ShortFromString(java.lang.String string){
        if (null != string)
            return java.lang.Short.decode(string);
        else
            return null;
    }
    public final static java.lang.String ShortToString(java.lang.Short instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Integer IntegerFromString(java.lang.String string){
        if (null != string)
            return java.lang.Integer.decode(string);
        else
            return null;
    }
    public final static java.lang.String IntegerToString(java.lang.Integer instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Long LongFromString(java.lang.String string){
        if (null != string)
            return java.lang.Long.decode(string);
        else
            return null;
    }
    public final static java.lang.String LongToString(java.lang.Long instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Float FloatFromString(java.lang.String string){
        if (null != string)
            return new java.lang.Float(string);
        else
            return null;
    }
    public final static java.lang.String FloatToString(java.lang.Float instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Double DoubleFromString(java.lang.String string){
        if (null != string)
            return new java.lang.Double(string);
        else
            return null;
    }
    public final static java.lang.String DoubleToString(java.lang.Double instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.util.Date DateFromString(java.lang.String string){
        if (null != string)
            return json.Date.ParseRFC1123(string);
        else
            return null;
    }
    public final static java.lang.String DateToString(java.util.Date instance){
        if (null != instance)
            return json.Date.FormatRFC1123(instance);
        else
            return null;
    }
    public final static java.lang.Enum EnumFromString(java.lang.String string){
        if (null != string){
            int parse = string.indexOf('#');
            if (-1 < parse){
                try {
                    Class clas = Class.forName(string.substring(0,parse));
                    Method valueOf = clas.getMethod("valueOf",String.class);
                    return (java.lang.Enum)valueOf.invoke(null,string.substring(parse+1));
                }
                catch (Exception any){
                    throw new IllegalArgumentException(string,any);
                }
            }
            else
                throw new IllegalArgumentException(string);
        }
        else
            return null;
    }
    public final static java.lang.String EnumToString(java.lang.Enum instance){
        if (null != instance)
            return instance.getClass().getName()+'#'+instance.name();
        else
            return null;
    }
    public final static java.math.BigInteger BigIntegerFromString(java.lang.String string){
        if (null != string)
            return new java.math.BigInteger(json.Hex.decode(string));
        else
            return null;
    }
    public final static java.lang.String BigIntegerToString(java.math.BigInteger instance){
        if (null != instance)
            return json.Hex.encode(instance.toByteArray());
        else
            return null;
    }
    public final static java.math.BigDecimal BigDecimalFromString(java.lang.String string){
        if (null != string)
            return new java.math.BigDecimal(string);
        else
            return null;
    }
    public final static java.lang.String BigDecimalToString(java.math.BigDecimal instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }

    public final static java.lang.String[] Add(java.lang.String[] list, Primitive type, java.lang.Object instance){
	return Add(list,ToString(type,instance));
    }
    public final static java.lang.String[] Add(java.lang.String[] list, java.lang.String item){
	if (null == item)
	    return list;
	else if (null == list)
	    return new java.lang.String[]{item};
	else {
	    int len = list.length;
	    java.lang.String[] copier = new java.lang.String[len+1];
	    java.lang.System.arraycopy(list,0,copier,0,len);
	    copier[len] = item;
	    return copier;
	}
    }
    private final static int RandomIdentifierOctets = 12;

    public final static java.lang.String RandomIdentifier(){
        byte[] bits = new byte[RandomIdentifierOctets];
        {
            new Random().nextBytes(bits);
        }
        return RandomIdentifierPathclean(B64.encodeBytes(bits));
    }
    private final static java.lang.String RandomIdentifierPathclean(java.lang.String r){
        char[] cary = r.toCharArray();
        final int count = cary.length;
        boolean change = false;
        for (int cc = 0; cc < count; cc++){
            switch (cary[cc]){
            case '/':
                change = true;
                cary[cc] = 'A';
                break;
            case '+':
                change = true;
                cary[cc] = 'B';
                break;
            case '\r':
                change = true;
                cary[cc] = 'C';
                break;
            case '\n':
                change = true;
                cary[cc] = 'D';
                break;
            case '=':
                change = true;
                cary[cc] = 'E';
                break;
            default:
                break;
            }
        }
        if (change)
            return new java.lang.String(cary,0,count);
        else
            return r;
    }
}
