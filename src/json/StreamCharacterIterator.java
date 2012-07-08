/*
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

import java.nio.charset.Charset;


/**
 * Read stream fully to buffer
 */
public class StreamCharacterIterator
    implements java.text.CharacterIterator
{
    private final static Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Count always zero-positive
     */
    public final int length;
    /**
     * Buffer may be null
     */
    private final char[] buffer;
    /**
     * Index always zero-positive
     */
    private int current;


    /**
     * @param in Closed by caller, UTF-8 charset
     */
    public StreamCharacterIterator(InputStream in)
        throws IOException
    {
        this(in,UTF8);
    }
    /**
     * @param in Closed by caller
     * @param cs Input charset
     */
    public StreamCharacterIterator(InputStream in, Charset cs)
        throws IOException
    {
        this(new InputStreamReader(in,cs));
    }
    /**
     * @param in Closed by caller
     */
    public StreamCharacterIterator(Reader in)
        throws IOException
    {
        super();

        int length = 0;
        char[] buffer = null;
        char[] iob = new char[0x200];
        int read;
        while (0 < (read = in.read(iob,0,0x200))){

            if (0 < length){
                char[] copier = new char[length+read];
                System.arraycopy(buffer,0,copier,0,length);
                System.arraycopy(iob,0,copier,length,read);
                buffer = copier;
            }
            else {
                char[] copier = new char[read];
                System.arraycopy(iob,0,copier,0,read);
                buffer = copier;
            }
            length += read;
        }
        this.length = length;
        this.buffer = buffer;
    }


    public char first(){
        this.current = 0;
        return this.current();
    }
    public char last(){
        if (0 < this.length){
            this.current = (this.length-1);
            return this.current();
        }
        else
            return DONE;
    }
    public char current(){
        if (this.current < this.length)
            return this.buffer[this.current];
        else
            return DONE;
    }
    public char next(){
        if (this.current < this.length){
            this.current++;
            return this.current();
        }
        else
            return DONE;
    }
    public char previous(){
        if (0 < this.current){
            this.current--;
            return this.current();
        }
        else
            return DONE;
    }
    public char setIndex(int p){
        if (-1 < p && p < this.length){
            this.current = p;
            return this.current();
        }
        else
            throw new IllegalArgumentException(String.valueOf(p));
    }
    public int getBeginIndex(){
        return 0;
    }
    public int getEndIndex(){
        return this.length;
    }
    public int getIndex(){
        return this.current;
    }
    public StreamCharacterIterator clone(){
        try {
            return (StreamCharacterIterator)super.clone();
        }
        catch (CloneNotSupportedException exc){
            throw new InternalError();
        }
    }
}
