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
public class NumberJson
    extends Json
{
    private Number val;


    public NumberJson(){
        super();
    }
    public NumberJson(Number val) { 
        super(); 
        this.val = val;
    }

    public boolean isNull(){
        return (null == val);
    }
    public boolean isNumber() { return true; }		
    public Object getValue() { return val; }
    public String asString() { return val.toString(); }
    public int asInteger() { return val.intValue(); }
    public float asFloat() { return val.floatValue(); }
    public double asDouble() { return val.doubleValue(); }
    public long asLong() { return val.longValue(); }
    public short asShort() { return val.shortValue(); }
    public byte asByte() { return val.byteValue(); }
		
    public String toString(final boolean child, final int d){

        if (null == val)
            return "null";
        else
            return val.toString();
    }
    public int hashCode() { return val.hashCode(); }
    public boolean equals(Object x)
    {			
        return (x instanceof NumberJson) && ((NumberJson)x).val.equals(val); 
    }				
}
