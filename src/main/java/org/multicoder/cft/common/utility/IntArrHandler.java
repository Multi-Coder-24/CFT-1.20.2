package org.multicoder.cft.common.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntArrHandler
{
    public static int[] Append(int[] Input,int TA)
    {
        List<Integer> Nums = new ArrayList<>();
        Arrays.stream(Input).forEach(i ->{
            Nums.add(i);
        });
        Nums.add(TA);
        int[] ToReturn = Nums.stream().mapToInt(Integer::intValue).toArray();
        return ToReturn;
    }
}
