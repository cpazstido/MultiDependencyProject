package com.cf;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println(""+InterfaceA.class.getSuperclass());
        System.out.println(""+InterfaceB.class.getSuperclass());

        System.out.println(""+InterfaceA.class.getGenericSuperclass());
        System.out.println(""+InterfaceA.class.getGenericInterfaces());

        System.out.println(""+ClassA.class.getSuperclass());
        System.out.println(""+ClassB.class.getSuperclass());

        System.out.println(""+ClassA.class.getGenericSuperclass());
        System.out.println(""+ClassA.class.getGenericInterfaces());

        System.out.println(""+ClassB.class.getGenericSuperclass());
        System.out.println(""+ClassB.class.getGenericInterfaces());

        ClassB classB = new ClassB();
        System.out.println(classB.getClass().getGenericSuperclass());
    }
}
