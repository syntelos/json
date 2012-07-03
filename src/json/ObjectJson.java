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

import lxl.Map;

/**
 * 
 */
public class ObjectJson
    extends Json
    implements Iterable<Json>
{
    private Map<String, Json> object = new Map();

		
    public ObjectJson() {
        super();
    }
    /**
     * @param args Sequence of name-value pairs 
     */
    public ObjectJson(Object... args) {
        super();

        if (args.length % 2 != 0)
            throw new IllegalArgumentException("Odd number of arguments for sequence of name-value pairs.");
        else {
            for (int i = 0; i < args.length; i++)
                this.set(args[i].toString(), args[++i]);
        }
    }
    public ObjectJson(Map map){
        super();

        for (Object key: map.keySet()){

            this.set( (String)key, map.get(key));
        }
    }
    public ObjectJson(java.util.Map map){
        super();

        for (java.util.Map.Entry entry : (java.util.Set<java.util.Map.Entry>)map.entrySet()){

            this.set( (String)entry.getKey(), entry.getValue());
        }
    }


    public java.util.Iterator<Json> iterator(){

        return this.object.iterator();
    }
    public boolean isNull(){
        return (0 == object.size());
    }
    @Override
    public ObjectJson clone(){ 

        ObjectJson clone = (ObjectJson)super.clone();
        clone.object = clone.object.clone();
        return clone;
    }
		
    public boolean has(String property)
    {
        return object.containsKey(property);
    }
		
    public boolean is(String property, Object value) 
    { 
        Json p = object.get(property);
        if (p == null)
            return false;
        else {
            Object po = p.getValue();
            if (null == po)
                return false;
            else
                return po.equals(value);
        }
    }		
		
    public Json at(String property)
    {
        return object.get(property);
    }

    public Json with(Json x)
    {
        if (x instanceof ObjectJson){

            ObjectJson that = (ObjectJson)x;
            /*
             * this.object.putAll(that.object)
             */
            for (String thatKey: that.object.keySet()){

                this.object.put(thatKey,that.object.get(thatKey));
            }
            return this;
        }
        else
            throw new UnsupportedOperationException();
    }
    public Object getValue(String property){

        Json wrap = object.get(property);
        if (null != wrap)
            return wrap.getValue();
        else
            return null;
    }
    public Object getValue(String property, Class clas){

        Json wrap = object.get(property);
        if (null != wrap)
            return wrap.getValue(clas);
        else
            return null;
    }
    public Json setValue(String property, Object value){

        object.put(property,Json.Wrap(value));

        return this;
    }
    public Json set(String property, Json el)
    {
        object.put(property, el);
        return this;
    }

    public Json atDel(String property) 
    {
        Json el = object.remove(property);

        return el;
    }
		
    public Json delAt(String property) 
    {
        Json el = object.remove(property);

        return this;
    }
    public Object getValue(){

        return this.object;
    }
    public boolean isObject() { return true; }
		
    public String toString(final boolean child, final int d)
    {
        StringBuilder sb = new StringBuilder();

        if (!child){
            for (int dd = 0; dd < d; dd++){
                sb.append(' ');
            }
        }
        sb.append("{\n");

        boolean start = true;

        for (String key : this.object.keySet()){

            if (start)
                start = false;
            else {
                sb.append(",\n");
            }

            String name = Escaper.Plain.escapeJsonString(key);

            Json value = this.object.get(key);

            for (int dd = -1; dd < d; dd++){
                sb.append(' ');
            }
            sb.append('"');
            sb.append(name);
            sb.append('"');
            sb.append(": ");
            sb.append(value.toString(true,d+1));
        }
        sb.append('\n');

        for (int dd = 0; dd < d; dd++){
            sb.append(' ');
        }
        sb.append('}');
        return sb.toString();
    }
    public int hashCode() { 
        int h = 0;
        for (String key: object.keySet()){

            h ^= key.hashCode();
        }
        return h;
    }
    public boolean equals(Object x)
    {			
        return (x instanceof ObjectJson) && ((ObjectJson)x).object.equals(object); 
    }				
}
