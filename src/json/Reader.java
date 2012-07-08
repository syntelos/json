/*
 * Copyright (C) 2008 Google Inc.
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

import java.io.InputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import java.util.HashMap;
import java.util.Map;


public class Reader
{
    private static final Object OBJECT_END = new Object();
    private static final Object ARRAY_END = new Object();
    private static final Object COLON = new Object();
    private static final Object COMMA = new Object();

    public static final int FIRST = 0;
    public static final int CURRENT = 1;
    public static final int NEXT = 2;

    private static Map<Character, Character> escapes = new HashMap<Character, Character>();
    static 
    {
        escapes.put(new Character('"'), new Character('"'));
        escapes.put(new Character('\\'), new Character('\\'));
        escapes.put(new Character('/'), new Character('/'));
        escapes.put(new Character('b'), new Character('\b'));
        escapes.put(new Character('f'), new Character('\f'));
        escapes.put(new Character('n'), new Character('\n'));
        escapes.put(new Character('r'), new Character('\r'));
        escapes.put(new Character('t'), new Character('\t'));
    }

    private CharacterIterator it;
    private char c;
    private Object token;
    private StringBuffer buf = new StringBuffer();


    public Reader(){
        super();
    }


    private char next() 
    {
        if (it.getIndex() == it.getEndIndex())
            throw new RuntimeException("Reached end of input at the " + 
                                       it.getIndex() + "th character.");
        c = it.next();
        return c;
    }

    private char previous()
    {
        c = it.previous();
        return c;
    }
	    
    private void skipWhiteSpace() 
    {
        do
            {
                if (Character.isWhitespace(c))
                    ;
                else if (c == '/')
                    {
                        next();
                        if (c == '*')
                            {
                                // skip multiline comments
                                while (c != CharacterIterator.DONE)
                                    if (next() == '*' && next() == '/')
                                        break;
                                if (c == CharacterIterator.DONE)
                                    throw new RuntimeException("Unterminated comment while parsing JSON string.");
                            }
                        else if (c == '/')
                            while (c != '\n' && c != CharacterIterator.DONE)
                                next();
                        else
                            {
                                previous();
                                break;
                            }
                    }
                else
                    break;
            } while (next() != CharacterIterator.DONE);
    }

    public <T> T read(CharacterIterator ci, int start) 
    {
        it = ci;
        switch (start) 
            {
            case FIRST:
                c = it.first();
                break;
            case CURRENT:
                c = it.current();
                break;
            case NEXT:
                c = it.next();
                break;
            }
        return read();
    }

    public <T> T read(CharacterIterator it) 
    {
        return read(it, NEXT);
    }

    public <T> T read(String string) 
    {
        return read(new StringCharacterIterator(string), FIRST);
    }
    public <T> T read(InputStream stream) 
        throws IOException
    {
        return read(new StreamCharacterIterator(stream), FIRST);
    }

    @SuppressWarnings("unchecked")
    private <T> T read() 
    {
        skipWhiteSpace();
        char ch = c;
        next();
        switch (ch) 
            {
            case '"': token = readString(); break;
            case '[': token = readArray(); break;
            case ']': token = ARRAY_END; break;
            case ',': token = COMMA; break;
            case '{': token = readObject(); break;
            case '}': token = OBJECT_END; break;
            case ':': token = COLON; break;
            case 't':
                if (c != 'r' || next() != 'u' || next() != 'e')
                    throw new RuntimeException("Invalid JSON token: expected 'true' keyword.");
                next();
                token = new BooleanJson(Boolean.TRUE);
                break;
            case'f':
                if (c != 'a' || next() != 'l' || next() != 's' || next() != 'e')
                    throw new RuntimeException("Invalid JSON token: expected 'false' keyword.");
                next();
                token = new BooleanJson(Boolean.FALSE);
                break;
            case 'n':
                if (c != 'u' || next() != 'l' || next() != 'l')
                    throw new RuntimeException("Invalid JSON token: expected 'null' keyword.");
                next();
                token = new NullJson();
                break;
            default:
                c = it.previous();
                if (Character.isDigit(c) || c == '-') {
                    token = readNumber();
                }
            }
        // System.out.println("token: " + token); // enable this line to see the token stream
        return (T)token;
    }
	    
    private String readObjectKey()
    {
        Object key = read();
        if (key == null)
            throw new RuntimeException(
                                       "Missing object key (don't forget to put quotes!).");
        else if (key != OBJECT_END)
            return ((Json)key).asString();
        else
            return key.toString();
    }
	    
    private Json readObject() 
    {
        Json ret = new ObjectJson();
        String key = readObjectKey();
        while (token != OBJECT_END) 
            {
                read(); // should be a colon
                if (token != OBJECT_END) 
                    {
                        Json value = read();
                        ret.set(key, value);
                        if (read() == COMMA) {
                            key = readObjectKey();
                        }
                    }
            }
        return ret;
    }

    private Json readArray() 
    {
        Json ret = new ArrayJson();
        Object value = read();
        while (token != ARRAY_END) 
            {
                ret.add((Json)value);
                if (read() == COMMA) 
                    value = read();
                else if (token != ARRAY_END)
                    throw new RuntimeException("Unexpected token in array " + token);
            }
        return ret;
    }

    private Json readNumber() 
    {
        int length = 0;
        boolean isFloatingPoint = false;
        buf.setLength(0);
	        
        if (c == '-') 
            {
                add();
            }
        length += addDigits();
        if (c == '.') 
            {
                add();
                length += addDigits();
                isFloatingPoint = true;
            }
        if (c == 'e' || c == 'E') 
            {
                add();
                if (c == '+' || c == '-') 
                    {
                        add();
                    }
                addDigits();
                isFloatingPoint = true;
            }
	 
        String s = buf.toString();
        Number n = isFloatingPoint 
            ? (length < 17) ? Double.valueOf(s) : new BigDecimal(s)
            : (length < 20) ? Long.valueOf(s) : new BigInteger(s);

        return new NumberJson(n);
    }
	 
    private int addDigits() 
    {
        int ret;
        for (ret = 0; Character.isDigit(c); ++ret) 
            {
                add();
            }
        return ret;
    }

    private Json readString() 
    {
        buf.setLength(0);
        while (c != '"') 
            {
                if (c == '\\') 
                    {
                        next();
                        if (c == 'u') 
                            {
                                add(unicode());
                            } 
                        else 
                            {
                                Object value = escapes.get(new Character(c));
                                if (value != null) 
                                    {
                                        add(((Character) value).charValue());
                                    }
                            }
                    } 
                else 
                    {
                        add();
                    }
            }
        next();
        return new StringJson(buf.toString());
    }

    private void add(char cc) 
    {
        buf.append(cc);
        next();
    }

    private void add() 
    {
        add(c);
    }

    private char unicode() 
    {
        int value = 0;
        for (int i = 0; i < 4; ++i) 
            {
                switch (next()) 
                    {
                    case '0': case '1': case '2': case '3': case '4': 
                    case '5': case '6': case '7': case '8': case '9':
                        value = (value << 4) + c - '0';
                        break;
                    case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
                        value = (value << 4) + (c - 'a') + 10;
                        break;
                    case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
                        value = (value << 4) + (c - 'A') + 10;
                        break;
                    }
            }
        return (char) value;
    }
}
