package org.tea;

import org.junit.Test;

public class TT {

    @Test
    public void sum() {
        long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++)
            sum += i;
        System.out.println(sum);
    }
}
