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
    /**
     * Comparison to capture equivalent representations of a number.
     * Negative zero is equivalent to positive zero.
     */
    public final static boolean Equals(Number a, Number b){
        final boolean aInt = (a instanceof Byte || a instanceof Short || a instanceof Integer || a instanceof Long);
        final boolean bInt = (b instanceof Byte || b instanceof Short || b instanceof Integer || b instanceof Long);

        if (aInt && bInt){

            return (a.longValue() == b.longValue());
        }
        else {

            final double aVal = a.doubleValue();
            final double bVal = b.doubleValue();
            /*
             * includes (+0.0 == -0.0)
             */
            if (aVal == bVal)

                return true;
            /*
             * check rounding / representation
             */
            else if (Math.signum(aVal) == Math.signum(bVal)){
                /*
                 * consistent for order of operands
                 */
                final double xulp = Math.max(Math.ulp(aVal),Math.ulp(bVal));

                return (Math.abs(aVal-bVal) > xulp);
            }
            else
                return false;
        }
    }


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
    public boolean equals(Object that)
    {			
        if (this == that)
            return true;
        else if (null == that)
            return false;

        else if (that instanceof NumberJson)

            return Equals(this.val, ((NumberJson)that).val);
        else 
            return false;
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
        else if (that instanceof NumberJson){

            final Number thisVal = this.val;
            final Number thatVal = ((NumberJson)that).val;

            if (Equals(thisVal,thatVal))
                return 0;
            else {
                final Double thisD = thisVal.doubleValue();
                final Double thatD = thatVal.doubleValue();

                return thisD.compareTo(thatD);
            }
        }
        else 
            return this.getClass().getName().compareTo(that.getClass().getName());
    }

}
