package com.example.camelProject.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
//        int min = 1;
//        int max = 100;
//        int targetSize = (max - min) / gridSize + 1;

//        System.out.println("targetSize : " + targetSize);
        Map<String, ExecutionContext> result = new HashMap<>();

//        int number = 0;
//        int start = min;
//        int end = start + targetSize - 1;

        int totalRecords = 100;
        int partitionSize = totalRecords / gridSize;

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            int start = i * partitionSize;
            int end = (i + 1) * partitionSize - 1;
            context.putInt("start", start);
            context.putInt("end", end);
            result.put("partition" + i, context);
        }

        return result;

//        while(start <= max){
//            ExecutionContext value = new ExecutionContext();
//            result.put("partition : " + number, value);
//
//            if(end >= max){
//                end = max;
//            }
//            value.putInt("minValue", start);
//            value.putInt("maxValue", end);
//
//            start += targetSize;
//            end += targetSize;
//            number++;
//        }
//        System.out.println("Partition result : " + result.toString());
//        return result;
    }
}
