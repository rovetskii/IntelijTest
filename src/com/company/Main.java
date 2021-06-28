package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Test {
    String name;
    int age;

    public Test(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


public class Main {

    public static void main(String[] args) {
	//System.out.println("Hello");
        List<Test> testList = new ArrayList<>();
        testList.add(new Test("ivan", 33));
        testList.add(new Test("sergey", 44));

       // testList.stream().filter(e->e.getAge() < 40).map(e->e.getName().toUpperCase()).forEach(System.out::println);
        List<String> list= testList.stream().map(e->e.getName().toUpperCase()).sorted((e1,e2)-> e1.compareTo(e2)).collect(Collectors.toList());
        list.forEach(System.out::println);

    }
}
