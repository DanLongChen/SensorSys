package com.sensor.Ford_Fulkerson;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

/**
 * Created by Paser on 2019/3/10.
 */
public class NetWorkTest {
    public static void main(String[] args) {
        int numE=8;
        DenseMatrix64F A=new DenseMatrix64F(3,8);
        A.set(0,0,(Math.random()+0.1)*10000);
        A.set(0,1,(Math.random()+0.1)*10000);
        A.set(0,2,(Math.random()+0.1)*10000);
        A.set(0,3,0);
        A.set(0,4,0);
        A.set(0,5,0);
        A.set(0,6,0);
        A.set(0,7,0);
        A.set(1,0,(Math.random()+0.1)*10000);
        A.set(1,1,(Math.random()+0.1)*10000);
        A.set(1,2,(Math.random()+0.1)*10000);
        A.set(1,3,0);
        A.set(1,4,0);
        A.set(1,5,0);
        A.set(1,6,0);
        A.set(1,7,0);
        A.set(2,0,0);
        A.set(2,1,0);
        A.set(2,2,0);
        A.set(2,3,(Math.random()+0.1)*10000);
        A.set(2,4,(Math.random()+0.1)*10000);
        A.set(2,5,0);
        A.set(2,6,0);
        A.set(2,7,0);
        DenseMatrix64F B=new DenseMatrix64F(3,8);
        B.set(0,0,0);
        B.set(0,1,0);
        B.set(0,2,(Math.random()+0.1)*10000);
        B.set(0,3,0);
        B.set(0,4,0);
        B.set(0,5,(Math.random()+0.1)*10000);
        B.set(0,6,0);
        B.set(0,7,(Math.random()+0.1)*10000);
        B.set(1,0,0);
        B.set(1,1,0);
        B.set(1,2,0);
        B.set(1,3,0);
        B.set(1,4,(Math.random()+0.1)*10000);
        B.set(1,5,0);
        B.set(1,6,(Math.random()+0.1)*10000);
        B.set(1,7,0);
        B.set(2,0,0);
        B.set(2,1,0);
        B.set(2,2,0);
        B.set(2,3,0);
        B.set(2,4,(Math.random()+0.1)*10000);
        B.set(2,5,0);
        B.set(2,6,(Math.random()+0.1)*10000);
        B.set(2,7,0);

        DenseMatrix64F F=new DenseMatrix64F(numE,numE);
        F.set(0,0,0);
        F.set(0,1,(Math.random()+0.1)*10000);
        F.set(0,2,0);
        F.set(0,3,0);
        F.set(0,4,(Math.random()+0.1)*10000);
        F.set(0,5,0);
        F.set(0,6,0);
        F.set(0,7,0);

        F.set(1,0,0);
        F.set(1,1,0);
        F.set(1,2,0);
        F.set(1,3,0);
        F.set(1,4,0);
        F.set(1,5,(Math.random()+0.1)*10000);
        F.set(1,6,(Math.random()+0.1)*10000);
        F.set(1,7,0);

        F.set(2,0,0);
        F.set(2,1,0);
        F.set(2,2,0);
        F.set(2,3,0);
        F.set(2,4,0);
        F.set(2,5,0);
        F.set(2,6,0);
        F.set(2,7,0);

        F.set(3,0,0);
        F.set(3,1,0);
        F.set(3,2,0);
        F.set(3,3,0);
        F.set(3,4,0);
        F.set(3,5,(Math.random()+0.1)*10000);
        F.set(3,6,(Math.random()+0.1)*10000);
        F.set(3,7,0);

        F.set(4,0,0);
        F.set(4,1,0);
        F.set(4,2,0);
        F.set(4,3,0);
        F.set(4,4,0);
        F.set(4,5,0);
        F.set(4,6,0);
        F.set(4,7,(Math.random()+0.1)*10000);

        F.set(5,0,0);
        F.set(5,1,0);
        F.set(5,2,0);
        F.set(5,3,0);
        F.set(5,4,0);
        F.set(5,5,0);
        F.set(5,6,0);
        F.set(5,7,0);

        F.set(6,0,0);
        F.set(6,1,0);
        F.set(6,2,0);
        F.set(6,3,0);
        F.set(6,4,0);
        F.set(6,5,0);
        F.set(6,6,0);
        F.set(6,7,(Math.random()+0.1)*10000);

        F.set(7,0,0);
        F.set(7,1,0);
        F.set(7,2,0);
        F.set(7,3,0);
        F.set(7,4,0);
        F.set(7,5,0);
        F.set(7,6,0);
        F.set(7,7,0);

        DenseMatrix64F I= CommonOps.identity(numE,numE);//设置单位矩阵
        DenseMatrix64F sub=new DenseMatrix64F(numE,numE);//(I-F)的结果
        System.out.println("sub: ");
        CommonOps.sub(I,F, sub);
        System.out.println(sub);
        DenseMatrix64F inverse=new DenseMatrix64F(numE,numE);
        CommonOps.invert(sub,inverse);//(I-F)-1  求逆的结果
        System.out.println("inverse: ");
        System.out.println(inverse);

        System.out.println("Test: ");
        DenseMatrix64F test=new DenseMatrix64F(numE,numE);
        CommonOps.mult(sub,inverse,test);
        System.out.println(CommonOps.det(test));

        DenseMatrix64F trans=new DenseMatrix64F(numE, 3);
        CommonOps.transpose(B,trans);//求转置
        System.out.println("trans: ");
        System.out.println(trans);

        DenseMatrix64F temp=new DenseMatrix64F(3,numE);
        CommonOps.mult(A,inverse,temp);
        DenseMatrix64F result=new DenseMatrix64F(3,3);
        CommonOps.mult(temp,trans,result);
        System.out.println("result: ");
        System.out.println(result);

//        System.out.println(F.toString());
        System.out.println(CommonOps.det(result)==0);
    }
}
