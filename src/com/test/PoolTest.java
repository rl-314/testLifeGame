package com.test;

import com.game.Pool;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Classname PoolTest
 * @Date 2020/5/21 20:03
 * @Created by Jason
 */
class PoolTest {
    Pool pool;
    @BeforeEach
    void setUp() {
       pool  = new Pool(3,3);
       System.out.println("setup");
    }

    @AfterEach
    void tearDown() {
        pool = null;
    }
    /*
    * 客户端自己设置游戏窗口的大小
    * 测试用例数据集:{-1,0,10,50,1000};
    * */
    @Test
    void reSize() {
        System.out.println("*********************开始测试reSize()方法*******************");
        int [] test = {-1,0,10,15,1000};
        for (int i = 0 ; i < 5; i ++ ) {
            pool.reSize(test[i]);
            switch (i){
                case 0:
                case 1:
                case 4: {
                    Assert.assertEquals(5, pool.get_Size());
                    break;
                }
                case 2:{
                    Assert.assertEquals(10, pool.get_Size());
                    break;
                }
                case 3:{
                    Assert.assertEquals(15, pool.get_Size());
                    break;
                }
            }
        }
        System.out.println("***********************测试通过***************************");
    }
    /*
    * 游戏池中的速度参数修改测试
    * */
    @Test
    void changeSpeedSlow() {
        System.out.println("**************开始测试changeSpeedSlow()方法**************");
        pool.changeSpeedSlow();
        Assert.assertEquals(80, pool.getSpeed());
        System.out.println("***********************测试通过***************************");
    }

    @Test
    void changeSpeedFast() {
        System.out.println("**************开始测试changeSpeedFast()方法**************");
        pool.changeSpeedFast();
        Assert.assertEquals(30, pool.getSpeed());
        System.out.println("***********************测试通过***************************");
    }

    @Test
    void changeSpeedHyper() {
        System.out.println("**************开始测试changeSpeedHyper()方法**************");
        pool.changeSpeedHyper();
        Assert.assertEquals(1, pool.getSpeed());
        System.out.println("***********************测试通过***************************");
    }
    /*
    * 测试初始状态的setInit_2()方法
    * */
    @Test
    void setInit_2() {
        System.out.println("**************开始测试setInit_2()方法**************");
        int [] x = {
                    1,
                    2,2,
                    3,3,3,3,3,3,
                    4,4,4,4,4,4,
                    5,5,5,5,5,5,
                    6,6,6,6,6,6,6,6,
                    7,7,7,
                    8,8,
                    9,9
        };
        int [] y = {
                    25,25,23,13,14,21,22,35,36,12,
                    16,21,22,35,36,1,2,11,17,21,
                    22,1,2,11,15,17,18,23,25,11,17,
                    25,12,16,13,14
        };
        pool.setInit_2();
        int [][] shape = pool.getShape();
        for (int  i = 0  ; i < x.length ; i ++ ) {
            System.out.println("pass:" +  x[i] + " , " + y[i] );
            Assert.assertEquals(1,shape[x[i]][y[i]]);
        }
        System.out.println("***********************测试通过***************************");
    }
    /*
     * 测试初始状态的setInit_1()方法
     * */
    @Test
    void setInit_1() {
        System.out.println("**************开始测试setInit_1()方法**************");
        int [] x = {
                4,5,5,6,6
        };
        int [] y = {
                4,5,6,4,5
        };
        pool.setInit_1();
        int [][] shape = pool.getShape();
        for (int i = 0 ; i < x.length; i ++ ) {
            System.out.println("pass:" +  x[i] + " , " + y[i] + "-" + shape[x[i]][y[i]]);
            Assert.assertEquals(1,shape[x[i]][y[i]]);
        }
        System.out.println("***********************测试通过***************************");
    }
    /*
     * 测试初始状态的isVaildCell()方法
     * */
    @Test
    void isVaildCell(){
        System.out.println("**************开始测试isVaildCell()方法**************");
        int [] x = {-1,0,2,3,5,150,151,7000};
        int [] y = {-1,0,2,3,5,300,301,7000};
        for (int item : x) {
            for (int value : y) {
                boolean flag = (item >= 0) && (item < pool.getRows()) && (value >= 0) && (value < pool.getColumns());
                Assert.assertEquals(flag, pool.isVaildCell(item, value));
            }
        }
        System.out.println("***********************测试通过***************************");
    }
    /*
     * 测试初始状态的setStop()方法
     * */
    @Test
    void setStop(){
        System.out.println("**************开始测试setStop()方法**************");
        boolean flag = false;
        pool.setRandom();
        int [][] shape = pool.getShape();
        for (int[] ints : shape) {
            for (int j = 0; j < shape[0].length; j++) {
                if (ints[j] == 1) {
                    flag = true;
                    break;
                }
            }
        }
        Assert.assertTrue(flag);
        System.out.println("********************测试阶段一通过************************");
        pool.setStop();
        shape = pool.getShape();
        for (int[] ints : shape) {
            for (int j = 0; j < shape[0].length; j++) {
                Assert.assertEquals(0, ints[j]);
            }
        }
        System.out.println("***********************测试通过***************************");
    }


    @Test
    void testTrans(){
        int [][] generation = new int[pool.getRows()][pool.getColumns()];
        int [][] generation1 = new int[pool.getRows()][pool.getColumns()];
        for (int i = 0 ; i < generation.length ; i++ ) {
            for (int j = 0 ; j < generation[0].length ; j++ ) {
                generation[i][j] = 1;
            }
        }
        pool.transfrom(generation,generation1);
        for (int i = 0 ; i < generation.length ; i++ ) {
            for (int j = 0 ; j < generation[0].length ; j++ ) {
                Assert.assertEquals(generation[i][j],generation1[i][j]);//NOPMD
            }
        }
        System.out.println("测试通过");
    }



    @Test
    void evolve() {
        System.out.println("**************开始测试evolve()方法**************");
       int [][] testMap = {
               {0,0,0, 1,0,1, 1,0,1, 0,0,0, 1,0,0, 1,0,0, 1,1,1},
               {0,0,0, 0,0,0, 1,0,0, 0,1,0, 1,1,0, 0,1,0, 1,1,0},
               {0,0,0, 0,1,0, 1,1,0, 0,0,0, 0,0,0, 1,1,0, 1,0,0}
       };
       pool.setCurrentGeneration(testMap);
       for (int i = 1 ; i < 21 ; i += 3 ) {
           pool.evolve(1,i);
           switch (i){
               case 4:
               case 13:
               case 16 :{
                   System.out.println("测试用例" + (i+2)/3 + ":下一代状态为 1 ，实际为：next=" + pool.getNextGeneration()[1][i] + ",curr=" +
                           pool.getCurrentGeneration()[1][i]);
                   Assert.assertEquals(1,pool.getNextGeneration()[1][i]);
                   break;
               }
               default:{
                   System.out.println("测试用例" + (i+2)/3 + ":下一代状态为 0 ，实际为：next=" + pool.getNextGeneration()[1][i] + ",curr=" +
                           pool.getCurrentGeneration()[1][i]);
                   Assert.assertEquals(0,pool.getNextGeneration()[1][i]);
                   break;
               }
           }
       }
       System.out.println("***********************测试通过***************************");
    }
}