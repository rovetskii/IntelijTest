package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
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
class task {
    public static Integer getTask(int n)  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2*n;
    }

    public static Supplier<Integer> getFirstTask() {
        return () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        };
    }

    public static Function<Integer, Integer> getSecondTask() {
        return (res) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return res * 10;
        };
    }

    public static Consumer<Integer> getThirdTask() {
        return res -> System.out.println("res=" + res);
    }

    public static CompletableFuture<Integer> create (int n, ForkJoinPool pool) {
        return CompletableFuture.supplyAsync(()->getTask(n));
    }

}



public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
	//System.out.println("Hello");
        List<Test> testList = new ArrayList<>();
        testList.add(new Test("ivan", 33));
        testList.add(new Test("sergey", 44));

       // testList.stream().filter(e->e.getAge() < 40).map(e->e.getName().toUpperCase()).forEach(System.out::println);
        List<String> list= testList.stream().map(e->e.getName().toUpperCase()).sorted((e1,e2)-> e1.compareTo(e2)).collect(Collectors.toList());
        list.forEach(System.out::println);

        //Executor pool = Executors.newFixedThreadPool(4);
        ForkJoinPool pool = new ForkJoinPool();
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(task.getFirstTask(), pool)
                .thenApply(task.getSecondTask()).thenAccept(task.getThirdTask());

        long start = System.currentTimeMillis();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(task.getFirstTask(), pool);
        CompletableFuture<Integer> future2= CompletableFuture.supplyAsync(task.getFirstTask(), pool);
        CompletableFuture<Integer> future3 = future1.thenCombine(future2,(a, b)-> a*b);



        future3.join();
        System.out.println("res="+future3.get());

//        CompletableFuture<Void> future = task.create(10, pool).thenCompose(r->task.create(r, pool)).thenAccept(System.out::println);
//        future.join();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
