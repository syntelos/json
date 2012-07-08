/*
 * Copyright (C) 2011 Miami-Dade County.
 * Copyright (C) 2012 John Pritchard, Gap Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Note: this file incorporates source code from 3d party entities. Such code 
 * is copyrighted by those entities as indicated below.
 */
package json;

/**
 * 
 */
public class StringJson
    extends Json
{
    private String val;


    public StringJson() {
        super();
    }
    public StringJson(String val) { 
        super();
        this.val = val;
    }


    public boolean isNull(){
        return (null == val || 1 > val.length());
    }
    public boolean isString() { return true; }
    public Object getValue() { return val; }
    public String asString() { return val; }
    public int asInteger() { return Integer.parseInt(val); }
    public float asFloat() { return Float.parseFloat(val); }
    public double asDouble() { return Double.parseDouble(val); }
    public long asLong() { return Long.parseLong(val); }
    public short asShort() { return Short.parseShort(val); }
    public byte asByte() { return Byte.parseByte(val); }
    public char asChar() { return val.charAt(0); }
		
    public String toString(final boolean child, final int d){
        if (null == val)
            return "null";
        else
            return '"' + Escaper.Plain.escapeJsonString(val) + '"'; 
    }
		
    public int hashCode() { return val.hashCode(); }
    public boolean equals(Object x)
    {			
        return (x instanceof StringJson) && ((StringJson)x).val.equals(val); 
    }		
    public int compareTo(Json that){
        if (this == that)
            return 0;
        else if (null == that)
            return +1;
        else if (this.isNull()){

            if (that.isNull())

                return 0;
            else
                return -1;
        }
        else if (that.isNull()){

            return +1;
        }
        else if (that instanceof StringJson){
            String thatVal = ((StringJson)that).val;

            String thisVal = this.val;

            return thisVal.compareTo(thatVal);
        }
        else 
            return this.getClass().getName().compareTo(that.getClass().getName());
    }
}
