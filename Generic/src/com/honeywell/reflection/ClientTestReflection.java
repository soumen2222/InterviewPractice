package com.honeywell.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClientTestReflection {

	public static void main(String[] args) {
    TestReflection objectInstance = new TestReflection();
	 Class testReflectionCalss = TestReflection.class;
	 Field[] fields = testReflectionCalss.getDeclaredFields();
	 try {
		Field intvalue = testReflectionCalss.getDeclaredField("integervalue");
		
		intvalue.setAccessible(true);
		intvalue.set(objectInstance, 5);		
		System.out.println("Modified integer value is " + intvalue.get(objectInstance));
		
	} catch (NoSuchFieldException | SecurityException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}catch (IllegalArgumentException | IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 Method[] methods = testReflectionCalss.getDeclaredMethods();
	 Constructor[] constructors = testReflectionCalss.getConstructors();
	 
	 for (Constructor constructor : constructors) {
		 System.out.println(constructor.getName());
	}
	  try {
		  Constructor constructor = TestReflection.class.getConstructor(String.class);
		TestReflection myObject = (TestReflection)constructor.newInstance("constructor-arg1");
		System.out.println("Value of the object" + myObject.getStringvalue());
		
	} catch (NoSuchMethodException | SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (InstantiationException | IllegalAccessException | IllegalArgumentException
			| InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 
	 System.out.println(testReflectionCalss.getPackage());
	 
	 for (Field field : fields) {
		 
		try {
			field.setAccessible(true);
			field.set(objectInstance, field.get(objectInstance));
			System.out.println(field.get(objectInstance));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(field.getName()+"  "+field.getModifiers()+"  "+ field.getType()+ "   Is modifier Public   " +Modifier.isPublic(field.getModifiers()));
	}
	 
	 for (Method method : methods) {
		 System.out.println(method.getName()+method.getModifiers()+ "Is modifier Public  " +Modifier.isPublic(method.getModifiers()));
	}
	 
	 printGettersSetters(TestReflection.class);
	 
	 Method method;
	try {
		method = TestReflection.class.getMethod("getStringvalues", null);
		Type returnType = method.getGenericReturnType();
		if(returnType instanceof ParameterizedType){
		    ParameterizedType type = (ParameterizedType) returnType;
		    Type[] typeArguments = type.getActualTypeArguments();
		    for(Type typeArgument : typeArguments){
		        Class typeArgClass = (Class) typeArgument;
		        System.out.println("typeArgClass = " + typeArgClass);
		    }
		}
		
		Type[] genericParameterTypes = method.getGenericParameterTypes();

		for(Type genericParameterType : genericParameterTypes){
		    if(genericParameterType instanceof ParameterizedType){
		        ParameterizedType aType = (ParameterizedType) genericParameterType;
		        Type[] parameterArgTypes = aType.getActualTypeArguments();
		        for(Type parameterArgType : parameterArgTypes){
		            Class parameterArgClass = (Class) parameterArgType;
		            System.out.println("parameterArgClass = " + parameterArgClass);
		        }
		    }
		}
		
		
		InvocationHandler handler = new MyInvocationHandler();
		MyInterface proxy = (MyInterface) Proxy.newProxyInstance(
		                            MyInterface.class.getClassLoader(),
		                            new Class[] { MyInterface.class },
		                            handler);
	} catch (NoSuchMethodException | SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	 
	}
	
	public static void printGettersSetters(Class aClass){
		  Method[] methods = aClass.getMethods();

		  for(Method method : methods){
		    if(isGetter(method)) System.out.println("getter: " + method);
		    if(isSetter(method)) System.out.println("setter: " + method);
		  }
		}

		public static boolean isGetter(Method method){
		  if(!method.getName().startsWith("get"))      return false;
		  if(method.getParameterTypes().length != 0)   return false;  
		  if(void.class.equals(method.getReturnType())) return false;
		  return true;
		}

		public static boolean isSetter(Method method){
		  if(!method.getName().startsWith("set")) return false;
		  if(method.getParameterTypes().length != 1) return false;
		  return true;
		}

}
