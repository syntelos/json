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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Object conversions for field types.
 * 
 * @author jdp
 */
public abstract class Objects {

    public final static <T> T From(Primitive type, java.lang.Object object){
        if (null == object)
            return null;
        else {
            switch(type){
            case String:
                return (T)StringFromObject(object);
            case Boolean:
                return (T)BooleanFromObject(object);
            case Byte:
                return (T)ByteFromObject(object);
            case Character:
                return (T)CharacterFromObject(object);
            case Short:
                return (T)ShortFromObject(object);
            case Integer:
                return (T)IntegerFromObject(object);
            case Long:
                return (T)LongFromObject(object);
            case Float:
                return (T)FloatFromObject(object);
            case Double:
                return (T)DoubleFromObject(object);
            case Date:
                return (T)DateFromObject(object);
            case Enum:
                return (T)EnumFromObject(object);
            case BigInteger:
                return (T)BigIntegerFromObject(object);
            case BigDecimal:
                return (T)BigDecimalFromObject(object);
            default:
                throw new java.lang.IllegalStateException("Unrecognized type "+type.name());
            }
        }
    }
    public final static java.lang.Object ObjectFromObject(java.lang.Object object){

        return object;
    }
    public final static java.lang.String StringFromObject(java.lang.Object object){
        if (object instanceof java.lang.String)
            return (java.lang.String)object;
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Boolean BooleanFromObject(java.lang.Object object){
        if (object instanceof java.lang.Boolean)
            return (java.lang.Boolean)object;
        else if (object instanceof java.lang.String)
            return Strings.BooleanFromString( (String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Byte ByteFromObject(java.lang.Object object){
        if (object instanceof java.lang.Byte)
            return (java.lang.Byte)object;
        else if (object instanceof java.lang.String)
            return Strings.ByteFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Byte( ((java.lang.Number)object).byteValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Character CharacterFromObject(java.lang.Object object){
        if (object instanceof java.lang.Character)
            return (java.lang.Character)object;
        /*
         * Special storage type is Integer
         */
        else if (object instanceof java.lang.Number)
            return new java.lang.Character( (char)((java.lang.Number)object).intValue());
        else if (object instanceof java.lang.String)
            return Strings.CharacterFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Short ShortFromObject(java.lang.Object object){
        if (object instanceof java.lang.Short)
            return (java.lang.Short)object;
        else if (object instanceof java.lang.String)
            return Strings.ShortFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Short( ((java.lang.Number)object).shortValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Integer IntegerFromObject(java.lang.Object object){
        if (object instanceof java.lang.Integer)
            return (java.lang.Integer)object;
        else if (object instanceof java.lang.String)
            return Strings.IntegerFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Integer( ((java.lang.Number)object).intValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Long LongFromObject(java.lang.Object object){
        if (object instanceof java.lang.Long)
            return (java.lang.Long)object;
        else if (object instanceof java.lang.String)
            return Strings.LongFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Long( ((java.lang.Number)object).longValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Float FloatFromObject(java.lang.Object object){
        if (object instanceof java.lang.Float)
            return (java.lang.Float)object;
        else if (object instanceof java.lang.String)
            return Strings.FloatFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Float( ((java.lang.Number)object).floatValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Double DoubleFromObject(java.lang.Object object){
        if (object instanceof java.lang.Double)
            return (java.lang.Double)object;
        else if (object instanceof java.lang.String)
            return Strings.DoubleFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Double( ((java.lang.Number)object).doubleValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.util.Date DateFromObject(java.lang.Object object){
        if (object instanceof java.util.Date)
            return (java.util.Date)object;
        else if (object instanceof java.lang.String)
            return Strings.DateFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.util.Date( ((java.lang.Number)object).longValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Enum EnumFromObject(java.lang.Object object){
        if (object instanceof java.lang.Enum)
            return (java.lang.Enum)object;
        else if (object instanceof java.lang.String)
            return Strings.EnumFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.math.BigInteger BigIntegerFromObject(java.lang.Object object){
        if (object instanceof java.math.BigInteger)
            return (java.math.BigInteger)object;
        else if (object instanceof java.lang.String)
            return Strings.BigIntegerFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.math.BigInteger( java.lang.Long.toString(((java.lang.Number)object).longValue(),16),16);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.math.BigDecimal BigDecimalFromObject(java.lang.Object object){
        if (object instanceof java.math.BigDecimal)
            return (java.math.BigDecimal)object;
        else if (object instanceof java.lang.String)
            return Strings.BigDecimalFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.math.BigDecimal( ((java.lang.Number)object).doubleValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
}
