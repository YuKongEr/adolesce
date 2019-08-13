package com.yukong.graphalgorithm._3_Dijkstras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yukong
 * @date 2019-05-16 10:14
 */
public class FindLowestCostNode {

    static   List<String>  process = new ArrayList<>();

    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>>  graph = new HashMap<>();
        // 起点
        HashMap<String, Integer> startNear = new HashMap<>();
        startNear.put("a", 6);
        startNear.put("b", 2);
        graph.put("start", startNear);
        // a节点
        HashMap<String, Integer> aNear = new HashMap<>();
        aNear.put("fin", 1);
        graph.put("a", aNear);

        // b节点
        HashMap<String, Integer> bNear = new HashMap<>();
        bNear.put("a", 3);
        bNear.put("fin", 5);
        graph.put("b", bNear);

        // 终点
        HashMap<String, Integer> finNear = new HashMap<>();
        graph.put("fin", finNear);

        // 到每个节点的花费时间
        HashMap<String, Integer> costs = new HashMap<>();
        costs.put("a", 6);
        costs.put("b", 2);
        costs.put("fin", Integer.MAX_VALUE);


        HashMap<String, String> parent = new HashMap<>();
        parent.put("a", "start");
        parent.put("b", "start");
        parent.put("fin", null);

        // 获取未处理的节点中 权重最小的节点
        String node = findLowestCostNode(costs);
        while (node != null) {
            // 获取最小的权重
            Integer cost = costs.get(node);
            // 获取该节点的相邻节点
            HashMap<String, Integer> neighbors = graph.get(node);
            // 遍历相邻节点
            for (String n : neighbors.keySet()) {

                Integer newCost = cost + neighbors.get(n);
                // 如果相邻节点新路径权重更小
                if(costs.getOrDefault(n, 0) > newCost ){
                    // 更新权重花费
                    costs.put(n, newCost);
                    parent.put(n, node);
                }
            }
            process.add(node);
            node = findLowestCostNode(costs);
        }

        System.out.println(costs);
    }

    private static String findLowestCostNode(HashMap<String, Integer> costs) {
        String lowestNodeName = null;
        Integer lowestNodeCost = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> entry : costs.entrySet()) {
            if(entry.getValue() < lowestNodeCost && !process.contains(entry.getKey())) {
                lowestNodeName = entry.getKey();
                lowestNodeCost = entry.getValue();
            }
        }
        return lowestNodeName;
    }

}
