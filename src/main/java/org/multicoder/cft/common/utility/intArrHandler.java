package org.multicoder.cft.common.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class intArrHandler
{
    public static int[] addIntToArray(int[] Input, int TA)
    {
        List<Integer> numbers = new ArrayList<>();
        Arrays.stream(Input).forEach(i ->{
            numbers.add(i);
        });
        numbers.add(TA);
        int[] ToReturn = numbers.stream().mapToInt(Integer::intValue).toArray();
        return ToReturn;
    }
}
