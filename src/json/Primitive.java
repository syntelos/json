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

/**
 * The gap primitive types from java lang and google datastore.
 * 
 * <h3>Special note for Key type</h3>
 * 
 * Instances of the Key type should be complete -- depending on the
 * context -- before being stored.  Stored incomplete keys have a name
 * component and are retrieved for a unique value.
 * 
 * <h3>Extensible user types</h3>
 * 
 * User primitives include the extensible types enum and date.
 * 
 * 
 * @author jdp
 */
public enum Primitive {
    String(java.lang.String.class),
    Boolean(java.lang.Boolean.class,java.lang.Boolean.TYPE),
    Byte(java.lang.Byte.class,java.lang.Byte.TYPE),
    Character(java.lang.Character.class,java.lang.Character.TYPE),
    Short(java.lang.Short.class,java.lang.Short.TYPE),
    Integer(java.lang.Integer.class,java.lang.Integer.TYPE), 
    Long(java.lang.Long.class,java.lang.Long.TYPE),
    Float(java.lang.Float.class,java.lang.Float.TYPE),
    Double(java.lang.Double.class,java.lang.Double.TYPE),
    Date(java.util.Date.class),
    Enum(java.lang.Enum.class),
    BigInteger(java.math.BigInteger.class),
    BigDecimal(java.math.BigDecimal.class);


    public final boolean dual;

    public final Class[] type;

    public final String full, pkg, local;


    private Primitive(Class... impl){
        this.type = impl;
        this.dual = (1 < impl.length);
        this.full = impl[0].getName();
        {
            final int idx = this.full.lastIndexOf('.');
            this.pkg = this.full.substring(0,idx);
            this.local = this.full.substring(idx+1);
        }
    }


    public String getName(){
        return this.type[0].getName();
    }
    public boolean isNumber(){
        switch(this){
        case Byte:
        case Short:
        case Integer:
        case Long:
        case Float:
        case Double:
            return true;

        default:
            return false;
        }
    }
    public boolean isInteger(){
        switch(this){
        case Byte:
        case Short:
        case Integer:
        case Long:
            return true;

        default:
            return false;
        }
    }
    public boolean isAssignableFrom(Class type){
        for (Class tt: this.type){
            if (tt.isAssignableFrom(type))
                return true;
        }
        return false;
    }


    public final static boolean Is(String name){
        return (null != Primitive.For(name));
    }
    public final static boolean Is(Class type){
        if (null != Primitive.For(type))
            return true;
        else if (Enum.isAssignableFrom(type))
            return true;
        else if (Date.isAssignableFrom(type))
            return true;
        else
            return false;
    }
    public final static Primitive For(Class type){
        if (null != type){
            Primitive p = Primitive.Map.get(type.getName());
            if (null != p)
                return p;
            else if (Enum.isAssignableFrom(type))
                return Primitive.Enum;
            else if (Date.isAssignableFrom(type))
                return Primitive.Date;
        }
        return null;
    }
    public final static Primitive For(Class type, boolean isEnum){
        if (isEnum)
            return Primitive.Enum;
        else
            return Primitive.For(type);
    }
    public final static Primitive For(Class type, boolean isEnum, boolean isTable, boolean isCollection){
        if (isEnum)
            return Primitive.Enum;
        else {
            return Primitive.For(type);
        }
    }
    public final static Primitive For(String name){
        Primitive type = Primitive.Map.get(name);
        if (null != type)
            return type;
        else {
            try {
                return Primitive.valueOf(name);
            }
            catch (IllegalArgumentException exc){
                return null;
            }
        }
    }
    public final static Primitive For(String name, Class type){
        if (null != type)
            return Primitive.For(type);
        else
            return Primitive.For(name);
    }


    /*
     * Primitive type map
     */
    private final static java.util.Map<String,Primitive> Map = new java.util.HashMap<String,Primitive>();
    static {
        for (Primitive type : Primitive.values()){
            for (Class clas: type.type){
                final String name = clas.getName();
                final String lname = name.toLowerCase();

                Primitive.Map.put(name,type);

                if (!lname.equals(name)){
                    Primitive.Map.put(lname,type);
                }
            }
        }
    }
}
