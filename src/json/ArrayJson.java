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

import lxl.ArrayList;
import lxl.List;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * 
 */	
public class ArrayJson
    extends Json
    implements Iterable<Json>
{
    private List<Json> list = new ArrayList<Json>();

		
    public ArrayJson() {
        super();
    }
    public ArrayJson(Object... args) {
        super();
        for (Object x : args){
            this.add(x);
        }
    }
    public ArrayJson(Iterable args) {
        super();
        for (Object x : args){
            this.add(x);
        }
    }
		

    public java.util.Iterator<Json> iterator(){

        return this.list.iterator();
    }
    public Json[] toArray(){

        return this.list.toArray(Json.class);
    }
    public boolean isNull(){
        return (0 == list.size());
    }
    public Json dup() 
    { 
        ArrayJson j = new ArrayJson();
        for (Json e : list)
            {
                Json v = e.dup();
                j.list.add(v);
            }
        return j;
    }
    public boolean is(int index, Object value) 
    { 
        if (index < 0 || index >= list.size())
            return false;
        else {
            Json p = list.get(index);
            if (null == p)
                return false;
            else {
                Object po = p.getValue();
                if (null == po)
                    return false;
                else
                    return po.equals(value);
            }
        }
    }
    public <T> T getValue(Class<T> clas){
        Class componentClass;
        if (clas.isArray())
            componentClass = clas.getComponentType();
        else
            componentClass = clas; // hope for the best? (lhs is Object)

        final int count = this.list.size();
        Object re;

        if (componentClass.isPrimitive()){

            if (java.lang.Boolean.TYPE.equals(componentClass)){
                boolean[] list = new boolean[count];
                for (int cc = 0; cc < count; cc++){
                    Boolean value = (Boolean)this.list.get(cc).getValue(componentClass);
                    list[cc] = value.booleanValue();
                }
                re = list;
            }
            else if (java.lang.Character.TYPE.equals(componentClass)){
                char[] list = new char[count];
                for (int cc = 0; cc < count; cc++){
                    Character value = (Character)this.list.get(cc).getValue(componentClass);
                    list[cc] = value.charValue();
                }
                re = list;
            }
            else if (java.lang.Byte.TYPE.equals(componentClass)){
                byte[] list = new byte[count];
                for (int cc = 0; cc < count; cc++){
                    Byte value = (Byte)this.list.get(cc).getValue(componentClass);
                    list[cc] = value.byteValue();
                }
                re = list;
            }
            else if (java.lang.Short.TYPE.equals(componentClass)){
                short[] list = new short[count];
                for (int cc = 0; cc < count; cc++){
                    Short value = (Short)this.list.get(cc).getValue(componentClass);
                    list[cc] = value.shortValue();
                }
                re = list;
            }
            else if (java.lang.Integer.TYPE.equals(componentClass)){
                int[] list = new int[count];
                for (int cc = 0; cc < count; cc++){
                    Integer value = (Integer)this.list.get(cc).getValue(componentClass);
                    list[cc] = value.intValue();
                }
                re = list;
            }
            else if (java.lang.Long.TYPE.equals(componentClass)){
                long[] list = new long[count];
                for (int cc = 0; cc < count; cc++){
                    Long value = (Long)this.list.get(cc).getValue(componentClass);
                    list[cc] = value.longValue();
                }
                re = list;
            }
            else if (java.lang.Float.TYPE.equals(componentClass)){
                float[] list = new float[count];
                for (int cc = 0; cc < count; cc++){
                    Float value = (Float)this.list.get(cc).getValue(componentClass);
                    list[cc] = value.floatValue();
                }
                re = list;
            }
            else if (java.lang.Double.TYPE.equals(componentClass)){
                double[] list = new double[count];
                for (int cc = 0; cc < count; cc++){
                    Double value = (Double)this.list.get(cc).getValue(componentClass);
                    list[cc] = value.doubleValue();
                }
                re = list;
            }
            else
                throw new IllegalStateException(clas.getName());
        }
        else {
            Object[] list = (Object[])Array.newInstance(componentClass,count);
            for (int cc = 0; cc < count; cc++){
                list[cc] = this.list.get(cc).getValue(componentClass);
            }
            re = list;
        }
        return (T)re;
    }
    public Object getValue() { return list; }
    public boolean isArray() { return true; }
    public Json at(int index) { return list.get(index); }
    public Json add(Json el) { list.add(el); return this; }
    public Json remove(Json el) { list.remove(el); return this; }

    public Json with(Json object) 
    {
        if (!object.isArray())
            throw new UnsupportedOperationException();
        else {
            list.addAll(((ArrayJson)object).list);
            return this;
        }
    }
		
    public Json atDel(int index) 
    { 
        Json el = list.remove(index); 

        return el; 
    }
		
    public Json delAt(int index) 
    { 
        Json el = list.remove(index); 

        return this; 
    }
		
    public String toString(final boolean child, final int d)
    {
        StringBuilder sb = new StringBuilder();

        if (!child){
            for (int dd = 0; dd < d; dd++){
                sb.append(' ');
            }
        }
        sb.append("[\n");

        for (Iterator<Json> i = list.iterator(); i.hasNext(); ){
            Json value = i.next();
            for (int dd = -1; dd < d; dd++){
                sb.append(' ');
            }
            sb.append(value.toString(d+1));
            if (i.hasNext())
                sb.append(',');
            sb.append('\n');
        }
        for (int dd = 0; dd < d; dd++){
            sb.append(' ');
        }
        sb.append(']');
        return sb.toString();
    }
    public int hashCode() { return list.hashCode(); }
    public boolean equals(Object x)
    {			
        return (x instanceof ArrayJson) && ((ArrayJson)x).list.equals(list); 
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
        else if (that instanceof ArrayJson){

            return this.list.compareTo( ((ArrayJson)that).list);
        }
        else 
            return this.getClass().getName().compareTo(that.getClass().getName());
    }
}
