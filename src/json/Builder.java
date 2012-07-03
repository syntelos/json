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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <h3>Methods</h3>
 * 
 * <p> An implementor returns JSON class instance from toJson method,
 * and may throw an unsupported operation exception from the fromJson
 * method. </p>
 * 
 * <h3>Constructor</h3>
 * 
 * <p> An implementor must always define a public constructor with one
 * Json parameter. </p>
 * 
 * @author John Pritchard
 */		
public interface Builder
{
    /**
     * Exception thrown by {@link Builder#fromJson} to indicate the
     * use of a constructor.
     */
    public static class Immutable
        extends UnsupportedOperationException
    {
        public Immutable(){
            super();
        }

        public final static <T extends Builder> T Construct(Class<T> clas, Json model){
            try {
                Constructor<T> ctor = clas.getConstructor(Json.class);

                return ctor.newInstance(model);
            }
            catch (NoSuchMethodException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (SecurityException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (InstantiationException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (IllegalAccessException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (IllegalArgumentException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (InvocationTargetException exc){

                Throwable target = exc.getCause();
                if (null != target)
                    throw new IllegalStateException(clas.getName(),target);
                else
                    throw new IllegalStateException(clas.getName(),exc);
            }
        }
        public final static <T> T Construct(Class<T> clas, String model){
            if (java.lang.Enum.class.isAssignableFrom(clas)){
                try {
                    Method valueOf = clas.getMethod("valueOf",String.class);

                    return (T)valueOf.invoke(null,model);
                }
                catch (NoSuchMethodException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (SecurityException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (IllegalAccessException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (IllegalArgumentException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (InvocationTargetException exc){

                    Throwable target = exc.getCause();
                    if (null != target)
                        throw new IllegalStateException(clas.getName(),target);
                    else
                        throw new IllegalStateException(clas.getName(),exc);
                }
            }
            else {
                try {
                    Constructor<T> ctor = clas.getConstructor(String.class);

                    return ctor.newInstance(model);
                }
                catch (NoSuchMethodException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (SecurityException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (InstantiationException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (IllegalAccessException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (IllegalArgumentException exc){

                    throw new IllegalStateException(clas.getName(),exc);
                }
                catch (InvocationTargetException exc){

                    Throwable target = exc.getCause();
                    if (null != target)
                        throw new IllegalStateException(clas.getName(),target);
                    else
                        throw new IllegalStateException(clas.getName(),exc);
                }
            }
        }
        public final static <T> T Construct(Class<T> clas, Object model){
            try {
                Constructor<T> ctor = clas.getConstructor(model.getClass());

                return ctor.newInstance(model);
            }
            catch (NoSuchMethodException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (SecurityException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (InstantiationException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (IllegalAccessException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (IllegalArgumentException exc){

                throw new IllegalStateException(clas.getName(),exc);
            }
            catch (InvocationTargetException exc){

                Throwable target = exc.getCause();
                if (null != target)
                    throw new IllegalStateException(clas.getName(),target);
                else
                    throw new IllegalStateException(clas.getName(),exc);
            }
        }
    }


    /**
     * @return Model of this
     */
    public Json toJson();
    /**
     * @param thisModel Model of this
     * @return Modified
     * @throws Immutable To indicate the use of a constructor
     */
    public boolean fromJson(Json thisModel);
}
