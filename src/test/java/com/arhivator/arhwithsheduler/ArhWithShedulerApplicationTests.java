package com.arhivator.arhwithsheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ArhWithShedulerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        List<String> listFirst = new ArrayList<>();
        listFirst.add("White");
        listFirst.add("Black");
        listFirst.add("Red");

        List<String> listSecond = new ArrayList<>();

        listSecond.add("Green");
        listSecond.add("Red");
        listSecond.add("White");


//        listFirst.retainAll(listSecond);


        listFirst.removeAll(listSecond);
        System.out.println(listFirst);
    }

}
